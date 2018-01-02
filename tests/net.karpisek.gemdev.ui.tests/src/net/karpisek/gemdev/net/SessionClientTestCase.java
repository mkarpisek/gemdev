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

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.net.actions.system.GetAllClassNames;
import net.karpisek.gemdev.ui.tests.IIntegrationTests;

@Category({ IIntegrationTests.class })
public abstract class SessionClientTestCase {
	/**
	 * This env. variable has to be set before running tests (for vmware for example 192.168.140.130)
	 */
	public static String getTestServerIpAddress() {
		return "localhost";
	}

	/**
	 * This env. variable has to be set before running tests (for example 2708 (session server))
	 */
	public static int getTestServerPortNumber() {
		return 2708;
	}

	protected SessionClient client;

	public void assertClassExists(final String className, final boolean shouldExist) {
		final Set<String> classNames = execute(new GetAllClassNames());
		assertEquals(shouldExist ? "DbClass " + className + "should exist" : "DbClass " + className + " should not exist", shouldExist,
				classNames.contains(className));
	}

	public <T> T execute(final ISessionAction<T> action) {
		return client.execute(action);
	}

	@Before
	public void setUp() throws Exception {
		client = new SessionClient(getTestServerIpAddress(), getTestServerPortNumber());
	}

	@After
	public void tearDown() throws Exception {
		client.close();
	}
}
