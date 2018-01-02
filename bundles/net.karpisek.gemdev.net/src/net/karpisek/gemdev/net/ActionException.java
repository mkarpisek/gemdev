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

public class ActionException extends RuntimeException {
	private static final long serialVersionUID = -8823793211861813895L;
	private final String actionName;
	private final String request;

	public ActionException(final String actionName, final String message, final String request, final Throwable cause) {
		super(message, cause);
		this.actionName = actionName;
		this.request = request;
	}

	/**
	 * Answers name of action for which the action execution failed.
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * Answers request for which the action execution failed.
	 * 
	 * @return
	 */
	public String getRequest() {
		return request;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s: %s failed (%s)", getClass().getName(), actionName, getMessage()));

		if (request != null && request.length() > 0) {
			sb.append(System.lineSeparator());
			sb.append("Request: ").append(request);
		}
		return sb.toString();
	}
}
