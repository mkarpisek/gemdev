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
package net.karpisek.gemdev.ui.browser.methods;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jface.dialogs.IInputValidator;

import net.karpisek.gemdev.lang.ParserUtils;
import net.karpisek.gemdev.ui.Messages;

/**
 * Performs checks if tested string can be used as method name. Checks are: 1) name must not be empty 2) name must be unique 3) name must be valid smalltalk
 * selector.
 */
public class MethodNameValidator implements IInputValidator {
	private final Collection<String> existingNames;

	public MethodNameValidator(final Set<String> existingNames) {
		this.existingNames = existingNames;
	}

	@Override
	public String isValid(final String newText) {
		if (newText == null || newText.isEmpty()) {
			return Messages.METHOD_NAME_IS_EMPTY_ERROR;
		}

		if (existingNames.contains(newText)) {
			return Messages.METHOD_ALREADY_EXISTS_ERROR;
		}

		if (ParserUtils.parseSelector(newText) == null) {
			return Messages.INVALID_METHOD_SELECTOR;
		}

		return null;
	}

}
