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
 * Answer all method selectors for class (instance or class side) or for one category.
 */
public class GetMethodNames extends SessionAction<Set<String>> {
	private final String className;
	private final boolean instanceSide;
	private final String categoryName;

	/**
	 * Creates action which answers all methods on instance or class side of class.
	 * 
	 * @param className should be name of existing DB class
	 * @param instanceSide true or false for class side
	 */
	public GetMethodNames(final String className, final boolean instanceSide) {
		this(className, instanceSide, null);
	}

	/**
	 * Creates action which answers all methods in category of methods in class.
	 * 
	 * @param className should be name of existing DB class
	 * @param instanceSide if category is on instance or class side
	 * @param categoryName for which we want to find all methods
	 */
	public GetMethodNames(final String className, final boolean instanceSide, final String categoryName) {
		this.className = className;
		this.instanceSide = instanceSide;
		this.categoryName = categoryName;
	}

	@Override
	public String asRequestString() {
		// TODO: refactor escaping smalltalk string to method
		final String script = newScriptWithArguments(getClass().getSimpleName(), className, Boolean.toString(instanceSide),
				categoryName == null ? "" : categoryName.replaceAll("'", "''") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		);
		return createExecuteOperationRequest(script);
	}

	@Override
	public Set<String> asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		if ("nil".equals(responseString)) { //$NON-NLS-1$
			return null;
		}

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
