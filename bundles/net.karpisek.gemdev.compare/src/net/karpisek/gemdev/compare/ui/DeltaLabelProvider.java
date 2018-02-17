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

import java.util.Map;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

import com.google.common.collect.Maps;

import net.karpisek.gemdev.compare.model.CategoryAdded;
import net.karpisek.gemdev.compare.model.CategoryChanged;
import net.karpisek.gemdev.compare.model.CategoryDelta;
import net.karpisek.gemdev.compare.model.CategoryRemoved;
import net.karpisek.gemdev.compare.model.ClassAdded;
import net.karpisek.gemdev.compare.model.ClassChanged;
import net.karpisek.gemdev.compare.model.ClassDelta;
import net.karpisek.gemdev.compare.model.ClassRemoved;
import net.karpisek.gemdev.compare.model.MethodAdded;
import net.karpisek.gemdev.compare.model.MethodChanged;
import net.karpisek.gemdev.compare.model.MethodDelta;
import net.karpisek.gemdev.compare.model.MethodRemoved;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Implementation for displaying delta objects.
 */
public class DeltaLabelProvider extends ColumnLabelProvider {
	enum TypeOfChange {
		NONE, ADDED, REMOVED, CHANGED
	}

	public static final RGB RED = new RGB(255, 0, 0);
	public static final RGB GREEN = new RGB(0, 192, 0);
	public static final RGB BLUE = new RGB(0, 0, 255);
	public static final RGB GREY = new RGB(192, 192, 192);

	public static final RGB YELLOW = new RGB(128, 128, 0);

	private final Map<TypeOfChange, Color> colors;

	public DeltaLabelProvider() {
		colors = Maps.newHashMap();
		colors.put(TypeOfChange.NONE, GemDevUiPlugin.getDefault().getColor(GREY));
		colors.put(TypeOfChange.ADDED, GemDevUiPlugin.getDefault().getColor(GREEN));
		colors.put(TypeOfChange.REMOVED, GemDevUiPlugin.getDefault().getColor(RED));
		colors.put(TypeOfChange.CHANGED, GemDevUiPlugin.getDefault().getColor(YELLOW));
	}

	@Override
	public Color getForeground(final Object element) {
		return colors.get(getTypeOfChange(element));
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof ClassDelta) {
			return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.CLASS_ICON);
		}
		if (element instanceof CategoryDelta) {
			final CategoryDelta d = (CategoryDelta) element;
			if (d.isInstanceSide()) {
				return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.INSTANCE_CATEGORY_ICON);
			}
			return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.CLASS_CATEGORY_ICON);
		}
		if (element instanceof MethodDelta) {
			final MethodDelta d = (MethodDelta) element;
			if (d.isInstanceSide()) {
				return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.INSTANCE_METHOD_ICON);
			}
			return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.CLASS_METHOD_ICON);
		}
		return null;
	}

	private TypeOfChange getTypeOfChange(final Object element) {
		if (element instanceof ClassAdded || element instanceof CategoryAdded || element instanceof MethodAdded) {
			return TypeOfChange.ADDED;
		}
		if (element instanceof ClassRemoved || element instanceof CategoryRemoved || element instanceof MethodRemoved) {
			return TypeOfChange.REMOVED;
		}
		if (element instanceof ClassChanged || element instanceof ClassChanged || element instanceof CategoryChanged || element instanceof MethodChanged) {
			return TypeOfChange.CHANGED;
		}
		return TypeOfChange.NONE;
	}

}