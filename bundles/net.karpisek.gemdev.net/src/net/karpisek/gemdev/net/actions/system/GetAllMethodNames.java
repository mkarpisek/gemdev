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

import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Answers all used names of methods (selectors of implementors).
 */
public class GetAllMethodNames extends SessionAction<Set<String>> {
	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName());
		return createExecuteOperationRequest(script);
	}

	/**
	 * @return set of method names
	 */
	@Override
	public Set<String> asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		if ("".equals(responseString)) { //$NON-NLS-1$
			return Sets.newHashSet();
		}

		return Sets.newHashSet(responseString.split("\n")); //$NON-NLS-1$
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

}
