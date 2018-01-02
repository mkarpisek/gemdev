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
package net.karpisek.gemdev.ui.editor.rewriters;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.handlers.HandlerUtil;

import net.karpisek.gemdev.ui.editor.GsEditor;

/**
 * 

 */
public class ToggleCommentHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final GsEditor editor = (GsEditor) HandlerUtil.getActiveEditorChecked(event);
		final ISelection selection = editor.getSelectionProvider().getSelection();
		if (selection instanceof ITextSelection) {
			final IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			final ToggleCommentRewriter rewriter = new ToggleCommentRewriter(document);
			rewriter.toggleComment((ITextSelection) selection);
		}
		return null;
	}
}
