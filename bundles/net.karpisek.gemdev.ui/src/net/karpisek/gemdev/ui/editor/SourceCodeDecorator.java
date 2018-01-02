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
package net.karpisek.gemdev.ui.editor;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import net.karpisek.gemdev.net.actions.CompilationError;

/**
 * Takes source code and inserts error messages into it.
 */
public class SourceCodeDecorator {
	private final CompilationError error;
	private final String decoratedSourceCode;
	private final IRegion decoratedRegion;

	public SourceCodeDecorator(final CompilationError error) {
		this.error = error;

		final String original = error.getSourceCode();
		final String prefix = original.substring(0, error.getOffset());
		final String postfix = original.substring(error.getOffset(), original.length());
		final String errorMessage = String.format(" %s->", error.getMessage()); //$NON-NLS-1$
		decoratedRegion = new Region(error.getOffset(), errorMessage.length());
		decoratedSourceCode = prefix + errorMessage + postfix;
	}

	public String getDecoratedSourceCode() {
		return decoratedSourceCode;
	}

	public IRegion getFirstDecoratedRegion() {
		return decoratedRegion;
	}

	public String getOriginalSourceCode() {
		return error.getSourceCode();
	}

	@Override
	public String toString() {
		return getDecoratedSourceCode();
	}
}
