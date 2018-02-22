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
package net.karpisek.gemdev.net.actions;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.category.CreateCategory;
import net.karpisek.gemdev.net.actions.clazz.CreateClass;
import net.karpisek.gemdev.net.actions.method.CreateMethod;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

public class GetSourcesTest extends SessionClientTestCase {
	private String className;
	private String categoryName1;
	private String categoryName2;
	private String methodName1;

	@Override
	@Before
	public void setUp() {
		super.setUp();

		execute(new AbortTransaction());

		className = execute(new CreateClass.Builder("Object", "GetSourcesTest", "TestCategory").build());

		assertClassExists(className, true);

		categoryName1 = "cat1";
		categoryName2 = "cat2";
		methodName1 = "msg1";

		execute(new CreateCategory(className, true, categoryName1));
		execute(new CreateMethod(className, true, categoryName2, methodName1));
	}

	@Override
	@After
	public void tearDown() {
		execute(new AbortTransaction());
		super.tearDown();
	}

	@Test
	public void testCategory() {
		final String expected = getResource("GetSourcesTest_testCategory_expected.txt");
		final String actual = execute(GetSources.forCategory(className, true, categoryName2));

		assertEquals(expected, actual);
	}

	@Test
	public void testClass() {
		final String expected = getResource("GetSourcesTest_testClass_expected.txt");
		final String actual = execute(GetSources.forClass(className));

		assertEquals(expected, actual);
	}

	@Test
	public void testMethod() {
		final String expected = getResource("GetSourcesTest_testMethod_expected.txt");
		final String actual = execute(GetSources.forMethod(className, true, methodName1));

		assertEquals(expected, actual);
	}

	private String getResource(final String fileName) {
		// normalize contents line ends on unix ones
		return UiTestsPlugin.getDefault().getFileContents("/resources/actions/" + fileName).replaceAll(System.lineSeparator(), "\n");
	}
}
