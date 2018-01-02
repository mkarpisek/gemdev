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

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionManagerListener;
import net.karpisek.gemdev.net.actions.PrintIt;
import net.karpisek.gemdev.net.actions.RemoveFromExportSet;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.HistoryListAction;
import net.karpisek.gemdev.ui.editor.GsSourceViewer;
import net.karpisek.gemdev.ui.editor.InspectItAbstractHandler;
import net.karpisek.gemdev.ui.editor.PrintItAbstractHandler;

/**
 * Shows tree graph of object references. Tree consists of InspectorNode instances which as value holds object proxies. It is possible to evaluate arbitrary
 * smalltalk expressions in context of selected object.
 */
public class InspectorView extends ViewPart implements ISessionManagerListener {
	private class HistoryAction extends HistoryListAction<ObjectInfo> {
		public HistoryAction(final IViewSite site, final int maxSize) {
			super(site, Messages.INSPECTOR_INSPECTED_OBJECTS_HISTORY_DIALOG_TITLE, Messages.INSPECTOR_INSPECTED_OBJECTS_DIALOG_MESSAGE, maxSize,
					new InspectorLabelProvider());
		}

		@Override
		public void clear() {
			for (final ObjectInfo object : list) {
				object.getSession().execute(new RemoveFromExportSet(object.getOop()));
			}
			super.clear();
		}

		@Override
		public void okPressed(final ObjectInfo selectedObject) {
			setInput(selectedObject);
		}
	}

	private final class InpectObjectWithOopAction extends Action {
		private InpectObjectWithOopAction() {
			super(Messages.INSPECTOR_FIND_OBJECT_BY_OOP_DIALOG_TITLE, GemDevUiPlugin.getDefault().getImageDescriptor(GemDevUiPlugin.FIND_ICON));
		}

		public void refresh() {
			setEnabled(getSession() != null);
		}

		@Override
		public void run() {
			final ISession session = getSession();
			if (session == null) {
				MessageDialog.openInformation(getSite().getShell(), getText(), Messages.INSPECTOR_NO_SESSIONS_FOUND_ERROR);
				return;
			}
			final InputDialog dialog = new InputDialog(getSite().getShell(), getText(), Messages.INSPECTOR_FIND_OBJECT_BY_OOP_DIALOG_MESSAGE, "", //$NON-NLS-1$
					new IInputValidator() {

						@Override
						public String isValid(final String newText) {
							final String t = newText.trim();
							if (t.length() <= 0) {
								return Messages.INSPECTOR_EMPTY_OOP_ERROR;
							}
							for (int i = 0; i < t.length(); i++) {
								if (!Character.isDigit(t.charAt(i))) {
									return Messages.INSPECTOR_INVALID_OOP_FORMAT_ERROR;
								}
							}
							return null;
						}
					});
			if (dialog.open() == Window.OK) {
				final String oop = dialog.getValue().trim();
				final String expr = String.format("((Object _objectForOop: %s) == nil)", oop); //$NON-NLS-1$
				if ("true".equals(session.execute(new PrintIt(expr)))) { //$NON-NLS-1$
					MessageDialog.openInformation(getSite().getShell(), getText(),
							MessageFormat.format(Messages.INSPECTOR_OBJECT_WITH_OOP_NOT_FOUND_ERROR, oop));
					return;
				}
				final ObjectInfo info = session.execute(new GetObjectInfo(session, oop));
				setInput(info);
			}
		}
	}

	private class InspectItHandler extends InspectItAbstractHandler {
		public InspectItHandler(final IWorkbenchSite site, final ISourceViewer viewer) {
			super(site, viewer);
		}

		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final ISelectionProvider provider = getSite().getSelectionProvider();
			final ISelection selection = provider.getSelection();

			if (provider == tree) {
				// inspect it in tree viewer
				final InspectorNode node = (InspectorNode) ((IStructuredSelection) selection).getFirstElement();
				if (node != null) {
					setInput(node.getValue());
				}
			}
			if (provider == text) {
				// inspect it in source viewer
				return super.execute(event);
			}
			return null;
		}

		@Override
		public String getContextOop() {
			final InspectorNode node = (InspectorNode) ((IStructuredSelection) tree.getSelection()).getFirstElement();
			if (node == null) {
				return null;
			}
			return node.getValue().getOop();
		}

		@Override
		public ISession getSession() {
			return InspectorView.this.getSession();
		}

		@Override
		public boolean isEnabled() {
			final ISelectionProvider provider = getSite().getSelectionProvider();
			if (provider == tree) {
				return getInputObject() != null && getTreeSelection() != null;
			}
			if (provider == text) {
				return getSession() != null;
			}
			return false;
		}
	}

	private static class ObjectAsStringReadOnlyDialog extends TitleAreaDialog {
		private final ObjectInfo info;
		private GsSourceViewer text;

		public ObjectAsStringReadOnlyDialog(final Shell parentShell, final ObjectInfo info) {
			super(parentShell);
			this.info = info;

			setShellStyle(getShellStyle() | SWT.RESIZE);
		}

		@Override
		protected Control createDialogArea(final Composite parent) {
			final Control c = super.createDialogArea(parent);

			final String value = info.execute(new PrintIt(String.format("(Object _objectForOop: %s) asString", info.getOop())) { //$NON-NLS-1$
				@Override
				public boolean isIdempotent() {
					return true;
				}
			});

			text = new GsSourceViewer((Composite) c, value);
			text.getTextWidget().setEditable(false);

			GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(text.getTextWidget());

			setTitle(Messages.INSPECTOR_OBJECT_AS_STRING_TITLE);
			setMessage(MessageFormat.format(Messages.INSPECTOR_OBJECT_AS_STRING_MESSAGE, info.getOop()));

			return c;
		}

		@Override
		protected Point getInitialSize() {
			return new Point(500, 400);
		}
	}

	private class PrintItHandler extends PrintItAbstractHandler {
		public PrintItHandler(final IWorkbenchSite site, final ISourceViewer viewer) {
			super(site, viewer);
		}

		@Override
		public String getContextOop() {
			final InspectorNode node = (InspectorNode) ((IStructuredSelection) tree.getSelection()).getFirstElement();
			if (node == null) {
				return null;
			}
			return node.getValue().getOop();
		}

		@Override
		public ISession getSession() {
			return InspectorView.this.getSession();
		}

		@Override
		public boolean isEnabled() {
			return getSession() != null;
		}
	}

	public static final String VIEW_ID = "net.karpisek.gemdev.ui.inspectorView"; //$NON-NLS-1$
	private ObjectInfo inputObject;

	private TreeViewer tree;

	private Text label;

	private SourceViewer text;

	private HistoryAction historyAction;

	private InpectObjectWithOopAction inspectObjectWithOopAction;

	private IWorkbenchAction removeAction;

	private IWorkbenchAction copyAction;

	private IWorkbenchAction pasteAction;

	public InspectorView() {

	}

	@Override
	public void createPartControl(final Composite parent) {
		final ViewForm form = new ViewForm(parent, SWT.FLAT);
		final SashForm sash = new SashForm(form, SWT.VERTICAL);

		createTree(sash);
		createText(sash);
		sash.setWeights(new int[] { 70, 30 });
		form.setContent(sash);
		createTitle(form);

		createActions();

		GuiUtils.activateContext(getSite());
		GuiUtils.activateHandlers(getSite(), ICommands.INSPECT_IT, new InspectItHandler(getSite(), text), ICommands.PRINT_IT,
				new PrintItHandler(getSite(), text));
		GemDevUiPlugin.getSessionManager().addListener(this);
	}

	@Override
	public void dispose() {
		historyAction.clear();
		GemDevUiPlugin.getSessionManager().removeListener(this);
		super.dispose();
	}

	public ObjectInfo getInputObject() {
		return inputObject;
	}

	public ITextSelection getTextSelection() {
		return (ITextSelection) text.getSelection();
	}

	public IStructuredSelection getTreeSelection() {
		return (IStructuredSelection) tree.getSelection();
	}

	@Override
	public void sessionClosed(final ISession session) {
		if (inputObject != null && session.equals(inputObject.getSession())) {
			historyAction.clear();
			inspectObjectWithOopAction.refresh();

			// if session for which we are viewing class is closed -> than clean all views
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					setInput(null);
				}
			});
		}
	}

	@Override
	public void sessionOpened(final ISession session) {
		inspectObjectWithOopAction.refresh();
	}

	@Override
	public void setFocus() {
		tree.getTree().setFocus();
	}

	public void setInput(final ObjectInfo inputObject) {
		this.inputObject = inputObject;

		if (inputObject == null) {
			tree.setInput(new InspectorNode[0]);
			return;
		}

		final InspectorNode node = new InspectorNode(inputObject.getPrintString(), inputObject);
		tree.setInput(new InspectorNode[] { node });
		tree.expandToLevel(2);
		tree.setSelection(new StructuredSelection(node));

		historyAction.add(inputObject);
	}

	private void createActions() {
		final IToolBarManager toolbar = getViewSite().getActionBars().getToolBarManager();
		inspectObjectWithOopAction = new InpectObjectWithOopAction();
		toolbar.add(inspectObjectWithOopAction);
		inspectObjectWithOopAction.refresh();

		historyAction = new HistoryAction(getViewSite(), 10);
		toolbar.add(historyAction);
	}

	private void createContextMenu(final Viewer viewer, final String menuId) {
		final MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(final IMenuManager m) {
				m.add(new Separator(ITextEditorActionConstants.GROUP_OPEN));
				m.add(new Separator(ITextEditorActionConstants.GROUP_EDIT));
				m.add(removeAction);
				m.add(copyAction);
				m.add(pasteAction);
				m.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		});
		final Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuId, menuMgr, viewer);
	}

	private void createText(final SashForm sash) {
		text = new GsSourceViewer(sash, ""); //$NON-NLS-1$
		createContextMenu(text, VIEW_ID + ".text"); //$NON-NLS-1$

		removeAction = ActionFactory.CUT.create(getViewSite().getWorkbenchWindow());
		getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.CUT.getId(), new Action() {
			@Override
			public void runWithEvent(final Event event) {
				text.doOperation(ITextOperationTarget.CUT);
			}
		});
		copyAction = ActionFactory.COPY.create(getViewSite().getWorkbenchWindow());
		getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.COPY.getId(), new Action() {
			@Override
			public void runWithEvent(final Event event) {
				text.doOperation(ITextOperationTarget.COPY);
			}
		});
		pasteAction = ActionFactory.PASTE.create(getViewSite().getWorkbenchWindow());
		getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.PASTE.getId(), new Action() {
			@Override
			public void runWithEvent(final Event event) {
				text.doOperation(ITextOperationTarget.PASTE);
			}
		});
		getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.UNDO.getId(), new Action() {
			@Override
			public void runWithEvent(final Event event) {
				text.doOperation(ITextOperationTarget.UNDO);
			}
		});
		getViewSite().getActionBars().setGlobalActionHandler(ActionFactory.REDO.getId(), new Action() {
			@Override
			public void runWithEvent(final Event event) {
				text.doOperation(ITextOperationTarget.REDO);
			}
		});

		getViewSite().getActionBars().updateActionBars();
		text.getTextWidget().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(final FocusEvent e) {
				getSite().setSelectionProvider(text);
			}
		});
	}

	private void createTitle(final ViewForm form) {
		label = new Text(form, SWT.SINGLE | SWT.READ_ONLY);
		form.setTopLeft(label);
		tree.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				String labelText = ""; //$NON-NLS-1$
				if (!selection.isEmpty() && selection.getFirstElement() instanceof InspectorNode) {
					final ObjectInfo object = ((InspectorNode) selection.getFirstElement()).getValue();
					labelText = MessageFormat.format(Messages.INSPECTOR_SELECTED_OBJECT_INFO, object.getOop(), object.getClassName(),
							object.getUnnamedVarSize());
				}
				label.setText(labelText);
			}
		});
	}

	private void createTree(final SashForm sash) {
		tree = new TreeViewer(sash, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setContentProvider(new InspectorContentProvider(this));
		tree.setLabelProvider(new InspectorLabelProvider());
		createContextMenu(tree, VIEW_ID + ".tree"); //$NON-NLS-1$
		tree.getTree().addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(final FocusEvent e) {
				getSite().setSelectionProvider(tree);
			}
		});
		tree.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(final MouseEvent e) {
				doubleClickInTree(((IStructuredSelection) tree.getSelection()).getFirstElement());
			}
		});
	}

	private void doubleClickInTree(final Object object) {
		if (object instanceof InspectorNode) {
			final Dialog dialog = new ObjectAsStringReadOnlyDialog(getSite().getShell(), ((InspectorNode) object).getValue());
			dialog.open();
		}
	}

	private ISession getSession() {
		if (getInputObject() != null) {
			return getInputObject().getSession();
		}
		final List<ISession> allSessions = Lists.newArrayList(GemDevUiPlugin.getSessionManager().getAllSessions().iterator());
		if (allSessions.isEmpty()) {
			return null;
		}
		return allSessions.get(0);
	}
}
