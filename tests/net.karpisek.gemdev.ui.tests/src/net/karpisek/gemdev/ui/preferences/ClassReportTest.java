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
package net.karpisek.gemdev.ui.preferences;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

public class ClassReportTest extends SessionClientTestCase {
	@Test
	public void test() {
		final String script = GemDevUiPlugin.getDefault().getScript("ClassReport");
		final String expr = String.format(script, "ScaledDecimal");
		final String actual = execute(new PrintIt(expr));
		assertTrue(actual.length() > 0);
	}
}
