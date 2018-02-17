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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.lang.parser.GsTree;

/**
 * Message sent in method model.

 */
public class Message extends Element{
	public enum Type{UNARY, BINARY, KEYWORD}
	private final Type type;
	private final List<MessageReference> references;

	public Message(final String name, final Type type, final List<MessageReference> references, final MethodModel model) {
		super(name, model);
		this.type = type;
		this.references = references;

		sortByFirstOffset(references);
	}

	public Type getType() {
		return type;
	}

	public List<MessageReference> getReferences() {
		return references;
	}

	@Override
	public List<GsTree> getOccurences() {
		final List<GsTree> result = super.getOccurences();
		for (final MessageReference reference : references) {
			result.addAll(reference.getParts());
		}
		sortByOffset(result);
		return result;
	}

	@Override
	public String toString() {
		if(getType() == Type.UNARY || getType() == Type.BINARY){
			return String.format("%sMsg %s[%s]", getType().toString().toLowerCase(), getName(), Joiner.on(",").join(getOccurencesPositionStrings())); //$NON-NLS-1$ //$NON-NLS-2$
		}

		final List<String> allOffsets = Lists.newLinkedList();
		for (final MessageReference reference : references) {
			final List<String> positionStrings = Lists.newLinkedList();
			for (final GsTree t : reference.getParts()) {
				positionStrings.add(String.format("(%d:%d)", t.getLine(), t.getCharPositionInLine())); //$NON-NLS-1$
			}
			allOffsets.add("["+Joiner.on(",").join(positionStrings) + "]");   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		}
		return String.format("%sMsg %s[%s]", getType().toString().toLowerCase(), getName(), Joiner.on(",").join(allOffsets)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected void sortByFirstOffset(final List<MessageReference> nodes) {
		Collections.sort(nodes, new Comparator<MessageReference>() {
			@Override
			public int compare(final MessageReference t1, final MessageReference t2) {
				if (t1.getPart(0).getOffset() < t2.getPart(0).getOffset()) {
					return -1;
				}
				if (t1.getPart(0).getOffset() > t2.getPart(0).getOffset()) {
					return 1;
				}
				return 0;
			}
		});
	}

	public MessageReference getReferenceAtOffset(final int offset) {
		for (final MessageReference ref : references) {
			for (final GsTree part : ref.getParts()) {
				if(part.getOffset() == offset){
					return ref;
				}
			}
		}
		return null;
	}
}
