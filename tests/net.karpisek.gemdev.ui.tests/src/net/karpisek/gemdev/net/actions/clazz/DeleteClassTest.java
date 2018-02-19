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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.system.AbortTransaction;

public class DeleteClassTest extends SessionClientTestCase {
	private String className;

	@Override
	@Before
	public void setUp() {
		super.setUp();

		execute(new AbortTransaction());

		final String newClassName = String.format("DeleteClassTest_%d", System.currentTimeMillis());

		assertClassExists(className, false);
		className = execute(new CreateClass.Builder("Object", newClassName, "TestCategory").build());

		assertEquals(className, newClassName);
		assertClassExists(className, true);
	}

	@Override
	@After
	public void tearDown() {
		execute(new AbortTransaction());
		super.tearDown();
	}

	@Test
	public void testExecuting() {
		assertClassExists(className, true);

		execute(new DeleteClass(className));

		assertClassExists(className, false);
	}
}
