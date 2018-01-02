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
package net.karpisek.gemdev.team.ui;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.team.Messages;
import net.karpisek.gemdev.team.TeamPlugin;
import net.karpisek.gemdev.team.WorkingCopy;
import net.karpisek.gemdev.team.model.LocalClass;
import net.karpisek.gemdev.ui.browser.methods.MethodsView;

/**
 * Recompile selected methods with source code from working copy. Skip those methods which are not in working copy.
 */
public class ReadMethodsFromWorkingCopyHandler extends AbstractWriteHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = getSelection(event);
		final List<DbMethod> methods = Lists.newLinkedList();
		for (final Iterator<?> i = selection.iterator(); i.hasNext();) {
			methods.add((DbMethod) i.next());
		}

		Preconditions.checkArgument(methods.size() > 0);
		final DbMethod firstMethod = methods.get(0);
		final ISession session = firstMethod.getSession();
		final String className = firstMethod.getBehavior().getClassName();
		final WorkingCopy workingCopy = TeamPlugin.getDefault().getWorkingCopy(session);
		LocalClass source = null;
		try {
			source = workingCopy.read(className);
		} catch (final CoreException e) {
			throw new ExecutionException(MessageFormat.format(Messages.CLASS_READ_ERROR_MESSAGE, className), e);
		}

		session.abortTransaction();
		final List<DbMethod> newMethods = Lists.newLinkedList();
		final List<IMethod> methodsNotInWorkingCopy = Lists.newLinkedList();
		for (final DbMethod targetMethod : methods) {
			final IMethod sourceMethod = source.getMethod(targetMethod.getName(), targetMethod.isInstanceSide());
			// keep methods which do not occur in working copy unchanged
			if (sourceMethod == null) {
				methodsNotInWorkingCopy.add(targetMethod);
			} else {
				newMethods.add(session.createMethod(targetMethod.getCategory(), sourceMethod.getSourceCode()));
			}
		}
		session.commitTransaction();

		final MethodsView view = (MethodsView) HandlerUtil.getActivePartChecked(event);
		if (!newMethods.isEmpty()) {
			view.setInput(firstMethod.getCategory(), newMethods.get(0));
		}

		final List<String> names = Lists.newLinkedList();
		for (final IMethod m : methodsNotInWorkingCopy) {
			names.add(m.getName());
		}
		final String message = String.format("%d methods compiled from working copy, skipped %d not in WC %s", newMethods.size(), names.size(), names); //$NON-NLS-1$
		view.getViewSite().getActionBars().getStatusLineManager().setMessage(message);

		return null;
	}
}
