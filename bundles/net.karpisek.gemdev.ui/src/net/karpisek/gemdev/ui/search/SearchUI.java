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
package net.karpisek.gemdev.ui.search;

import org.eclipse.search.ui.NewSearchUI;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.Selector;

/**
 * Utility class for executing search queries.
 */
public class SearchUI {
	public static void showImplementorsOf(final Selector selector) {
		Preconditions.checkNotNull(selector);
		NewSearchUI.runQueryInBackground(new ImplementorsSearchQuery(selector));
	}

	public static void showReferencesTo(final DbClass c) {
		Preconditions.checkNotNull(c);
		NewSearchUI.runQueryInBackground(new ClassReferencesSearchQuery(c));
	}

	public static void showSendersOf(final Selector selector) {
		Preconditions.checkNotNull(selector);
		NewSearchUI.runQueryInBackground(new SendersSearchQuery(selector));
	}
}
