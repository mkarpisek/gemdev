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
package net.karpisek.gemdev.net.actions.category;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.clazz.CreateClass;
import net.karpisek.gemdev.net.actions.clazz.GetCategoryNames;
import net.karpisek.gemdev.net.actions.clazz.GetMethodNames;
import net.karpisek.gemdev.net.actions.method.CreateMethod;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;

public class RenameCategoryTest extends SessionClientTestCase {
	private String className;

	@Override
	@Before
	public void setUp() {
		super.setUp();

		execute(new AbortTransaction());

		final String newClassName = String.format("NewClassTest_%d", System.currentTimeMillis());
		className = execute(new CreateClass.Builder("Object", newClassName, "TestCategory").build());

		assertClassExists(className, true);
	}

	@Override
	@After
	public void tearDown() {
		execute(new AbortTransaction());
		super.tearDown();
	}

	@Test
	public void test() {
		final String categoryName1 = "cat1";
		final String categoryName2 = "cat2";
		final String categoryName3 = "cat3";

		execute(new CreateCategory(className, true, categoryName1));
		execute(new CreateCategory(className, true, categoryName2));
		execute(new CreateMethod(className, true, categoryName2, "msg1"));

		assertEquals("Required prerequisite categories does not exists", Sets.newHashSet(categoryName1, categoryName2),
				execute(new GetCategoryNames(className, true)));

		// rename category -> method msg1 should be moved and in category with new name
		execute(new RenameCategory(className, true, categoryName2, categoryName3));

		assertEquals("Expected different category names", Sets.newHashSet(categoryName1, categoryName3), execute(new GetCategoryNames(className, true)));
		assertEquals("Expected method msg1 is in renamed category", Sets.newHashSet("msg1"), execute(new GetMethodNames(className, true, categoryName3)));
	}
}
