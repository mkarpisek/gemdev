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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;

public class HasClassCategoryNamedTest extends SessionClientTestCase {
	@Test
	public void testExecuting() {
		assertTrue(execute(new HasClassCategoryNamed("ScaledDecimal", true, "Comparing")));
		assertFalse(execute(new HasClassCategoryNamed("ScaledDecimal", false, "Comparing")));

		assertFalse(execute(new HasClassCategoryNamed("ScaledDecimal", true, "Instance Creation")));
		assertTrue(execute(new HasClassCategoryNamed("ScaledDecimal", false, "Instance Creation")));
	}
}
