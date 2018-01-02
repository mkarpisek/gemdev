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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.utils.MethodReferenceTree.MethodReferenceTreeNode;

/**
 * 

 *
 */
public class MethodReferenceTreeLabelProvider extends LabelProvider {
	@Override
	public Image getImage(final Object element) {
		final MethodReferenceTreeNode node = (MethodReferenceTreeNode) element;
		final MethodReference ref = node.getValue();
		if (ref == null) {
			return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.CLASS_DISABLED_ICON);
		}
		if (ref.isInstanceSide()) {
			return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.INSTANCE_METHOD_ICON);
		}
		return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.CLASS_METHOD_ICON);
	}
}
