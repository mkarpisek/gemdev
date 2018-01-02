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
/**
 * 
 */
package net.karpisek.gemdev.net.actions.broker;

/**
 * Connection profile for broker server.
 */
public class Profile {
	public static final String DEFAULT_NAME = "glass"; //$NON-NLS-1$
	public static final String DEFAULT_USER_NAME = "DataCurator"; //$NON-NLS-1$
	public static final String DEFAULT_PASSWORD = "swordfish"; //$NON-NLS-1$

	private final String name;
	private final String userName;
	private final String password;

	/**
	 * @param name of profile
	 * @param userName of GS user used for connection
	 * @param password md5sum of GS password (in hex format)
	 */
	public Profile(final String name, final String userName, final String password) {
		this.name = name;
		this.userName = userName;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return String.format("profile: %s userName: %s (%s)", getName(), getUserName(), getClass().getSimpleName()); //$NON-NLS-1$
	}

	private String asSmalltalkArray(final String... strings) {
		final StringBuilder sb = new StringBuilder();
		sb.append("#("); //$NON-NLS-1$
		for (final String s : strings) {
			sb.append("'").append(s.replaceAll("'", "''")).append("' "); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		sb.append(")"); //$NON-NLS-1$
		return sb.toString();
	}

	String asSmalltalkArrayString() {
		return asSmalltalkArray(getName(), getUserName(), getPassword());
	}
}