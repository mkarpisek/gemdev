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
package net.karpisek.gemdev.ui.console;

import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Represents line position in source code of some smalltalk method. Created for one frame of stack trace. Syntax is of line is default gemstone generated code.
 * <line number> ' ' <className> '>>' <selector> 'line' <lineNumber>
 */
public class MethodCodeReference {
	public static MethodCodeReference on(final String line) {
		if (Strings.isNullOrEmpty(line)) {
			return null;
		}

		final String s = Strings.nullToEmpty(line).trim();
		if (Strings.isNullOrEmpty(s)) {
			return null;
		}

		final List<String> tokens = Lists.newArrayList(Splitter.on(CharMatcher.whitespace()).trimResults().split(s));
		final int i = tokens.indexOf(">>");
		if (i < 1 || i >= tokens.size() - 1) {
			// line did not contained >> or is first in line,
			// then we are assuming that line does not contain method descriptor in form Class name>>selector
			return null;
		}

		// one token before '>>' should be className, or className + 'class' token
		boolean instanceSide = true;
		String className = tokens.get(i - 1);
		if (className.equals("class")) {
			instanceSide = false;
			if (i - 2 < 0) {
				// we are out of line and missing className itself - that should not happen
				return null;
			}
			className = tokens.get(i - 2);
		}

		final String methodName = tokens.get(i + 1);
		final int i2 = tokens.indexOf("line");
		if (i2 == -1) {
			return null;
		}
		// line number should have only digits
		final String lineNumberString = tokens.get(i2 + 1);
		final Integer ln = Ints.tryParse(lineNumberString);
		if (ln == null) {
			return null;
		}

		return new MethodCodeReference(new MethodReference(className, instanceSide, methodName), ln);
	}

	private final MethodReference methodReference;
	private final int lineNumber;

	public MethodCodeReference(final MethodReference methodReference, final int lineNumber) {
		this.methodReference = methodReference;
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public MethodReference getMethodReference() {
		return methodReference;
	}

	@Override
	public String toString() {
		return String.format("[line %d] %s", getLineNumber(), getMethodReference());
	}
}
