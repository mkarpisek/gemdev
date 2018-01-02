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
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.MethodReference;

public class GetAllImplementorsTest extends SessionClientTestCase {
	@Test
	public void testConvertingEmptyResponse() {
		assertEquals("Expected empty set", Sets.newHashSet(), new GetAllImplementors("add:").asResponse(""));
	}

	@Test
	public void testConvertingResponse() {
		final Set<MethodReference> expected = Sets.newHashSet(new MethodReference("Array", true, "add:"), new MethodReference("Collection", false, "add:"));
		final Set<MethodReference> actual = new GetAllImplementors("add:").asResponse("Array i\nCollection c");

		assertEquals("Expected equal sets of method references", expected, actual);
	}

	@Test
	public void testExecuting() {
		final Set<MethodReference> result = execute(new GetAllImplementors("add:"));
		assertTrue("Expected that implementors contains Array>>add:", result.contains(new MethodReference("Array", true, "add:")));
		assertTrue("Expected that implementors contains Dictionary>>add:", result.contains(new MethodReference("Dictionary", true, "add:")));
	}
}
