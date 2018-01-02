/*******************************************************************************
 * Copyright (c) 2008, 2018 Martin Karpisek.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Martin Karpisek - initial API and implementation
 *******************************************************************************/
package net.karpisek.gemdev.core.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.google.common.base.Charsets;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;

import net.karpisek.gemdev.core.CorePlugin;
import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Cached local files in one project. Local files for method source code enables reuse of resource framework and usage of file input. Also enables storing
 * problem markers locally. It is not possible to map directly selector of method to name of file, so we have to use additional mapping step. For each gemdev
 * project cache project contains lazily created folder with same name.
 */
public class MethodFileCache {
	public static final String CACHE_PROJECT_NAME = "gemdev-cache"; //$NON-NLS-1$

	public static final String MAPPING_FILE_NAME = "mappings.properties"; //$NON-NLS-1$
	public static final String SOURCES_DIR_NAME = ".src"; //$NON-NLS-1$
	public static final String ENCODING = "UTF-8"; //$NON-NLS-1$

	private final Map<String, BiMap<MethodReference, IFile>> mapping;
	private final IWorkspace workspace;

	/**
	 * Creates object for provided workspace. There should be only one cache per workspace.
	 * 
	 * @param project must be open, existing project in workspace
	 */
	public MethodFileCache(final IWorkspace workspace) {
		this.workspace = workspace;
		this.mapping = Maps.newHashMap();
	}

	/**
	 * Answers project in workspace used for caching method files. In case project does not exists creates it and/or opens it.
	 * 
	 * @param monitor used for controlling of process
	 * @return project handle
	 * @throws CoreException
	 */
	public IProject createCacheProject(final IProgressMonitor monitor) throws CoreException {
		final IProject p = getCacheProject();
		if (!p.exists()) {
			p.create(monitor);
		}
		if (!p.isOpen()) {
			p.open(monitor);
		}
		return p;
	}

	/**
	 * Creates sources folder for target gemdev project.
	 * 
	 * @param name of project for which will be this folder containing cached files. If folder does not exist it is created.
	 * @return
	 * @throws CoreException
	 */
	public IFolder createSourcesFolder(final String name) throws CoreException {
		final IProject project = createCacheProject(new NullProgressMonitor());
		final IFolder folder = project.getFolder(name);
		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		return folder;
	}

	/**
	 * Deletes all data stored in this project.
	 * 
	 * @param monitor
	 * @throws CoreException
	 */
	public void delete(final IProgressMonitor monitor) throws CoreException {
		final IProject p = getCacheProject();
		if (p.exists()) {
			p.delete(true, monitor);
		}
	}

	/**
	 * Answers project in workspace used for caching method files.
	 * 
	 * @return handle only, project can be existing/not existing
	 */
	public IProject getCacheProject() {
		return workspace.getRoot().getProject(CACHE_PROJECT_NAME);
	}

	/**
	 * Answers file which should be used for storing method source code.
	 * 
	 * @param projectName in which is method existing
	 * @return ref is reference to method which should be stored in this file
	 * @throws CoreException
	 */
	public synchronized IFile getFile(final String projectName, final MethodReference ref) throws CoreException {
		final BiMap<MethodReference, IFile> m = getMapping(projectName);
		IFile file = m.get(ref);
		if (file == null) {
			final String fileName = FilenameUtils.getFilename(ref, "gsm", Integer.toString(m.size())); //$NON-NLS-1$
			file = getFile(projectName, fileName);
			m.put(ref, file);
		}

		return file;
	}

	/**
	 * Answers mapping between method reference and corresponding file.
	 * 
	 * @param projectName of gemdev project for which is this mapping defined
	 * @return
	 * @throws CoreException
	 */
	public synchronized BiMap<MethodReference, IFile> getMapping(final String projectName) throws CoreException {
		BiMap<MethodReference, IFile> m = mapping.get(projectName);

		if (m == null) {
			final IFolder sourcesFolder = createSourcesFolder(projectName);
			sourcesFolder.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

			final IFile file = sourcesFolder.getFile(MAPPING_FILE_NAME);
			m = HashBiMap.create();
			mapping.put(projectName, m);
			if (file.exists()) {
				try (InputStream stream = file.getContents(); InputStreamReader reader = new InputStreamReader(stream, Charsets.UTF_8);) {
					List<String> lines = CharStreams.readLines(reader);
					for (final String line : lines) {
						// mappings file format is:
						// <className> ' ' <instanceOrClassSide> ' ' <methodName> ' ' <filename>
						int index = 0;
						final char separator = ' ';
						final String className = line.substring(0, index = line.indexOf(separator));
						final boolean instanceSide = "i".equals(line.substring(index + 1, index = line.indexOf(separator, index + 1))); //$NON-NLS-1$
						final String methodName = line.substring(index + 1, index = line.indexOf(separator, index + 1));
						final String fileName = line.substring(index + 1);

						m.put(new MethodReference(className, instanceSide, methodName), getFile(projectName, fileName));
					}
				} catch (final CoreException e) {
					CorePlugin.getDefault().logError(e);
				} catch (final IOException e) {
					CorePlugin.getDefault().logError(e);
				}
			}
		}

		return m;
	}

	/**
	 * Answers reference to method which is stored in the file.
	 * 
	 * @param file cached in this cache
	 * @return
	 * @throws CoreException
	 */
	public synchronized MethodReference getMethodReference(final IFile file) throws CoreException {
		final String projectName = file.getParent().getName();
		return getMapping(projectName).inverse().get(file);
	}

	/**
	 * Saves all mapping on disk. Mappings are not persisted automatically. This method should be invoked minimally when plugin is stopped.
	 */
	public synchronized void save() throws CoreException {
		for (final String projectName : mapping.keySet()) {
			final StringBuilder sb = new StringBuilder();

			final List<BiMap.Entry<MethodReference, IFile>> entries = Lists.newLinkedList();
			entries.addAll(getMapping(projectName).entrySet());
			Collections.sort(entries, new Comparator<BiMap.Entry<MethodReference, IFile>>() {
				@Override
				public int compare(final Entry<MethodReference, IFile> entry1, final Entry<MethodReference, IFile> entry2) {
					return entry1.getValue().getName().compareTo(entry2.getValue().getName());
				}
			});

			for (final BiMap.Entry<MethodReference, IFile> entry : entries) {
				sb.append(entry.getKey().getClassName());
				sb.append(" "); //$NON-NLS-1$
				sb.append(entry.getKey().isInstanceSide() ? "i" : "c"); //$NON-NLS-1$ //$NON-NLS-2$
				sb.append(" "); //$NON-NLS-1$
				sb.append(entry.getKey().getMethodName());
				sb.append(" "); //$NON-NLS-1$
				sb.append(entry.getValue().getName());
				sb.append(System.lineSeparator());
			}

			final IFile mappingFile = getFile(projectName, MAPPING_FILE_NAME);
			try {
				final InputStream stream = new ByteArrayInputStream(sb.toString().getBytes(ENCODING));
				if (mappingFile.exists()) {
					mappingFile.setContents(stream, true, true, new NullProgressMonitor());
				} else {
					mappingFile.create(stream, true, new NullProgressMonitor());
				}
			} catch (final UnsupportedEncodingException e) {
				CorePlugin.getDefault().logError(e);
			}
		}
	}

	private IFile getFile(final String projectName, final String fileName) throws CoreException {
		return createSourcesFolder(projectName).getFile(fileName);
	}
}
