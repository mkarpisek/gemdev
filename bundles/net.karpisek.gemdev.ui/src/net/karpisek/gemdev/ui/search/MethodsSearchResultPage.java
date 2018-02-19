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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.search.ui.text.Match;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.browser.MethodEditor;
import net.karpisek.gemdev.ui.browser.MethodEditorInput;
import net.karpisek.gemdev.ui.utils.MethodReferenceTree;
import net.karpisek.gemdev.ui.utils.MethodReferenceTree.MethodReferenceTreeNode;
import net.karpisek.gemdev.ui.utils.MethodReferenceTreeLabelProvider;
import net.karpisek.gemdev.ui.utils.TreeContentProvider;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**

 */
public class MethodsSearchResultPage extends AbstractTextSearchViewPage {


	private class FindImplementorsLocalHandler extends AbstractHandler implements ISelectionChangedListener {
		protected IStructuredSelection selection;

		public FindImplementorsLocalHandler(final MethodsSearchResultPage page) {
			page.getSite().getSelectionProvider().addSelectionChangedListener(this);
		}

		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			MethodReference m = null;
			if (selection.getFirstElement() instanceof MethodReferenceTreeNode) {
				m = ((MethodReferenceTreeNode) selection.getFirstElement()).getValue();
			} else {
				m = (MethodReference) selection.getFirstElement();
			}

			final MethodsSearchResult result = (MethodsSearchResult) getInput();
			final SearchQuery query = (SearchQuery) result.getQuery();
			final ISession session = query.getSession();

			execute(session, m);
			return null;
		}

		@Override
		public boolean isEnabled() {
			return selection != null && !selection.isEmpty();
		}

		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			selection = (IStructuredSelection) event.getSelection();

			fireHandlerChanged(new HandlerEvent(this, true, false));
		}

		protected void execute(final ISession session, final MethodReference m) {
			SearchUI.showImplementorsOf(new Selector(m.getMethodName(), session));
		}
	}

	private class FindSendersLocalHandler extends FindImplementorsLocalHandler {

		public FindSendersLocalHandler(final MethodsSearchResultPage page) {
			super(page);
		}

		@Override
		protected void execute(final ISession session, final MethodReference m) {
			SearchUI.showSendersOf(new Selector(m.getMethodName(), session));
		}
	}

	public MethodsSearchResultPage() {
		super(AbstractTextSearchViewPage.FLAG_LAYOUT_FLAT | AbstractTextSearchViewPage.FLAG_LAYOUT_TREE);
	}

	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);

		GuiUtils.activateContext(getSite());
		GuiUtils.activateHandlers(getViewPart().getSite(), ICommands.FIND_DECLARATIONS, new FindImplementorsLocalHandler(this), ICommands.FIND_REFERENCES,
				new FindSendersLocalHandler(this));
	}

	@Override
	public Match getCurrentMatch() {
		final Viewer viewer = getViewer();
		final ISelection sel = viewer.getSelection();
		if (viewer instanceof TreeViewer && sel instanceof IStructuredSelection) {
			final Object element = ((IStructuredSelection) sel).getFirstElement();
			if (element instanceof TreeNode) {
				final Match[] matches = getDisplayedMatches(((TreeNode<?>) element).getValue());
				if (matches.length > 0) {
					return matches[0];
				}
			}
		}

		return super.getCurrentMatch();
	}

	@Override
	protected void clear() {
	}

	@Override
	protected void configureTableViewer(final TableViewer viewer) {
		viewer.setContentProvider(new ArrayContentProvider() {
			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement instanceof MethodsSearchResult) {
					return ((MethodsSearchResult) inputElement).getElements();
				}
				return super.getElements(inputElement);
			}
		});
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(final Object element) {
				return GemDevUiPlugin.getDefault()
						.getImage(((MethodReference) element).isInstanceSide() ? GemDevUiPlugin.INSTANCE_METHOD_ICON : GemDevUiPlugin.CLASS_METHOD_ICON);
			}
		});
		viewer.setSorter(new ViewerSorter());
		// GuiUtils.activateContext(getSite());

	}

	@Override
	protected void configureTreeViewer(final TreeViewer viewer) {
		viewer.setContentProvider(new TreeContentProvider() {
			private final Object[] emptyArray = new Object[0];

			@Override
			public Object[] getElements(final Object inputElement) {
				return getRoots((MethodsSearchResult) getInput());
			}

			private Object[] getRoots(final MethodsSearchResult result) {
				ISession session = null;
				if (result.getElements().length <= 0) {
					return emptyArray;
				}

				final List<MethodReference> methods = Lists.newLinkedList();
				for (final Object o : result.getElements()) {
					methods.add((MethodReference) o);
					if (session == null) {
						session = ((MethodReferenceMatch) result.getMatches(o)[0]).getSession();
					}
				}
				if (session == null) {
					return emptyArray;
				}

				final MethodReferenceTree tree = MethodReferenceTree.createFilteredTree(session.getCachedClasses(), methods);
				return tree.getRoots().toArray();
			}
		});
		viewer.setLabelProvider(new MethodReferenceTreeLabelProvider());

	}

	@Override
	protected void elementsChanged(final Object[] objects) {
		if (objects.length <= 0) {
			return;
		}

		Arrays.sort(objects, new Comparator<Object>() {
			@Override
			public int compare(final Object o1, final Object o2) {
				return ((MethodReference) o1).toString().compareTo(((MethodReference) o2).toString());
			}
		});

		getViewer().setInput(objects);
	}

	@Override
	protected void handleOpen(final OpenEvent event) {
		super.handleOpen(event);
	}

	@Override
	protected void showMatch(final Match match, final int currentOffset, final int currentLength) throws PartInitException {
		final DbMethod method = ((MethodReferenceMatch) match).getMethod();
		if (method == null) {
			MessageDialog.openError(getSite().getShell(), "Error", //$NON-NLS-1$
					String.format("Can not find method for method reference %s", ((MethodReferenceMatch) match).getMethodReference())); //$NON-NLS-1$
			return;
		}

		try {
			getSite().getPage().openEditor(new MethodEditorInput(method), MethodEditor.EDITOR_ID);
		} catch (final CoreException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
	}
}
