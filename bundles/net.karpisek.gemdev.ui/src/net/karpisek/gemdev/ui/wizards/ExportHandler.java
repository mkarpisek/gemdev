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
package net.karpisek.gemdev.ui.wizards;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.resources.FilenameUtils;
import net.karpisek.gemdev.net.actions.GetSources;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Export selected objects in topaz fileout format.
 */
public class ExportHandler extends AbstractHandler {
	// TODO: store last used directory in preferences
	private static String lastPath;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getActiveSite(event).getSelectionProvider().getSelection();
		final List<Object> objects = getValidObjects(selection);
		if (objects.isEmpty()) {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event), Messages.EXPORT, Messages.EXPORT_OBJECTS_NOT_SELECTED_ERROR);
			return null;
		}

		final DirectoryDialog dialog = new DirectoryDialog(HandlerUtil.getActiveShell(event));
		dialog.setMessage(Messages.SELECTED_EXPORT_DIRECTORY_DIALOG_MESSAGE);
		dialog.setFilterPath(ExportHandler.lastPath);
		final String path = dialog.open();
		if (path != null) {
			ExportHandler.lastPath = path;
			try {
				for (final Object object : objects) {
					exportObject(path, object);
				}
			} catch (final IOException e) {
				MessageDialog.openError(HandlerUtil.getActiveShell(event), Messages.EXPORT_ERROR, e.getMessage());
			}
		}
		return null;
	}

	private void export(final String directory, final DbCategory value) throws IOException {
		final String sources = value.getSession().execute(GetSources.forCategory(value.getBehavior().getClassName(), value.isInstanceSide(), value.getName()));
		final String filename = String.format("%s%s [%s].gs", value.getBehavior().getClassName(), value.isInstanceSide() ? "" : " class", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				FilenameUtils.replaceIllegalFilenameCharacters(value.getName(), ' '));
		export(directory, filename, sources);
	}

	private void export(final String directory, final DbClass value) throws IOException {
		final String sources = value.getSession().execute(GetSources.forClass(value.getClassName()));
		export(directory, String.format("%s.gs", value.getClassName()), sources); //$NON-NLS-1$
	}

	private void export(final String directory, final DbMethod value) throws IOException {
		final String sources = value.getSession().execute(GetSources.forMethod(value.getBehavior().getClassName(), value.isInstanceSide(), value.getName()));

		final String filename = FilenameUtils.getFilename(new MethodReference(value.getBehavior().getClassName(), value.isInstanceSide(), value.getName()),
				"gs", //$NON-NLS-1$
				"" //$NON-NLS-1$
		);

		export(directory, filename, sources);

	}

	private void export(final String directory, final String filename, final String sources) throws IOException {
		Files.write(sources, new File(directory + File.separator + filename), Charsets.UTF_8);
	}

	private void exportObject(final String directory, final Object object) throws IOException {
		if (object instanceof TreeNode && ((TreeNode<?>) object).getValue() instanceof DbClass) {
			export(directory, (DbClass) ((TreeNode<?>) object).getValue());
		}
		if (object instanceof DbClass) {
			export(directory, (DbClass) object);
		}
		if (object instanceof DbCategory) {
			export(directory, (DbCategory) object);
		}
		if (object instanceof DbMethod) {
			export(directory, (DbMethod) object);
		}
	}

	private List<Object> getValidObjects(final ISelection selection) {
		final List<Object> result = Lists.newLinkedList();
		if (selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
			return result;
		}

		for (final Iterator<?> iterator = ((IStructuredSelection) selection).iterator(); iterator.hasNext();) {
			final Object object = iterator.next();
			if (object instanceof DbClass || object instanceof DbCategory || object instanceof DbMethod) {
				result.add(object);
			} else {
				if (object instanceof TreeNode && ((TreeNode<?>) object).getValue() instanceof DbClass) {
					// class hierarchy nodes
					result.add(((TreeNode<?>) object).getValue());
				}
			}
		}
		return result;
	}
}
