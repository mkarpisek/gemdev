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
package net.karpisek.gemdev.net.actions;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;

/**
 * Answers string with sources in topaz filein format.
 */
public class GetSources extends SessionAction<String> {
	private enum Target {
		CLASS, CATEGORY, METHOD
	}

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$ ;

	public static GetSources forCategory(final String className, final boolean instanceSide, final String categoryName) {
		return new GetSources(Target.CATEGORY, className, instanceSide, categoryName, EMPTY_STRING);
	}

	public static GetSources forClass(final String className) {
		return new GetSources(Target.CLASS, className, true, EMPTY_STRING, EMPTY_STRING);
	}

	public static GetSources forMethod(final String className, final boolean instanceSide, final String methodName) {
		return new GetSources(Target.METHOD, className, instanceSide, EMPTY_STRING, methodName);
	}

	private final Target target;
	private final String className;
	private final boolean instanceSide;
	private final String categoryName;
	private final String methodName;

	private GetSources(final Target target, final String className, final boolean instanceSide, final String categoryName, final String methodName) {
		this.target = target;
		this.className = className;
		this.instanceSide = instanceSide;
		this.categoryName = categoryName;
		this.methodName = methodName;
	}

	@Override
	public String asRequestString() {
		final String script = newScriptWithArguments(getClass().getSimpleName(), target.toString().toLowerCase(), className, (instanceSide ? "true" : "false"), //$NON-NLS-1$//$NON-NLS-2$
				categoryName.replaceAll("'", "''"), methodName.replace("'", "''") //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$
		);
		return createExecuteOperationRequest(script);
	}

	@Override
	public String asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);
		return responseString;
	}

	@Override
	public boolean isIdempotent() {
		return true;
	}

}
