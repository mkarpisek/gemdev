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
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.clazz.CreateClass;
import net.karpisek.gemdev.net.actions.clazz.GetCategoryNames;

public class CreateAndDeleteCategoryTest extends SessionClientTestCase {
	private final static String TEST_CLASS_NAME = "CreateAndDeleteCategoryTest";

	@Override
	@Before
	public void setUp() {
		super.setUp();
		execute(new CreateClass.Builder("Object", TEST_CLASS_NAME, "UserGlobals").build());
	}

	/**
	 * this test smells but how to perform clean up correctly? if test pass completely - than clean up is performed, otherwise we have leak
	 */
	@Test
	public void test() {
		final Set<String> beforeNewCategory = execute(new GetCategoryNames(TEST_CLASS_NAME, true));
		final String categoryName = "testCategory" + beforeNewCategory.size();

		assertEquals(categoryName, execute(new CreateCategory(TEST_CLASS_NAME, true, categoryName)));

		final Set<String> afterNewCategory = execute(new GetCategoryNames(TEST_CLASS_NAME, true));
		assertTrue(afterNewCategory.contains(categoryName));

		assertEquals(categoryName, execute(new DeleteCategory(TEST_CLASS_NAME, true, categoryName)));
		final Set<String> afterDeleteCategory = execute(new GetCategoryNames(TEST_CLASS_NAME, true));
		assertEquals(beforeNewCategory, afterDeleteCategory);
	}
}
