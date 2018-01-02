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
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.ui.utils.TreeNode;

public class CompareHierarchyWithWorkingCopyHandler extends AbstractWriteHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final List<DbClass> classes = Lists.newLinkedList();
		for (final Iterator<?> i = getSelection(event).iterator(); i.hasNext();) {
			final TreeNode<?> node = (TreeNode<?>) i.next();
			final DbClass c = (DbClass) node.getValue();
			classes.addAll(collectAllSubclasses(c));
		}
		final CompareClassesWithWorkingCopyJob job = new CompareClassesWithWorkingCopyJob(classes, HandlerUtil.getActiveSite(event).getPage());
		job.schedule();
		return null;
	}

}
