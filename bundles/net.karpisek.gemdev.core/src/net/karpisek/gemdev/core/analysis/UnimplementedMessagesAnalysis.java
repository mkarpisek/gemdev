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
import net.karpisek.gemdev.lang.model.Message;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.lang.parser.GsTree;

/**
 * Find all message calls which are not implemented. Markers are created for all message parts (so keyword message will have multiple markers).
 */
public class UnimplementedMessagesAnalysis extends MethodAnalysis {

	public UnimplementedMessagesAnalysis(final IMethodContext context, final MethodModel model) {
		super(context, model);
	}

	@Override
	public List<IMarker> createProblemMarkers(final IFile file) {
		final List<IMarker> markers = Lists.newLinkedList();

		for (final Message msg : model.getMessages(null)) {
			if (!context.isSystemImplementing(msg.getName())) {
				final String text = MessageFormat.format(Messages.UNIMPLEMENTED_MESSAGES_WARNING, msg.getName());

				for (final GsTree t : msg.getOccurences()) {
					try {
						markers.add(createMarker(file, t, text));
					} catch (final CoreException e) {
						CorePlugin.getDefault().logError(e);
					}
				}
			}
		}
		return markers;
	}

}
