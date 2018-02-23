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
import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Answers all methods with method name.
 */
public class GetAllImplementors extends SessionAction<Set<MethodReference>> {
	private final String selector;

	public GetAllImplementors(final String selector) {
		this.selector = selector;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), selector.replaceAll("'", "''") //$NON-NLS-1$//$NON-NLS-2$
		);

		return createExecuteOperationRequest(script);
	}

	@Override
	public Set<MethodReference> asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		final Set<MethodReference> result = Sets.newHashSet();
		if ("".equals(responseString)) { //$NON-NLS-1$
			return result;
		}
		for (final String methodRefReport : responseString.split("\n")) { //$NON-NLS-1$
			final String[] array = methodRefReport.split(" "); //$NON-NLS-1$

			result.add(new MethodReference(array[0], array[1].equals("i") ? true : false, selector)); //$NON-NLS-1$

		}
		return result;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

}
