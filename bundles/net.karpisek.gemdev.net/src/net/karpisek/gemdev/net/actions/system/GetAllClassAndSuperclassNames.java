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
package net.karpisek.gemdev.net.actions.system;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Answers all classes together with names of their super classes.
 */
public class GetAllClassAndSuperclassNames extends SessionAction<Map<String, String>> {
	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName());
		return createExecuteOperationRequest(script);
	}

	/**
	 * @return map containing pairs <className, superClassName>, if object does not have superclass than value is null
	 */
	@Override
	public Map<String, String> asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		if ("".equals(responseString)) { //$NON-NLS-1$
			return Maps.newHashMap();
		}

		final String[] pairsStrings = responseString.split("\n"); //$NON-NLS-1$

		final Map<String, String> classNameToSuperclassName = Maps.newHashMap();
		for (final String pairString : pairsStrings) {
			final String[] pair = pairString.split(" "); //$NON-NLS-1$

			Preconditions.checkArgument(pair.length == 2);
			classNameToSuperclassName.put(pair[0], pair[1]);
		}

		return classNameToSuperclassName;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}
}
