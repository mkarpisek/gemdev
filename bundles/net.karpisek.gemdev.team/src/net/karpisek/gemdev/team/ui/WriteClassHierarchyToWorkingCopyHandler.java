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
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Writes hierarchy of selected classes to working copy. Because there can be multiple selected classes, merges hierarchies of selection. a
 */
public class WriteClassHierarchyToWorkingCopyHandler extends AbstractWriteHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = getSelection(event);
		final Set<DbClass> classes = Sets.newHashSet();
		for (final Iterator<?> i = selection.iterator(); i.hasNext();) {
			final TreeNode<?> node = (TreeNode<?>) i.next();
			final DbClass c = (DbClass) node.getValue();
			classes.addAll(collectAllSubclasses(c));
		}

		final WriteClassesToWorkingCopyJob job = new WriteClassesToWorkingCopyJob(classes);
		job.schedule();
		return null;
	}

}
