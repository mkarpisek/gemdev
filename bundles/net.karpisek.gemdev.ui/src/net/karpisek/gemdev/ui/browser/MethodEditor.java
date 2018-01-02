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
package net.karpisek.gemdev.ui.browser;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

import net.karpisek.gemdev.core.analysis.MethodContext;
import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionListener;
import net.karpisek.gemdev.core.db.ISessionManagerListener;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.core.db.SessionManager;
import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.Message;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.net.actions.CompilationError;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.net.actions.method.GetMethodStamp;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.categories.CategoriesView;
import net.karpisek.gemdev.ui.browser.classes.ClassHierarchyView;
import net.karpisek.gemdev.ui.browser.methods.MethodsView;
import net.karpisek.gemdev.ui.browser.projects.ProjectsView;
import net.karpisek.gemdev.ui.editor.GsEditor;
import net.karpisek.gemdev.ui.editor.GsSourceViewer;
import net.karpisek.gemdev.ui.editor.IEditorLinker;
import net.karpisek.gemdev.ui.editor.IGsSourceViewerListener;
import net.karpisek.gemdev.ui.editor.InspectItAbstractHandler;
import net.karpisek.gemdev.ui.editor.PrintItAbstractHandler;
import net.karpisek.gemdev.ui.editor.SourceCodeDecorator;
import net.karpisek.gemdev.ui.refactoring.RenameRefactoring;
import net.karpisek.gemdev.ui.search.SearchUI;

/**
 * For in-memory editing of DB methods. Performs abort + commit on method save!.
 */
public class MethodEditor extends GsEditor
		implements ISessionManagerListener, IGsSourceViewerListener, IPropertyChangeListener, ISessionListener, IEditorLinker {
	private class InspectItHandler extends InspectItAbstractHandler {
		public InspectItHandler(final IWorkbenchSite site, final ISourceViewer viewer) {
			super(site, viewer);
		}

		@Override
		public String getContextOop() {
			return getMethodOop();
		}

		@Override
		public ISession getSession() {
			return getMethodEditorInput().getMethod().getSession();
		}
	}

	private class PrintItHandler extends PrintItAbstractHandler {
		public PrintItHandler(final IWorkbenchSite site, final ISourceViewer viewer) {
			super(site, viewer);
		}

		@Override
		public String getContextOop() {
			return getMethodOop();
		}

		@Override
		public ISession getSession() {
			return getMethodEditorInput().getMethod().getSession();
		}
	}

	private class ProjectDeclarationsHandler extends SelectedElementListeningHandler {
		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final Selector selector = new Selector(selectedElement.getName(), getMethodEditorInput().getMethod().getSession());
			SearchUI.showImplementorsOf(selector);
			return null;
		}

		@Override
		public boolean isEnabled() {
			return super.isEnabled() && (selectedElement instanceof Message);
		}
	}

	private class ProjectReferencesHandler extends SelectedElementListeningHandler {
		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final Selector selector = new Selector(selectedElement.getName(), getMethodEditorInput().getMethod().getSession());
			SearchUI.showSendersOf(selector);
			return null;
		}

		@Override
		public boolean isEnabled() {
			return super.isEnabled() && (selectedElement instanceof Message);
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

	private class ShowMethodStampJob extends Job {
		private final DbMethod method;

		public ShowMethodStampJob(final DbMethod method) {
			super(MessageFormat.format(Messages.GET_METHOD_TIMESTAMP_JOB_NAME, method.toString()));
			this.method = method;
			setSystem(true);
		}

		public DbMethod getMethod() {
			return method;
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			final String stamp = method.getSession()
					.execute(new GetMethodStamp(method.getBehavior().getClassName(), method.isInstanceSide(), method.getName()));
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (!monitor.isCanceled()) {
						final IStatusLineManager statusLine = getEditorSite().getActionBars().getStatusLineManager();
						statusLine.setMessage(stamp);
					}
				}
			});
			return Status.OK_STATUS;
		}
	}

	public static final String EDITOR_ID = "net.karpisek.gemdev.ui.methodEditor"; //$NON-NLS-1$

	private ShowMethodStampJob showMethodStampJob;

	public MethodEditor() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);

		setPartName(getMethodEditorInput().getName());
		GuiUtils.activateHandlers(getSite(), ICommands.INSPECT_IT, new InspectItHandler(getSite(), getSourceViewer()), ICommands.PRINT_IT,
				new PrintItHandler(getSite(), getSourceViewer()), ICommands.RENAME, new RenameRefactoringHandler(), ICommands.FIND_DECLARATIONS,
				new ProjectDeclarationsHandler(), ICommands.FIND_REFERENCES, new ProjectReferencesHandler());
	}

	@Override
	public void dispose() {
		removeMarkers(getMethodEditorInput().getFile());

		final SessionManager sessionManager = GemDevUiPlugin.getSessionManager();
		for (final ISession session : sessionManager.getAllSessions()) {
			session.removeListener(this);
		}
		super.dispose();
	}

	public MethodEditorInput getMethodEditorInput() {
		return (MethodEditorInput) getEditorInput();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void linkActivated(final ProjectsView projectsView) {
		final IWorkbenchPage page = getSite().getPage();
		if (page == null) {
			return;
		}
		final DbMethod method = getMethodEditorInput().getMethod();

		final MethodsView methodsView = (MethodsView) page.findView(MethodsView.VIEW_ID);
		if (methodsView != null) {
			final DbMethod selectedMethod = (DbMethod) ((IStructuredSelection) methodsView.getSite().getSelectionProvider().getSelection()).getFirstElement();
			if (method.equals(selectedMethod)) {
				return;
			}
		}

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					final ClassHierarchyView classHierarchyView = (ClassHierarchyView) page.showView(ClassHierarchyView.VIEW_ID);
					final CategoriesView categories = (CategoriesView) page.showView(CategoriesView.VIEW_ID);
					final MethodsView methodsView = (MethodsView) page.showView(MethodsView.VIEW_ID);

					projectsView.setSelection(method.getSession());
					classHierarchyView.setInput(method.getBehavior().getInstanceSide());
					categories.setSelection(method.getCategory());
					methodsView.setSelection(method);
				} catch (final PartInitException e) {
					GemDevUiPlugin.getDefault().logError(e);
				}
			}
		});
	}

	@Override
	public void removedMethodReferences(final List<MethodReference> allRemoved) {
		final DbMethod m = getMethodEditorInput().getMethod();
		for (final MethodReference ref : allRemoved) {
			if (ref.getClassName().equals(m.getBehavior().getClassName()) && ref.isInstanceSide() == m.isInstanceSide()
					&& ref.getMethodName().equals(m.getName())) {
				close(false);
			}
		}

		refreshContext();
	}

	@Override
	public void selectedElementChanged(final MethodModel currentModel, final Element selectedElement) {
		// currently nothing to do
	}

	@Override
	public void sessionClosed(final ISession session) {
		if (getMethodEditorInput() != null && getMethodEditorInput().getMethod().getSession().equals(session)) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					getSite().getPage().closeEditor(MethodEditor.this, false);
				}
			});
		}
	}

	@Override
	public void sessionOpened(final ISession session) {
		// do nothing
	}

	@Override
	public void setFocus() {
		showMethodStamp();
	}

	private String getMethodOop() {
		final DbMethod method = getMethodEditorInput().getMethod();
		final String expr = String.format("%s asOop", method.getBehavior().getClassName()); //$NON-NLS-1$
		return method.getSession().execute(new PrintIt(expr));
	}

	private void showMethodStamp() {
		final DbMethod m = getMethodEditorInput().getMethod();

		if (showMethodStampJob != null) {
			showMethodStampJob.cancel();
		}

		showMethodStampJob = new ShowMethodStampJob(m);
		showMethodStampJob.schedule();
	}

	@Override
	protected ISourceViewer createSourceViewer(final Composite parent, final IVerticalRuler ruler, final int styles) {
		final GsSourceViewer viewer = (GsSourceViewer) super.createSourceViewer(parent, ruler, styles);

		final DbMethod m = getMethodEditorInput().getMethod();
		viewer.setContext(new MethodContext(m.getBehavior()));

		return viewer;
	}

	@Override
	protected void doSetInput(final IEditorInput input) throws CoreException {
		DbMethod oldMethod = null;

		if (getMethodEditorInput() != null) {
			removeMarkers(getMethodEditorInput().getFile());
			oldMethod = getMethodEditorInput().getMethod();
		}

		IEditorInput input2 = input;
		if (!(input2 instanceof MethodEditorInput) && input2 instanceof FileEditorInput) {
			final IFile file = ((FileEditorInput) input2).getFile();
			final ISession session = GemDevUiPlugin.getSessionManager().getSession(file.getProject());
			final DbMethod method = session.getMethod(((FileEditorInput) input2).getFile());
			input2 = new MethodEditorInput(method);
		}

		super.doSetInput(input2);

		final DbMethod m = getMethodEditorInput().getMethod();
		if (getGsSourceViewer() != null) {
			getGsSourceViewer().setContext(new MethodContext(m.getBehavior()));
		}
		setTitleImage(GemDevUiPlugin.getDefault().getImage(m.isInstanceSide() ? GemDevUiPlugin.INSTANCE_METHOD_ICON : GemDevUiPlugin.CLASS_METHOD_ICON));
		showMethodStamp();

		if (oldMethod != null) {
			oldMethod.getSession().removeListener(this);
		}
		m.getSession().addListener(this);
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setEditorContextMenuId("#MethodEditorContextMenu"); //$NON-NLS-1$
	}

	@Override
	protected void performSave(final boolean overwrite, final IProgressMonitor monitor) {
		final DbMethod currentMethod = getMethodEditorInput().getMethod();
		final DbCategory category = currentMethod.getCategory();
		final ISession session = currentMethod.getSession();

		try {
			final DbMethod newMethod = session.createMethod(category, getSourceViewer().getDocument().get());

			if (currentMethod.getName().equals(newMethod.getName())) {
				// rewritten existing method
				super.performSave(true, monitor);
			} else {
				try {
					// new method created - refresh categories + preselect new method
					final CategoriesView view = (CategoriesView) getSite().getPage().showView(CategoriesView.VIEW_ID);
					view.setSelection(category);
					final MethodsView view2 = (MethodsView) getSite().getPage().showView(MethodsView.VIEW_ID);
					view2.setSelection(newMethod);

					// revert this editor to original state - new state is in new editor
					setInput(getEditorInput());
				} catch (final PartInitException e) {
					GemDevUiPlugin.getDefault().logError(e);
				}
			}

		} catch (final CompilationError e) {
			final SourceCodeDecorator decorator = new SourceCodeDecorator(e);

			// set text editor contents to be source code with annotations embedded in it
			getSourceViewer().getDocument().set(decorator.getDecoratedSourceCode());

			// select embedded error message for easy deletion
			final IRegion r = decorator.getFirstDecoratedRegion();
			selectAndReveal(r.getOffset(), r.getLength());
		} catch (final CommitFailedException e) {
			getEditorSite().getActionBars().getStatusLineManager().setErrorMessage(Messages.METHOD_EDITOR_COMMIT_ERROR);
		}
	}

	@Override
	protected void refreshContext() {
		final GsSourceViewer v = getGsSourceViewer();
		if (v != null) {
			final DbMethod m = getMethodEditorInput().getMethod();
			v.setContext(new MethodContext(m.getBehavior()));
			modelChanged(null, v.getModel());
		}
	}

}
