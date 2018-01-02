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
import java.util.Set;

import net.karpisek.gemdev.lang.parser.GsTree;

import org.antlr.runtime.tree.CommonTree;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Variable and parameters scope context.
 * (for method and block contexts).

 */
public class Context {
	private final Context parent;
	private final List<Context> children;

	private final List<GsTree> identifierDeclarations;
	private final List<GsTree> identifierReferences;

	public Context(final Context parent){
		this.parent = parent;
		this.children = Lists.newLinkedList();
		this.identifierDeclarations = Lists.newLinkedList();
		this.identifierReferences = Lists.newLinkedList();

		if(parent != null){
			parent.children.add(this);
		}
	}

	public List<GsTree> getIdentifierDeclarations() {
		return identifierDeclarations;
	}

	public List<GsTree> getIdentifierReferences() {
		return identifierReferences;
	}

	public Set<String> getDeclaredIdentifierNames(){
		final Set<String> result = Sets.newHashSet();
		for (final CommonTree id : identifierDeclarations) {
			result.add(id.getText());
		}
		return result;
	}

	/**
	 * For this context identifier as declared (do not care about duplicates)
	 * Identifier means method or block parameter or method or block temporary variable.
	 */
	void declareIdentifier(final GsTree id){
		identifierDeclarations.add(id);
		id.setContext(this);
	}

	void referenceIdentifier(final GsTree id){
		identifierReferences.add(id);
		id.setContext(this);
	}

	/**
	 * Find declaration of identifier in this or any of receiver parent contexts.
	 * @return declaration node or null if nothing is found
	 */
	public GsTree resolve(final String id) {
		for (final GsTree decl : identifierDeclarations) {
			if(id.equals(decl.getText())){
				return decl;
			}
		}
		if(parent != null){
			return parent.resolve(id);
		}
		return null;
	}
}
