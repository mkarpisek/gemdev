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
package net.karpisek.gemdev.compare.ui;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.compare.ComparePlugin;
import net.karpisek.gemdev.compare.DeltaBuilder;
import net.karpisek.gemdev.compare.Messages;
import net.karpisek.gemdev.compare.model.ClassDelta;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionManagerListener;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Performs comparing against class with same name in some other session. Handler enabled if more than one sessions are existing. If it is possible to use more
 * than 1 session, asks user for selecting against which should be comparing done.
 */
public class CompareWithOtherSessionHandler extends AbstractHandler implements ISessionManagerListener {
	public CompareWithOtherSessionHandler() {
		GemDevUiPlugin.getSessionManager().addListener(this);
	}

	@Override
	public void dispose() {
		GemDevUiPlugin.getSessionManager().removeListener(this);
		super.dispose();
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getActiveSite(event).getSelectionProvider().getSelection();

		final TreeNode<?> selectedClassNode = (TreeNode<?>) selection.getFirstElement();
		if (selectedClassNode == null) {
			return null;
		}
		final DbClass sourceClass = (DbClass) (selectedClassNode).getValue();

		final ISession sourceSession = sourceClass.getSession();
		ISession targetSession = null;
		final List<ISession> otherSessions = Lists.newArrayList(GemDevUiPlugin.getSessionManager().getAllSessions().iterator());
		otherSessions.remove(sourceSession);

		if (otherSessions.size() > 1) {
			final ElementListSelectionDialog dialog = new ElementListSelectionDialog(HandlerUtil.getActiveShell(event), new LabelProvider() {
				@Override
				public String getText(final Object element) {
					return ((ISession) element).getProject().getName();
				}
			});
			dialog.setTitle(Messages.SELECT_OTHER_SESSION_MESSAGE);
			dialog.setElements(otherSessions.toArray());
			if (dialog.open() == Window.OK) {
				targetSession = (ISession) dialog.getResult()[0];
			}
		} else {
			targetSession = otherSessions.get(0);
		}

		if (sourceSession == null || targetSession == null) {
			return null;
		}

		final DbClass targetClass = targetSession.getCachedClass(sourceClass.getClassName());
		if (targetClass == null) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event), Messages.COMPARE_JOB_NAME,
					MessageFormat.format(Messages.CLASS_NOT_FOUND_IN_SESSION_ERROR, sourceClass.getClassName(), targetSession.getProject().getName()));
			return null;
		}

		final Job job = new Job(Messages.COMPARE_JOB_NAME) {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				final String title = MessageFormat.format(Messages.COMPARE_EDITOR_TITLE, sourceClass.getName(), sourceClass.getSession().getProject().getName(),
						targetClass.getName(), targetClass.getSession().getProject().getName());
				final ClassDelta delta = DeltaBuilder.diffClass(sourceClass, targetClass);
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}

				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						try {
							final CompareEditorInput input = CompareEditorInput.create(title, delta);
							final IWorkbenchPage page = HandlerUtil.getActiveSite(event).getPage();
							page.openEditor(input, CompareEditor.EDITOR_ID);
						} catch (final PartInitException e) {
							ComparePlugin.getDefault().logError(e);
						}
					}
				});
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled() && GemDevUiPlugin.getSessionManager().getAllSessions().size() > 1;
	}

	@Override
	public void sessionClosed(final ISession session) {
		fireHandlerChanged(new HandlerEvent(this, true, false));
	}

	@Override
	public void sessionOpened(final ISession session) {
		fireHandlerChanged(new HandlerEvent(this, true, false));
	}
}
