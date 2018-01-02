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
package net.karpisek.gemdev.ui.browser.projects;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionManagerListener;
import net.karpisek.gemdev.core.db.SessionManager;
import net.karpisek.gemdev.core.resources.ProjectNature;
import net.karpisek.gemdev.net.actions.system.SetAuthorInitials;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.MethodEditor;
import net.karpisek.gemdev.ui.editor.IEditorLinker;
import net.karpisek.gemdev.ui.preferences.GsPreferences;

/**
 * View displays smalltalk project resources in workspace.
 */
public class ProjectsView extends CommonNavigator implements ISessionManagerListener {
	public class ConnectHandler extends AbstractHandler implements ISelectionChangedListener {
		private IProject selectedProject;

		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

			// check author initials are filled
			if (!checkAuthorInitialsAreFilled(window)) {
				return null;
			}

			try {
				final ConnectJob job = new ConnectJob(sessionManager, selectedProject);
				job.setPriority(Job.LONG);
				job.schedule();
				job.addJobChangeListener(new JobChangeAdapter() {
					@Override
					public void done(final IJobChangeEvent event) {
						PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
							@Override
							public void run() {
								getCommonViewer().update(selectedProject, null);
							}
						});
					}
				});
			} catch (final CoreException e) {
				throw new ExecutionException(Messages.PROJECTS_VIEW_CONNECT_ERROR, e);
			}
			return selectedProject;
		}

		@Override
		public boolean isEnabled() {
			return selectedProject != null && !sessionManager.isConnectingProject(selectedProject) && !sessionManager.hasSession(selectedProject);
		}

		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			final Object selection = ((IStructuredSelection) event.getSelection()).getFirstElement();

			if (selection == selectedProject) {
				return;
			}

			try {
				selectedProject = null;
				if (selection instanceof IProject && ((IProject) selection).hasNature(ProjectNature.ID)) {
					selectedProject = (IProject) selection;
				}
				fireHandlerChanged(new HandlerEvent(this, true, true));
			} catch (final CoreException e) {
				GemDevUiPlugin.getDefault().logError(e);
			}
		}

		private boolean checkAuthorInitialsAreFilled(final IWorkbenchWindow window) {
			if (GsPreferences.loadAuthorInitials().length() <= 0) {
				final InputDialog dialog = new InputDialog(window.getShell(), Messages.PROJECTS_VIEW_AUTHOR_INITIALS_DIALOG_TITLE,
						Messages.PROJECTS_VIEW_AUTHOR_INITIALS_DIALOG_MESSAGE, "", new IInputValidator() { //$NON-NLS-1$
							@Override
							public String isValid(final String newText) {
								if (newText.length() <= 0) {
									return Messages.PROJECTS_VIEW_AUTHOR_INITIALS_MUST_NOT_BE_EMPTY;
								}
								return null;
							}
						});
				if (dialog.open() != Window.OK) {
					return false;
				}
				GsPreferences.saveAuthorInitials(dialog.getValue());
			}
			return true;
		}
	}

	public static class ConnectJob extends Job {
		private final SessionManager sessionManager;
		private final IProject project;

		protected ConnectJob(final SessionManager sessionManager, final IProject project) throws CoreException {
			super(MessageFormat.format(Messages.PROJECTS_VIEW_CONNECT_JOB_NAME, project.getName()));
			this.sessionManager = sessionManager;
			this.project = project;
		}

		/**
		 * TODO: setting author initials should be most probably in Session#setUp
		 */
		private void setAuthorInitials(final ISession newSession) {
			final String initials = GsPreferences.loadAuthorInitials();
			newSession.execute(new SetAuthorInitials(initials));
		}

		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			final ISession newSession = sessionManager.openSession(project, monitor);
			if (monitor.isCanceled() || newSession == null) {
				return Status.CANCEL_STATUS;
			}
			setAuthorInitials(newSession);
			return Status.OK_STATUS;
		}
	}

	public class DisconnectHandler extends AbstractHandler implements ISelectionChangedListener {
		private IProject selectedProject;

		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
			final List<MethodEditor> dirtyMethodEditors = getDirtyMethodEditors(selectedProject);
			if (!dirtyMethodEditors.isEmpty() && !MessageDialog.openQuestion(window.getShell(), Messages.PROJECTS_VIEW_OPEN_EDITORS_WARNING_DIALOG_TITLE,
					MessageFormat.format(Messages.PROJECTS_VIEW_OPEN_EDITORS_WARNING_DIALOG_MESSAGE, dirtyMethodEditors.size()))) {
				return null;
			}

			if (sessionManager.hasSession(selectedProject)) {
				sessionManager.closeSession(selectedProject, new NullProgressMonitor());
			}
			return selectedProject;
		}

		@Override
		public boolean isEnabled() {
			return selectedProject != null && !sessionManager.isConnectingProject(selectedProject) && sessionManager.hasSession(selectedProject);
		}

		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			final Object selection = ((IStructuredSelection) event.getSelection()).getFirstElement();

			if (selection == selectedProject) {
				return;
			}

			try {
				selectedProject = null;
				if (selection instanceof IProject && ((IProject) selection).hasNature(ProjectNature.ID)) {
					selectedProject = (IProject) selection;
				}
				fireHandlerChanged(new HandlerEvent(this, true, false));
			} catch (final CoreException e) {
				GemDevUiPlugin.getDefault().logError(e);
			}
		}

		/**
		 * Collect all method editors with unsaved modifications for session opened on project.
		 */
		private List<MethodEditor> getDirtyMethodEditors(final IProject project) {
			final ISession session = sessionManager.getSession(project);
			if (session == null) {
				return Lists.newLinkedList();
			}

			final List<MethodEditor> dirtyMethodEditors = Lists.newLinkedList();
			for (final IWorkbenchWindow window : PlatformUI.getWorkbench().getWorkbenchWindows()) {
				for (final IWorkbenchPage page : window.getPages()) {
					for (final IEditorPart editor : page.getDirtyEditors()) {
						if (editor instanceof MethodEditor) {
							final ISession editorSession = ((MethodEditor) editor).getMethodEditorInput().getMethod().getSession();
							if (session.equals(editorSession)) {
								dirtyMethodEditors.add((MethodEditor) editor);
							}
						}
					}
				}
			}
			return dirtyMethodEditors;
		}
	}

	private final class PartActivationListener implements IPartListener2 {
		private IWorkbenchPart partBeingActivated;

		@Override
		public void partActivated(final IWorkbenchPartReference partRef) {
			if (isLinkingEnabled() && partBeingActivated == null) {
				final IWorkbenchPart part = partRef.getPart(true);

				if (part instanceof IEditorLinker) {
					try {
						partBeingActivated = part;
						((IEditorLinker) part).linkActivated(ProjectsView.this);
					} finally {
						partBeingActivated = null;
					}
				}
			}
		}

		@Override
		public void partBroughtToTop(final IWorkbenchPartReference partRef) {
		}

		@Override
		public void partClosed(final IWorkbenchPartReference partRef) {
		}

		@Override
		public void partDeactivated(final IWorkbenchPartReference partRef) {
		}

		@Override
		public void partHidden(final IWorkbenchPartReference partRef) {
		}

		@Override
		public void partInputChanged(final IWorkbenchPartReference partRef) {
		}

		@Override
		public void partOpened(final IWorkbenchPartReference partRef) {
		}

		@Override
		public void partVisible(final IWorkbenchPartReference partRef) {
		}
	}

	/**
	 * Specialised provider for {@link CommonViewer} which highlights resources for which exists session. Uses bold font for highlighting, otherwise uses
	 * default.
	 */
	private class SessionResourceLabelProvider extends DecoratingLabelProvider {
		public SessionResourceLabelProvider(final ILabelProvider provider, final ILabelDecorator decorator) {
			super(provider, decorator);
		}

		@Override
		public Font getFont(final Object element) {
			if (element instanceof IProject && sessionManager.hasSession((IProject) element)) {
				return sessionResourceFont;
			}
			return super.getFont(element);
		}
	}

	public static final String VIEW_ID = "net.karpisek.gemdev.ui.projectsView"; //$NON-NLS-1$

	private final SessionManager sessionManager;

	private Font sessionResourceFont;

	private ConnectHandler connectHandler;

	private DisconnectHandler disconnectHandler;

	private ILabelDecorator defaultDecorator;

	private PartActivationListener editorsListener;

	public ProjectsView() {
		sessionManager = GemDevUiPlugin.getSessionManager();
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);

		sessionManager.addListener(this);

		final CommonViewer viewer = getCommonViewer();
		sessionResourceFont = createBoldFont(viewer.getControl().getFont());

		GuiUtils.activateContext(getSite());
		GuiUtils.activateHandlers(getSite(), ICommands.CONNECT, connectHandler = new ConnectHandler(), ICommands.DISCONNECT,
				disconnectHandler = new DisconnectHandler());
		getSite().getSelectionProvider().addSelectionChangedListener(connectHandler);
		getSite().getSelectionProvider().addSelectionChangedListener(disconnectHandler);

		// use same label provider as is in default common viewer but wrap with font change aware label provider
		final ILabelProvider defaultLabelProvider = viewer.getNavigatorContentService().createCommonLabelProvider();
		defaultDecorator = PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();
		viewer.setLabelProvider(new SessionResourceLabelProvider(defaultLabelProvider, defaultDecorator));

		editorsListener = new PartActivationListener();
		getSite().getPage().addPartListener(editorsListener);
	}

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(editorsListener);
		getSite().getSelectionProvider().removeSelectionChangedListener(disconnectHandler);
		getSite().getSelectionProvider().removeSelectionChangedListener(connectHandler);
		defaultDecorator.dispose();
		sessionResourceFont.dispose();
		sessionManager.removeListener(this);
		super.dispose();
	}

	@Override
	public void sessionClosed(final ISession session) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				getCommonViewer().update(session.getProject(), null);
			}
		});
	}

	@Override
	public void sessionOpened(final ISession session) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				getCommonViewer().update(session.getProject(), null);
			}
		});
	}

	public void setSelection(final ISession session) {
		if (session == null) {
			getCommonViewer().setSelection(null);
			return;
		}
		getCommonViewer().setSelection(new StructuredSelection(session.getProject()), true);
	}

	public void show(final IFile file) {
		getCommonViewer().expandToLevel(file, AbstractTreeViewer.ALL_LEVELS);
		getCommonViewer().setSelection(new StructuredSelection(file));
	}

	/**
	 * Creates new font based on input font with bold style. Client is responsible for dispose.
	 * 
	 * @param font initial
	 * @return new font
	 */
	private Font createBoldFont(final Font font) {
		final FontData[] fontData = font.getFontData();
		for (final FontData data : fontData) {
			data.setStyle(data.getStyle() | SWT.BOLD);
		}
		return new Font(font.getDevice(), fontData);
	}
}
