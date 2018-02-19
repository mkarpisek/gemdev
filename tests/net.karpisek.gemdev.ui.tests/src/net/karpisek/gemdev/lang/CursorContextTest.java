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
package net.karpisek.gemdev.lang;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.tests.IUnitTests;

@Category({ IUnitTests.class })
public class CursorContextTest {
	@Test
	public void testKeywordMessage() {
		assertContext(" some_rec _kw1:kw2:x ", 20, "_kw1:kw2:x", "some_rec");
	}

	@Test
	public void testNoPrefix() {
		assertContext("", 0, "", "");
	}

	@Test
	public void testNoPrefix_receiver() {
		assertContext("rec  ", 5, "", "rec");
	}

	@Test
	public void testNoPrefix_receiver_hasToSkipWhitespace() {
		assertContext("rec \t	  ", 8, "", "rec");
	}

	@Test
	public void testNormal() {
		assertContext("rec msg", 7, "msg", "rec");
	}

	@Test
	public void testPrefix_noReceiver() {
		assertContext("msg", 3, "msg", "");
	}

	@Test
	public void testPrefix_noReceiver_someWhitespace() {
		assertContext(" \t 	msg", 7, "msg", "");
	}

	private void assertContext(final String line, final int offset, final String expectedPrefix, final String expectedReceiver) {
		final CursorContext context = CursorContext.analyze(line, offset);

		assertEquals(expectedPrefix, context.getPrefix());
		assertEquals(expectedReceiver, context.getReceiver());
	}
}
