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

import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.team.Messages;
import net.karpisek.gemdev.team.TeamPlugin;
import net.karpisek.gemdev.team.WorkingCopy;
import net.karpisek.gemdev.team.model.LocalClass;
import net.karpisek.gemdev.ui.browser.categories.CategoriesView;

/**
 * Recompile selected categories with source code from working copy. Skip those categories which are not in working copy.
 */
public class ReadCategoriesFromWorkingCopyHandler extends AbstractWriteHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = getSelection(event);
		final List<DbCategory> categories = Lists.newLinkedList();
		for (final Iterator<?> i = selection.iterator(); i.hasNext();) {
			categories.add((DbCategory) i.next());
		}

		Preconditions.checkArgument(!categories.isEmpty());
		final DbCategory firstMethod = categories.get(0);
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
		final List<ICategory> notInWorkingCopy = Lists.newLinkedList();
		final List<IMethod> newMethods = Lists.newLinkedList();
		final List<IMethod> deletedMethods = Lists.newLinkedList();
		for (final DbCategory targetCategory : categories) {
			final ICategory sourceCategory = source.getCategory(targetCategory.getName(), targetCategory.isInstanceSide());
			// keep categories which do not occur in working copy unchanged
			if (sourceCategory == null) {
				notInWorkingCopy.add(targetCategory);
			} else {
				// remove methods from category which are not in working copy
				for (final IMethod targetMethod : targetCategory.getMethods()) {
					if (sourceCategory.getMethod(targetMethod.getName()) == null) {
						session.deleteMethod((DbMethod) targetMethod);
						deletedMethods.add(targetMethod);
					}
				}
				// compile all existing methods from source category to target category
				// compilation will create new one, recompile existing or possibly move methods from other categories to here
				for (final IMethod sourceMethod : sourceCategory.getMethods()) {
					newMethods.add(session.createMethod(targetCategory, sourceMethod.getSourceCode()));
				}

			}
		}
		session.commitTransaction();

		final CategoriesView view = (CategoriesView) HandlerUtil.getActivePartChecked(event);
		view.setInput(categories.get(0).getBehavior().getInstanceSide(), categories.get(0));

		final List<String> names = Lists.newLinkedList();
		for (final ICategory o : notInWorkingCopy) {
			names.add(o.getName());
		}
		final String message = String.format("%d methods compiled, %d deleted, skipped %d categories not in WC %s", newMethods.size(), deletedMethods.size(), //$NON-NLS-1$
				names.size(), names);
		view.getViewSite().getActionBars().getStatusLineManager().setMessage(message);

		return null;
	}
}
