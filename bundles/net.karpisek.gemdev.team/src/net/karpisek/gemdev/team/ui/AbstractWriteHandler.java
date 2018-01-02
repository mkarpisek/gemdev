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

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbClass;

public abstract class AbstractWriteHandler extends AbstractHandler {

	public IStructuredSelection getSelection(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
		return (IStructuredSelection) part.getSite().getSelectionProvider().getSelection();
	}

	protected List<DbClass> collectAllSubclasses(final DbClass root) {
		final List<DbClass> result = Lists.newLinkedList();
		result.add(root);
		for (final DbClass c : root.getSubclasses()) {
			result.addAll(collectAllSubclasses(c));
		}
		return result;
	}
}
