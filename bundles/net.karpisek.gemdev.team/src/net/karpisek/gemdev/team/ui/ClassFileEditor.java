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
package net.karpisek.gemdev.team.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.browser.projects.ProjectsView;
import net.karpisek.gemdev.ui.editor.IEditorLinker;

/**
 * Editor of .gsc class file.
 */
public class ClassFileEditor extends TextEditor implements IEditorLinker {

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);

		GuiUtils.activateContext(getSite());
	}

	@Override
	public void linkActivated(final ProjectsView view) {
		view.show(((FileEditorInput) getEditorInput()).getFile());
	}

}
