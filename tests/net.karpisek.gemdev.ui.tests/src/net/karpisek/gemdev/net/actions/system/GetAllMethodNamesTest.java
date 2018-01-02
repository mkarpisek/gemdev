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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionClientTestCase;

public class GetAllMethodNamesTest extends SessionClientTestCase {
	@Test
	public void testConvertingResponse() {
		final Set<String> expected = Sets.newHashSet("add:", "at:", "at:put:");

		final Set<String> actual = new GetAllMethodNames().asResponse("add:\nat:\nat:put:");

		assertEquals(expected, actual);
	}

	@Test
	public void testConvertingResponseWithNoSelectors() {
		assertEquals(Sets.newHashSet(), new GetAllMethodNames().asResponse(""));
	}

	@Test
	public void testExecution() {
		final Set<String> result = execute(new GetAllMethodNames());

		assertFalse("Expected to find at least some classes", result.isEmpty());
		assertTrue("Expected to have Object class", result.contains("at:"));
		assertTrue("Expected to have Array class", result.contains("at:put:"));
		assertTrue("Expected to have DbClass class", result.contains("add:"));
	}
}
