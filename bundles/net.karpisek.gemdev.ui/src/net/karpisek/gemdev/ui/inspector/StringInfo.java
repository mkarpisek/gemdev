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
package net.karpisek.gemdev.ui.inspector;

import org.eclipse.swt.graphics.Image;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**

 */
public class StringInfo extends ObjectInfo {
	public StringInfo(final ISession session, final String oop, final String className, final String printString, final int namedVarSize,
			final int unnamedVarSize) {
		super(session, oop, className, printString, namedVarSize, unnamedVarSize);
	}

	@Override
	public Image getIcon() {
		return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.STRING_ICON);
	}
}
