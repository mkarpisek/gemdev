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
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * @param <T> of object stored in list
 */
public class HistoryListAction<T> extends Action {
	private final IViewSite site;
	private final String title;
	private final String message;
	private final int maxSize;
	protected final List<T> list;
	private final ILabelProvider labelProvider;

	public HistoryListAction(final IViewSite site, final String title, final String message, final int maxSize, final ILabelProvider labelProvider) {
		super(title, GemDevUiPlugin.getDefault().getImageDescriptor(GemDevUiPlugin.HISTORY_ICON));
		this.site = site;
		this.title = title;
		this.message = message;
		this.maxSize = maxSize;
		this.list = Lists.newLinkedList();
		this.labelProvider = labelProvider;
		refreshState();
	}

	public void add(final T node) {
		if (list.size() >= maxSize) {
			list.remove(0);
		}
		list.remove(node);
		list.add(node);

		refreshState();
	}

	public void clear() {
		list.clear();
		refreshState();
	}

	public List<T> getReversedHistoryList() {
		final List<T> result = Lists.newArrayList(list.iterator());
		Collections.reverse(result);
		return result;
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void okPressed(final T selectedObject) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		final ElementListSelectionDialog dialog = new ElementListSelectionDialog(site.getShell(), labelProvider);
		dialog.setTitle(title);
		dialog.setImage(GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.HISTORY_ICON));
		dialog.setMessage(message);
		dialog.setElements(getReversedHistoryList().toArray());
		if (dialog.open() == Window.OK) {
			okPressed((T) dialog.getResult()[0]);
		}
	}

	private void refreshState() {
		setEnabled(!isEmpty());
	}
}