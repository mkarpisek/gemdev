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

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Writes selected db classes to working copy class files.
 */
public class WriteClassesToWorkingCopyHandler extends AbstractWriteHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = getSelection(event);
		final List<DbClass> classes = Lists.newLinkedList();
		for (final Iterator<?> i = selection.iterator(); i.hasNext();) {
			final TreeNode<?> node = (TreeNode<?>) i.next();
			classes.add((DbClass) node.getValue());
		}

		final WriteClassesToWorkingCopyJob job = new WriteClassesToWorkingCopyJob(classes);
		job.schedule();
		return null;
	}

}
