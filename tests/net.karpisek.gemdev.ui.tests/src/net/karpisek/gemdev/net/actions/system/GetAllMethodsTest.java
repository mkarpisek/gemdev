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
package net.karpisek.gemdev.net.actions.system;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.actions.MethodReference;

public class GetAllMethodsTest {
	@Test
	public void testConvertingEmptyResponse() {
		assertEquals(Sets.newHashSet(), new GetAllMethods().asResponse(""));
	}

	@Test
	public void testConvertingResponse() {
		final Set<MethodReference> expected = Sets.newHashSet(new MethodReference("Object", true, "at:"), new MethodReference("Object", true, "at:put:"),
				new MethodReference("Array", false, "new"));

		final Set<MethodReference> actual = new GetAllMethods().asResponse("Object i at: at:put:\nObject c\nArray i\nArray c new");

		assertEquals(expected, actual);
	}
}
