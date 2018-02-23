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
 * Answers basic information about running session server.
 */
public class GetSessionServerInfo extends SessionAction<SessionServerInfo> {
	@Override
	public String asRequestString() {
		return "#('' '' #info #())"; //$NON-NLS-1$
	}

	@Override
	public SessionServerInfo asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		final String[] lines = responseString.split("\n"); //$NON-NLS-1$

		int i = 0;
		return new SessionServerInfo(Integer.valueOf(lines[i++]), lines[i++], lines[i++], lines[i++], lines[i++], lines[i++]);
	}
}
