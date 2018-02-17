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

import com.google.common.base.Joiner;

import net.karpisek.gemdev.lang.Messages;
import net.karpisek.gemdev.lang.parser.GsTree;

/**
 * Represents identifier declared locally in method (method parameter, block parameter or temporary variable)
 * 

 */
public class LocalIdentifier extends Identifier {
	public enum Type {
		TMP_VAR(Messages.TEMPORARY_VARIABLE_LABEL),
		METHOD_PARAMETER(Messages.METHOD_PARAMETER_LABEL),
		BLOCK_PARAMETER(Messages.BLOCK_PARAMETER_LABEL);

		private String label;

		Type(final String label){
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	private final Type type;
	private final GsTree declaration;

	public LocalIdentifier(final Type type, final GsTree declaration, final List<GsTree> references, final MethodModel model) {
		super(declaration.getText(), references, model);
		this.type = type;
		this.declaration = declaration;
	}

	public Type getType() {
		return type;
	}

	@Override
	public GsTree getDeclaration() {
		return declaration;
	}

	@Override
	public List<GsTree> getOccurences() {
		final List<GsTree> result = super.getOccurences();
		result.add(declaration);
		sortByOffset(result);
		return result;
	}

	@Override
	public String toString() {
		return String.format(
			"%s %s[%s]", //$NON-NLS-1$
			getType().getLabel(),
			getName(),
			Joiner.on(",").join(getOccurencesPositionStrings()) //$NON-NLS-1$
		);
	}
}
