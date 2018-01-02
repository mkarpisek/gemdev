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
package net.karpisek.gemdev.ui;

import java.util.List;

import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.handlers.IHandlerActivation;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.IPageSite;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Variaous utility methods for UI of this plugin.
 * 
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class GuiUtils {
	private static final String CONTEXT = "net.karpisek.gemdev.ui.context"; //$NON-NLS-1$

	public static IContextActivation activateContext(final IPageSite site) {
		final IContextService contextService = site.getService(IContextService.class);
		return contextService.activateContext(CONTEXT);
	}

	public static IContextActivation activateContext(final IWorkbenchPartSite site) {
		final IContextService contextService = site.getService(IContextService.class);
		return contextService.activateContext(CONTEXT);
	}

	public static List<IHandlerActivation> activateHandlers(final IWorkbenchPartSite site, final Object... commandsAndHandlers) {
		Preconditions.checkArgument(site != null, "Site must not be null"); //$NON-NLS-1$
		Preconditions.checkArgument((commandsAndHandlers.length % 2) == 0, "Expecting pairs of commands and its handlers"); //$NON-NLS-1$

		final IHandlerService handlerService = site.getService(IHandlerService.class);
		final List<IHandlerActivation> activations = Lists.newLinkedList();
		for (int i = 0; i < commandsAndHandlers.length; i += 2) {
			final String commandId = (String) commandsAndHandlers[i];
			final IHandler handler = (IHandler) commandsAndHandlers[i + 1];
			activations.add(handlerService.activateHandler(commandId, handler));
		}
		return activations;
	}

	private GuiUtils() {
		// should not be instantiated
	}
}
