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
package net.karpisek.gemdev.ui.search;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.search.ui.ISearchResult;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.system.GetAllSenders;
import net.karpisek.gemdev.ui.Messages;

/**

 */
public class SendersSearchQuery extends SearchQuery {
	protected final Selector selector;
	protected MethodsSearchResult result;

	public SendersSearchQuery(final Selector selector) {
		super(selector.getSession());

		this.selector = selector;
		this.result = new MethodsSearchResult(this, selector.getName(), Messages.SENDERS);
	}

	@Override
	public String getLabel() {
		return MessageFormat.format(Messages.SENDERS_SEARCH_LABEL, selector.getName());
	}

	@Override
	public ISearchResult getSearchResult() {
		return result;
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) {
		result.removeAll();

		String part = selector.getName();
		if (part.indexOf(':') >= 0) {
			part = part.substring(0, part.indexOf(':'));
		}

		final Map<MethodReference, Integer> report = execute(new GetAllSenders(selector.getName()));

		final List<MethodReferenceMatch> matches = Lists.newLinkedList();
		for (final Map.Entry<MethodReference, Integer> entry : report.entrySet()) {
			matches.add(new MethodReferenceMatch(session, entry.getKey(), entry.getValue(), part));
		}

		result.addMatches(matches.toArray(new MethodReferenceMatch[matches.size()]));
		return Status.OK_STATUS;
	}
}
