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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.net.UnhandledErrorException;
import net.karpisek.gemdev.net.actions.CheckSyntax;
import net.karpisek.gemdev.net.actions.CompilationError;
import net.karpisek.gemdev.net.actions.Evaluate;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.console.GsConsole;
import net.karpisek.gemdev.ui.inspector.GetObjectInfo;
import net.karpisek.gemdev.ui.inspector.InspectorView;
import net.karpisek.gemdev.ui.inspector.ObjectInfo;

/**
 * Handler for source viewers (for example in method editor and inspector view)
 */
public abstract class InspectItAbstractHandler extends AbstractHandler {
	protected final IWorkbenchSite site;
	protected final ISourceViewer viewer;

	public InspectItAbstractHandler(final IWorkbenchSite site, final ISourceViewer viewer) {
		this.site = site;
		this.viewer = viewer;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final TextSelection selection = (TextSelection) viewer.getSelectionProvider().getSelection();
		final String expr = getSelectedText(selection);
		if (expr.length() == 0) {
			return null;
		}

		try {
			final ISession session = getSession();
			final String context = getContextOop();
			session.execute(new CheckSyntax(expr, context, false));// throws compilation error exception

			final Job job = new Job(Messages.INSPECT_JOB_NAME) {
				public boolean sessionExists(final ISession s) {
					return GemDevUiPlugin.getSessionManager().hasSession(s.getProject());
				}

				@Override
				protected IStatus run(final IProgressMonitor monitor) {
					try {
						if (monitor.isCanceled() || !sessionExists(session)) {
							return Status.CANCEL_STATUS;
						}
						final String resultOopString = session.execute(new Evaluate(expr, context, true));
						if (monitor.isCanceled() || !sessionExists(session)) {
							return Status.CANCEL_STATUS;
						}
						execute(session, resultOopString);
					} catch (final UnhandledErrorException e) {
						GsConsole.getDefault().print(e).show();
					}
					return Status.OK_STATUS;
				}
			};
			job.schedule();
		} catch (final CompilationError e) {
			handleCompilationErrror(selection, e);
		} catch (final ActionException e) {
			handleUnhandledError(e);
		}
		return null;
	}

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

	/**
	 * @return oop of object in which context will be expression inspected or null if no context should be used
	 */
	public abstract String getContextOop();

	/**
	 * @return session in which is expression evaluated
	 */
	public abstract ISession getSession();

	public void handleUnhandledError(final ActionException e) {
		// TODO: WTF i tried with this?
		final String message = e.getMessage().substring(0, e.getMessage().indexOf("StackTrace")); //$NON-NLS-1$

		final IStatus status = new Status(IStatus.ERROR, GemDevUiPlugin.PLUGIN_ID, message);
		final ErrorDialog dialog = new ErrorDialog(site.getShell(), Messages.UNHANDLED_EXCEPTION_DIALOG_TITLE, null, status, IStatus.ERROR);
		dialog.open();
	}

	protected String getSelectedText(final ITextSelection selection) {
		final String expr = selection.getText();
		if (expr.length() == 0) {
			try {
				// in case nothing was selected inspect the line
				final IDocument doc = viewer.getDocument();
				final IRegion info = doc.getLineInformation(selection.getStartLine());
				return doc.get(info.getOffset(), info.getLength());
			} catch (final BadLocationException e) {
				GemDevUiPlugin.getDefault().logError(e);
			}
		}
		return expr;
	}

	protected void handleCompilationErrror(final ITextSelection selection, final CompilationError e) {
		final SourceCodeDecorator decorator = new SourceCodeDecorator(e);

		final IDocument doc = viewer.getDocument();
		try {
			doc.replace(selection.getOffset(), selection.getLength(), decorator.getDecoratedSourceCode());
			// select embedded error message for easy deletion
			final IRegion r = decorator.getFirstDecoratedRegion();
			selectAndReveal(selection.getOffset() + r.getOffset(), r.getLength());
		} catch (final BadLocationException e1) {
			GemDevUiPlugin.getDefault().logError(e);
		}
	}

	protected void selectAndReveal(final int offset, final int length) {
		viewer.setSelectedRange(offset, length);
		viewer.revealRange(offset, length);
	}
}
