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
import net.karpisek.gemdev.lang.model.LocalIdentifier;
import net.karpisek.gemdev.lang.model.MethodModel;

/**
 * Finds all local identifiers (method and block parameters and temporary variables) which are not used in method.
 */
public class UnusedLocalIdentifierssAnalysis extends MethodAnalysis {
	public UnusedLocalIdentifierssAnalysis(final IMethodContext context, final MethodModel model) {
		super(context, model);
	}

	@Override
	public List<IMarker> createProblemMarkers(final IFile file) {
		final List<IMarker> markers = Lists.newLinkedList();
		for (final LocalIdentifier id : model.getLocalIdentifiers(null)) {
			if (id.getReferences().isEmpty()) {
				try {
					final String text = MessageFormat.format(Messages.UNUSED_LOCAL_IDENTIFIERS_WARNING, id.getType().getLabel(), id.getName());
					markers.add(createMarker(file, id.getDeclaration(), text));
				} catch (final CoreException e) {
					CorePlugin.getDefault().logError(e);
				}
			}
		}
		return markers;
	}
}
