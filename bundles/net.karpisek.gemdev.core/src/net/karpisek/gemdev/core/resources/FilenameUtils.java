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
package net.karpisek.gemdev.core.resources;

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.net.actions.MethodReference;

/**
 * Converting of method names to filenames with removing of all characters illegal for file systems.
 * 
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class FilenameUtils {
	public static final char COLON_REPLACEMENT_CHAR = ';';
	/**
	 * Illegal characters for filenames on windows. They are taken as worst possible case. On unix it is less restricted so it is safe.
	 */
	private static final char[] ILLEGAL_CHARACTERS = new char[] { '\\', '/', ':', '*', '?', '<', '>', '|', '"' };

	public static String getFilename(final MethodReference m, final String extension, final String defaultName) {
		final String name = m.getMethodName();
		if (name.indexOf(':') >= 0) {
			// this is keyword message name - replace only all ':' characters
			return getFilename(m.getClassName(), m.isInstanceSide(), name.replace(':', COLON_REPLACEMENT_CHAR), extension);
		}

		for (int i = 0; i < ILLEGAL_CHARACTERS.length; i++) {
			if (name.indexOf(ILLEGAL_CHARACTERS[i]) >= 0) {
				return getFilename(m.getClassName(), m.isInstanceSide(), defaultName, extension);
			}
		}

		// this really should be unary message - that "should" be safe to use directly
		return getFilename(m.getClassName(), m.isInstanceSide(), name, extension);
	}

	/**
	 * Replace in string all characters illegal for file system files with defined character.
	 * 
	 * @param filename which should be checked.
	 * @param replaceChar which should be used as replacement (must not be illegal).
	 * @return
	 */
	public static String replaceIllegalFilenameCharacters(final String filename, final char replaceChar) {
		Preconditions.checkArgument(filename != null, "Filename must not be null"); //$NON-NLS-1$
		for (final char c : ILLEGAL_CHARACTERS) {
			Preconditions.checkArgument(c != replaceChar, "Replacement character must not be illegal character itself"); //$NON-NLS-1$
		}

		String s = filename;
		for (final char c : ILLEGAL_CHARACTERS) {
			s = s.replace(c, replaceChar);
		}
		return s;
	}

	private static String getFilename(final String className, final boolean instanceSide, final String escapedMethodName, final String extension) {
		final StringBuilder sb = new StringBuilder();
		sb.append(className);
		if (!instanceSide) {
			sb.append(" class"); //$NON-NLS-1$
		}
		sb.append("#").append(escapedMethodName).append(".").append(extension); //$NON-NLS-1$ //$NON-NLS-2$

		return sb.toString();
	}

	private FilenameUtils() {
		// should not be able to instantiate
	}
}
