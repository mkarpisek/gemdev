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

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.MethodReference;

public class GetAllSendersTest extends SessionClientTestCase {
	@Test
	public void testConvertingEmptyResponse() {
		assertEquals("Expected empty set", Maps.newHashMap(), new GetAllSenders("add:").asResponse(""));
	}

	@Test
	public void testConvertingResponse() {
		final Map<MethodReference, Integer> expected = Maps.newHashMap();
		expected.put(new MethodReference("AbstractDictionary", true, "addAll:"), 4);
		expected.put(new MethodReference("Collection", false, "with:"), 9);
		final Map<MethodReference, Integer> actual = new GetAllSenders("Array").asResponse("AbstractDictionary i addAll: 5\nCollection c with: 10");

		assertEquals("Expected equal sets of method references", expected, actual);
	}

	@Test
	public void testExecuting() {
		final Map<MethodReference, Integer> result = execute(new GetAllSenders("add:"));
		assertTrue("Expected that implementors contains Collection>>addAll:", result.containsKey(new MethodReference("Collection", true, "addAll:")));
		assertTrue("Expected that implementors contains Collection class>>with:", result.containsKey(new MethodReference("Collection", false, "with:")));
	}
}
