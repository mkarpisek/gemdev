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
package net.karpisek.gemdev.ui.utils;

import java.util.List;

import com.google.common.collect.BiMap;

/**
 * Generic tree consisting of TreeNode instances.
 * 
 * @param <T> any object
 */
public class Tree<T> {
	private final List<? extends TreeNode<T>> roots;
	private final BiMap<T, ? extends TreeNode<T>> nodes;

	protected Tree(final List<? extends TreeNode<T>> roots, final BiMap<T, ? extends TreeNode<T>> nodes) {
		this.roots = roots;
		this.nodes = nodes;
	}

	public TreeNode<T> getNode(final T nodeValue) {
		return nodes.get(nodeValue);
	}

	public List<? extends TreeNode<T>> getRoots() {
		return roots;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final TreeNode<T> node : roots) {
			sb.append(toString(node));
		}
		return sb.toString();
	}

	private String toString(final TreeNode<T> node) {
		if (node.getChildren().isEmpty()) {
			return node.toString();
		}
		final StringBuilder sb = new StringBuilder();
		sb.append("("); //$NON-NLS-1$
		sb.append(node.toString());
		for (final TreeNode<T> ch : node.getChildren()) {
			sb.append(" ").append(toString(ch)); //$NON-NLS-1$
		}
		sb.append(")"); //$NON-NLS-1$
		return sb.toString();
	}
}
