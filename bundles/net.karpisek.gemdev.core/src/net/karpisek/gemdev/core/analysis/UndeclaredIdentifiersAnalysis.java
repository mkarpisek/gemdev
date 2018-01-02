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
package net.karpisek.gemdev.core.analysis;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.CorePlugin;
import net.karpisek.gemdev.core.Messages;
import net.karpisek.gemdev.lang.model.Identifier;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.lang.parser.GsTree;

/**
 * Find all identifiers which are not declared as local or global or class.
 */
public class UndeclaredIdentifiersAnalysis extends MethodAnalysis {

	public UndeclaredIdentifiersAnalysis(final IMethodContext context, final MethodModel model) {
		super(context, model);
	}

	@Override
	public List<IMarker> createProblemMarkers(final IFile file) {
		final List<IMarker> markers = Lists.newLinkedList();
		for (final Identifier id : model.getUndeclaredIdentifiers()) {
			if (!context.isSystemDeclaring(id.getName())) {
				try {
					final String text = MessageFormat.format(Messages.UNDECLARED_IDENTIFIERS_WARNING, id.getName().toString());
					for (final GsTree t : id.getReferences()) {
						markers.add(createMarker(file, t, text));
					}
				} catch (final CoreException e) {
					CorePlugin.getDefault().logError(e);
				}
			}
		}
		return markers;
	}

}
