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
package net.karpisek.gemdev.ui.browser.classes;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.SessionManagerListeningHandler;
import net.karpisek.gemdev.ui.browser.projects.ProjectsView;

/**
 * This handler opens dialog with all classes in all opened sessions.
 */
public class OpenClassHandler extends SessionManagerListeningHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		final List<ISession> sessions = getAllSessionsSortedByName();
		final String message = getMessage(sessions);
		final List<DbClass> items = getElements(sessions);

		final ElementListSelectionDialog dialog = createDialog(window, message, sessions, items);
		setDialogFilterFromCurrentSelection(event, sessions, dialog);
		if (dialog.open() == Window.OK) {
			final DbClass c = (DbClass) dialog.getResult()[0];

			try {
				final ProjectsView view = (ProjectsView) window.getActivePage().showView(ProjectsView.VIEW_ID);
				view.setSelection(c.getSession());

				final ClassHierarchyView view2 = (ClassHierarchyView) window.getActivePage().showView(ClassHierarchyView.VIEW_ID);
				view2.setInput(c);
			} catch (final PartInitException e) {
				GemDevUiPlugin.getDefault().logError(e);
			}
		}
		return null;
	}

	private ElementListSelectionDialog createDialog(final IWorkbenchWindow window, final String message, final List<ISession> sessions,
			final List<DbClass> elements) {
		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(), new LabelProvider() {
			@Override
			public Image getImage(final Object element) {
				return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.CLASS_ICON);
			}

			@Override
			public String getText(final Object element) {
				if (element instanceof DbClass) {
					final DbClass c = (DbClass) element;
					if (sessions.size() > 1) {
						return fullLabel(c);
					}
					return c.getClassName();
				}
				return super.getText(element);
			}
		});
		dialog.setTitle(Messages.FIND_CLASS_DIALOG_TITLE);
		dialog.setImage(GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.FIND_CLASS_ICON));
		dialog.setMessage(message);
		dialog.setElements(elements.toArray());
		return dialog;
	}

	private String fullLabel(final DbClass c) {
		return c.getClassName() + " - " + c.getSession().getProject().getName(); //$NON-NLS-1$
	}

	private List<DbClass> getElements(final List<ISession> sessions) {
		final List<DbClass> result = Lists.newLinkedList();
		if (sessions.size() <= 0) {
			return Lists.newArrayList();
		}
		for (final ISession s : sessions) {
			result.addAll(s.getCachedClasses());
		}

		if (sessions.size() > 1) {
			Collections.sort(result, new Comparator<DbClass>() {
				@Override
				public int compare(final DbClass c1, final DbClass c2) {
					return fullLabel(c1).compareTo(fullLabel(c2));
				}
			});
		} else {
			Collections.sort(result, new Comparator<DbClass>() {
				@Override
				public int compare(final DbClass c1, final DbClass c2) {
					return c1.getClassName().compareTo(c2.getClassName());
				}
			});
		}
		return result;
	}

	/**
	 * In case in some active editor is selected text, which is name of class in any open session set is as predefined filter in dialog.
	 */
	private void setDialogFilterFromCurrentSelection(final ExecutionEvent event, final List<ISession> sessions, final ElementListSelectionDialog dialog) {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (editor == null || editor.getEditorSite() == null || editor.getEditorSite().getSelectionProvider() == null) {
			return;
		}
		final ISelection selection = editor.getEditorSite().getSelectionProvider().getSelection();
		if (selection instanceof ITextSelection) {
			boolean found = true;
			final String text = ((ITextSelection) selection).getText();
			for (final ISession session : sessions) {
				found = found && session.getCachedClass(text) != null;
			}
			if (found) {
				dialog.setFilter(text);
			}
		}
	}
}
