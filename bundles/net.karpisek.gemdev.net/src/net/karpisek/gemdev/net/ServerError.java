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

/**
 * This is error which occurred in broker or session server itself.
 */
public class ServerError extends ActionException {
	private static final long serialVersionUID = 6342494440428990007L;

	public ServerError(final String actionName, final String message, final String request) {
		super(actionName, message, request, null);
	}

}
