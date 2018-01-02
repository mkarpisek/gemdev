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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorPart;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.MethodEditor;
import net.karpisek.gemdev.ui.browser.MethodEditorInput;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**

 */
public class MethodsSearchResult extends AbstractTextSearchResult {
	private final ISearchQuery query;
	private final String searchObject;
	private final String searchObjectLabel;

	public MethodsSearchResult(final ISearchQuery query, final String searchObject, final String searchObjectLabel) {
		this.query = query;
		this.searchObject = searchObject;
		this.searchObjectLabel = searchObjectLabel;
	}

	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return new IEditorMatchAdapter() {

			@Override
			public Match[] computeContainedMatches(final AbstractTextSearchResult result, final IEditorPart editor) {
				if (!(result instanceof MethodsSearchResult && editor instanceof MethodEditor)) {
					return new Match[0];
				}
				final MethodEditor methodEditor = (MethodEditor) editor;
				final DbMethod method = methodEditor.getMethodEditorInput().getMethod();

				final List<Match> matches = Lists.newLinkedList();
				for (final Object element : result.getElements()) {
					for (final Match match : result.getMatches(element)) {
						if (contains(method, match)) {
							if (match instanceof MethodReferenceMatch) {
								// this is kind of hack
								// because method source code is normalized for eclipse to client endline convention
								// offsets to method source code computed on GS side does not necessary match
								// because of it here we will find nearest occurence of same text fragment as was original search
								// based on index incoming from GS
								// this should affect only open editors, so it is not so expensive like normalizing GS results somehow differently
								final MethodReferenceMatch m = (MethodReferenceMatch) match;
								final String src = methodEditor.getGsSourceViewer().getDocument().get();

								final int idx = src.indexOf(m.getText(), m.getOffset());
								if (idx != -1) {
									m.setOffset(idx);
								}
							}
							matches.add(match);
						}
					}
				}
				return matches.toArray(new Match[matches.size()]);
			}

			@Override
			public boolean isShownInEditor(final Match match, final IEditorPart editor) {
				if (!(match instanceof MethodReferenceMatch && editor instanceof MethodEditor)) {
					return false;
				}
				final MethodEditor methodEditor = (MethodEditor) editor;
				final MethodEditorInput input = methodEditor.getMethodEditorInput();

				return contains(input.getMethod(), match);
			}

			private boolean contains(final DbMethod method, final Match match) {
				if (method == null || match == null) {
					return false;
				}
				if (!(match instanceof MethodReferenceMatch)) {
					return false;
				}
				final MethodReferenceMatch refMatch = (MethodReferenceMatch) match;
				if (!method.getSession().equals(refMatch.getSession())) {
					return false;
				}

				final MethodReference ref = refMatch.getMethodReference();
				return (ref.getClassName().equals(method.getBehavior().getClassName()) && ref.isInstanceSide() == method.isInstanceSide()
						&& ref.getMethodName().equals(method.getName()));
			}
		};
	}

	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
		return null;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getLabel() {
		return MessageFormat.format(Messages.METHODS_SEARCH_RESULT_LABEL, searchObject, getMatchCount(), searchObjectLabel);
	}

	@Override
	public Match[] getMatches(final Object element) {
		if (element instanceof TreeNode) {
			return super.getMatches(((TreeNode<?>) element).getValue());
		}
		return super.getMatches(element);
	}

	@Override
	public ISearchQuery getQuery() {
		return query;
	}

	@Override
	public String getTooltip() {
		return null;
	}
}
