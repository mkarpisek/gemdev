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
package net.karpisek.gemdev.team;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.team.model.LocalClass;

/**
 * Working copy is stored as files in project file system. For each local class there will be workspace file in path project/'src'/categoryName/className'.gsc'.
 */
public class WorkingCopy {
	/**
	 * File extension for storing {@link LocalClass} into file.
	 */
	public static final String FILE_EXTENSION = "gsc"; //$NON-NLS-1$

	private final IProject project;
	private final IFolder sourceFolder;

	public WorkingCopy(final IProject project) {
		this.project = project;
		this.sourceFolder = project.getFolder("src"); //$NON-NLS-1$
	}

	/**
	 * Answer handle to sourceFolder which should represent the class. File does not necessarily exists.
	 * 
	 * @param c is class for which should be file related
	 * @return file handle
	 */
	public IFile getFile(final LocalClass c) {
		return getSourceFolder().getFolder(c.getCategoryName()).getFile(c.getName() + "." + FILE_EXTENSION); //$NON-NLS-1$
	}

	public IProject getProject() {
		return project;
	}

	/**
	 * @return handle to root sourceFolder of working copy
	 */
	public IFolder getSourceFolder() {
		return sourceFolder;
	}

	public LocalClass read(final String className) throws CoreException {
		final List<IFile> files = collectFiles(getSourceFolder(), className + "." + FILE_EXTENSION); //$NON-NLS-1$
		if (files.isEmpty()) {
			return null;
		}
		if (files.size() == 1) {
			final IFile file = files.get(0);

			try (InputStream is = file.getContents()) {
				return LocalClass.read(this, new InputStreamReader(is, "UTF-8")); //$NON-NLS-1$
			} catch (final UnsupportedEncodingException e) {
				TeamPlugin.getDefault().logError(e);
			} catch (final IOException e) {
				throw new CoreException(new Status(IStatus.ERROR, TeamPlugin.PLUGIN_ID, "Error loading file " + file.getLocation(), e)); //$NON-NLS-1$
			}
		}

		throw new RuntimeException(String.format("Found more than 1 files for class %s %s", className, files)); //$NON-NLS-1$
	}

	/**
	 * Write the class into file in working copy. If file exists overwrite it. If class file is in different category, move the file.
	 * 
	 * @param monitor
	 * @param c
	 * @throws CoreException
	 */
	public void write(final IProgressMonitor monitor, final LocalClass c) throws CoreException {
		mkdir(monitor, c.getCategoryName());

		final IFile file = getFile(c);
		final List<IFile> existingFiles = collectFiles(getSourceFolder(), file.getName());
		if (existingFiles.size() == 1) {
			final IFile oldFile = existingFiles.get(0);
			if (!Objects.equal(file.getLocation(), oldFile.getLocation())) {
				oldFile.delete(true, monitor);
			}
		} else {
			if (existingFiles.size() > 1) {
				throw new RuntimeException(String.format("Found more than 1 files for class %s %s", c.getName(), existingFiles)); //$NON-NLS-1$
			}
		}

		try {
			final byte[] contents = c.toString().getBytes("UTF-8"); //$NON-NLS-1$
			try (ByteArrayInputStream is = new ByteArrayInputStream(contents)) {
				if (file.exists()) {
					file.setContents(is, IResource.FORCE, monitor);
				} else {
					file.create(is, true, monitor);
				}
			}
		} catch (final IOException e) {
			TeamPlugin.getDefault().logError(e);
		}
	}

	private List<IFile> collectFiles(final IFolder folder, final String filename) throws CoreException {
		final List<IFile> files = Lists.newLinkedList();
		if (!folder.exists()) {
			return files;
		}

		for (final IResource r : folder.members()) {
			if (r instanceof IFolder) {
				files.addAll(collectFiles((IFolder) r, filename));
			}
			if (r instanceof IFile) {
				final IFile file = (IFile) r;
				if (filename.equals(file.getName())) {
					files.add(file);
				}
			}
		}
		return files;
	}

	private void mkdir(final IProgressMonitor monitor, final String categoryName) throws CoreException {
		// create first root sourceFolder of working copy
		if (!sourceFolder.exists()) {
			sourceFolder.create(true, true, monitor);
		}

		// create sourceFolder for system category
		final IFolder categoryFolder = sourceFolder.getFolder(categoryName);
		if (!(categoryFolder.exists())) {
			categoryFolder.create(true, true, monitor);
		}
	}
}
