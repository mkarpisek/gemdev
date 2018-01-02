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
package net.karpisek.gemdev.ui.browser;

import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.search.SearchUI;

/**
 * This handler opens dialog with all selectors for existing methods, after selection of one it will search for sending methods.
 */
public class FindSendersHandler extends FindImplementorsHandler {
	public FindSendersHandler() {
		super(Messages.FIND_SENDERS_DIALOG_TITLE, GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.FIND_SENDERS_ICON));
	}

	@Override
	protected void okPressed(final Selector selector) {
		SearchUI.showSendersOf(selector);
	}
}
