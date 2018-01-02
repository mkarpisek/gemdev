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
package net.karpisek.gemdev.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPropertyPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PropertyPage;

import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Displays basic information about method.
 */
public class MethodPropertyPage extends PropertyPage implements IWorkbenchPropertyPage {

	private String getClassReport() {
		final DbMethod m = (DbMethod) getElement();
		final String script = GemDevUiPlugin.getDefault().getScript("MethodReport"); //$NON-NLS-1$
		final String expr = String.format(script, m.getBehavior().getClassName(), m.isInstanceSide() ? "true" : "false", m.getName()); //$NON-NLS-1$ //$NON-NLS-2$
		return m.getSession().execute(new PrintIt(expr));
	}

	@Override
	protected Control createContents(final Composite parent) {
		final StyledText text = new StyledText(parent, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		text.setText(getClassReport());
		text.setEditable(false);
		text.setBackground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		return text;
	}
}
