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
package net.karpisek.gemdev.ui.editor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jface.text.IRegion;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class DoubleClickStrategyTest {
	private DoubleClickStrategy strategy;

	@Before
	public void setUp() {
		strategy = new DoubleClickStrategy();
	}

	@Test
	public void testBraces() {
		assertDoubleClickSelection("blas{}blabla", " {blas{}blabla} ", 2);
		assertDoubleClickSelection("blas{}blabla", " {blas{}blabla} ", 14);
	}

	@Test
	public void testBrackets() {
		assertDoubleClickSelection("blas[]blabla", " [blas[]blabla] ", 2);
		assertDoubleClickSelection("blas[]blabla", " [blas[]blabla] ", 14);
	}

	@Test
	public void testBracketsUnclosed() {
		assertDoubleClickSelection("blas[]blabla ", " [blas[]blabla ", 2);
		assertDoubleClickSelection(" blas[]blabla", " blas[]blabla] ", 13);
	}

	@Test
	public void testComment() {
		assertDoubleClickSelection("comment\"\"comment", " \"comment\"\"comment\" ", 2);
		assertDoubleClickSelection("comment\"\"comment", " \"comment\"\"comment\" ", 18);
	}

	@Test
	public void testCommentUnlosed() {
		assertDoubleClickSelection("comment", " \"comment", 2);
		assertDoubleClickSelection("comment", "comment\" ", 7);
	}

	@Test
	public void testCompleteTextSelection() {
		assertDoubleClickSelection(" word1 word2 ", " word1 word2 ", 0);
		assertDoubleClickSelection(" word1 word2 ", " word1 word2 ", 13);
	}

	@Test
	public void testEmptyText() {
		assertDoubleClick(null, "", 0);
		assertDoubleClick(null, "", -777);
		assertDoubleClick(null, "", 777);
	}

	@Test
	public void testExpr() {
		assertDoubleClickSelection("a", " a+b ", 1);
		assertDoubleClick(null, " a+b ", 2);
		assertDoubleClickSelection("b", " a+b ", 3);
	}

	@Test
	public void testKeyword() {
		assertDoubleClickSelection("word1", "word1: ", 2);
	}

	@Test
	public void testParameter() {
		assertDoubleClickSelection("word1", " :word1", 2);
	}

	@Test
	public void testParens() {
		assertDoubleClickSelection("blas()blabla", " (blas()blabla) ", 2);
		assertDoubleClickSelection("blas()blabla", " (blas()blabla) ", 14);
	}

	@Test
	public void testString() {
		assertDoubleClickSelection("string''string", " 'string''string' ", 2);
		assertDoubleClickSelection("string''string", " 'string''string' ", 16);
	}

	@Test
	public void testStringUnlosed() {
		assertDoubleClickSelection("string", " 'string", 2);
		assertDoubleClickSelection("string", "string' ", 6);
	}

	@Test
	public void testSymbolWord() {
		assertDoubleClick(null, " #kw1:kw2: ", 1);
		assertDoubleClickSelection("kw1:kw2:", " #kw1:kw2: ", 2);
		assertDoubleClickSelection("kw1:kw2:", " #kw1:kw2: ", 3);
	}

	@Test
	public void testWord() {
		assertDoubleClickSelection("word1", " word1 word2 word3 ", 1);
		assertDoubleClickSelection("word1", " word1 word2 word3 ", 5);
		assertDoubleClick(null, " word1 word2 word3 ", 6);
		assertDoubleClickSelection("word1", "word1", 1);
	}

	private void assertDoubleClick(final IRegion expected, final String text, final int clickOffset) {
		final IRegion actual = strategy.doubleClicked(text, clickOffset);
		assertEquals(expected, actual);
	}

	private void assertDoubleClickSelection(final String expectedSelection, final String text, final int clickOffset) {
		final IRegion actual = strategy.doubleClicked(text, clickOffset);

		assertNotNull("Expected that region will be found", actual);
		assertTrue("Found region is out of text bounds " + actual.toString(),
				actual.getOffset() >= 0 && actual.getOffset() + actual.getLength() <= text.length());
		assertEquals(expectedSelection, text.substring(actual.getOffset(), actual.getOffset() + actual.getLength()));
	}
}
