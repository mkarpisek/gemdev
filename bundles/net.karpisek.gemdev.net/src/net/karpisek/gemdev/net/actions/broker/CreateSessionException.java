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
package net.karpisek.gemdev.net.actions.broker;

import net.karpisek.gemdev.net.ActionException;

/**
 * Exception thrown in case session creation request fails in broker server.
 */
public class CreateSessionException extends ActionException {
	private static final long serialVersionUID = -8298556673560531891L;

	public CreateSessionException(final String actionName, final String message, final String request) {
		super(actionName, message, request, null);
	}

}
