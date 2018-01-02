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

import com.google.common.base.Objects;

/**
 * @param <K>
 * @param <V>
 */
public class Pair<K, V> {
	private final K key;
	private final V value;

	public Pair(final K key, final V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Pair) {
			final Pair<?, ?> other = (Pair<?, ?>) obj;
			return Objects.equal(getKey(), other.getKey()) && Objects.equal(getValue(), other.getValue());
		}
		return super.equals(obj);
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getKey(), getValue());
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", key.toString(), value.toString()); //$NON-NLS-1$
	}

}
