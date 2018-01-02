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
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.clazz.GetMethodNames;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.categories.CategoriesView;

/**
 * Handler offers dialog with all methods of class. After selection of one method it will highlight category + method in categories and methods view.
 */
public class FindMethodHandler extends AbstractHandler {
	private final CategoriesView view;

	public FindMethodHandler(final CategoriesView view) {
		this.view = view;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		final DbClass inputClass = (DbClass) view.getInputClass();
		if (inputClass != null) {
			final ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(), new LabelProvider() {
				@Override
				public Image getImage(final Object element) {
					if (((MethodReference) element).isInstanceSide()) {
						return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.INSTANCE_METHOD_ICON);
					}
					return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.CLASS_METHOD_ICON);
				}

				@Override
				public String getText(final Object element) {
					return ((MethodReference) element).getMethodName();
				}
			});
			dialog.setTitle(Messages.FIND_METHOD_DIALOG_TITLE);
			dialog.setMessage(MessageFormat.format(Messages.FIND_METHOD_DIALOG_MESSAGE, inputClass.getClassName()));

			dialog.setElements(getElements(inputClass.getInstanceSide()).toArray());

			if (dialog.open() == Window.OK) {
				final MethodReference selectedReference = (MethodReference) dialog.getResult()[0];
				final IMethod selectedMethod = inputClass.getMethod(selectedReference.getMethodName(), selectedReference.isInstanceSide());
				if (selectedMethod == null) {
					MessageDialog.openInformation(HandlerUtil.getActiveShellChecked(event), "Find DbMethod", //$NON-NLS-1$
							MessageFormat.format(Messages.FIND_METHOD_DIALOG_ERROR, selectedReference.toString()));
					return null;
				}

				try {
					view.setSelection(selectedMethod.getCategory());
					final MethodsView methodsView = (MethodsView) view.getSite().getPage().showView(MethodsView.VIEW_ID);
					methodsView.setSelection((DbMethod) selectedMethod);
					methodsView.setFocus();
				} catch (final PartInitException e) {
					GemDevUiPlugin.getDefault().logError(e);
				}
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return view != null && view.getInputClass() != null;
	}

	private List<MethodReference> getElements(final DbClass b) {
		final List<MethodReference> result = Lists.newLinkedList();
		result.addAll(getMethods(b));
		result.addAll(getMethods(b.getClassSide()));

		Collections.sort(result, new Comparator<MethodReference>() {
			@Override
			public int compare(final MethodReference m1, final MethodReference m2) {
				return m1.getMethodName().compareTo(m2.getMethodName());
			}
		});
		return result;
	}

	private List<MethodReference> getMethods(final DbBehavior b) {
		final List<MethodReference> result = Lists.newLinkedList();
		for (final String name : b.getSession().execute(new GetMethodNames(b.getClassName(), b.isInstanceSide()))) {
			result.add(new MethodReference(b.getClassName(), b.isInstanceSide(), name));
		}
		return result;
	}
}
