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

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.SessionAction;

/**
 * Creates new class in DB. Does not perform commit or abort, that is responsibility of client.
 */
public class CreateClass extends SessionAction<String> {
	/**
	 * Use this builder class for creating class with own definition message.
	 */
	public static class Builder {
		private final String superclassName;
		private final String className;
		private List<String> instVars;
		private List<String> classVars;
		private List<String> classInstVars;
		private String dictionary;
		private final String category;

		public Builder(final String superclassName, final String className, final String category) {
			this.superclassName = superclassName;
			this.className = className;
			this.instVars = Lists.newLinkedList();
			this.classVars = Lists.newLinkedList();
			this.classInstVars = Lists.newLinkedList();
			this.dictionary = "UserGlobals"; //$NON-NLS-1$
			this.category = category;
		}

		public CreateClass build() {
			// TODO: refactor to text file?
			final String definition = String.format("%s subclass: '%s'\n" + //$NON-NLS-1$
					"	instVarNames: #(%s)\n" + //$NON-NLS-1$
					"	classVars: #(%s)\n" + //$NON-NLS-1$
					"	classInstVars: #(%s)\n" + //$NON-NLS-1$
					"	poolDictionaries: #[]\n" + //$NON-NLS-1$
					"	inDictionary: %s\n" + //$NON-NLS-1$
					"	instancesInvariant: false\n" + "	isModifiable: true",

					superclassName, className, Joiner.on(" ").join(instVars), //$NON-NLS-1$
					Joiner.on(" ").join(classVars), //$NON-NLS-1$
					Joiner.on(" ").join(classInstVars), //$NON-NLS-1$
					dictionary
			// TODO: with seaside/glass in image? category
			);

			return new CreateClass(definition);
		}

		public Builder classInstVars(final List<String> names) {
			this.classInstVars = Lists.newArrayList(names.iterator());
			return this;
		}

		public Builder classVars(final List<String> names) {
			this.classVars = Lists.newArrayList(names.iterator());
			return this;
		}

		public Builder dictionary(final String name) {
			this.dictionary = name;
			return this;
		}

		public Builder instVars(final List<String> names) {
			this.instVars = Lists.newArrayList(names.iterator());
			return this;
		}
	}

	private final String definition;

	/**
	 * @param definition message used for creating classes
	 */
	public CreateClass(final String definition) {
		this.definition = definition;
	}

	@Override
	public String asRequestString() {
		final String expr = String.format("[(%s)name asString] on: ExceptionA do:[:e|'NewClassError',e description]", definition); //$NON-NLS-1$
		return createExecuteOperationRequest(expr);
	}

	/**
	 * @return name of created class
	 */
	@Override
	public String asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);

		final String errorHeader = "NewClassError"; //$NON-NLS-1$
		if (responseString.startsWith(errorHeader)) {
			throw new ActionException(getClass().getSimpleName(), responseString.substring(errorHeader.length() + 1), asRequestString(), null);
		}
		return responseString;
	}

	/**
	 * @return expression which is used for creating class on action execution
	 */
	public String getDefinition() {
		return definition;
	}

}
