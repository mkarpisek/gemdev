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

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * Generic node implementation for {@link Tree}.
 * 
 * @param <T>
 */
public class TreeNode<T> {
	private TreeNode<T> parent;
	private List<TreeNode<T>> children;
	private T value;
	private boolean enabled;

	public TreeNode(final T value) {
		this.children = Lists.newLinkedList();
		this.value = value;
		this.enabled = true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof TreeNode) {
			final TreeNode<T> other = (TreeNode<T>) obj;
			return Objects.equal(value, other.getValue());
		}
		return false;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public T getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void setChildren(final List<TreeNode<T>> children) {
		this.children = children;
	}

	public void setParent(final TreeNode<T> parent) {
		this.parent = parent;
	}

	public void setValue(final T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
