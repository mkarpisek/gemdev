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
package net.karpisek.gemdev.core.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class FilenameUtilsTest {
	@Test
	public void testBinaryMessage() {
		assertEquals("Object class#=.gsm", FilenameUtils.getFilename(new MethodReference("Object", false, "="), "gsm", "666"));

		for (final String illegalSubstring : new String[] { "\\", "/", "*", "?", "<", ">", "|", "\"" }) {
			assertEquals("Object class#666.gsm", FilenameUtils.getFilename(new MethodReference("Object", false, illegalSubstring), "gsm", "666"));
		}
	}

	@Test
	public void testKeywordMessage() {
		assertEquals("Object class#_kw1;.gsm", FilenameUtils.getFilename(new MethodReference("Object", false, "_kw1:"), "gsm", "666"));
		assertEquals("Object class#kw1;kw2;.gsm", FilenameUtils.getFilename(new MethodReference("Object", false, "kw1:kw2:"), "gsm", "666"));
	}

	@Test
	public void testReplaceIllegalFilenameCharacters() {
		assertEquals(" x ", FilenameUtils.replaceIllegalFilenameCharacters("*x*", ' '));
	}

	@Test
	public void testReplaceIllegalFilenameCharactersWithIllegalCharacterItself() {
		try {
			FilenameUtils.replaceIllegalFilenameCharacters("*", '*');
			fail("Should throw illegal argument exception");
		} catch (final IllegalArgumentException e) {
			// correctly throw exception
		}
	}

	@Test
	public void testUnaryMessage() {
		assertEquals("Object class#_msg1.gsm", FilenameUtils.getFilename(new MethodReference("Object", false, "_msg1"), "gsm", "666"));
		assertEquals("Object class#msg1.gsm", FilenameUtils.getFilename(new MethodReference("Object", false, "msg1"), "gsm", "666"));
	}
}
