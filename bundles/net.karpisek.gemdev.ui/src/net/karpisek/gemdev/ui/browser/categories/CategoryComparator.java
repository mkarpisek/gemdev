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
package net.karpisek.gemdev.ui.browser.categories;

import java.util.Comparator;

import net.karpisek.gemdev.core.db.DbCategory;

/**
 * Comparator for Categories view. Sort instance categories on the beginning, class on the end.
 */
public class CategoryComparator implements Comparator<DbCategory> {
	@Override
	public int compare(final DbCategory c1, final DbCategory c2) {
		final boolean c1i = c1.isInstanceSide();
		final boolean c2i = c2.isInstanceSide();
		if (c1i && !c2i) {
			return -1;
		}
		if (!c1i && c2i) {
			return 1;
		}
		return c1.getName().compareTo(c2.getName());
	}
}