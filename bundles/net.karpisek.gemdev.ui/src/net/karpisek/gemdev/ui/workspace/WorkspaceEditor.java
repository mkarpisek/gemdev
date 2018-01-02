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
package net.karpisek.gemdev.ui.workspace;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import net.karpisek.gemdev.core.analysis.IMethodContext;
import net.karpisek.gemdev.core.analysis.NullMethodContext;
import net.karpisek.gemdev.core.analysis.UndeclaredIdentifiersAnalysis;
import net.karpisek.gemdev.core.analysis.UnimplementedMessagesAnalysis;
import net.karpisek.gemdev.core.analysis.UnusedLocalIdentifierssAnalysis;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionManagerListener;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.Message;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.browser.projects.ProjectsView;
import net.karpisek.gemdev.ui.editor.GsEditor;
import net.karpisek.gemdev.ui.editor.GsSourceViewer;
import net.karpisek.gemdev.ui.editor.IEditorLinker;
import net.karpisek.gemdev.ui.editor.IGsSourceViewerListener;
import net.karpisek.gemdev.ui.editor.InspectItAbstractHandler;
import net.karpisek.gemdev.ui.editor.PrintItAbstractHandler;
import net.karpisek.gemdev.ui.inspector.GetObjectInfo;
import net.karpisek.gemdev.ui.inspector.InspectorView;
import net.karpisek.gemdev.ui.inspector.ObjectInfo;
import net.karpisek.gemdev.ui.preferences.GsPreferences;
import net.karpisek.gemdev.ui.refactoring.RenameRefactoring;
import net.karpisek.gemdev.ui.search.SearchUI;

public class WorkspaceEditor extends GsEditor implements ISessionManagerListener, IGsSourceViewerListener, IPropertyChangeListener, IEditorLinker {
	private class InspectItHandler extends InspectItAbstractHandler {
		public InspectItHandler(final IWorkbenchSite site, final ISourceViewer viewer) {
			super(site, viewer);
		}

		@Override
		public void execute(final ISession session, final String resultOopString) {
			final ObjectInfo info = session.execute(new GetObjectInfo(session, resultOopString));

			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						final InspectorView view = (InspectorView) site.getPage().showView(InspectorView.VIEW_ID);
						view.setInput(info);
					} catch (final PartInitException e) {
						GemDevUiPlugin.getDefault().logError(e);
					}
				}
			});
		}

		@Override
		public String getContextOop() {
			return null;
		}

		@Override
		public ISession getSession() {
			return WorkspaceEditor.this.getSession();
		}

		@Override
		public boolean isEnabled() {
			return super.isEnabled() && getSession() != null;
		}
	}

	private class PrintItHandler extends PrintItAbstractHandler {
		public PrintItHandler(final IWorkbenchSite site, final ISourceViewer viewer) {
			super(site, viewer);
		}

		@Override
		public String getContextOop() {
			return null;
		}

		@Override
		public ISession getSession() {
			return WorkspaceEditor.this.getSession();
		}

		@Override
		public boolean isEnabled() {
			return super.isEnabled() && getSession() != null;
		}
	}

	private class ProjectDeclarationsHandler extends SelectedElementListeningHandler {
		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final Selector selector = new Selector(selectedElement.getName(), getSession());
			SearchUI.showImplementorsOf(selector);
			return null;
		}

		@Override
		public boolean isEnabled() {
			return super.isEnabled() && getSession() != null && (selectedElement instanceof Message);
		}
	}

	private class ProjectReferencesHandler extends SelectedElementListeningHandler {
		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final Selector selector = new Selector(selectedElement.getName(), getSession());
			SearchUI.showSendersOf(selector);
			return null;
		}

		@Override
		public boolean isEnabled() {
			return super.isEnabled() && getSession() != null && (selectedElement instanceof Message);
		}
	}

	private class RenameRefactoringHandler extends SelectedElementListeningHandler {
		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			RenameRefactoring.openDialog(HandlerUtil.getActiveShellChecked(event), getDocument(), getCurrentModel(), getSelectedElement());
			return null;
		}
	}

	private abstract class SelectedElementListeningHandler extends AbstractHandler implements IGsSourceViewerListener {
		protected IDocument document;
		protected Element selectedElement;
		protected MethodModel currentModel;

		public SelectedElementListeningHandler() {
			getGsSourceViewer().addModelListener(this);
		}

		public MethodModel getCurrentModel() {
			return currentModel;
		}

		public IDocument getDocument() {
			return document;
		}

		public Element getSelectedElement() {
			return selectedElement;
		}

		@Override
		public boolean isEnabled() {
			return currentModel != null && selectedElement != null;
		}

		@Override
		public void modelChanged(final MethodModel oldModel, final MethodModel newModel) {
			if (newModel == null) {
				selectedElement = null;
				fireHandlerChanged(new HandlerEvent(this, true, false));
			}
		}

		@Override
		public void selectedElementChanged(final MethodModel currentModel, final Element selectedElement) {
			this.document = getSourceViewer().getDocument();
			this.currentModel = currentModel;
			this.selectedElement = selectedElement;
			fireHandlerChanged(new HandlerEvent(this, true, false));
		}
	}

	public static final String EDITOR_ID = "net.karpisek.gemdev.ui.methodEditor"; //$NON-NLS-1$

	public WorkspaceEditor() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);

		GuiUtils.activateHandlers(getSite(), ICommands.INSPECT_IT, new InspectItHandler(getSite(), getSourceViewer()), ICommands.PRINT_IT,
				new PrintItHandler(getSite(), getSourceViewer()), ICommands.RENAME, new RenameRefactoringHandler(), ICommands.FIND_DECLARATIONS,
				new ProjectDeclarationsHandler(), ICommands.FIND_REFERENCES, new ProjectReferencesHandler());
	}

	@Override
	public void linkActivated(final ProjectsView view) {
		view.show(getFile());
	}

	@Override
	public void modelChanged(final MethodModel oldModel, final MethodModel newModel) {
		final IFile file = ((FileEditorInput) getEditorInput()).getFile();
		final IMethodContext context = getGsSourceViewer().getContext();

		removeMarkers(file);
		final ISession s = getSession();
		final Preferences p = GsPreferences.getPluginPreferences();
		if (p.getBoolean(GsPreferences.ENABLE_SYNTAX_ERROR_ANALYSIS)) {
			addSyntaxErrorMarker(file, getSourceViewer().getDocument(), newModel);
		}
		if (s != null && p.getBoolean(GsPreferences.ENABLE_UNDECLARED_IDENTIFIERS_ANALYSIS)) {
			new UndeclaredIdentifiersAnalysis(context, newModel).createProblemMarkers(file);
		}
		if (p.getBoolean(GsPreferences.ENABLE_UNUSED_LOCAL_IDENTIFIERS_ANALYSIS)) {
			new UnusedLocalIdentifierssAnalysis(context, newModel).createProblemMarkers(file);
		}

		if (s != null && p.getBoolean(GsPreferences.ENABLE_UNIMPLEMENTED_MESSAGES_ANALYSIS)) {
			new UnimplementedMessagesAnalysis(context, newModel).createProblemMarkers(file);
		}
	}

	@Override
	public void selectedElementChanged(final MethodModel currentModel, final Element selectedElement) {
		// currently nothing to do
	}

	@Override
	public void sessionClosed(final ISession session) {
		if (session == getSession()) {
			getGsSourceViewer().setContext(new NullMethodContext());
			modelChanged(null, getGsSourceViewer().getModel());
		}
	}

	@Override
	public void sessionOpened(final ISession session) {
		if (session == getSession()) {
			refreshContext();
			modelChanged(null, getGsSourceViewer().getModel());
		}
	}

	private IFile getFile() {
		return ((FileEditorInput) getEditorInput()).getFile();
	}

	private IProject getProject() {
		return getFile().getProject();
	}

	private ISession getSession() {
		return GemDevUiPlugin.getSessionManager().getSession(getProject());
	}

	@Override
	protected ISourceViewer createSourceViewer(final Composite parent, final IVerticalRuler ruler, final int styles) {
		final GsSourceViewer viewer = (GsSourceViewer) super.createSourceViewer(parent, ruler, styles);

		viewer.setContext(new WorkspaceMethodContextImpl(getSession()));

		return viewer;
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setEditorContextMenuId("#WorkspaceEditorContextMenu"); //$NON-NLS-1$
	}

	@Override
	protected void refreshContext() {
		final GsSourceViewer v = getGsSourceViewer();
		if (v != null) {
			v.setContext(new WorkspaceMethodContextImpl(getSession()));
		}
	}
}
