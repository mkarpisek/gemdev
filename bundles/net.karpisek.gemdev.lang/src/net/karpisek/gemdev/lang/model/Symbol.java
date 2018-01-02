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
package net.karpisek.gemdev.lang.model;

import java.util.List;

import net.karpisek.gemdev.lang.parser.GsTree;

import com.google.common.base.Joiner;

/**
 * Represents symbol syntax element (it has offset starting after '#').
 * 

 */
public class Symbol extends Element {
	private final List<GsTree> references;

	public Symbol(final String name, final List<GsTree> references, final MethodModel model) {
		super(name, model);
		this.references = references;
	}

	public List<GsTree> getReferences() {
		return references;
	}

	@Override
	public List<GsTree> getOccurences() {
		final List<GsTree> result = super.getOccurences();
		result.addAll(references);
		sortByOffset(result);
		return result;
	}

	@Override
	public String toString() {
		return String.format("#%s[%s]", getName(), Joiner.on(",").join(getOccurencesPositionStrings()));  //$NON-NLS-1$//$NON-NLS-2$
	}
}
