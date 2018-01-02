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

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.ui.browser.categories.CategoriesView;
import net.karpisek.gemdev.ui.browser.classes.ClassHierarchyView;
import net.karpisek.gemdev.ui.browser.methods.MethodsView;
import net.karpisek.gemdev.ui.browser.projects.ProjectsView;

/**
 * Traditional smalltalk browser implementation.
 */
public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(final IPageLayout layout) {
		final String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);

		layout.createFolder("top", IPageLayout.TOP, (float) 0.3, editorArea).addView(ProjectsView.VIEW_ID); //$NON-NLS-1$
		layout.createFolder("p1", IPageLayout.RIGHT, (float) 0.25, "top").addView(ClassHierarchyView.VIEW_ID); //$NON-NLS-1$ //$NON-NLS-2$
		layout.createFolder("p2", IPageLayout.RIGHT, (float) 0.33, "p1").addView(CategoriesView.VIEW_ID); //$NON-NLS-1$ //$NON-NLS-2$
		layout.createFolder("p3", IPageLayout.RIGHT, (float) 0.5, "p2").addView(MethodsView.VIEW_ID); //$NON-NLS-1$ //$NON-NLS-2$

		for (final String viewId : Lists.newArrayList(ProjectsView.VIEW_ID, ClassHierarchyView.VIEW_ID, CategoriesView.VIEW_ID, MethodsView.VIEW_ID)) {
			layout.getViewLayout(viewId).setCloseable(false);
		}
	}

}
