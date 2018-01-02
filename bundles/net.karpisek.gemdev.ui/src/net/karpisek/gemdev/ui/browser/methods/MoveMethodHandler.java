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
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.net.actions.method.MoveMethod;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.categories.CategoriesView;

/**

 */
public class MoveMethodHandler extends AbstractHandler implements ISelectionChangedListener {
	private final MethodsView view;
	private IStructuredSelection selection;

	public MoveMethodHandler(final MethodsView view) {
		this.view = view;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final List<DbMethod> selectedMethods = Lists.newLinkedList();
		for (final Object o : selection.toList()) {
			if (o instanceof DbMethod) {
				selectedMethods.add((DbMethod) o);
			}
		}

		final DbCategory inputCategory = view.getInputCategory();
		final DbBehavior inputClass = inputCategory.getBehavior();
		final ISession session = inputClass.getSession();

		session.abortTransaction();

		// put into set names of all existing categories from same side of class as is selected category
		final List<String> existingCategoryNames = Lists.newLinkedList();
		for (final ICategory category : inputClass.getCategories()) {
			if (inputCategory.isInstanceSide() == category.isInstanceSide()) {
				existingCategoryNames.add(category.getName());
			}
		}
		Collections.sort(existingCategoryNames);

		String message = ""; //$NON-NLS-1$
		if (selectedMethods.size() > 1) {
			message = MessageFormat.format(Messages.MOVE_METHOD_DIALOG_MULTIPLE_ELEMENTS, selectedMethods.size());
		} else {
			message = MessageFormat.format(Messages.MOVE_METHOD_DIALOG_SINGLE_ELEMENT, selectedMethods.get(0).getName());
		}

		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(HandlerUtil.getActiveShell(event), new LabelProvider());
		dialog.setTitle(Messages.MOVE_METHOD_DIALOG_TITLE);
		dialog.setMessage(message);
		dialog.setElements(existingCategoryNames.toArray());

		if (dialog.open() == Window.OK) {
			final String newCategoryName = (String) dialog.getResult()[0];

			try {
				session.abortTransaction();
				for (final DbMethod method : selectedMethods) {
					session.execute(new MoveMethod(method.getBehavior().getClassName(), method.isInstanceSide(), method.getName(), newCategoryName));
				}
				session.commitTransaction();

				// refresh view
				final CategoriesView view2 = (CategoriesView) view.getSite().getPage().showView(CategoriesView.VIEW_ID);
				view2.setInput(inputClass.getInstanceSide(), inputClass.getCategory(newCategoryName));
			} catch (final CommitFailedException e) {
				view.getViewSite().getActionBars().getStatusLineManager().setErrorMessage(Messages.MOVE_METHOD_DIALOG_COMMIT_ERROR);
			} catch (final PartInitException e) {
				GemDevUiPlugin.getDefault().logError(e);
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
