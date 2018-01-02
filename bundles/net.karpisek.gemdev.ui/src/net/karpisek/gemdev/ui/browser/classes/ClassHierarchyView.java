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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.google.common.collect.Lists;

import net.karpisek.gemdev.core.db.CommitFailedException;
import net.karpisek.gemdev.core.db.DbBehavior;
import net.karpisek.gemdev.core.db.DbClass;
import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.core.db.ISessionManagerListener;
import net.karpisek.gemdev.core.model.IClass;
import net.karpisek.gemdev.ui.GemDevUiPlugin;
import net.karpisek.gemdev.ui.GuiUtils;
import net.karpisek.gemdev.ui.ICommands;
import net.karpisek.gemdev.ui.Messages;
import net.karpisek.gemdev.ui.browser.HistoryListAction;
import net.karpisek.gemdev.ui.browser.PropertiesHandler;
import net.karpisek.gemdev.ui.search.SearchUI;
import net.karpisek.gemdev.ui.utils.ClassTree;
import net.karpisek.gemdev.ui.utils.ClassTreeLabelProvider;
import net.karpisek.gemdev.ui.utils.TreeContentProvider;
import net.karpisek.gemdev.ui.utils.TreeNode;

/**
 * Displaying superclass and subclass hierarchy for one class.
 */
public class ClassHierarchyView extends ViewPart implements ISessionManagerListener {
	private class FindReferences extends AbstractHandler implements ISelectionChangedListener {
		protected IStructuredSelection selection;

		public FindReferences() {
			viewer.addSelectionChangedListener(this);
		}

		@Override
		public Object execute(final ExecutionEvent event) throws ExecutionException {
			final TreeNode<?> method = (TreeNode<?>) selection.getFirstElement();
			SearchUI.showReferencesTo((DbClass) method.getValue());
			return null;
		}

		@Override
		public boolean isEnabled() {
			return getInputClass() != null && !selection.isEmpty();
		}

		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			selection = (IStructuredSelection) event.getSelection();

			fireHandlerChanged(new HandlerEvent(this, true, false));
		}
	}
	private class HistoryAction extends HistoryListAction<DbClass> {
		public HistoryAction(final IViewSite site, final int maxSize) {
			super(site, Messages.RECENT_CLASSES_HISTORY_TITLE, Messages.RECENT_CLASSES_HISTORY_MESSAGE, maxSize, new LabelProvider() {
				@Override
				public Image getImage(final Object element) {
					return GemDevUiPlugin.getDefault().getImage(GemDevUiPlugin.CLASS_ICON);
				}
			});
		}

		@Override
		public void okPressed(final DbClass selectedObject) {
			setInput(selectedObject);
		}
	}
	public static final String VIEW_ID = "net.karpisek.gemdev.ui.classHierarchyView"; //$NON-NLS-1$
	private TreeViewer viewer;
	private IClass inputClass;
	private NewClassHandler newClassHandler;
	private DeleteClassHandler deleteClassHandler;
	private Action commitAction;
	private Action abortAction;
	private Action refreshAction;

	private HistoryAction historyAction;

	private PropertiesHandler propertiesHandler;

	public ClassHierarchyView() {
	}

	@Override
	public void createPartControl(final Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new TreeContentProvider());
		viewer.setLabelProvider(new ClassTreeLabelProvider());
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(final DoubleClickEvent event) {
				final TreeNode<?> node = ((TreeNode<?>) ((IStructuredSelection) viewer.getSelection()).getFirstElement());
				if (node != null) {
					setInput((DbClass) node.getValue());
				}
			}
		});
		getSite().setSelectionProvider(viewer);

		createContextMenu(viewer);

		GuiUtils.activateContext(getSite());
		GuiUtils.activateHandlers(getSite(), ICommands.NEW_CLASS, newClassHandler = new NewClassHandler(this), ICommands.DELETE_CLASS,
				deleteClassHandler = new DeleteClassHandler(this), ICommands.FIND_REFERENCES, new FindReferences(), ICommands.PROPERTIES,
				propertiesHandler = new PropertiesHandler());
		getSite().getSelectionProvider().addSelectionChangedListener(newClassHandler);
		getSite().getSelectionProvider().addSelectionChangedListener(deleteClassHandler);
		getSite().getSelectionProvider().addSelectionChangedListener(propertiesHandler);

		GemDevUiPlugin.getSessionManager().addListener(this);

		abortAction = createAbortAction();
		commitAction = createCommitAction();
		refreshAction = createRefreshAction();
		setActionsEnabled(false);

		historyAction = new HistoryAction(getViewSite(), 10);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent event) {
				final IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (!selection.isEmpty()) {
					historyAction.add((DbClass) ((TreeNode<?>) selection.getFirstElement()).getValue());
				}
			}
		});

		for (final IAction action : Lists.newArrayList(abortAction, commitAction, refreshAction, historyAction)) {
			getViewSite().getActionBars().getToolBarManager().add(action);
		}
	}

	@Override
	public void dispose() {
		historyAction.clear();
		GemDevUiPlugin.getSessionManager().removeListener(this);
		getSite().getSelectionProvider().removeSelectionChangedListener(propertiesHandler);
		getSite().getSelectionProvider().removeSelectionChangedListener(deleteClassHandler);
		getSite().getSelectionProvider().removeSelectionChangedListener(newClassHandler);
		super.dispose();
	}

	public DbClass getInputClass() {
		return (DbClass) inputClass;
	}

	@Override
	public void sessionClosed(final ISession session) {
		if (inputClass != null && session.equals(((DbBehavior) inputClass).getSession())) {
			historyAction.clear();

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
		final DbClass c = session.getCachedClass("Object"); //$NON-NLS-1$
		if (c != null) {
			// if we opened some new session - than show some default class to indicate it
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					setInput(c);
				}
			});
		}
	}

	public void setActionsEnabled(final boolean enabled) {
		for (final IAction action : Lists.newArrayList(abortAction, commitAction, refreshAction)) {
			action.setEnabled(enabled);
		}
	}

	@Override
	public void setFocus() {
		viewer.getTree().setFocus();
	}

	/**
	 * @param inputClass for which is hierarchy displayed
	 */
	public void setInput(final IClass inputClass) {
		this.inputClass = inputClass;

		if (inputClass == null) {
			viewer.setInput(new Object[0]);
			setActionsEnabled(false);
			return;
		}
		setActionsEnabled(true);

		final ClassTree tree = ClassTree.createFullTree(getInputClass());
		viewer.setInput(tree);
		viewer.expandToLevel(getInputClass().getSuperclasses().size() + 2);
		viewer.setSelection(new StructuredSelection(tree.getNode(getInputClass())));
		historyAction.add(getInputClass());
	}

	private Action createAbortAction() {
		return new Action(Messages.ABORT_TRANSACTION, GemDevUiPlugin.getDefault().getImageDescriptor(GemDevUiPlugin.ABORT_TRANSACTION_ICON)) {
			@Override
			public void run() {
				if (getInputClass() == null) {
					return;
				}
				setActionsEnabled(false);
				((DbBehavior) getInputClass()).getSession().abortTransaction();
				setActionsEnabled(true);
			}
		};
	}

	private Action createCommitAction() {
		return new Action(Messages.COMMIT_TRANSACTION, GemDevUiPlugin.getDefault().getImageDescriptor(GemDevUiPlugin.COMMIT_TRANSACTION_ICON)) {
			@Override
			public void run() {
				if (getInputClass() == null) {
					return;
				}
				setActionsEnabled(false);
				final ToolBar toolbar = ((ToolBarManager) getViewSite().getActionBars().getToolBarManager()).getControl();
				final Color prevColor = toolbar.getBackground();
				try {
					((DbBehavior) getInputClass()).getSession().commitTransaction();
					toolbar.setBackground(GemDevUiPlugin.getDefault().getColor(new RGB(0, 255, 0)));
				} catch (final CommitFailedException e) {
					toolbar.setBackground(GemDevUiPlugin.getDefault().getColor(new RGB(255, 0, 0)));
				} finally {
					setActionsEnabled(true);
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(200);
							} catch (final InterruptedException e) {
								GemDevUiPlugin.getDefault().logError(e);
							}
							toolbar.setBackground(prevColor);
						}
					});
				}
			}
		};
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

	private Action createRefreshAction() {
		return new Action(Messages.SYNCHRONIZE, GemDevUiPlugin.getDefault().getImageDescriptor(GemDevUiPlugin.REFRESH_ICON)) {
			@Override
			public void run() {
				final DbClass oldInputClass = getInputClass();
				if (oldInputClass == null) {
					return;
				}
				setActionsEnabled(false);

				final ISession session = oldInputClass.getSession();
				final Job job = new Job("Refresh") { //$NON-NLS-1$
					@Override
					protected IStatus run(final IProgressMonitor monitor) {
						session.refreshCachedValues(monitor);
						setActionsEnabled(true);
						return Status.OK_STATUS;
					}
				};
				job.setPriority(Job.SHORT);
				job.schedule();

				job.addJobChangeListener(new IJobChangeListener() {

					@Override
					public void aboutToRun(final IJobChangeEvent event) {
					}

					@Override
					public void awake(final IJobChangeEvent event) {
					}

					@Override
					public void done(final IJobChangeEvent event) {

						if (event.getResult().isOK() && !viewer.getControl().isDisposed()) {
							PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
								@Override
								public void run() {
									DbClass newInputClass = session.getCachedClass(oldInputClass.getClassName());
									if (newInputClass == null) {
										newInputClass = session.getCachedClass("Object"); //$NON-NLS-1$
									}
									setInput(newInputClass);
								}
							});
						}
					}

					@Override
					public void running(final IJobChangeEvent event) {
					}

					@Override
					public void scheduled(final IJobChangeEvent event) {
					}

					@Override
					public void sleeping(final IJobChangeEvent event) {
					}

				});
			}
		};
	}
}
