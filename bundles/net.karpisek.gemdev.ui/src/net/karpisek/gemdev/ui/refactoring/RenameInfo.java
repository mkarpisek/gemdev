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
package net.karpisek.gemdev.ui.refactoring;

import java.text.MessageFormat;

import org.eclipse.jface.text.IDocument;

import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.LocalIdentifier;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.ui.Messages;

/**

 */
public class RenameInfo {
	protected IDocument document;
	protected MethodModel model;
	protected Element selectedElement;

	protected String newName;

	public RenameInfo(final IDocument document, final MethodModel model, final Element selectedElement) {
		this.document = document;
		this.model = model;
		this.selectedElement = selectedElement;

		this.newName = ""; //$NON-NLS-1$
	}

	public String getDescription() {
		if (selectedElement instanceof LocalIdentifier) {
			return MessageFormat.format(Messages.RENAME_LOCAL_IDENTIFIER_TITLE, ((LocalIdentifier) selectedElement).getType().getLabel(),
					selectedElement.getName());
		}

		return MessageFormat.format(Messages.RENAME_ELEMENT_TITLE, selectedElement.getName());
	}

	public IDocument getDocument() {
		return document;
	}

	public MethodModel getModel() {
		return model;
	}

	public String getNewName() {
		return newName;
	}

	public String getOldName() {
		return selectedElement.getName();
	}

	public Element getSelectedElement() {
		return selectedElement;
	}

	public void setNewName(final String newName) {
		this.newName = newName;
	}
}
