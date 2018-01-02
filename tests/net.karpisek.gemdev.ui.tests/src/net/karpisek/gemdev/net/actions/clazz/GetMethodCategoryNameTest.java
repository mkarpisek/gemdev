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
import static org.junit.Assert.assertNull;

import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.UnhandledErrorException;

public class GetMethodCategoryNameTest extends SessionClientTestCase {
	@Test
	public void testExecutingForExistingClassMethod() {
		assertEquals("Instance Creation", execute(new GetMethodCategoryName("ScaledDecimal", false, "fromString:")));
	}

	@Test
	public void testExecutingForExistingInstanceMethod() {
		assertEquals("Accessing", execute(new GetMethodCategoryName("ScaledDecimal", true, "at:")));
	}

	@Test
	public void testExecutingForNonExistingClass() {
		try {
			execute(new GetMethodCategoryName("ScaledDecimalX", false, "fromString:"));
		} catch (final UnhandledErrorException e) {
			assertEquals("User defined error, Class ScaledDecimalX not found.", e.getMessage());
		}
	}

	@Test
	public void testExecutingForNonExistingInstanceMethod() {
		assertNull("Expected null for non existing method category", execute(new GetMethodCategoryName("ScaledDecimal", true, "_nonExistingMethod")));
	}
}
