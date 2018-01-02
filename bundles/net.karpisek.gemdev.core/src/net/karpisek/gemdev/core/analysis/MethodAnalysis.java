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
package net.karpisek.gemdev.core.analysis;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.lang.parser.GsTree;

/**
 * Base class of all method model analysis subclasses.
 */
public abstract class MethodAnalysis {
	protected final IMethodContext context;
	protected final MethodModel model;

	public MethodAnalysis(final IMethodContext context, final MethodModel model) {
		this.context = context;
		this.model = model;
	}

	public abstract List<IMarker> createProblemMarkers(IFile file);

	public IMethodContext getContext() {
		return context;
	}

	public MethodModel getModel() {
		return model;
	}

	protected IMarker createMarker(final IFile file, final GsTree t, final String message) throws CoreException {
		final IMarker m = file.createMarker(IMarker.PROBLEM);

		m.setAttribute(IMarker.LINE_NUMBER, t.getLine());
		m.setAttribute(IMarker.MESSAGE, message);
		m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_WARNING);
		m.setAttribute(IMarker.CHAR_START, t.getOffset());
		m.setAttribute(IMarker.CHAR_END, t.getOffset() + t.getText().length());
		return m;
	}
}
