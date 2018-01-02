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
 * Exception which was not caught on GS was thrown.
 */
public class UnhandledErrorException extends ActionException {

	private static final long serialVersionUID = 5127961713205220227L;

	private final String gsStackReport;

	public UnhandledErrorException(final String actionName, final String message, final String request, final String gsStackReport) {
		super(actionName, message, request, null);
		this.gsStackReport = gsStackReport;
	}

	/**
	 * @return stack report as reported by original GS stack report method.
	 */
	public String getGsStackReport() {
		return gsStackReport;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(super.toString());

		if (gsStackReport != null && gsStackReport.length() > 0) {
			sb.append(System.lineSeparator());
			sb.append(gsStackReport);
		}
		return sb.toString();
	}

}
