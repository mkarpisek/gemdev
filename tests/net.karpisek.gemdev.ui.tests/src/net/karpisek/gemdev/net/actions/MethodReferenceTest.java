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
	@Test
	public void testEquals() {
		final MethodReference receiver = new MethodReference("className", true, "methodName");
		assertTrue(receiver.equals(receiver));
		assertTrue(receiver.equals(new MethodReference("className", true, "methodName")));
		assertFalse(receiver.equals(null));
		assertFalse(receiver.equals(new MethodReference("classNameX", true, "methodName")));
		assertFalse(receiver.equals(new MethodReference("className", true, "methodNameX")));
		assertFalse(receiver.equals(new MethodReference("className", false, "methodName")));
	}
}
