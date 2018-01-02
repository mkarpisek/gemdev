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

import java.util.List;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.compare.model.ClassDelta;

/**
 * Input for compare editor build on changes of two classes.
 */
public class CompareEditorInput extends PlatformObject implements IEditorInput {
	public static CompareEditorInput create(final String title, final ClassDelta delta) {
		final List<ClassDelta> deltas = Lists.newLinkedList();
		if (delta != null) {
			deltas.add(delta);
		}
		return new CompareEditorInput(title, deltas);
	}

	private final String title;
	private final List<ClassDelta> deltas;

	public CompareEditorInput(final String title, final List<ClassDelta> deltas) {
		this.title = title;
		this.deltas = deltas;
	}

	@Override
	public boolean exists() {
		return false;
	}

	public List<ClassDelta> getDeltas() {
		return deltas;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return ImageDescriptor.getMissingImageDescriptor();
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return getName();
	}
}
