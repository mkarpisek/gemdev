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

import net.karpisek.gemdev.net.SessionAction;

/**
 * Stops session server on which will be action executed.
 */
public class KillSessionServer extends SessionAction<String> {
	@Override
	public String asRequestString() {
		return "#('' '' #die #())"; //$NON-NLS-1$
	}

	@Override
	public String asResponse(final String responseString) {
		return ""; //$NON-NLS-1$
	}
}
