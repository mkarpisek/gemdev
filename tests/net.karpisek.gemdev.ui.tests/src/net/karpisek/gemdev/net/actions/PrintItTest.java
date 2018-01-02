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
package net.karpisek.gemdev.net.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.karpisek.gemdev.net.SessionClientTestCase;

public class PrintItTest extends SessionClientTestCase {
	@Test
	public void testEvaluateBasicExpression() {
		final String result = execute(new PrintIt("1+2"));
		assertEquals("3", result);
	}
}
