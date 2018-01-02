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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Content provider implementation for {@link Tree}.
 */
public class TreeContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// not used
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof Tree) {
			return ((Tree<?>) inputElement).getRoots().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		final TreeNode<?> node = asTreeNode(parentElement);
		if (node != null) {
			return node.getChildren().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(final Object element) {
		final TreeNode<?> node = asTreeNode(element);
		if (node != null) {
			return node.getParent();
		}
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		final TreeNode<?> node = asTreeNode(element);
		if (node != null) {
			return !node.getChildren().isEmpty();
		}
		return false;
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// not used
	}

	private TreeNode<?> asTreeNode(final Object element) {
		if (element instanceof TreeNode) {
			return (TreeNode<?>) element;
		}
		return null;
	}

}
