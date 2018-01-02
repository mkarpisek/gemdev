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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.UnhandledErrorException;

public class GetMethodSourceCodeTest extends SessionClientTestCase {
	public void tesExecutingForExistingClassMethod() {
		final String actual = execute(new GetMethodSourceCode("ScaledDecimal", false, "fromString:"));
		assertNotNull("Expected string with source code will be returned", actual);
		assertTrue("Expected returned source code is not empty", actual.length() > 0);
	}

	@Test
	public void testExecutingForExistingInstanceMethod() {
		final String actual = execute(new GetMethodSourceCode("ScaledDecimal", true, "at:"));
		assertNotNull("Expected string with source code will be returned", actual);
		assertTrue("Expected returned source code is not empty", actual.length() > 0);
	}

	@Test
	public void testExecutingForNonExistingClass() {
		try {
			execute(new GetMethodSourceCode("ScaledDecimalX", true, "add:"));
		} catch (final UnhandledErrorException e) {
			assertEquals("User defined error, Class ScaledDecimalX not found.", e.getMessage());
		}
	}

	@Test
	public void testExecutingForNonExistingInstanceMethod() {
		assertNull("Expected null for non existing method", execute(new GetMethodSourceCode("ScaledDecimal", true, "_nonExistingMethod")));
	}
}
