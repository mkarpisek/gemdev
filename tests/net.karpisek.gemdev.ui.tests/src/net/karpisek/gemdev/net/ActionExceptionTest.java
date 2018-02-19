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

import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

@Category({ IUnitTests.class })
public class ActionExceptionTest {
	@Test
	public void test() {
		final String actionName = "Zzz";
		final String details = "something went wrong";
		final String request = "some request text";

		try {
			throw new ActionException(actionName, details, request, null);
		} catch (final ActionException e) {
			assertEquals(actionName, e.getActionName());
			assertEquals(request, e.getRequest());
			UiTestsPlugin.getDefault().logError(e);
		}
	}
}
