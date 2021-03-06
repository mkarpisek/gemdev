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
import net.karpisek.gemdev.ui.GemDevUiPlugin;

public class AbstractDictionaryInfo extends ObjectInfo {
	private static class FetchContentsReportAction extends BaseAction<List<String>> {
		public FetchContentsReportAction(final ISession session, final String oop) {
			super(session, oop);
		}

		@Override
		public String asRequestString() {
			return createExecuteOperationRequest(newScriptWithArguments(AbstractDictionaryInfo.class.getSimpleName(), oop, KEY_MAX_STRING));
		}

		@Override
		public List<String> asResponse(final String responseString) {
			Preconditions.checkNotNull(responseString);
			return Lists.newArrayList(responseString.split("\n")); //$NON-NLS-1$
		}
	}

	/**
	 * How big fetched keys (converted to print string) will be, bigger will be truncated.
	 */
	public static final int KEY_MAX_STRING = 20;

	public AbstractDictionaryInfo(final ISession session, final String oop, final String className, final String printString, final int namedVarSize,
			final int unnamedVarSize) {
		super(session, oop, className, printString, namedVarSize, unnamedVarSize);
	}

	@Override
	public void fetchUnnamedVars(final IElementCollector collector, final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return;
		}

		final List<String> keyAndValue = execute(new FetchContentsReportAction(getSession(), getOop()));
		for (int i = 0; i < keyAndValue.size(); i += 2) {
			if (monitor.isCanceled()) {
				return;
			}

			final String keyPrintString = keyAndValue.get(i);
			final String valueOop = keyAndValue.get(i + 1);

			final ObjectInfo value = execute(new GetObjectInfo(getSession(), valueOop));
			collector.add(new InspectorNode(String.format("%s: %s", keyPrintString, value.getPrintString()), value), monitor); //$NON-NLS-1$
		}
	}

	@Override
	public Image getIcon() {
		return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.ABSTRACT_DICTIONARY_ICON);
	}
}
