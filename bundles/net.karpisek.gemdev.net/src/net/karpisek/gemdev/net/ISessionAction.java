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
 * Common interface which has to implement all actions performed by {@link SessionServer} in GS.
 *
 * @param <T> type of object to which is result(response) of action converted
 */
public interface ISessionAction<T> {
	/**
	 * Convert receiver to string which can be executed by {@link SessionServer} in GS.
	 */
	public String asRequestString();

	/**
	 * Convert response from SessionServer to response java object
	 * 
	 * @param responseString from {@link SessionServer}
	 * @return java object
	 * @throws ActionException in case something goes wrong
	 */
	public T asResponse(String responseString);

	/**
	 * If action is idempotent it can be automatically retried without side effects.
	 * 
	 * @return true if action can be retried without side effects.
	 */
	public boolean isIdempotent();
}
