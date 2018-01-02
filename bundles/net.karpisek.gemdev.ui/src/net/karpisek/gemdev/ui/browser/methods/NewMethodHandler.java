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

import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.lang.ParserUtils;
import net.karpisek.gemdev.lang.ParserUtils.ParsedSelector;
import net.karpisek.gemdev.lang.model.Message.Type;
import net.karpisek.gemdev.net.actions.CompilationError;
import net.karpisek.gemdev.ui.Messages;

public class NewMethodHandler extends AbstractHandler {
	private final MethodsView view;

	public NewMethodHandler(final MethodsView view) {
		this.view = view;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		final DbCategory inputCategory = view.getInputCategory();
		final DbBehavior inputClass = inputCategory.getBehavior();
		final Set<String> existingMethodNames = Sets.newHashSet();
		for (final ICategory c : inputClass.getCategories()) {
			for (final IMethod method : c.getMethods()) {
				if (inputCategory.isInstanceSide() == method.isInstanceSide()) {
					existingMethodNames.add(method.getName());
				}
			}
		}

		final InputDialog dialog = new InputDialog(window.getShell(), Messages.NEW_METHOD_DIALOG_TITLE, Messages.NEW_METHOD_DIALOG_MESSAGE, "", //$NON-NLS-1$
				new MethodNameValidator(existingMethodNames));

		if (dialog.open() == Window.OK) {
			try {
				final ISession session = inputClass.getSession();
				final String sourceCode = getMethodTemplate(dialog.getValue());
				final DbMethod newMethod = session.createMethod(inputCategory, sourceCode);
				view.setInput(inputCategory, newMethod);
			} catch (final CompilationError e) {
				MessageDialog.openInformation(window.getShell(), Messages.NEW_METHOD_DIALOG_COMPILATION_ERROR, e.getMessage());
			} catch (final CommitFailedException e) {
				view.getViewSite().getActionBars().getStatusLineManager().setErrorMessage(Messages.NEW_METHOD_DIALOG_COMMIT_ERROR);
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return view != null && view.getInputCategory() != null;
	}

	private String getMethodTemplate(final String methodName) {
		// TODO: use text file template
		final ParsedSelector selector = ParserUtils.parseSelector(methodName);
		if (selector == null) {
			throw new IllegalArgumentException("Invalid selector name " + methodName); //$NON-NLS-1$
		}
		final StringBuilder sb = new StringBuilder();
		if (selector.getType() == Type.UNARY) {
			sb.append(methodName);
		}
		if (selector.getType() == Type.BINARY) {
			sb.append(methodName).append(" arg"); //$NON-NLS-1$
		}
		if (selector.getType() == Type.KEYWORD) {
			int counter = 1;
			for (final String kw : selector.getSelectorParts()) {
				sb.append(kw).append(": ").append("arg").append(counter).append(" "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				counter++;
			}
		}
		sb.append("\n\t\"TODO: method implementation\"\n"); //$NON-NLS-1$
		return sb.toString();
	}
}
