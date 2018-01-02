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
package net.karpisek.gemdev.ui.browser.classes;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.net.ActionException;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.editor.GsSourceViewer;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Creates new class as subclass of other already selected. Performs abort and commit.
 */
public class NewClassHandler extends AbstractHandler implements ISelectionChangedListener {
	private static class Dialog extends TitleAreaDialog {
		private String value;
		private GsSourceViewer text;

		public Dialog(final Shell parentShell, final String initialValue) {
			super(parentShell);
			this.value = initialValue;

			setShellStyle(getShellStyle() | SWT.RESIZE);
		}

		public String getValue() {
			return value;
		}

		@Override
		protected Control createDialogArea(final Composite parent) {
			final Control c = super.createDialogArea(parent);

			text = new GsSourceViewer((Composite) c, value);
			text.getTextWidget().addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(final ModifyEvent e) {
					value = text.getDocument().get();
				}
			});

			GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(text.getTextWidget());

			setTitle(Messages.NEW_CLASS_DIALOG_TITLE);
			setMessage(Messages.NEW_CLASS_DIALOG_MESSAGE);

			return c;
		}

		@Override
		protected Point getInitialSize() {
			return new Point(500, 400);
		}

	}

	private final ClassHierarchyView view;

	private IStructuredSelection selection;

	public NewClassHandler(final ClassHierarchyView view) {
		this.view = view;
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);

		final TreeNode<?> firstElement = (TreeNode<?>) selection.getFirstElement();
		final DbClass selectedClass = (DbClass) firstElement.getValue();

		final Dialog dialog = new Dialog(window.getShell(), selectedClass.getDefinition());
		if (dialog.open() == Window.OK) {
			try {
				final DbClass c = selectedClass.getSession().createClass(dialog.getValue());
				view.setInput(c);
			} catch (final ActionException e) {
				MessageDialog.openError(window.getShell(), Messages.NEW_CLASS_DIALOG_ERROR, e.getMessage());
			} catch (final CommitFailedException e) {
				view.getViewSite().getActionBars().getStatusLineManager().setErrorMessage(Messages.NEW_CLASS_DIALOG_COMMIT_ERROR);
			}
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return view != null && view.getInputClass() != null && !selection.isEmpty();
	}

	@Override
	public void selectionChanged(final SelectionChangedEvent event) {
		selection = (IStructuredSelection) event.getSelection();

		fireHandlerChanged(new HandlerEvent(this, true, false));
	}
}
