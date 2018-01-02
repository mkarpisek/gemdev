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
package net.karpisek.gemdev.ui.editor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Handler for source viewers (for example in method editor and inspector view)
 */
public abstract class PrintItAbstractHandler extends InspectItAbstractHandler {
	public PrintItAbstractHandler(final IWorkbenchSite site, final ISourceViewer viewer) {
		super(site, viewer);
	}

	@Override
	public void execute(final ISession session, final String resultOopString) {
		final String result = session.execute(new PrintIt(String.format("(Object _objectForOop: %s) printString", resultOopString))); //$NON-NLS-1$

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IDocument doc = viewer.getDocument();
				final ITextSelection selection = (ITextSelection) viewer.getSelectionProvider().getSelection();
				try {
					doc.replace(selection.getOffset() + selection.getLength(), 0, result);
				} catch (final BadLocationException e1) {
					GemDevUiPlugin.getDefault().logError(e1);
				}
				selectAndReveal(selection.getOffset() + selection.getLength(), result.length());
			}
		});
	}
}
