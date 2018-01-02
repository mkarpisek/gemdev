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

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Sends arbitrary string into database and returns it back (round trip)
 */
public class Echo extends SessionAction<String> {
	private final String originalMessage;

	public Echo(final String message) {
		this.originalMessage = message;
	}

	@Override
	public String asRequestString() {
		return String.format("#('' '' #echo #('%s'))", originalMessage.replaceAll("'", "''")); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}

	@Override
	public String asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		return responseString;
	}
}
