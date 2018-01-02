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
package net.karpisek.gemdev.ui.inspector;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.progress.DeferredTreeContentManager;

/**
 * Content provider with Deferred loading of children nodes of inspected objects from db.
 */
public class InspectorContentProvider implements ITreeContentProvider {
	private final static Object[] EMPTY_ARRAY = new Object[0];

	private final InspectorView view;
	private DeferredTreeContentManager manager;

	public InspectorContentProvider(final InspectorView view) {
		this.view = view;
	}

	@Override
	public void dispose() {
		// nothing
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement == null) {
			return new InspectorNode[0];
		}
		return (InspectorNode[]) inputElement;
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		final InspectorNode parent = (InspectorNode) parentElement;

		if (shouldStopTraversion(parent)) {
			return EMPTY_ARRAY;
		}

		final Object[] children = manager.getChildren(parent);
		if (children != null) {
			return children;
		}
		return EMPTY_ARRAY;
	}

	@Override
	public Object getParent(final Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(final Object element) {
		if (element == InspectorNode.SEARCH_CANCELED_NODE) {
			return false;
		}

		if (element instanceof InspectorNode) {
			final ObjectInfo value = ((InspectorNode) element).getValue();
			if (value instanceof SequenceableCollectionInfo) {
				return value.getUnnamedVarSize() > 0;
			}

			return value.getNamedVarSize() > 0;
		}

		return false; // the "Update ..." placeholder has no children
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		if (viewer instanceof AbstractTreeViewer) {
			manager = new DeferredTreeContentManager((AbstractTreeViewer) viewer, view.getSite());
		}
	}

	private boolean shouldStopTraversion(final InspectorNode node) {
		return false;
	}
}
