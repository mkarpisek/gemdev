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

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.team.Messages;
import net.karpisek.gemdev.team.TeamPlugin;
import net.karpisek.gemdev.team.WorkingCopy;
import net.karpisek.gemdev.team.actions.GetSourcesInGscFormat;
import net.karpisek.gemdev.team.model.LocalClass;

public class WriteClassesToWorkingCopyJob extends WorkspaceJob {
	private final Collection<DbClass> classes;

	public WriteClassesToWorkingCopyJob(final Collection<DbClass> classes) {
		super(Messages.WRITE_CLASSES_JOB_NAME);
		this.classes = classes;
		setUser(true);
	}

	@Override
	public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {

		final SubMonitor progress = SubMonitor.convert(monitor);
		progress.beginTask(getName(), classes.size());

		final List<String> notInWorkingCopy = Lists.newLinkedList();
		for (final DbClass c : classes) {
			progress.setTaskName(c.getName());

			c.getSession().abortTransaction();
			if (c.isInSymbolList()) {
				final WorkingCopy wc = TeamPlugin.getDefault().getWorkingCopy(c.getSession());
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}

				try {
					final String src = c.getSession().execute(new GetSourcesInGscFormat(c.getName()));
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}

					final LocalClass localClass = LocalClass.read(wc, new StringReader(src));
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					wc.write(progress.newChild(1), localClass);
				} catch (final CoreException e) {
					return new Status(IStatus.ERROR, TeamPlugin.PLUGIN_ID, MessageFormat.format(Messages.WRITE_CLASSES_ERROR_MESSAGE, c.getClassName()), e);
				} catch (final IOException e) {
					TeamPlugin.getDefault().logError(e);
				}
			} else {
				notInWorkingCopy.add(c.getName());
			}
		}
		monitor.done();

		if (!notInWorkingCopy.isEmpty()) {
			return new Status(IStatus.INFO, TeamPlugin.PLUGIN_ID, "Some classes not written as they are not in session list. " + notInWorkingCopy); //$NON-NLS-1$
		}
		return Status.OK_STATUS;
	}
}
