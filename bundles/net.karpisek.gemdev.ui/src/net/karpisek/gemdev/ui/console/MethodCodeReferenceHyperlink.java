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
package net.karpisek.gemdev.ui.console;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IHyperlink;

import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.clazz.GetMethodCategoryName;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.browser.MethodEditor;
import net.karpisek.gemdev.ui.browser.MethodEditorInput;

/**
 * {@link GsConsole} hyperlink to gemstone code of one line of stack trace.
 */
public class MethodCodeReferenceHyperlink implements IHyperlink {
	private final MethodCodeReference reference;

	public MethodCodeReferenceHyperlink(final MethodCodeReference reference) {
		this.reference = reference;
	}

	@Override
	public void linkActivated() {

		try {
			final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if (window == null) {
				return;
			}
			final IWorkbenchPage page = window.getActivePage();
			if (page == null) {
				return;
			}
			final ISession[] sessions = GemDevUiPlugin.getSessionManager().getAllSessions().toArray(new ISession[0]);
			if (sessions.length == 0) {
				return;
			}
			final ISession session = sessions[0];
			final MethodReference ref = reference.getMethodReference();

			final DbClass target = session.getCachedClass(ref.getClassName());
			if (target == null) {
				return;
			}

			final String categoryName = session.execute(new GetMethodCategoryName(ref.getClassName(), ref.isInstanceSide(), ref.getMethodName()));
			if (categoryName == null) {
				return;
			}
			final DbCategory category = (DbCategory) target.getCategory(categoryName, ref.isInstanceSide());
			if (category == null) {
				return;
			}

			final DbMethod method = (DbMethod) category.getMethod(ref.getMethodName());
			final IEditorPart part = page.openEditor(new MethodEditorInput(method), MethodEditor.EDITOR_ID);
			if (part instanceof MethodEditor) {
				final MethodEditor editor = (MethodEditor) part;
				final IDocument document = editor.getGsSourceViewer().getDocument();
				final int lineOffset = document.getLineOffset(reference.getLineNumber() - 1);
				final int lineLength = document.getLineLength(reference.getLineNumber() - 1);
				editor.setHighlightRange(lineOffset, lineLength, true);
			}
		} catch (final PartInitException e) {
			GemDevUiPlugin.getDefault().logError(e);
		} catch (final CoreException e) {
			GemDevUiPlugin.getDefault().logError(e);
		} catch (final BadLocationException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}

	}

	@Override
	public void linkEntered() {

	}

	@Override
	public void linkExited() {

	}

}
