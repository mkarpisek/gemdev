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
package net.karpisek.gemdev.ui.browser.methods;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.ViewPart;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbMethod;
import net.karpisek.gemdev.core.db.Selector;
import net.karpisek.gemdev.net.actions.method.GetMessagesSentByMethod;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.MethodEditor;
import net.karpisek.gemdev.ui.browser.MethodEditorInput;
import net.karpisek.gemdev.ui.browser.PropertiesHandler;
import net.karpisek.gemdev.ui.browser.categories.CategoriesView;
import net.karpisek.gemdev.ui.search.SearchUI;

/**
 * Display all methods in selected category.
 */
public class MethodsView extends ViewPart implements ISelectionListener {
	private class FindImplementorsLocalHandler extends AbstractHandler implements ISelectionChangedListener {
		protected IStructuredSelection selection;

		public FindImplementorsLocalHandler() {
			viewer.addSelectionChangedListener(this);
		}

		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final DbMethod method = (DbMethod) selection.getFirstElement();
			SearchUI.showImplementorsOf(new Selector(method.getName(), method.getSession()));
			return null;
		}

		@Override
		public boolean isEnabled() {
			return getInputCategory() != null && selection != null && !selection.isEmpty();
		}

		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			selection = (IStructuredSelection) event.getSelection();

			fireHandlerChanged(new HandlerEvent(this, true, false));
		}
	}

	private class FindSendersLocalHandler extends FindImplementorsLocalHandler {
		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final DbMethod method = (DbMethod) selection.getFirstElement();
			SearchUI.showSendersOf(new Selector(method.getName(), method.getSession()));
			return null;
		}
	}

	private class MessagesLocalHandler extends FindImplementorsLocalHandler {
		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
			final DbMethod method = (DbMethod) selection.getFirstElement();

			final List<String> selectors = Lists.newLinkedList();
			selectors.addAll(
					method.getSession().execute(new GetMessagesSentByMethod(method.getBehavior().getClassName(), method.isInstanceSide(), method.getName())));
			Collections.sort(selectors);

			final ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(), new LabelProvider());
			dialog.setTitle(String.format(Messages.MESSAGES_DIALOG_TITLE, method.getName()));
			dialog.setMessage(Messages.MESSAGES_DIALOG_MESSAGE);
			dialog.setElements(selectors.toArray());
			if (dialog.open() == Window.OK) {
				final String selectedSelector = ((String) dialog.getResult()[0]);
				SearchUI.showImplementorsOf(new Selector(selectedSelector, method.getSession()));
			}
			return null;
		}
	}

	public static final String VIEW_ID = "net.karpisek.gemdev.ui.methodsView"; //$NON-NLS-1$
	private TableViewer viewer;
	private DbCategory inputCategory;

	private DeleteMethodHandler deleteMethodHandler;

	private PropertiesHandler propertiesHandler;

	private MoveMethodHandler moveHandler;

	public MethodsView() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(final Object element) {
				final DbMethod m = (DbMethod) element;
				return GemDevUiPlugin.getDefault().getImage(m.isInstanceSide() ? GemDevUiPlugin.INSTANCE_METHOD_ICON : GemDevUiPlugin.CLASS_METHOD_ICON);
			}

			@Override
			public String getText(final Object element) {
				return ((DbMethod) element).getName();
			}
		});
		viewer.setSorter(new ViewerSorter());
		initTable(viewer);
		createContextMenu(viewer);

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection) {
					final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					final DbMethod selectedMethod = (DbMethod) selection.getFirstElement();
					if (selectedMethod != null) {
						openEditor(selectedMethod);
						getViewSite().getActionBars().getStatusLineManager().setMessage(selectedMethod.getStamp());
					}
				}
			}
		});

		getSite().setSelectionProvider(viewer);
		getSite().getPage().addSelectionListener(CategoriesView.VIEW_ID, this);

		GuiUtils.activateContext(getSite());
		GuiUtils.activateHandlers(getSite(), ICommands.NEW_METHOD, new NewMethodHandler(this), ICommands.MOVE, moveHandler = new MoveMethodHandler(this),
				ICommands.DELETE_METHOD, deleteMethodHandler = new DeleteMethodHandler(this), ICommands.FIND_DECLARATIONS, new FindImplementorsLocalHandler(),
				ICommands.FIND_REFERENCES, new FindSendersLocalHandler(), ICommands.MESSAGES, new MessagesLocalHandler(), ICommands.PROPERTIES,
				propertiesHandler = new PropertiesHandler());

		getSite().getSelectionProvider().addSelectionChangedListener(moveHandler);
		getSite().getSelectionProvider().addSelectionChangedListener(deleteMethodHandler);
		getSite().getSelectionProvider().addSelectionChangedListener(propertiesHandler);
	}

	@Override
	public void dispose() {
		getSite().getSelectionProvider().removeSelectionChangedListener(moveHandler);
		getSite().getSelectionProvider().removeSelectionChangedListener(propertiesHandler);
		getSite().getSelectionProvider().removeSelectionChangedListener(deleteMethodHandler);
		getSite().getPage().removeSelectionListener(CategoriesView.VIEW_ID, this);
		super.dispose();
	}

	public DbCategory getInputCategory() {
		return inputCategory;
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			setInput((DbCategory) ((IStructuredSelection) selection).getFirstElement(), null);

		}
	}

	@Override
	public void setFocus() {
		viewer.getTable().setFocus();
	}

	public void setInput(final DbCategory inputCategory, final DbMethod selection) {
		this.inputCategory = inputCategory;

		if (inputCategory == null) {
			viewer.setInput(new Object[0]);
			return;
		}

		viewer.setInput(inputCategory.getMethods());

		if (selection != null) {
			viewer.setSelection(new StructuredSelection(selection), true);
		}
	}

	public void setSelection(final DbMethod selectedMethod) {
		Preconditions.checkNotNull(selectedMethod);

		viewer.setSelection(new StructuredSelection(selectedMethod), true);
	}

	private void createContextMenu(final Viewer viewer) {
		final MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(final IMenuManager m) {
				m.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		});
		final Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(VIEW_ID, menuMgr, viewer);
	}

	private MethodEditor getOpenMethodEditor(final IWorkbenchPage page, final MethodEditorInput input) {
		for (final IEditorReference ref : page.getEditorReferences()) {
			final IEditorPart editor = ref.getEditor(false);
			if (editor instanceof MethodEditor && ((MethodEditor) editor).getMethodEditorInput().equals(input)) {
				return (MethodEditor) editor;
			}
		}
		return null;
	}

	private void initTable(final TableViewer v) {
		final Table table = v.getTable();

		new TableColumn(table, SWT.LEFT);

		table.addControlListener(new ControlListener() {
			@Override
			public void controlMoved(final ControlEvent e) {
			}

			@Override
			public void controlResized(final ControlEvent e) {
				final int width = table.getClientArea().width;
				final TableColumn[] tableColumns = table.getColumns();
				if (tableColumns.length > 0) {
					tableColumns[0].setWidth(width);
				}
			}

		});
	}

	private void openEditor(final DbMethod m) {
		if (m == null) {
			return;
		}

		try {
			final IWorkbenchPage page = getSite().getPage();

			final MethodEditorInput input = new MethodEditorInput(m);
			final MethodEditor editor = getOpenMethodEditor(page, input);
			if (editor != null) {
				if (page.getActiveEditor() == editor) {
					return;
				}
				page.activate(editor);
			} else {
				final IEditorPart activeEditor = page.getActiveEditor();
				if (activeEditor instanceof MethodEditor && !activeEditor.isDirty()) {
					((MethodEditor) activeEditor).setInput(input);
				} else {
					page.openEditor(input, MethodEditor.EDITOR_ID, true, IWorkbenchPage.MATCH_INPUT);
				}
			}
			page.showView(VIEW_ID);
		} catch (final PartInitException e) {
			GemDevUiPlugin.getDefault().logError(e);
		} catch (final CoreException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
	}
}
