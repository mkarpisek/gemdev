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
package net.karpisek.gemdev.team;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.model.IMethod;
import net.karpisek.gemdev.team.model.LocalClass;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

public abstract class TeamTestCase {
	public void assertHasMethods(final String expectedMethodReport, final LocalClass c) {
		final List<String> list = Lists.newArrayList();
		for (final IMethod m : c.getMethods(true)) {
			list.add(m.toString());
		}
		for (final IMethod m : c.getMethods(false)) {
			list.add(m.toString());
		}
		Collections.sort(list);

		assertEquals(expectedMethodReport, list.toString());
	}

	public String getResource(final String fileName) {
		return UiTestsPlugin.getDefault().getFileContents("/resources/team/" + fileName);
	}
}
