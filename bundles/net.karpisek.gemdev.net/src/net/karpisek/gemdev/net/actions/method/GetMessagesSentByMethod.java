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
package net.karpisek.gemdev.net.actions.method;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Answers set of method names which are sent by method.
 */
public class GetMessagesSentByMethod extends SessionAction<Set<String>> {
	private final String className;
	private final boolean instanceSide;
	private final String methodName;

	public GetMessagesSentByMethod(final String className, final boolean instanceSide, final String methodName) {
		this.className = className;
		this.instanceSide = instanceSide;
		this.methodName = methodName;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className, Boolean.toString(instanceSide), methodName.replaceAll("'", "''") //$NON-NLS-1$//$NON-NLS-2$
		);
		return createExecuteOperationRequest(script);
	}

	@Override
	public Set<String> asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		final Set<String> result = Sets.newHashSet();
		if ("".equals(responseString)) { //$NON-NLS-1$
			return result;
		}
		if ("nil".equals(responseString)) { //$NON-NLS-1$
			return null;
		}
		for (final String selector : responseString.split("\n")) { //$NON-NLS-1$
			result.add(selector);
		}

		return result;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

}
