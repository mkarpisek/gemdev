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

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;
import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Answers all methods (implementors) in system as set of method references.
 */
public class GetAllMethods extends SessionAction<Set<MethodReference>> {

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName());
		return createExecuteOperationRequest(script);
	}

	@Override
	public Set<MethodReference> asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);

		final Set<MethodReference> result = Sets.newHashSet();
		if ("".equals(responseString)) { //$NON-NLS-1$
			return result;
		}

		for (final String line : responseString.split("\n")) { //$NON-NLS-1$
			final String[] tokens = line.split(" "); //$NON-NLS-1$
			final String className = tokens[0];
			final boolean instanceSide = "i".equals(tokens[1]); //$NON-NLS-1$

			for (int i = 2; i < tokens.length; i++) {
				result.add(new MethodReference(className, instanceSide, tokens[i]));
			}
		}
		return result;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

}
