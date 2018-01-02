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
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.search.ui.ISearchResult;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.system.GetAllMethodsWithSubstring;
import net.karpisek.gemdev.ui.Messages;

/**
 * Answer all methods which reference some class.
 */
public class MethodsWithSubstringSearchQuery extends SearchQuery {
	private final String searchString;
	private final boolean caseSensitive;
	protected MethodsSearchResult result;

	public MethodsWithSubstringSearchQuery(final ISession session, final String searchString, final boolean caseSensitive) {
		super(session);
		this.searchString = searchString;
		this.caseSensitive = caseSensitive;
		this.result = new MethodsSearchResult(this, String.format("'%s'", searchString), Messages.METHODS); //$NON-NLS-1$
	}

	@Override
	public String getLabel() {
		return MessageFormat.format(Messages.METHODS_SEARCH_LABEL, searchString);
	}

	@Override
	public ISearchResult getSearchResult() {
		return result;
	}

	@Override
	public IStatus run(final IProgressMonitor monitor) throws OperationCanceledException {
		result.removeAll();

		final Map<MethodReference, Integer> report = execute(new GetAllMethodsWithSubstring(searchString, caseSensitive));

		final List<MethodReferenceMatch> matches = Lists.newLinkedList();
		for (final Map.Entry<MethodReference, Integer> entry : report.entrySet()) {
			matches.add(new MethodReferenceMatch(session, entry.getKey(), entry.getValue(), searchString));
		}

		result.addMatches(matches.toArray(new MethodReferenceMatch[matches.size()]));
		return Status.OK_STATUS;
	}
}
