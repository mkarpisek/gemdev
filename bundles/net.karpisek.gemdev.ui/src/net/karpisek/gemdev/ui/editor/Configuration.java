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

import org.eclipse.jface.text.DefaultTextHover;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.MultipleHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.URLHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;

import net.karpisek.gemdev.ui.editor.contentassist.GsCompletionProcessor;
import net.karpisek.gemdev.ui.preferences.SyntaxColoringStyleSet;

/**
 * Customized configuration of smalltalk source viewer.
 */
public class Configuration extends SourceViewerConfiguration {
	private final Scanner scanner;

	public Configuration(final SyntaxColoringStyleSet styles) {
		this.scanner = new Scanner(styles);
	}

	@Override
	public IAnnotationHover getAnnotationHover(final ISourceViewer sourceViewer) {
		return new DefaultAnnotationHover();
	}

	@Override
	public IContentAssistant getContentAssistant(final ISourceViewer sourceViewer) {
		final ContentAssistant assistant = new ContentAssistant();
		assistant.setStatusLineVisible(true);
		assistant.enableAutoInsert(true);
		assistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		final IContentAssistProcessor processor = new GsCompletionProcessor();
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);

		assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));

		return assistant;
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(final ISourceViewer sourceViewer, final String contentType) {
		return new DoubleClickStrategy();
	}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(final ISourceViewer sourceViewer) {
		if (sourceViewer == null) {
			return null;
		}

		return new IHyperlinkDetector[] { new URLHyperlinkDetector(), new HyperlinkDetector() };
	}

	@Override
	public IHyperlinkPresenter getHyperlinkPresenter(final ISourceViewer sourceViewer) {
		return new MultipleHyperlinkPresenter(new RGB(0, 0, 255));
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(final ISourceViewer sourceViewer) {
		final PresentationReconciler reconciler = new PresentationReconciler();
		reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner) {
			@Override
			public IRegion getDamageRegion(final ITypedRegion partition, final DocumentEvent e, final boolean documentPartitioningChanged) {
				return new Region(0, e.getDocument().getLength());
			}
		};
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
		return reconciler;
	}

	@Override
	public IReconciler getReconciler(final ISourceViewer sourceViewer) {
		final MonoReconciler r = new MonoReconciler(new ReconcilingStrategy((GsSourceViewer) sourceViewer), false);
		r.install(sourceViewer);
		r.setDelay(200);
		return r;
	}

	@Override
	public ITextHover getTextHover(final ISourceViewer sourceViewer, final String contentType) {
		return new DefaultTextHover(sourceViewer);
	}

}
