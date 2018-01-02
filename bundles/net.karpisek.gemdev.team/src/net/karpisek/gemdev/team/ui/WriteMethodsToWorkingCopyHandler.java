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
package net.karpisek.gemdev.team.ui;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.team.LocalMerge;
import net.karpisek.gemdev.team.Messages;
import net.karpisek.gemdev.team.TeamPlugin;
import net.karpisek.gemdev.team.WorkingCopy;
import net.karpisek.gemdev.team.model.LocalClass;

/**
 * Writes selected db methods to working copy class file.
 */
public class WriteMethodsToWorkingCopyHandler extends AbstractWriteHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = getSelection(event);
		final List<IMethod> methods = Lists.newLinkedList();
		for (final Iterator<?> i = selection.iterator(); i.hasNext();) {
			methods.add((DbMethod) i.next());
		}

		final WorkspaceJob job = new WorkspaceJob(Messages.WRITE_METHODS_JOB_NAME) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				final DbClass c = (DbClass) methods.get(0).getBehavior().getInstanceSide();
				final WorkingCopy wc = TeamPlugin.getDefault().getWorkingCopy(c.getSession());

				try {
					final LocalClass localClass = wc.read(c.getClassName());
					LocalMerge merge = null;
					if (localClass == null) {
						merge = new LocalMerge(c);
					} else {
						merge = new LocalMerge(localClass);
						merge.mergeMethods(methods);
					}
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					wc.write(monitor, merge.getResult());
				} catch (final CoreException e) {
					return new Status(IStatus.ERROR, TeamPlugin.PLUGIN_ID, MessageFormat.format(Messages.WRITE_METHODS_ERROR_MESSAGE, methods), e);
				}

				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

}
