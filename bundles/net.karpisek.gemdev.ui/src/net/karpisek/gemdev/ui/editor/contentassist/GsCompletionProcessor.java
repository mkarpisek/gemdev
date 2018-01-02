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
package net.karpisek.gemdev.ui.editor.contentassist;

import java.util.List;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.analysis.IMethodContext;
import net.karpisek.gemdev.lang.CursorContext;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.editor.GsSourceViewer;

/**
 * 

 */
public class GsCompletionProcessor extends TemplateCompletionProcessor {
	private final TemplateFactory templateFactory;

	public GsCompletionProcessor() {
		templateFactory = new TemplateFactory();
	}

	@Override
	public ICompletionProposal[] computeCompletionProposals(final ITextViewer viewer, final int offset) {
		final List<ICompletionProposal> matches = Lists.newLinkedList();

		for (final ICompletionProposal proposal : super.computeCompletionProposals(viewer, offset)) {
			if (proposal instanceof TemplateProposal) {
				if (((TemplateProposal) proposal).getRelevance() > 0) {
					matches.add(proposal);
				}
			} else {
				matches.add(proposal);
			}
		}

		final IMethodContext methodContext = getBindingTarget(viewer);
		if (methodContext != null && methodContext.getSession() != null) {
			final CursorContext cursorContext = CursorContext.analyze(viewer.getDocument().get(), offset);

			final Region region = new Region(offset - cursorContext.getPrefix().length(), cursorContext.getPrefix().length());
			final TemplateContext templateContext = createContext(viewer, region);
			if (templateContext != null) {
				for (final Template t : templateFactory.createTemplates(methodContext, templateContext, cursorContext.getPrefix(),
						cursorContext.getReceiver())) {
					matches.add(createProposal(t, templateContext, (IRegion) region, 1));
				}
			}
		}

		return matches.toArray(new ICompletionProposal[matches.size()]);

	}

	private IMethodContext getBindingTarget(final ITextViewer viewer) {
		if (viewer == null || !(viewer instanceof GsSourceViewer))
			return null;

		return ((GsSourceViewer) viewer).getContext();
	}

	@Override
	protected TemplateContextType getContextType(final ITextViewer viewer, final IRegion region) {
		return GemDevUiPlugin.getDefault().getContextTypeRegistry().getContextType(GsTemplateContextType.CONTEXT_TYPE);
	}

	@Override
	protected Image getImage(final Template template) {
		if (template instanceof TemplateWithImage)
			return GemDevUiPlugin.getDefault().getImage(((TemplateWithImage) template).getImageId());
		return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.TEMPLATE_ICON);
	}

	@Override
	protected Template[] getTemplates(final String contextTypeId) {
		if (GsTemplateContextType.CONTEXT_TYPE.equals(contextTypeId)) {
			return GemDevUiPlugin.getDefault().getTemplateStore().getTemplates();
		}
		return null;
	}
}
