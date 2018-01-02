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

import java.text.MessageFormat;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.handlers.HandlerUtil;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Delete selected class.eclipse dialog Performs abort and commit. TODO: should work also for multiple classes
 */
public class DeleteClassHandler extends AbstractHandler implements ISelectionChangedListener {
	private final ClassHierarchyView view;
	private IStructuredSelection selection;

	public DeleteClassHandler(final ClassHierarchyView view) {
		this.view = view;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final TreeNode<?> node = (TreeNode<?>) selection.getFirstElement();
		if (node != null) {
			final DbClass selectedClass = (DbClass) node.getValue();
			if (!MessageDialog.openQuestion(HandlerUtil.getActiveShell(event), Messages.CONFIRM_DELETE_TITLE,
					MessageFormat.format(Messages.DELETE_CLASS_DIALOG_MESSAGE, selectedClass.getClassName()))) {
				return null;
			}

			try {
				final DbClass superclass = selectedClass.getSuperclass();
				selectedClass.getSession().deleteClass(selectedClass);
				view.setInput(superclass);
			} catch (final CommitFailedException e) {
				view.getViewSite().getActionBars().getStatusLineManager().setErrorMessage(Messages.DELETE_CLASS_FAILED);
			}
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		return view != null && view.getInputClass() != null && !selection.isEmpty();
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		selection = (IStructuredSelection) event.getSelection();

		fireHandlerChanged(new HandlerEvent(this, true, false));
	}
}
