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

public class GetAllClassAndSuperclassNamesTest extends SessionClientTestCase {
	@Test
	public void testConvertingResponse() {
		final Map<String, String> expected = Maps.newHashMap();
		expected.put("Array", "SequenceableCollection");
		expected.put("SequenceableCollection", "Collection");
		expected.put("Collection", "Object");
		expected.put("Object", "nil");

		final Map<String, String> actual = new GetAllClassAndSuperclassNames()
				.asResponse("Array SequenceableCollection\nSequenceableCollection Collection\nCollection Object\nObject nil");

		assertEquals(expected, actual);
	}

	@Test
	public void testExecution() {
		final Map<String, String> actual = execute(new GetAllClassAndSuperclassNames());
		assertEquals("SequenceableCollection", actual.get("Array"));
		assertEquals("Collection", actual.get("SequenceableCollection"));
		assertEquals("Object", actual.get("Collection"));
		assertEquals("nil", actual.get("Object"));
	}
}
