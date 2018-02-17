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

import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class MethodReferenceTest {
	private static final String CLASS_NAME_A = "classNameA";
	private static final String CLASS_NAME_B = "classNameB";
	private static final String SELECTOR_1 = "methodName1";
	private static final String SELECTOR_2 = "methodName2";

	@Test
	public void testEquals() {
		final MethodReference receiver = new MethodReference(CLASS_NAME_A, true, SELECTOR_1);
		assertTrue(receiver.equals(receiver));
		assertTrue(receiver.equals(new MethodReference(CLASS_NAME_A, true, SELECTOR_1)));
		assertFalse(receiver.equals(new MethodReference(CLASS_NAME_B, true, SELECTOR_1)));
		assertFalse(receiver.equals(new MethodReference(CLASS_NAME_A, true, SELECTOR_2)));
		assertFalse(receiver.equals(new MethodReference(CLASS_NAME_A, false, SELECTOR_1)));
	}
}
