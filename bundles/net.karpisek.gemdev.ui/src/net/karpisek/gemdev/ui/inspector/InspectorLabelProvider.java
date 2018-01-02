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
package net.karpisek.gemdev.ui.inspector;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for inspector view, can work with InspectorNodes or ObjectInfo objects.
 */
public class InspectorLabelProvider extends LabelProvider {
	@Override
	public Image getImage(final Object element) {
		if (element instanceof InspectorNode) {
			return ((InspectorNode) element).getValue().getIcon();
		}
		if (element instanceof ObjectInfo) {
			return ((ObjectInfo) element).getIcon();
		}
		return null;
	}
}
