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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.search.SearchUI;

/**
 * This handler opens dialog with all selectors for existing methods, after selection of one it will search for implementing methods.
 */
public class FindImplementorsHandler extends SessionManagerListeningHandler {
	private final String dialogTitle;
	private final Image dialogIcon;

	public FindImplementorsHandler() {
		this(Messages.FIND_IMPLEMENTORS_DIALOG_TITLE, GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.FIND_IMPLEMENTORS_ICON));
	}

	public FindImplementorsHandler(final String dialogTitle, final Image dialogIcon) {
		this.dialogTitle = dialogTitle;
		this.dialogIcon = dialogIcon;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		final List<ISession> sessions = getAllSessionsSortedByName();
		final String message = getMessage(sessions);
		final List<Selector> items = getElements(sessions);

		final ElementListSelectionDialog dialog = createDialog(window, message, sessions, items);
		if (dialog.open() == Window.OK) {
			okPressed((Selector) dialog.getResult()[0]);
		}
		return null;
	}

	protected ElementListSelectionDialog createDialog(final IWorkbenchWindow window, final String message, final List<ISession> sessions,
			final List<Selector> elements) {
		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(), new LabelProvider() {
			@Override
			public Image getImage(final Object element) {
				return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.SELECTOR_ICON);
			}

			@Override
			public String getText(final Object element) {
				if (element instanceof Selector) {
					final Selector c = (Selector) element;
					if (sessions.size() > 1) {
						return c.getFullLabel();
					}
					return c.getName();
				}
				return super.getText(element);
			}
		});
		dialog.setTitle(dialogTitle);
		dialog.setImage(dialogIcon);
		dialog.setMessage(message);
		dialog.setElements(elements.toArray());
		return dialog;
	}

	protected List<Selector> getElements(final List<ISession> sessions) {
		final List<Selector> result = Lists.newLinkedList();

		if (sessions.isEmpty()) {
			return Lists.newArrayList();
		}
		for (final ISession s : sessions) {
			result.addAll(s.getCachedSelectors());
		}

		if (sessions.size() > 1) {
			Collections.sort(result, new Comparator<Selector>() {
				@Override
				public int compare(final Selector c1, final Selector c2) {
					return c1.getFullLabel().compareTo(c2.getFullLabel());
				}
			});
		} else {
			Collections.sort(result, new Comparator<Selector>() {
				@Override
				public int compare(final Selector c1, final Selector c2) {
					return c1.getName().compareTo(c2.getName());
				}
			});
		}
		return result;
	}

	protected void okPressed(final Selector result) {
		SearchUI.showImplementorsOf(result);
	}
}
