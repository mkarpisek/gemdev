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

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.analysis.IMethodContext;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.Identifier;
import net.karpisek.gemdev.lang.model.LocalIdentifier;
import net.karpisek.gemdev.lang.model.Message;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.lang.parser.GsTree;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.clazz.GetMethodCategoryName;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.browser.MethodEditor;
import net.karpisek.gemdev.ui.browser.MethodEditorInput;
import net.karpisek.gemdev.ui.browser.classes.ClassHierarchyView;

/**
 * Hyperlinks for smalltalk system.
 */
public class HyperlinkDetector extends AbstractHyperlinkDetector {

	/**
	 * After clicking on this hyperlink it will open class in class hierarchy view.
	 */
	private static class ClassHyperlink implements IHyperlink {
		private final DbClass target;
		private final IRegion region;

		public ClassHyperlink(final DbClass target, final IRegion region) {
			this.target = target;
			this.region = region;
		}

		@Override
		public IRegion getHyperlinkRegion() {
			return region;
		}

		@Override
		public String getHyperlinkText() {
			return null;
		}

		@Override
		public String getTypeLabel() {
			return null;
		}

		@Override
		public void open() {
			try {
				final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window == null) {
					return;
				}
				final IWorkbenchPage page = window.getActivePage();
				if (page == null) {
					return;
				}
				final ClassHierarchyView view = (ClassHierarchyView) page.showView(ClassHierarchyView.VIEW_ID);
				view.setInput(target);
			} catch (final PartInitException e) {
				GemDevUiPlugin.getDefault().logError(e);
			}
		}
	}

	/**
	 * After clicking on this hyperlink jump do declaration, selects it and reveals it.
	 */
	private static class LocalIdentifierHyperlink implements IHyperlink {
		private final LocalIdentifier id;
		private final IRegion region;
		private final ITextViewer viewer;

		public LocalIdentifierHyperlink(final LocalIdentifier id, final IRegion region, final ITextViewer viewer) {
			this.id = id;
			this.region = region;
			this.viewer = viewer;
		}

		@Override
		public IRegion getHyperlinkRegion() {
			return region;
		}

		@Override
		public String getHyperlinkText() {
			return null;
		}

		@Override
		public String getTypeLabel() {
			return null;
		}

		@Override
		public void open() {
			final GsTree target = id.getDeclaration();
			viewer.setSelectedRange(target.getOffset(), target.getLength());
			viewer.revealRange(target.getOffset(), target.getLength());
		}
	}

	/**
	 * After clicking on this hyperlink it will select method (or offer list of implementors) in browser perspective.
	 */
	private static class MethodHyperlink implements IHyperlink {
		private final ISession session;
		private final MethodReference ref;
		private final IRegion region;

		public MethodHyperlink(final ISession session, final MethodReference ref, final IRegion region) {
			this.session = session;
			this.ref = ref;
			this.region = region;
		}

		@Override
		public IRegion getHyperlinkRegion() {
			return region;
		}

		@Override
		public String getHyperlinkText() {
			return ref.toString();
		}

		@Override
		public String getTypeLabel() {
			return ref.toString();
		}

		@Override
		public void open() {
			try {
				final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window == null) {
					return;
				}
				final IWorkbenchPage page = window.getActivePage();
				if (page == null) {
					return;
				}
				final DbClass target = session.getCachedClass(ref.getClassName());
				if (target == null) {
					return;
				}

				final String categoryName = session.execute(new GetMethodCategoryName(ref.getClassName(), ref.isInstanceSide(), ref.getMethodName()));
				if (categoryName == null) {
					return;
				}
				final DbCategory category = (DbCategory) target.getCategory(categoryName, ref.isInstanceSide());
				if (category == null) {
					return;
				}

				final DbMethod method = (DbMethod) category.getMethod(ref.getMethodName());
				page.openEditor(new MethodEditorInput(method), MethodEditor.EDITOR_ID);

			} catch (final PartInitException e) {
				GemDevUiPlugin.getDefault().logError(e);
			} catch (final CoreException e) {
				GemDevUiPlugin.getDefault().logError(e);
			}
		}
	}

	@Override
	public IHyperlink[] detectHyperlinks(final ITextViewer textViewer, final IRegion region, final boolean canShowMultipleHyperlinks) {
		if (!(textViewer instanceof GsSourceViewer)) {
			return null;
		}

		final IMethodContext context = ((GsSourceViewer) textViewer).getContext();
		final MethodModel model = ((GsSourceViewer) textViewer).getModel();
		final ISession session = context.getSession();

		if (model == null || session == null) {
			return null;
		}

		final int offset = region.getOffset();
		for (final Element element : model.getElements().values()) {
			if (element instanceof Identifier) {
				if (element instanceof LocalIdentifier) {
					// params + tmp vars
					final LocalIdentifier id = (LocalIdentifier) element;
					for (final GsTree t : element.getOccurences()) {
						if (t.getOffset() <= offset && offset < t.getOffset() + t.getLength()) {
							return new IHyperlink[] { new LocalIdentifierHyperlink(id, t, textViewer) };
						}
					}

				} else {
					// searching for class identifiers
					for (final GsTree t : element.getOccurences()) {
						if (t.getOffset() <= offset && offset < t.getOffset() + t.getLength()) {
							final DbClass target = session.getCachedClass(element.getName());
							if (target != null) {
								return new IHyperlink[] { new ClassHyperlink(target, t) };
							}
						}
					}
				}
			}

			// messages
			if (element instanceof Message) {
				final Message msg = (Message) element;
				final Selector selector = new Selector(msg.getName(), session);
				final List<MethodReference> implementors = session.getCachedMethods(selector);
				if (implementors != null && !implementors.isEmpty()) {
					for (final GsTree t : element.getOccurences()) {
						if (t.getOffset() <= offset && offset < t.getOffset() + t.getLength()) {
							final List<IHyperlink> links = Lists.newLinkedList();
							for (final MethodReference implementor : implementors) {
								links.add(new MethodHyperlink(session, implementor, t));
							}
							return links.toArray(new IHyperlink[links.size()]);
						}
					}
				}
			}
		}
		return null;
	}
}
