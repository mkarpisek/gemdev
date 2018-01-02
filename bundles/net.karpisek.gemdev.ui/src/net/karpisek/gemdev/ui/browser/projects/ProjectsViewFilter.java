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
package net.karpisek.gemdev.ui.browser.projects;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import net.karpisek.gemdev.core.resources.ProjectNature;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Filter into Common navigator framework which after application will hide all non-smalltalk projects and files.
 */
public class ProjectsViewFilter extends ViewerFilter {
	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if (element instanceof IResource) {
			final IResource r = (IResource) element;
			final IProject project = r.getProject();
			try {
				if (project == null || !project.isOpen() || project.getNature(ProjectNature.ID) == null) {
					return false;
				}
			} catch (final CoreException e) {
				GemDevUiPlugin.getDefault().logError(e);
				return false;
			}
			return true;
		}
		return false;
	}
}
