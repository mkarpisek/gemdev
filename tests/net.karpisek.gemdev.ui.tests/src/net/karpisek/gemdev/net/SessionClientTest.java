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
package net.karpisek.gemdev.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class SessionClientTest extends SessionClientTestCase {
	private class TestAction extends SessionAction<String> {
		private final String requestString;

		public TestAction(final String requestString) {
			this.requestString = requestString;
		}

		@Override
		public String asRequestString() {
			return requestString;
		}

		@Override
		public String asResponse(final String responseString) {
			throw new UnsupportedOperationException("not implemented for testing");
		}

	}

	@Test
	public void testMalformedRequest() {
		final String requestString = "&&&$@^%";
		try {
			execute(new TestAction(requestString));
			fail("Should throw exception");
		} catch (final ServerError e) {
			assertEquals("Request body is not Array of size 4, it is nil.", e.getMessage());
		}
	}

	@Test
	public void testUnknownOperationError() {
		final String requestString = "#('' '' #undefinedOperationName #())";
		try {
			execute(new TestAction(requestString));
			fail("Should throw exception");
		} catch (final ServerError e) {
			assertEquals("Unknown operation 'undefinedOperationName'.", e.getMessage());
		}
	}
}
