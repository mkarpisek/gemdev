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
package net.karpisek.gemdev.net.actions.method;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.category.CreateCategory;
import net.karpisek.gemdev.net.actions.clazz.CreateClass;
import net.karpisek.gemdev.net.actions.clazz.GetCategoryNames;
import net.karpisek.gemdev.net.actions.clazz.GetMethodCategoryName;
import net.karpisek.gemdev.net.actions.clazz.GetMethodNames;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;

public class MoveMethodTest extends SessionClientTestCase {
	private String className;

	public void assertClassHasCategories(final Set<String> expectedCategoryNames, final String className, final boolean instanceSide) {
		assertEquals("Expected different categories of class " + className, expectedCategoryNames, execute(new GetCategoryNames(className, instanceSide)));
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		execute(new AbortTransaction());

		final String newClassName = String.format("NewClassTest_%d", System.currentTimeMillis());
		className = execute(new CreateClass.Builder("Object", newClassName, "TestCategory").build());

		assertClassExists(className, true);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		execute(new AbortTransaction());
		super.tearDown();
	}

	@Test
	public void test() {
		final String categoryName1 = "cat1";
		final String categoryName2 = "cat2";
		final String methodName = "msg1";

		execute(new CreateCategory(className, true, categoryName1));
		execute(new CreateCategory(className, true, categoryName2));
		execute(new CreateMethod(className, true, categoryName2, methodName));

		assertClassHasCategories(Sets.newHashSet(categoryName1, categoryName2), className, true);
		assertEquals(categoryName2, execute(new GetMethodCategoryName(className, true, methodName)));
		assertEquals("Expected different category of method", Sets.newHashSet(methodName), execute(new GetMethodNames(className, true, categoryName2)));

		execute(new MoveMethod(className, true, methodName, categoryName1));

		assertEquals(categoryName1, execute(new GetMethodCategoryName(className, true, methodName)));
		assertEquals("Expected different category of method", Sets.newHashSet(methodName), execute(new GetMethodNames(className, true, categoryName1)));
	}
}
