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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;

public class CheckSyntaxTest extends SessionClientTestCase {
	@Test
	public void testExecuting() {
		assertTrue(execute(new CheckSyntax("1+2", null, false)));
	}

	@Test
	public void testSyntaxWithSyntaxError() {
		try {
			assertTrue(execute(new CheckSyntax("##", null, false)));
			fail("Expected to throw Compilation error exception");
		} catch (final CompilationError e) {
			// this is ok
		}
	}

	@Test
	public void testSyntaxWithSyntaxErrorSilently() {
		assertFalse(execute(new CheckSyntax("##", null, true)));
	}
}
