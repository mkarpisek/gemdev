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
package net.karpisek.gemdev.lang.parser;

import net.karpisek.gemdev.lang.ParserUtils;
import net.karpisek.gemdev.lang.model.Context;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.jface.text.IRegion;

/**
 * Own AST which contain defined context level.
 * User {@link ParserUtils#parse(String)} for default parsing.

 */
public class GsTree extends CommonTree implements IRegion{
	private Context context;
	private int offset;

	public GsTree(){

	}

	public GsTree(final Token t){
		super(t);
	}

	public Context getContext() {
		return context;
	}

	public void setContext(final Context context) {
		this.context = context;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	public void setOffset(final int offset) {
		this.offset = offset;
	}

	@Override
	public int getLength() {
		return getText().length();
	}

	/**
	 * Go up the hierarchy and return first node of requested type.
	 * @param type
	 * @return ndoe or null if nothing is found
	 */
	public GsTree getParentOfType(final int type){
		final GsTree p = (GsTree) getParent();
		if(p != null){
			if(p.getType() == type){
				return p;
			}
			return p.getParentOfType(type);
		}
		return null;
	}
}
