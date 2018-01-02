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
package net.karpisek.gemdev.team.model;

import com.google.common.base.Objects;

import net.karpisek.gemdev.core.model.IBehavior;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.net.actions.MethodReference;
import net.karpisek.gemdev.team.Messages;
import net.karpisek.gemdev.team.WorkingCopy;

/**
 * In memory representation of {@link IMethod}.
 */
public class LocalMethod implements IMethod {
	private final String name;
	private LocalCategory category;

	private final String sourceCode;

	public LocalMethod(final LocalCategory category, final String name, final String sourceCode) {
		this.category = category;

		this.name = name;
		this.sourceCode = sourceCode;
		category.add(this);
	}

	@Override
	public MethodReference asReference() {
		return new MethodReference(getBehavior().getClassName(), isInstanceSide(), getName());
	}

	public void delete() {
		if (getCategory() == null) {
			return;
		}

		((LocalBehavior) getBehavior()).remove(this);
		((LocalCategory) getCategory()).remove(this);
		category = null;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof LocalMethod) {
			final LocalMethod other = (LocalMethod) obj;
			return Objects.equal(getBehavior().getName(), other.getBehavior().getName()) && Objects.equal(getName(), other.getName());
		}
		return false;
	}

	@Override
	public IBehavior getBehavior() {
		return getCategory().getBehavior();
	}

	@Override
	public ICategory getCategory() {
		return category;
	}

	@Override
	public String getDescription() {
		return String.format("%s#%s", getBehavior().getName(), getName()); //$NON-NLS-1$
	}

	@Override
	public String getFullDescription() {
		String projectName = Messages.UNKNOWN_PROJECT;

		final WorkingCopy wc = ((LocalClass) getBehavior().getInstanceSide()).getWorkingCopy();
		if (wc != null) {
			projectName = wc.getProject().getName();
		}

		return String.format("%s [%s] @ %s (Working Copy)", getDescription(), getCategory().getName(), projectName); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSourceCode() {
		return sourceCode;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getBehavior().getName(), getName());
	}

	@Override
	public boolean isInstanceSide() {
		return getCategory().isInstanceSide();
	}

	@Override
	public String toString() {
		return String.format("%s#%s[%s]", getBehavior().getName(), getName(), getCategory().getName()); //$NON-NLS-1$
	}
}
