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
package net.karpisek.gemdev.ui.refactoring;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import net.karpisek.gemdev.ui.Messages;

/**

 */
public class RenameWizard extends RefactoringWizard {
	/**
	 * Page with input box for entering new name
	 */
	private static class RenameWizardPage extends UserInputWizardPage {
		protected RenameInfo info;
		protected Text input;

		public RenameWizardPage(final RenameInfo info) {
			super(info.getDescription());
			this.info = info;
		}

		@Override
		public void createControl(final Composite parent) {
			final GridLayoutFactory f = GridLayoutFactory.swtDefaults();
			final Composite c = new Composite(parent, SWT.NONE);

			final CLabel label = new CLabel(c, SWT.LEFT);
			label.setText(Messages.RENAME_DIALOG_LABEL + ":"); //$NON-NLS-1$
			GridDataFactory.fillDefaults().grab(false, false).applyTo(label);

			input = new Text(c, SWT.BORDER);
			input.addModifyListener(new ModifyListener() {

				@Override
				public void modifyText(final ModifyEvent e) {
					textChanged(e);
				}

			});
			GridDataFactory.fillDefaults().grab(true, false).applyTo(input);

			f.numColumns(2);
			f.generateLayout(c);
			setControl(c);

			input.setText(info.getOldName());
			input.selectAll();
			input.setFocus();
		}

		private void textChanged(final ModifyEvent e) {
			final String newName = ((Text) e.getSource()).getText();
			setErrorMessage(null);
			setPageComplete(false);
			if (newName.length() == 0) {
				setErrorMessage(Messages.NAME_IS_EMPTY_ERROR);
				return;
			}

			if (newName.equals(info.getOldName())) {
				return; // we dont refactor to same name -> only disable ok button
			}

			info.setNewName(newName);
			setPageComplete(true);
		}
	}

	private final RenameInfo info;

	public RenameWizard(final RenameRefactoring refactoring, final RenameInfo info) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE);
		this.info = info;
	}

	@Override
	protected void addUserInputPages() {
		setDefaultPageTitle(getRefactoring().getName());
		addPage(new RenameWizardPage(info));
	}
}
