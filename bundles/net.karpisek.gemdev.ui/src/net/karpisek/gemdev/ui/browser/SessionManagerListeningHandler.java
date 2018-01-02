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

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.HandlerEvent;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionManagerListener;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.Messages;

/**

 */
public abstract class SessionManagerListeningHandler extends AbstractHandler implements ISessionManagerListener {
	public SessionManagerListeningHandler() {
		GemDevUiPlugin.getSessionManager().addListener(this);
	}

	@Override
	public void dispose() {
		GemDevUiPlugin.getSessionManager().removeListener(this);
		super.dispose();
	}

	@Override
	public boolean isEnabled() {
		return GemDevUiPlugin.getSessionManager().getAllSessions().size() > 0;
	}

	@Override
	public void sessionClosed(final ISession session) {
		fireHandlerChanged(new HandlerEvent(this, true, true));
	}

	@Override
	public void sessionOpened(final ISession session) {
		fireHandlerChanged(new HandlerEvent(this, true, true));
	}

	protected List<ISession> getAllSessionsSortedByName() {
		final List<ISession> sessions = Lists.newArrayList(GemDevUiPlugin.getSessionManager().getAllSessions().iterator());
		Collections.sort(sessions, new Comparator<ISession>() {
			@Override
			public int compare(final ISession o1, final ISession o2) {
				return o1.getProject().getName().compareTo(o2.getProject().getName());
			}
		});
		return sessions;
	}

	protected String getMessage(final List<ISession> sessions) {
		if (sessions.size() <= 0) {
			return ""; //$NON-NLS-1$
		}

		if (sessions.size() <= 1) {
			return MessageFormat.format(Messages.FIND_DIALOG_MESSAGE_SINGLE_PROJECT, sessions.get(0).getProject().getName());
		}
		// handling of multiple open sessions - it must be possible to select any class in any of open sessions
		final StringBuilder sb = new StringBuilder();
		sb.append(MessageFormat.format(Messages.FIND_DIALOG_MESSAGE_MULTIPLE_PROJECTS, sessions.size()));

		boolean first = true;
		for (final ISession s : sessions) {
			if (!first) {
				sb.append(", "); //$NON-NLS-1$
			}
			sb.append("'" + s.getProject().getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
			first = false;
		}
		return sb.toString();
	}
}
