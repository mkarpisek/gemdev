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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.handlers.HandlerUtil;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Common browser views handler for showing properties dialog for class or method.
 */
public class PropertiesHandler extends AbstractHandler implements ISelectionChangedListener {
	private IStructuredSelection selection;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Object element = selection.getFirstElement();
		if (element != null) {
			final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
			if (element instanceof TreeNode) {
				final DbClass value = (DbClass) ((TreeNode<?>) element).getValue();
				PreferencesUtil.createPropertyDialogOn(window.getShell(), value, null, null, null).open();
				return null;
			}
			if (element instanceof DbMethod) {
				PreferencesUtil.createPropertyDialogOn(window.getShell(), (DbMethod) element, null, null, null).open();
				return null;
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return selection != null && !selection.isEmpty();
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		selection = (IStructuredSelection) event.getSelection();

		fireHandlerChanged(new HandlerEvent(this, true, false));
	}
}
