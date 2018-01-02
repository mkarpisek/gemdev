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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.progress.IDeferredWorkbenchAdapter;
import org.eclipse.ui.progress.IElementCollector;

import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Node used in inspector tree.
 */
public class InspectorNode implements IDeferredWorkbenchAdapter {
	private static class BatchSimilarSchedulingRule implements ISchedulingRule {
		public String id;

		public BatchSimilarSchedulingRule(final String id) {
			this.id = id;
		}

		/*
		 * @see org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core.runtime.jobs.ISchedulingRule)
		 */
		@Override
		public boolean contains(final ISchedulingRule rule) {
			return this == rule;
		}

		/*
		 * @see org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse.core.runtime.jobs.ISchedulingRule)
		 */
		@Override
		public boolean isConflicting(final ISchedulingRule rule) {
			if (rule instanceof BatchSimilarSchedulingRule) {
				return ((BatchSimilarSchedulingRule) rule).id.equals(id);
			}
			return false;
		}
	}

	public static final InspectorNode SEARCH_CANCELED_NODE = new InspectorNode("Cancelled", null); //$NON-NLS-1$
	private final String name;

	private final ObjectInfo value;

	public InspectorNode(final String name, final ObjectInfo value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public void fetchDeferredChildren(final Object object, final IElementCollector collector, final IProgressMonitor monitor) {
		try {
			final InspectorNode node = (InspectorNode) object;
			final ObjectInfo objectInfo = node.getValue();
			objectInfo.fetchDeferredChildren(collector, monitor);
			collector.done();
		} catch (final OperationCanceledException e) {
			collector.add(new Object[] { InspectorNode.SEARCH_CANCELED_NODE }, monitor);
		} catch (final Exception e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
	}

	@Override
	public Object[] getChildren(final Object o) {
		return new Object[0];
	}

	@Override
	public ImageDescriptor getImageDescriptor(final Object object) {
		if (object instanceof InspectorNode) {
			final ObjectInfo node = ((InspectorNode) object).getValue();
			if (node instanceof ClassInfo) {
				return GemDevUiPlugin.getDefault().getImageDescriptor(GemDevUiPlugin.CLASS_ICON);
			}
		}
		return null;
	}

	@Override
	public String getLabel(final Object o) {
		return getName();
	}

	public String getName() {
		return name;
	}

	@Override
	public Object getParent(final Object o) {
		return null;
	}

	@Override
	public ISchedulingRule getRule(final Object object) {
		return new BatchSimilarSchedulingRule("net.karpisek.gemdev"); //$NON-NLS-1$
	}

	public ObjectInfo getValue() {
		return value;
	}

	@Override
	public boolean isContainer() {
		return true;
	}

	@Override
	public String toString() {
		return getName();
	}
}
