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
 * Answers all methods where is class referenced and offset into method source code where this reference first occurs.
 */
public class GetAllReferencesToClass extends SessionAction<Map<MethodReference, Integer>> {
	private final String className;

	public GetAllReferencesToClass(final String className) {
		this.className = className;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className);

		return createExecuteOperationRequest(script);
	}

	/**
	 * @return map<referencing method, offset to source code(zero based)> of all methods referencing class
	 */
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
