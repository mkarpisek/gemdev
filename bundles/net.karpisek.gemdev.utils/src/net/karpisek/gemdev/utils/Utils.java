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
package net.karpisek.gemdev.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Supplier;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * Some utility methods.
 */
public class Utils {
	public static String md5sum(final String string) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(string.getBytes("UTF-8"));
			final byte[] resultArray = digest.digest();
			final StringBuilder sb = new StringBuilder();
			for (final byte b : resultArray) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (final NoSuchAlgorithmException e) {
			// it should exist
			throw new RuntimeException(e);
		} catch (final UnsupportedEncodingException e) {
			// utf8 should always exist
			throw new RuntimeException(e);
		}
	}

	public static <K, V> Multimap<K, V> newHashSetMultimap() {
		return Multimaps.newSetMultimap(new HashMap<K, Collection<V>>(), new Supplier<Set<V>>() {
			@Override
			public Set<V> get() {
				return new HashSet<V>();
			}
		});
	}
}
