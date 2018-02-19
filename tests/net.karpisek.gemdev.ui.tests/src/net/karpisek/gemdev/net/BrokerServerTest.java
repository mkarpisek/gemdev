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
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.net.actions.broker.GetBrokerServerInfo;
import net.karpisek.gemdev.net.actions.broker.GetBrokerServerInfo.Info;
import net.karpisek.gemdev.net.actions.broker.Profile;
import net.karpisek.gemdev.ui.tests.IIntegrationTests;
import net.karpisek.gemdev.utils.Utils;

@Category({ IIntegrationTests.class })
public class BrokerServerTest {
	private static final int PORT_NUMBER = 2707;
	private static final Profile VALID = new Profile("vboxstone", "DataCurator", Utils.md5sum("swordfish"));

	@Test
	public void testGetInfo() {
		final BrokerServerClient client = new BrokerServerClient(SessionClientTestCase.getTestServerIpAddress(), PORT_NUMBER);
		final Info info = client.execute(new GetBrokerServerInfo());
		assertEquals(PORT_NUMBER, info.getPortNumber());
		assertEquals(Lists.newArrayList(VALID.getName()), info.getProfileNames());
	}

	@Test
	public void testCheckConnection_invalidIpAddress() {
		assertEquals("Failed to connect to broker server.", BrokerServer.checkConnection("0.0.0.0", 0, VALID));
	}

	@Test
	public void testCheckConnection_invalidPassword() {
		final Profile p = new Profile(VALID.getName(), VALID.getUserName(), "wrongPassword");
		assertEquals("Wrong user or password.", BrokerServer.checkConnection(SessionClientTestCase.getTestServerIpAddress(), PORT_NUMBER, p));
	}

	@Test
	public void testCheckConnection_invalidProfileName() {
		final Profile p = new Profile("unknownProfileName", VALID.getUserName(), VALID.getPassword());
		assertEquals("Unknown connection profile \"unknownProfileName\".",
				BrokerServer.checkConnection(SessionClientTestCase.getTestServerIpAddress(), 2707, p));
	}

	@Test
	public void testCheckConnection_invalidUsername() {
		final Profile p = new Profile(VALID.getName(), "wrongUsername", VALID.getPassword());
		assertEquals("Wrong user or password.", BrokerServer.checkConnection(SessionClientTestCase.getTestServerIpAddress(), 2707, p));
	}

	@Test
	public void testCheckConnection_valid() {
		assertNull(BrokerServer.checkConnection(SessionClientTestCase.getTestServerIpAddress(), PORT_NUMBER, VALID));
	}
}
