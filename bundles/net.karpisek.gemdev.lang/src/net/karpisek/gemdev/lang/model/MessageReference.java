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

import com.google.common.base.Preconditions;

/**
 * This is one call site of some message. For unary and binary it consists of one GsTree, for keyword messages it will consist of 1 or more GsStree instances.
 * 

 * 
 */
public class MessageReference {
	private final Element receiver;
	private final List<GsTree> parts;

	public MessageReference(final Element receiver, final List<GsTree> parts) {
		this.receiver = receiver;
		this.parts = parts;
	}

	public Element getReceiver() {
		return receiver;
	}

	public List<GsTree> getParts() {
		return parts;
	}

	/**
	 * Answers part of message on required index.
	 * 
	 * @param partIndex of message call, unary and binary messages has only 1 part, keyword messages can have >=1 parts.
	 * @return part of message call
	 */
	public GsTree getPart(final int partIndex) {
		Preconditions.checkArgument(partIndex >= 0 && partIndex < parts.size(), "Part index %s out of range <0,%s) )", partIndex, parts.size()); //$NON-NLS-1$

		return parts.get(partIndex);
	}
}
