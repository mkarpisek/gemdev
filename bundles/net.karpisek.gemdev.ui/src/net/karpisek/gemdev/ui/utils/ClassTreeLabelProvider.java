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
package net.karpisek.gemdev.ui.utils;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;

/**
 * Implementation of Label provider for class hierarchy tree.
 */
public class ClassTreeLabelProvider extends ColumnLabelProvider {
	public static final RGB GREY = new RGB(192, 192, 192);

	@Override
	public Color getForeground(final Object element) {
		if (!isEnabled(element)) {
			return GemDevUiPlugin.getDefault().getColor(GREY);
		}
		return super.getForeground(element);
	}

	@Override
	public Image getImage(final Object element) {
		String imageName = GemDevUiPlugin.CLASS_ICON;
		if (!isEnabled(element)) {
			imageName = GemDevUiPlugin.CLASS_DISABLED_ICON;
		}
		return GemDevUiPlugin.getDefault().getImage(imageName);
	}

	@Override
	public String getText(final Object element) {
		final DbClass c = asClass(element);
		if (c != null && !c.isInSymbolList()) {
			return String.format("%s (%s)", super.getText(element), Messages.NOT_IN_SYMBOL_LIST_WARNING); //$NON-NLS-1$
		}
		return super.getText(element);
	}

	private DbClass asClass(final Object element) {
		if (element == null) {
			return null;
		}
		final TreeNode<?> node = (TreeNode<?>) element;
		return (DbClass) node.getValue();
	}

	private boolean isEnabled(final Object element) {
		final TreeNode<?> node = (TreeNode<?>) element;
		if (node == null || !node.isEnabled()) {
			return false;
		}

		final DbClass c = asClass(element);
		return c != null && c.isInSymbolList();
	}
}
