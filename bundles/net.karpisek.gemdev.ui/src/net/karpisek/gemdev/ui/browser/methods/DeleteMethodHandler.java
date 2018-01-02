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
package net.karpisek.gemdev.ui.browser.methods;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.ui.Messages;

/**
 * Deletes selected methods. Performs abort and commit on deletion TODO: create GsAction
 */
public class DeleteMethodHandler extends AbstractHandler implements ISelectionChangedListener {
	private final MethodsView view;
	private IStructuredSelection selection;

	public DeleteMethodHandler(final MethodsView view) {
		this.view = view;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		final List<DbMethod> selectedMethods = Lists.newLinkedList();
		for (final Object o : selection.toList()) {
			if (o instanceof DbMethod) {
				selectedMethods.add((DbMethod) o);

			}
		}

		String message = ""; //$NON-NLS-1$
		if (selectedMethods.size() > 1) {
			message = MessageFormat.format(Messages.CONFIRM_DELETE_MULTIPLE_ELEMENTS_MESSAGE, selectedMethods.size());
		} else {
			message = MessageFormat.format(Messages.DELETE_METHOD_DIALOG_MESSAGE_SINGLE, selectedMethods.get(0).getName());
		}

		if (MessageDialog.openQuestion(window.getShell(), Messages.CONFIRM_DELETE_TITLE, message)) {

			try {
				final DbCategory inputCategory = view.getInputCategory();
				final ISession session = inputCategory.getSession();
				for (final DbMethod method : selectedMethods) {
					session.deleteMethod(method);
				}

				// refresh view
				view.setInput(inputCategory, null);
			} catch (final CommitFailedException e) {
				view.getViewSite().getActionBars().getStatusLineManager().setErrorMessage(Messages.DELETE_METHOD_ERROR);
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return view != null && view.getInputCategory() != null && selection != null && !selection.isEmpty();
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		selection = (IStructuredSelection) event.getSelection();

		fireHandlerChanged(new HandlerEvent(this, true, false));
	}
}
