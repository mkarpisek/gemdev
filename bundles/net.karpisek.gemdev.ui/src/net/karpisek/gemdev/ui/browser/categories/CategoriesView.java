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
package net.karpisek.gemdev.ui.browser.categories;

import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.InputDialog;
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
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.ViewPart;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbCategory;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.model.ICategory;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.net.actions.category.RenameCategory;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.classes.ClassHierarchyView;
import net.karpisek.gemdev.ui.browser.methods.FindMethodHandler;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Display both instance and class side categories of selected class.
 */
public class CategoriesView extends ViewPart implements ISelectionListener {
	/**
	 * Performs abort transaction.
	 */
	public class RenameCategoryHandler extends AbstractHandler implements ISelectionChangedListener {
		private final CategoriesView view;
		private IStructuredSelection selection;

		public RenameCategoryHandler(final CategoriesView view) {
			this.view = view;
		}

		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final DbCategory selectedCategory = (DbCategory) selection.getFirstElement();
			Preconditions.checkArgument(selectedCategory != null, "No selected category."); //$NON-NLS-1$

			final ISession session = selectedCategory.getSession();
			session.abortTransaction();

			// put into set names of all existing categories from same side of class as is selected category
			final Set<String> existingCategoryNames = Sets.newHashSet();
			for (final ICategory category : selectedCategory.getBehavior().getCategories()) {
				if (selectedCategory.isInstanceSide() == category.isInstanceSide()) {
					existingCategoryNames.add(category.getName());
				}
			}

			final InputDialog dialog = new InputDialog(HandlerUtil.getActiveShell(event), Messages.RENAME_CATEGORY_DIALOG_TITLE,
					Messages.RENAME_CATEGORY_DIALOG_MESSAGE, selectedCategory.getName(),
					CategoryNameValidator.on(selectedCategory.getBehavior().getInstanceSide(), selectedCategory.isInstanceSide()));
			if (dialog.open() == Window.OK) {
				try {
					session.abortTransaction();
					session.execute(new RenameCategory(selectedCategory.getBehavior().getClassName(), selectedCategory.isInstanceSide(),
							selectedCategory.getName(), dialog.getValue()));
					session.commitTransaction();
					view.setInput(inputClass, inputClass.getCategory(dialog.getValue(), selectedCategory.isInstanceSide()));
				} catch (final CommitFailedException e) {
					view.getViewSite().getActionBars().getStatusLineManager().setErrorMessage(Messages.RENAME_CATEGORY_COMMIT_ERROR);
				}
			}

			return null;
		}

		@Override
		public boolean isEnabled() {
			return view != null && view.getInputClass() != null && selection != null && !selection.isEmpty();
		}

		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			selection = (IStructuredSelection) event.getSelection();

			fireHandlerChanged(new HandlerEvent(this, true, false));
		}
	}

	public static final String VIEW_ID = "net.karpisek.gemdev.ui.categoriesView"; //$NON-NLS-1$
	private TableViewer viewer;

	private IClass inputClass;
	private DeleteCategoryHandler deleteCategoryHandler;

	private RenameCategoryHandler renameCategoryHandler;

	public CategoriesView() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public Image getImage(final Object element) {
				final DbCategory c = (DbCategory) element;
				return GemDevUiPlugin.getDefault().getImage(c.isInstanceSide() ? GemDevUiPlugin.INSTANCE_CATEGORY_ICON : GemDevUiPlugin.CLASS_CATEGORY_ICON);

			}

			@Override
			public String getText(final Object element) {
				return ((DbCategory) element).getName();
			}
		});
		viewer.setSorter(new ViewerSorter() {
			@Override
			public int category(final Object element) {
				final DbCategory c = (DbCategory) element;
				if (!c.isInstanceSide()) {
					return 1;
				}
				return 0;
			}
		});
		initTable(viewer);
		createContextMenu(viewer);

		getSite().setSelectionProvider(viewer);
		getSite().getPage().addSelectionListener(ClassHierarchyView.VIEW_ID, this);

		GuiUtils.activateContext(getSite());
		GuiUtils.activateHandlers(getSite(), ICommands.FIND_METHOD, new FindMethodHandler(this), ICommands.NEW_CATEGORY, new NewCategoryHandler(this),
				ICommands.DELETE_CATEGORY, deleteCategoryHandler = new DeleteCategoryHandler(this), ICommands.RENAME,
				renameCategoryHandler = new RenameCategoryHandler(this));

		getSite().getSelectionProvider().addSelectionChangedListener(deleteCategoryHandler);
		getSite().getSelectionProvider().addSelectionChangedListener(renameCategoryHandler);
	}

	@Override
	public void dispose() {
		getSite().getSelectionProvider().removeSelectionChangedListener(renameCategoryHandler);
		getSite().getSelectionProvider().removeSelectionChangedListener(deleteCategoryHandler);
		getSite().getPage().removeSelectionListener(ClassHierarchyView.VIEW_ID, this);
		super.dispose();
	}

	public IClass getInputClass() {
		return inputClass;
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final TreeNode<?> treeNode = (TreeNode<?>) ((IStructuredSelection) selection).getFirstElement();
			if (treeNode == null) {
				setInput(null, null);
			} else {
				setInput((DbClass) treeNode.getValue(), null);
			}
		}
	}

	@Override
	public void setFocus() {

	}

	public void setInput(final IClass inputClass, final ICategory selectedCategory) {
		this.inputClass = inputClass;

		if (inputClass == null || !((DbClass) inputClass).isInSymbolList()) {
			viewer.setInput(new Object[0]);
			return;
		}

		final List<ICategory> categories = Lists.newLinkedList();
		categories.addAll(inputClass.getCategories());
		categories.addAll(inputClass.getClassSide().getCategories());

		viewer.setInput(categories);
		if (selectedCategory != null) {
			viewer.setSelection(new StructuredSelection(selectedCategory), true);
		}
	}

	public void setSelection(final ICategory selectedCategory) {
		Preconditions.checkNotNull(selectedCategory);

		viewer.setSelection(new StructuredSelection(selectedCategory), true);
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
}
