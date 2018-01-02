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
package net.karpisek.gemdev.net.actions.clazz;

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;

/**
 * Answers all instance variable names of some class (own and inherited).
 */
public class GetAllInstVarNames extends SessionAction<Set<String>> {
	private String className;
	private boolean instanceSide;

	public GetAllInstVarNames(final String className) {
		this.className = className;
		this.instanceSide = true;

		// TODO: this is strange, is it necessary to parse className?
		if (className.endsWith(" class")) { //$NON-NLS-1$
			this.className = className.substring(0, className.indexOf(' '));
			this.instanceSide = false;
		}
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className, Boolean.toString(instanceSide));
		return createExecuteOperationRequest(script);
	}

	@Override
	public Set<String> asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);

		if (responseString.length() <= 0) {
			return Sets.newHashSet();
		}

		return Sets.newHashSet(responseString.split("\n")); //$NON-NLS-1$
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}
}
