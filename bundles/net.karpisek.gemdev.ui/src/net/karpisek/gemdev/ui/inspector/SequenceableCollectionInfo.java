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
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.progress.IElementCollector;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**

 */
public class SequenceableCollectionInfo extends ObjectInfo {
	public SequenceableCollectionInfo(final ISession session, final String oop, final String className, final String printString, final int namedVarSize,
			final int unnamedVarSize) {
		super(session, oop, className, printString, namedVarSize, unnamedVarSize);
	}

	@Override
	public void fetchUnnamedVars(final IElementCollector collector, final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}

		for (int i = 0; i < getUnnamedVarSize(); i++) {
			if (monitor.isCanceled()) {
				return;
			}
			final String expr = String.format("((Object _objectForOop: %s) at: %d) asOop", getOop(), i + 1); //$NON-NLS-1$
			final String varOop = execute(new PrintIt(expr) {
				@Override
				public boolean isIdempotent() {
					return true;
				}
			});
			final ObjectInfo value = execute(new GetObjectInfo(getSession(), varOop));

			collector.add(new InspectorNode(String.format("%d: %s", i + 1, value.getPrintString()), value), monitor); //$NON-NLS-1$
		}
	}

	@Override
	public Image getIcon() {
		return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.SEQUENCEABLE_COLLECTION_ICON);
	}
}
