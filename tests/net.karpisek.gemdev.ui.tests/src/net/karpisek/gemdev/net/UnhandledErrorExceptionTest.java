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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import net.karpisek.gemdev.net.actions.Evaluate;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

public class UnhandledErrorExceptionTest extends SessionClientTestCase {

	private final String expectedErrorRequest;

	public UnhandledErrorExceptionTest() {
		expectedErrorRequest = UiTestsPlugin.getDefault().getFileContents("/resources/net/UnhandledErrorExceptionTest_expectedErrorRequest.txt");
	}

	@Test
	public void test() {
		try {
			execute(new Evaluate("Object error: 'msg'"));
			fail("Expected to throw unhandled error exception");
		} catch (final UnhandledErrorException e) {
			UiTestsPlugin.getDefault().logError(e);
			assertEquals(expectedErrorRequest, e.getRequest());

			final String report = e.getGsStackReport();
			assertTrue("GsStackReport starts with wrong text", report.startsWith("1 ComplexBlock in Executed Code"));
		}
	}
}
