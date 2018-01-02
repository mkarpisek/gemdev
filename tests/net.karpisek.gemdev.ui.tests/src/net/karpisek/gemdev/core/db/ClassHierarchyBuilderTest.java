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
package net.karpisek.gemdev.core.db;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Maps;

import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class ClassHierarchyBuilderTest {
	@Test
	public void testEmpty() {
		final Map<String, String> map = Maps.newHashMap();
		assertBuilder("", map);
	}

	@Test
	public void testFull() {
		final Map<String, String> map = Maps.newHashMap();
		map.put("C1", null);
		map.put("C2", "nil"); // null && "nil" means same - no superclass (root class)
		map.put("C21", "C2");
		map.put("C211", "C21");
		map.put("C22", "C2");

		assertBuilder("C1 (C2 (C21 C211) C22)", map);
	}

	@Test
	public void testFullWithUnknownSuperclass() {
		final Map<String, String> map = Maps.newHashMap();
		map.put("C1", null);
		map.put("C2", "Unknown"); // <- if unkonw class is superclass, create class as root
		map.put("C21", "C2");
		map.put("C211", "C21");
		map.put("C22", "C2");

		assertBuilder("C1 (C2 (C21 C211) C22)", map);
	}

	@Test
	public void testOneClass() {
		final Map<String, String> map = Maps.newHashMap();
		map.put("C1", null);
		assertBuilder("C1", map);
	}

	private void assertBuilder(final String expected, final Map<String, String> map) {
		final ClassHierarchyBuilder builder = new ClassHierarchyBuilder(map, null);
		assertEquals("Builder created different hierarchy", expected, builder.toString());
	}
}
