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
package net.karpisek.gemdev.ui.editor;

import java.io.IOException;

import org.antlr.runtime.tree.RewriteCardinalityException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;

import net.karpisek.gemdev.lang.ParserUtils;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Reconciling strategy which builds model of smalltalk method and sets it to viewer. Always process complete method.
 */
public class ReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {
	private final GsSourceViewer viewer;
	private IDocument document;

	public ReconcilingStrategy(final GsSourceViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void initialReconcile() {
		reconcile();
	}

	@Override
	public void reconcile(final DirtyRegion dirtyRegion, final IRegion subRegion) {
		// empty - this is for incremental reconciling
	}

	@Override
	public void reconcile(final IRegion partition) {
		reconcile();
	}

	@Override
	public void setDocument(final IDocument document) {
		this.document = document;
	}

	@Override
	public void setProgressMonitor(final IProgressMonitor monitor) {
		// do nothing
	}

	private void reconcile() {
		try {
			viewer.setModel(ParserUtils.build(document.get()));
		} catch (final RewriteCardinalityException e) {
			GemDevUiPlugin.getDefault().logError(e);
		} catch (final IOException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
	}

}
