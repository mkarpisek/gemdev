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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.progress.IElementCollector;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**

 */
public class UnorderedCollectionInfo extends ObjectInfo {
	private static class FetchContentsReportAction extends BaseAction<List<String>> {
		public FetchContentsReportAction(final ISession session, final String oop) {
			super(session, oop);
		}

		@Override
		public String asRequestString() {
			return createExecuteOperationRequest(newScriptWithArguments(UnorderedCollectionInfo.class.getSimpleName(), oop));
		}

		@Override
		public List<String> asResponse(final String responseString) throws ActionException {
			Preconditions.checkNotNull(responseString);
			return Lists.newArrayList(responseString.split("\n")); //$NON-NLS-1$
		}
	}

	/**
	 * How big fetched keys (converted to print string) will be, bigger will be truncated.
	 */
	public static final int KEY_MAX_STRING = 20;

	public UnorderedCollectionInfo(final ISession session, final String oop, final String className, final String printString, final int namedVarSize,
			final int unnamedVarSize) {
		super(session, oop, className, printString, namedVarSize, unnamedVarSize);
	}

	@Override
	public void fetchUnnamedVars(final IElementCollector collector, final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}

		final List<String> oops = execute(new FetchContentsReportAction(getSession(), getOop()));
		for (final String valueOop : oops) {
			if (monitor.isCanceled()) {
				return;
			}

			final ObjectInfo value = execute(new GetObjectInfo(getSession(), valueOop));
			collector.add(new InspectorNode(value.getPrintString(), value), monitor);
		}
	}

	@Override
	public Image getIcon() {
		return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.UNORDERED_COLLECTION_ICON);
	}
}
