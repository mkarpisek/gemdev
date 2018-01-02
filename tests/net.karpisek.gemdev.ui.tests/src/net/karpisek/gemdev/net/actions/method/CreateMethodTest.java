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
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.CompilationError;
import net.karpisek.gemdev.net.actions.clazz.CreateClass;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;

public class CreateMethodTest extends SessionClientTestCase {
	private String className;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		execute(new AbortTransaction());

		final String newClassName = String.format("CompileMethodTest_%d", System.currentTimeMillis());

		assertClassExists(className, false);
		className = execute(new CreateClass.Builder("Object", newClassName, "TestCategory").build());

		assertEquals(className, newClassName);
		assertClassExists(className, true);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		execute(new AbortTransaction());
		super.tearDown();
	}

	@Test
	public void testConvertingErrorResponse() {
		try {
			new CreateMethod("Array", true, "testCategory", "testSourceCode&&&").asResponse("CompilationError\n1034 15 unexpected token");
			fail("Expected to throw CompilationError exception");
		} catch (final CompilationError e) {
			assertEquals(new CompilationError("TestActionName", "unexpected token", "TestRequest", 1034, 14, "testSourceCode&&&"), e);
		}
	}

	@Test
	public void testConvertingOkResponse() {
		final String expected = "at:put:";
		final String actual = new CreateMethod("Array", true, "testCategory", "testSourceCode").asResponse("at:put:");
		assertEquals(expected, actual);
	}

	@Test
	public void testExecuting() {
		final String actual = execute(new CreateMethod(className, true, "testCategory", "testSourceCode"));
		assertEquals("testSourceCode", actual);
	}

	@Test
	public void testExecutingForSourceCodeWithSyntaxError() {
		try {
			execute(new CreateMethod(className, true, "testCategory", "testSourceCode&&&"));
			fail("Expected to throw CompilationError exception");
		} catch (final CompilationError e) {
			assertEquals(new CompilationError("TestActionName", "unexpected token", "TestRequest", 1034, 14, "testSourceCode&&&"), e);
		}
	}
}
