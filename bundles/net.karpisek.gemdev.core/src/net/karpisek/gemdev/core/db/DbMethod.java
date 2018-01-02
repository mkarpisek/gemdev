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
package net.karpisek.gemdev.core.db;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.io.CharStreams;

import net.karpisek.gemdev.core.CorePlugin;
import net.karpisek.gemdev.core.Messages;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.method.GetMethodSourceCode;
import net.karpisek.gemdev.net.actions.method.GetMethodStamp;

/**
 * Representation of method of DB class.
 */
public class DbMethod implements IMethod, IAdaptable {
	private static final String ENCODING = "UTF-8"; //$NON-NLS-1$

	private final String name;
	private final DbCategory category;

	public DbMethod(final String name, final DbCategory category) {
		this.name = name;
		this.category = category;
	}

	@Override
	public MethodReference asReference() {
		return new MethodReference(getBehavior().getClassName(), isInstanceSide(), getName());
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof DbMethod) {
			final DbMethod other = (DbMethod) obj;
			return Objects.equal(getName(), other.getName()) && Objects.equal(isInstanceSide(), other.isInstanceSide())
					&& Objects.equal(getBehavior(), other.getBehavior());
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object getAdapter(final java.lang.Class adapter) {
		return null;
	}

	@Override
	public DbBehavior getBehavior() {
		return getCategory().getBehavior();
	}

	@Override
	public DbCategory getCategory() {
		return category;
	}

	@Override
	public String getDescription() {
		return String.format("%s#%s", getBehavior().getName(), getName()); //$NON-NLS-1$
	}

	/**
	 * Creates file for method source code in workspace. If file already existed and has different contents than it is overwritten.
	 * 
	 * @return local file with method source code
	 * @throws CoreException
	 */
	public IFile getFile() throws CoreException {
		final IFile file = getSession().getMethodFile(new MethodReference(getBehavior().getClassName(), isInstanceSide(), getName()));
		try {
			final String sourceCode = getSourceCode();
			final String fileContents = getFileContents(file);

			final InputStream stream = new ByteArrayInputStream(sourceCode.getBytes(ENCODING));
			if (file.exists()) {
				if (!sourceCode.equals(fileContents)) {
					file.setContents(stream, true, true, new NullProgressMonitor());
				}
			} else {
				file.create(stream, true, new NullProgressMonitor());
			}
		} catch (final UnsupportedEncodingException e) {
			CorePlugin.getDefault().logError(e);
		}
		return file;
	}

	@Override
	public String getFullDescription() {
		String projectName = Messages.UNKNOWN_PROJECT;
		if (getSession() != null) {
			projectName = getSession().getProject().getName();
		}

		return String.format("%s [%s] @ %s", getDescription(), getCategory().getName(), projectName); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return name;
	}

	public ISession getSession() {
		return getBehavior().getSession();
	}

	@Override
	public String getSourceCode() {
		final String src = getSession().execute(new GetMethodSourceCode(getBehavior().getClassName(), isInstanceSide(), getName()));
		// normalize line endings to client native EOL
		// probably not fastest possible
		// TODO: do normalization of EOL somehow differently? - team plugin depends on consistent EOL
		// in addition ensure that source code ENDS with EOL
		final StringWriter sw = new StringWriter();
		final StringReader reader = new StringReader(src);
		final PrintWriter writer = new PrintWriter(sw);
		try {
			List<String> lines = CharStreams.readLines(reader);
			for (String line : lines) {
				writer.println(line);
			}
		} catch (IOException e) {
			CorePlugin.getDefault().logError(e);
		}
		return sw.toString();
	}

	public String getStamp() {
		final String stamp = getSession().execute(new GetMethodStamp(getBehavior().getClassName(), isInstanceSide(), getName()));
		if (stamp != null) {
			return stamp;
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getName(), isInstanceSide(), getBehavior());
	}

	@Override
	public boolean isInstanceSide() {
		return getBehavior().isInstanceSide();
	}

	@Override
	public String toString() {
		return getFullDescription();
	}

	private String getFileContents(final IFile file) throws CoreException {
		if (!file.exists()) {
			return null;
		}
		try (InputStream stream = file.getContents()) {
			return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
		} catch (final IOException e) {
			CorePlugin.getDefault().logError(e);
		}
		return null;

	}
}
