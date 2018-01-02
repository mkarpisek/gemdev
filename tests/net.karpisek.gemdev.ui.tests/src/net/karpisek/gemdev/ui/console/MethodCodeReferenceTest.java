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
package net.karpisek.gemdev.ui.console;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class MethodCodeReferenceTest {
	@Test
	public void testClassMethod() {
		assertEquals("[line 6] Object class#error:", MethodCodeReference.on("15 Object class >> error: @1 line 6  [GsMethod OOP 1942017]").toString());
	}

	@Test
	public void testEmptyLine() {
		assertNull(MethodCodeReference.on(""));
		assertNull(MethodCodeReference.on(" \t "));
	}

	@Test
	public void testInstanceMethod() {
		assertEquals("[line 6] Object#error:", MethodCodeReference.on("15 Object >> error: @1 line 6  [GsMethod OOP 1942017]").toString());
	}

	@Test
	public void testLineWithoutLineNumber() {
		assertNull(MethodCodeReference.on("15 Object >> error:"));
	}

	@Test
	public void testLineWithoutSelector() {
		assertNull(MethodCodeReference.on("15 Object"));
	}

	@Test
	public void testLineWitInvalidLineNumber() {
		assertNull(MethodCodeReference.on("15 Object class >> error: @1 line XX"));
	}
}
