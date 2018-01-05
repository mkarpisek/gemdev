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
package net.karpisek.gemdev.team;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.team.model.LocalClass;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class LocalClassTest extends TeamTestCase {
	@Test
	public void testReadClassDeclaration_emptyInput() throws Exception {
		try {
			LocalClass.read(null, new StringReader(""));
		} catch (final IOException e) {
			assertEquals("[Line 0] Property 'Class' not found on line ''", e.getMessage());
		}
	}

	@Test
	public void testReadClassDeclaration_emptySuperClass() throws Exception {
		try {
			LocalClass.read(null, new StringReader("Class: xxxx \nSuperClass:  \n"));
		} catch (final IOException e) {
			assertEquals("[Line 2] Property 'SuperClass' must not be empty", e.getMessage());
		}
	}

	@Test
	public void testReadClassDeclaration_invalidFirstProperty() throws Exception {
		try {
			LocalClass.read(null, new StringReader("Something: xxxx \n"));
		} catch (final IOException e) {
			assertEquals("[Line 1] Property 'Class' not found on line 'Something: xxxx '", e.getMessage());
		}
	}

	@Test
	public void testReadEmptyClass() throws IOException {
		final String contents = getResource("EmptyClass.gsc");
		final LocalClass c = LocalClass.read(null, new StringReader(contents));
		assertEquals("Test1", c.getName());
		assertEquals("nil", c.getSuperClassName());
		assertEquals(Lists.newArrayList(), c.getInstanceVariables());
		assertEquals(Lists.newArrayList(), c.getInstanceVariables());
		assertEquals("Example Class Category", c.getCategoryName());
	}

	@Test
	public void testReadEmptyClassWithVariables() throws IOException {
		final String contents = getResource("EmptyClass2.gsc");
		final LocalClass c = LocalClass.read(null, new StringReader(contents));
		assertEquals("Test1", c.getName());
		assertEquals("Object", c.getSuperClassName());
		assertEquals(Lists.newArrayList("i1", "i2", "i3"), c.getInstanceVariables());
		assertEquals(Lists.newArrayList("c1", "c2"), c.getClassVariables());
		assertEquals("Example Class Category", c.getCategoryName());
	}

	@Test
	public void testReadFullClass() throws IOException {
		final String contents = getResource("FullClass.gsc");
		final LocalClass c = LocalClass.read(null, new StringReader(contents));
		assertEquals("FullClass", c.getName());
		assertEquals("Object", c.getSuperClassName());
		assertEquals(Lists.newArrayList("i1", "i2", "i3"), c.getInstanceVariables());
		assertEquals(Lists.newArrayList("c1"), c.getClassVariables());
		assertEquals("Example Class Category", c.getCategoryName());

		assertHasMethods("[FullClass class#msg1[cat1], FullClass#action1[cat2], FullClass#action2[cat1]]", c);
	}

	@Test
	public void testWriteClass() throws IOException {
		final String contents = getResource("FullClass.gsc");
		final LocalClass c = LocalClass.read(null, new StringReader(contents));

		final StringWriter sw = new StringWriter();
		c.write(sw);

		assertEqualsAfterEolNormalization(contents, sw.toString());
	}

	@Test
	public void testWriteClass_checkMethodsSorting() throws IOException {
		final LocalClass c = new LocalClass(null, "C1");
		c.createMethod("m2", "c1", "m2");
		c.createMethod("m1", "c2", "m1");

		final StringWriter sw = new StringWriter();
		c.write(sw);

		assertEqualsAfterEolNormalization(getResource("testWriteClass_checkMethodsSorting.gsc"), sw.toString());
	}
	
	/**
	 * Compare texts by contents, but ignore differences in actual EOL codes (think windows x linux).
	 */
	private void assertEqualsAfterEolNormalization(String expected, String actual) {
		String expectedUnixified = expected.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
		String actualUnixified = actual.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
		
		assertEquals(expectedUnixified, actualUnixified);
	}
}
