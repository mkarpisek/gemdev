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
package net.karpisek.gemdev.ui.browser;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.part.FileEditorInput;

import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Editor input for in-memory editing of DB methods.
 */
public class MethodEditorInput extends FileEditorInput {
	private final DbMethod method;

	public MethodEditorInput(final DbMethod method) throws CoreException {
		super(method.getFile());
		this.method = method;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof MethodEditorInput) {
			final MethodEditorInput other = (MethodEditorInput) obj;
			return getMethod().equals(other.getMethod());
		}
		return false;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return GemDevUiPlugin.getDefault().getImageDescriptor(method.isInstanceSide() ? GemDevUiPlugin.INSTANCE_METHOD_ICON : GemDevUiPlugin.CLASS_METHOD_ICON);
	}

	public DbMethod getMethod() {
		return method;
	}

	@Override
	public String getName() {
		return String.format("%s%s#%s", method.getBehavior().getClassName(), method.isInstanceSide() ? "" : " class", method.getName()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return getName();
	}
}
