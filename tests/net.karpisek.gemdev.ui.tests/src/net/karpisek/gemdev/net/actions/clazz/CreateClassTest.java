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
package net.karpisek.gemdev.net.actions.clazz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;

public class CreateClassTest extends SessionClientTestCase {
	private String className;
	private String categoryName;

	@Override
	@Before
	public void setUp() {
		super.setUp();

		execute(new AbortTransaction());

		className = String.format("NewClassTest_%d", System.currentTimeMillis());
		categoryName = "TestCategory";

		assertClassExists(className, false);
	}

	@Override
	@After
	public void tearDown() {
		execute(new AbortTransaction());
		super.tearDown();
	}

	@Test
	public void testBuilderWithDefaultValues() {
		final CreateClass action = new CreateClass.Builder("SuperClass", "ClassA", "TestCategory").build();
		final String expected = "SuperClass subclass: 'ClassA'\n" + "	instVarNames: #()\n" + "	classVars: #()\n" + "	classInstVars: #()\n"
				+ "	poolDictionaries: #[]\n" + "	inDictionary: UserGlobals\n" + "	instancesInvariant: false\n" + "	isModifiable: true";

		assertEquals(expected, action.getDefinition());
	}

	@Test
	public void testBuilderWithNonDefaultValues() {
		final CreateClass action = new CreateClass.Builder("SuperClass", "ClassA", "TestCategory").instVars(Lists.newArrayList("i1", "i2"))
				.classVars(Lists.newArrayList("c1")).classInstVars(Lists.newArrayList("ci1", "ci2", "ci3")).dictionary("Globals").build();

		final String expected = "SuperClass subclass: 'ClassA'\n" + "	instVarNames: #(i1 i2)\n" + "	classVars: #(c1)\n" + "	classInstVars: #(ci1 ci2 ci3)\n"
				+ "	poolDictionaries: #[]\n" + "	inDictionary: Globals\n" + "	instancesInvariant: false\n" + "	isModifiable: true";

		assertEquals(expected, action.getDefinition());
	}

	@Test
	public void testConvertingErrorResponse() {
		try {
			new CreateClass("").asResponse("NewClassError\nerrorMessage");
			fail("Expected to throw exception");
		} catch (final ActionException e) {
			assertEquals("errorMessage", e.getMessage());
		}
	}

	@Test
	public void testExecuting() {
		final String newClassName = execute(new CreateClass.Builder("Object", className, categoryName).build());
		assertEquals(className, newClassName);
		assertClassExists(className, true);
	}
}
