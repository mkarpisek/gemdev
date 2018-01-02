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

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.MethodReference;

public class GetAllMethodsWithSubstringTest extends SessionClientTestCase {
	@Test
	public void testConvertingEmptyResponse() {
		assertEquals("Expected empty set", Maps.newHashMap(), new GetAllMethodsWithSubstring("HACK", true).asResponse(""));
	}

	@Test
	public void testConvertingResponse() {
		final Map<MethodReference, Integer> expected = Maps.newHashMap();
		expected.put(new MethodReference("AbstractDictionary", true, "collectValuesAsArray:"), 4);
		expected.put(new MethodReference("BinaryFloat", false, "enabledExceptions"), 9);
		final Map<MethodReference, Integer> actual = new GetAllMethodsWithSubstring("HACK", true)
				.asResponse("AbstractDictionary i collectValuesAsArray: 5\nBinaryFloat c enabledExceptions 10");

		assertEquals("Expected equal sets of method references", expected, actual);
	}

	/*
	 * mka2011-08-20 disabled test because it is not in current gemstone image available
	 *
	 * @Test public void testExecutingCaseSensitive(){ final Map<MethodReference, Integer> result = execute(new GetAllMethodsWithSubstring("HACK", true));
	 * assertEquals(1, result.size()); assertTrue(result.containsKey((new MethodReference("PRDistribution", true, "stylesheetFile")))); }
	 * 
	 * @Test public void testExecutingCaseInsensitive(){ final Map<MethodReference, Integer> result = execute(new GetAllMethodsWithSubstring("HACK", false));
	 * assertEquals(14, result.size()); assertTrue(result.containsKey((new MethodReference("Class", true, "_constraintCreationExpression")))); }
	 */
}
