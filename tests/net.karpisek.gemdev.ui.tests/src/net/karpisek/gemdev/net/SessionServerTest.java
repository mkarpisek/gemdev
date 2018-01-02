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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.tests.IIntegrationTests;

@Category({ IIntegrationTests.class })
public class SessionServerTest {
	@Test
	public void testCheckConnection_invalid() throws Exception {
		assertNotNull(SessionServer.checkConnection("0.0.0.0", 0));
	}

	@Test
	public void testCheckConnection_valid() throws Exception {
		assertNull(SessionServer.checkConnection(SessionClientTestCase.getTestServerIpAddress(), SessionClientTestCase.getTestServerPortNumber()));
	}
}
