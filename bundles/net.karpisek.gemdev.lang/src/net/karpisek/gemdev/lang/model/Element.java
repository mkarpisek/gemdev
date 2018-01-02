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

import net.karpisek.gemdev.lang.parser.GsTree;

import com.google.common.collect.Lists;

/**
 * Syntax element in method model.
 * 

 */
public abstract class Element {
	private final String name;
	private final MethodModel model;

	public Element(final String name, final MethodModel model) {
		this.name = name;
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public MethodModel getModel() {
		return model;
	}

	/**
	 * @return list of offsets on which this element occurs in source code of method.
	 */
	public List<Integer> getOffsets() {
		final List<Integer> offsets = Lists.newLinkedList();
		for (final GsTree tree : getOccurences()) {
			offsets.add(tree.getOffset());
		}

		return offsets;
	}

	public List<GsTree> getOccurences(){
		return Lists.newLinkedList();
	}

	protected void sortByOffset(final List<GsTree> nodes) {
		Collections.sort(nodes, new Comparator<GsTree>() {
			@Override
			public int compare(final GsTree t1, final GsTree t2) {
				if (t1.getOffset() < t2.getOffset()) {
					return -1;
				}
				if (t1.getOffset() > t2.getOffset()) {
					return 1;
				}
				return 0;
			}
		});
	}

	protected List<String> getOccurencesPositionStrings() {
		final List<String> positions = Lists.newLinkedList();
		for (final GsTree t : getOccurences()) {
			positions.add(String.format("(%d:%d)", t.getLine(), t.getCharPositionInLine())); //$NON-NLS-1$
		}
		return positions;
	}
}
