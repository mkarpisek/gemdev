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
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.compare.ComparePlugin;
import net.karpisek.gemdev.compare.DeltaBuilder;
import net.karpisek.gemdev.compare.Messages;
import net.karpisek.gemdev.compare.model.ClassDelta;
import net.karpisek.gemdev.compare.ui.CompareEditor;
import net.karpisek.gemdev.compare.ui.CompareEditorInput;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.team.TeamPlugin;
import net.karpisek.gemdev.team.WorkingCopy;
import net.karpisek.gemdev.team.model.LocalClass;

public class CompareClassesWithWorkingCopyJob extends WorkspaceJob {

	private final Collection<DbClass> classes;
	private final IWorkbenchPage page;

	public CompareClassesWithWorkingCopyJob(final Collection<DbClass> classes, final IWorkbenchPage page) {
		super(Messages.COMPARE_CLASSES_JOB_NAME);
		this.classes = classes;
		this.page = page;
		setUser(true);
	}

	@Override
	public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor);
		progress.beginTask(getName(), classes.size());

		final List<IClass> source = Lists.newLinkedList();
		final List<IClass> target = Lists.newLinkedList();
		for (final DbClass c : classes) {
			progress.setTaskName(c.getName());

			c.getSession().abortTransaction();
			source.add(c);

			final WorkingCopy wc = TeamPlugin.getDefault().getWorkingCopy(c.getSession());
			try {
				final LocalClass targetClass = wc.read(c.getName());
				if (targetClass != null) {
					target.add(targetClass);
				}
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				progress.worked(1);
			} catch (final CoreException e) {
				return new Status(IStatus.ERROR, TeamPlugin.PLUGIN_ID, "Failed " + Messages.COMPARE_JOB_NAME, e); //$NON-NLS-1$
			}
		}

		final List<ClassDelta> deltas = DeltaBuilder.diffClasses(source, target);
		final String title = MessageFormat.format(Messages.COMPARE_EDITOR_TITLE, source.get(0).getName(),
				((DbClass) source.get(0)).getSession().getProject().getName(), source.get(0).getName(), Messages.WORKING_COPY);

		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		monitor.done();

		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					final CompareEditorInput input = new CompareEditorInput(title, deltas);
					page.openEditor(input, CompareEditor.EDITOR_ID);
				} catch (final PartInitException e) {
					ComparePlugin.getDefault().logError(e);
				}
			}
		});
		return Status.OK_STATUS;
	}

}
