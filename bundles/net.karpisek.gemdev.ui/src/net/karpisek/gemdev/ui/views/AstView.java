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
package net.karpisek.gemdev.ui.views;

import java.io.IOException;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.ViewPart;

import net.karpisek.gemdev.lang.ParserUtils;
import net.karpisek.gemdev.lang.ParserUtils.Result;
import net.karpisek.gemdev.lang.parser.GsParser;
import net.karpisek.gemdev.lang.parser.GsTree;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.editor.GsEditor;

/**
 * Simple tree view for displaying AST of smalltalk code.
 */
public class AstView extends ViewPart {

	private TreeViewer viewer;

	public AstView() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final GsTree node = (GsTree) element;
				final String typeString = GsParser.tokenNames[node.getType()];
				return String.format("%s - %s [%d:%d]", node.toString(), typeString, node.getLine(), node.getCharPositionInLine()); //$NON-NLS-1$
			}
		});
		viewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public void dispose() {
				// nothing
			}

			@Override
			public Object[] getElements(final Object inputElement) {
				if (inputElement == null) {
					return new Object[0];
				}
				return (Object[]) inputElement;
			}

			@Override
			public Object[] getChildren(final Object parentElement) {
				final List<?> ch = ((GsTree) parentElement).getChildren();
				if (ch == null) {
					return new Object[0];
				}
				return ch.toArray();
			}

			@Override
			public Object getParent(final Object element) {
				return ((GsTree) element).getParent();
			}

			@Override
			public boolean hasChildren(final Object element) {
				final List<?> ch = ((GsTree) element).getChildren();
				return ch != null && !ch.isEmpty();
			}

			@Override
			public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
				// nothing
			}
		});

		createActions();
	}

	@Override
	public void setFocus() {
		viewer.getTree().setFocus();
	}

	private void createActions() {
		final IToolBarManager toolbar = getViewSite().getActionBars().getToolBarManager();
		toolbar.add(new Action(Messages.REFRESH, GemDevUiPlugin.getDefault().getImageDescriptor(GemDevUiPlugin.REFRESH_ICON)) {
			@Override
			public void run() {
				final IEditorPart editor = getSite().getPage().getActiveEditor();
				if (editor != null && editor instanceof GsEditor) {
					final String sourceCode = ((GsEditor) editor).getGsSourceViewer().getDocument().get();
					setInput(sourceCode);
				}
			}
		});
	}

	private void setInput(final String sourceCode) {
		try {
			final Result result = ParserUtils.parse(sourceCode);
			if (!result.getSyntaxErrors().isEmpty()) {
				GemDevUiPlugin.getDefault().logError(result.getSyntaxErrors().toString());
			} else {
				viewer.setInput(new Object[] { result.getAst() });
				viewer.expandAll();
			}
		} catch (final IOException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
	}
}
