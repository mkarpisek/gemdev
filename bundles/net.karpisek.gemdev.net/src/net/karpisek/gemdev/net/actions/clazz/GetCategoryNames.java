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

import net.karpisek.gemdev.net.SessionAction;

/**
 * Answers all category names on one side of class.
 */
public class GetCategoryNames extends SessionAction<Set<String>> {
	private final String className;
	private final boolean instanceSide;

	/**
	 * @param className should be valid smalltalk class name
	 * @param instanceSide should be true for instance side or false for class side
	 */
	public GetCategoryNames(final String className, final boolean instanceSide) {
		this.className = className;
		this.instanceSide = instanceSide;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), className, Boolean.toString(instanceSide));
		return createExecuteOperationRequest(script);
	}

	@Override
	public Set<String> asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		if (responseString.length() <= 0) {
			return Sets.newHashSet();
		}

		// refactor splitting of string by LF to separate method
		return Sets.newHashSet(responseString.split("\n")); //$NON-NLS-1$
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}
}
