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
package net.karpisek.gemdev.compare.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.team.ui.TeamUI;
import org.eclipse.ui.handlers.HandlerUtil;

import net.karpisek.gemdev.core.db.DbMethod;

/**
 * Compares selected method with its local history in workspace.
 */
public class CompareWithLocalHistoryHandler extends AbstractHandler {
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveSite(event).getSelectionProvider().getSelection();

		final DbMethod method = (DbMethod) selection.getFirstElement();

		final MethodLocalHistoryPageSource pageSource = MethodLocalHistoryPageSource.getInstance();

		TeamUI.showHistoryFor(HandlerUtil.getActiveSite(event).getPage(), method, pageSource);

		return null;
	}
}
