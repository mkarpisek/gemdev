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

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;
import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Answers all methods which contains substring in source code and offset into method source code where this reference first occurs.
 */
public class GetAllMethodsWithSubstring extends SessionAction<Map<MethodReference, Integer>> {
	private final String searchString;
	private final boolean caseSensitive;

	public GetAllMethodsWithSubstring(final String searchString, final boolean caseSensitive) {
		this.searchString = searchString;
		this.caseSensitive = caseSensitive;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), searchString.replaceAll("'", "''"), (caseSensitive ? "true" : "false") //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		);
		return createExecuteOperationRequest(script);
	}

	@Override
	public Map<MethodReference, Integer> asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);

		final Map<MethodReference, Integer> result = Maps.newHashMap();
		if ("".equals(responseString)) { //$NON-NLS-1$
			return result;
		}

		for (final String methodRefReport : responseString.split("\n")) { //$NON-NLS-1$
			final String[] tokens = methodRefReport.split(" "); //$NON-NLS-1$

			result.put(new MethodReference(tokens[0], tokens[1].equals("i") ? true : false, tokens[2]), //$NON-NLS-1$
					Integer.valueOf(tokens[3]) - 1);

		}
		return result;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

}
