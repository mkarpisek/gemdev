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

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMetaclass;
import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.SessionAdapter;

@Category({ IUnitTests.class })
public class CategoryComparatorTest {
	@Test
	public void testSorting() {
		final DbClass cl = new DbClass("TestClass", null, new SessionAdapter());
		final DbCategory a = new DbCategory("A", cl);
		final DbCategory b = new DbCategory("B", cl);
		final DbCategory c = new DbCategory("X", cl);
		final DbCategory bPvt = new DbCategory("x private B", cl);
		final DbCategory cPvt = new DbCategory("x private C", cl);

		final DbMetaclass mcl = cl.getClassSide();
		final DbCategory aC = new DbCategory("A", mcl);
		final DbCategory bC = new DbCategory("B", mcl);
		final DbCategory cC = new DbCategory("X", mcl);
		final DbCategory bPvtC = new DbCategory("x private B", mcl);
		final DbCategory cPvtC = new DbCategory("x private C", mcl);

		final List<DbCategory> categories = Lists.newArrayList(cPvt, bPvt, c, b, a, cPvtC, bPvtC, cC, bC, aC);
		Collections.sort(categories, new CategoryComparator());

		assertEquals(Lists.newArrayList(a, b, c, bPvt, cPvt, aC, bC, cC, bPvtC, cPvtC), categories);
	}
}
