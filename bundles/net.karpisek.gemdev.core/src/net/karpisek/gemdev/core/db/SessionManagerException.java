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
package net.karpisek.gemdev.core.db;

/**
 * Exception used by {@link SessionManager}.
 */
public class SessionManagerException extends RuntimeException {
	private static final long serialVersionUID = 632063429654824303L;

	public SessionManagerException(final String message) {
		super(message);
	}

	public SessionManagerException(final String message, final Throwable t) {
		super(message, t);
	}

}
