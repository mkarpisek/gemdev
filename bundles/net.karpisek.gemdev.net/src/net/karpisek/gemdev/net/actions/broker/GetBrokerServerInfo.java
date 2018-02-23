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
package net.karpisek.gemdev.net.actions.broker;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.net.SessionAction;

/**
 * Answers basic information about running broker server.
 */
public class GetBrokerServerInfo extends SessionAction<GetBrokerServerInfo.Info> implements IBrokerAction<GetBrokerServerInfo.Info> {
	public static class Info {
		private int portNumber;
		private String serverStartTime;
		private String lastRequestTime;
		private int firstSessionServerPortNumber;
		private int lastSessionServerPortNumber;
		private List<String> profileNames;

		public int getFirstSessionServerPortNumber() {
			return firstSessionServerPortNumber;
		}

		public String getLastRequestTime() {
			return lastRequestTime;
		}

		public int getLastSessionServerPortNumber() {
			return lastSessionServerPortNumber;
		}

		public int getPortNumber() {
			return portNumber;
		}

		public List<String> getProfileNames() {
			return profileNames;
		}

		public String getServerStartTime() {
			return serverStartTime;
		}
	}

	@Override
	public String asRequestString() {
		return "#('' '' #info #())"; //$NON-NLS-1$
	}

	@Override
	public Info asResponse(final String responseString) {
		Preconditions.checkNotNull(responseString);

		final String[] lines = responseString.split("\n"); //$NON-NLS-1$

		int i = 0;
		final Info info = new Info();
		info.portNumber = (Integer.valueOf(lines[i++])).intValue();
		info.serverStartTime = lines[i++];
		info.lastRequestTime = lines[i++];
		info.firstSessionServerPortNumber = (Integer.valueOf(lines[i++])).intValue();
		info.lastSessionServerPortNumber = (Integer.valueOf(lines[i++])).intValue();

		info.profileNames = Lists.newLinkedList();
		for (int i2 = i; i2 < lines.length; i2++) {
			info.profileNames.add(lines[i2]);
		}
		return info;
	}
}
