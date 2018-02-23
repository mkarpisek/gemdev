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

 */
public class RemoveFromExportSet extends SessionAction<String> {
	private final String oop;

	/**
	 * @param oop of object in export set, for null or if object is not in export set or object does not exists results in no-op
	 */
	public RemoveFromExportSet(final String oop) {
		this.oop = oop;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), oop == null ? "''" : oop //$NON-NLS-1$
		);

		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) {
		// nothing to do
		return responseString;
	}
}
