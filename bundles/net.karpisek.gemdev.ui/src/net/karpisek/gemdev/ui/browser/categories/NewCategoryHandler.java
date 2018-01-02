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
package net.karpisek.gemdev.ui.browser.categories;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.ui.Messages;

/**
 * Create new method category. Performs abort + commit when creating category. TODO: reuse error messages from rename category handler, change handling of
 * initial value
 */
public class NewCategoryHandler extends AbstractHandler {
	private static class NewCategoryDialog extends InputDialog {
		private Button instanceSideCheckbox;
		private boolean instanceSideValue;

		public NewCategoryDialog(final IWorkbenchWindow window, final String initialValue, final IInputValidator validator,
				final boolean initialInstanceSideValue) {
			super(window.getShell(), Messages.NEW_CATEGORY_DIALOG_TITLE, Messages.NEW_CATEGORY_DIALOG_MESSAGE, initialValue, validator);
			this.instanceSideValue = initialInstanceSideValue;
		}

		public boolean isInstanceSide() {
			return instanceSideValue;
		}

		@Override
		protected Control createDialogArea(final Composite parent) {
			final Control c = super.createDialogArea(parent);

			instanceSideCheckbox = new Button((Composite) c, SWT.CHECK);
			instanceSideCheckbox.setText(Messages.NEW_CATEGORY_DIALOG_INSTANCE_SIDE_CHECKBOX);
			instanceSideCheckbox.setSelection(instanceSideValue);
			instanceSideCheckbox.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent e) {
					instanceSideValue = instanceSideCheckbox.getSelection();
					((CategoryNameValidator) getValidator()).setInstanceSide(instanceSideValue);
					validateInput();
				}
			});
			getText().selectAll();
			return c;
		}
	}

	private final CategoriesView view;

	public NewCategoryHandler(final CategoriesView view) {
		this.view = view;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		final DbClass inputClass = (DbClass) view.getInputClass();
		final IStructuredSelection selection = (IStructuredSelection) view.getSite().getSelectionProvider().getSelection();
		final DbCategory selectedCategory = (DbCategory) selection.getFirstElement();
		String initialCategoryNameValue = ""; //$NON-NLS-1$
		boolean initialIsInstanceSideValue = true;
		if (selectedCategory != null) {
			initialCategoryNameValue = selectedCategory.getName();
			initialIsInstanceSideValue = selectedCategory.isInstanceSide();
		}

		final NewCategoryDialog dialog = new NewCategoryDialog(window, initialCategoryNameValue,
				CategoryNameValidator.on(inputClass, initialIsInstanceSideValue), initialIsInstanceSideValue);

		if (dialog.open() == Window.OK) {
			final ISession session = inputClass.getSession();
			try {
				final DbCategory newCategory = session.createCategory((DbBehavior) inputClass.getBehavior(dialog.isInstanceSide()), dialog.getValue());
				view.setInput(inputClass, newCategory);
			} catch (final CommitFailedException e) {
				view.getViewSite().getActionBars().getStatusLineManager().setErrorMessage(Messages.NEW_CATEGORY_FAILED);
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return view != null && view.getInputClass() != null;
	}
}
