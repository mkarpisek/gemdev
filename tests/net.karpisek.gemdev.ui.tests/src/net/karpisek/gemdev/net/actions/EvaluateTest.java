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
import static org.junit.Assert.fail;

import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.UnhandledErrorException;

public class EvaluateTest extends SessionClientTestCase {
	@Test
	public void testExecutingCorrectStatement() {
		final String oopStringOfInteger3 = "26";
		assertEquals(oopStringOfInteger3, execute(new Evaluate("1+2")));
	}

	@Test
	public void testExecutingErrorStatement() {
		try {
			execute(new Evaluate("##"));
			fail("Expected to throw compilation error");
		} catch (final CompilationError e) {
			// ok
		}
	}

	@Test
	public void testExecutingUnhandledError() {
		try {
			execute(new Evaluate("1&&2"));
			fail("Expected to throw unhandled error");
		} catch (final UnhandledErrorException e) {
			// ok
		}
	}
}
