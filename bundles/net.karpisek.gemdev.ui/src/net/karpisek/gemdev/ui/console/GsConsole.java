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
package net.karpisek.gemdev.ui.console;

import java.io.IOException;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IPatternMatchListener;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.console.PatternMatchEvent;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.part.IPageBookViewPage;

import net.karpisek.gemdev.net.UnhandledErrorException;
import net.karpisek.gemdev.ui.GemDevUiPlugin;

/**
 * Console for displaying GS errors.
 */
public class GsConsole implements IConsole {
	public static GsConsole getDefault() {
		return getConsole("GemDev"); //$NON-NLS-1$
	}

	private static GsConsole getConsole(final String name) {
		final ConsolePlugin plugin = ConsolePlugin.getDefault();
		final IConsoleManager consoleManager = plugin.getConsoleManager();
		for (final IConsole console : consoleManager.getConsoles()) {
			if (name.equals(console.getName())) {
				return (GsConsole) console;
			}
		}

		final GsConsole console = new GsConsole(name);
		consoleManager.addConsoles(new IConsole[] { console });
		return console;
	}

	private final MessageConsole messageConsole;

	public GsConsole(final String name) {
		this.messageConsole = new MessageConsole(name, null);
		this.messageConsole.addPatternMatchListener(new IPatternMatchListener() {

			@Override
			public void connect(final TextConsole console) {
			}

			@Override
			public void disconnect() {
			}

			@Override
			public int getCompilerFlags() {
				return 0;
			}

			@Override
			public String getLineQualifier() {
				return "[0-9]+(\\s)+(.)*";
			}

			@Override
			public String getPattern() {
				return "[0-9]+(\\s)+(\\w|\\s)+(\\s)+(>>)(\\s)+(.)*(line)(\\s)+(\\d)+(.)*";
			}

			@Override
			public void matchFound(final PatternMatchEvent event) {
				try {
					final MessageConsole c = (MessageConsole) event.getSource();
					final String text = c.getDocument().get(event.getOffset(), event.getLength());
					final MethodCodeReference ref = MethodCodeReference.on(text);
					if (ref != null) {
						// select from line offset and length of pattern className>>selector lineNumber
						// if it can not be found select whole line
						int offset = text.indexOf(ref.getMethodReference().getClassName());
						int length = -1;
						if (offset != -1) {
							final int offsetOfLineString = text.indexOf("line", offset);
							if (offsetOfLineString != -1) {
								final String lineNumberString = Integer.toString(ref.getLineNumber());
								final int offsetOfLineNumber = text.indexOf(lineNumberString, offsetOfLineString);
								if (offsetOfLineString != -1) {
									length = offsetOfLineNumber + lineNumberString.length() - offset;
								}
							}
						}

						if (offset == -1 || length == -1) {
							// just to be sure that we have some offset
							offset = event.getOffset();
							length = event.getLength();
						}

						c.addHyperlink(new MethodCodeReferenceHyperlink(ref), event.getOffset() + offset, length);
					}
				} catch (final BadLocationException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void addPropertyChangeListener(final IPropertyChangeListener listener) {
		messageConsole.addPropertyChangeListener(listener);
	}

	@Override
	public IPageBookViewPage createPage(final IConsoleView view) {
		return messageConsole.createPage(view);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return messageConsole.getImageDescriptor();
	}

	@Override
	public String getName() {
		return messageConsole.getName();
	}

	@Override
	public String getType() {
		return messageConsole.getType();
	}

	public GsConsole print(final UnhandledErrorException e) {
		println(e.getMessage());

		String stack = e.getGsStackReport();
		int idx = stack.lastIndexOf("GsMethod >> _executeInContext:"); //$NON-NLS-1$
		if (idx > -1 && (idx -= 3) > 0) {
			stack = stack.substring(1, idx);
		}
		println(stack);
		return this;
	}

	public GsConsole println() {
		try(MessageConsoleStream stream = messageConsole.newMessageStream()){			
			stream.println("\n"); //$NON-NLS-1$
		} catch (IOException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
		return this;
	}

	public GsConsole println(final String message) {
		try(MessageConsoleStream stream = messageConsole.newMessageStream()){			
			stream.println(message);
		} catch (IOException e) {
			GemDevUiPlugin.getDefault().logError(e);
		}
		return this;
	}

	@Override
	public void removePropertyChangeListener(final IPropertyChangeListener listener) {
		messageConsole.removePropertyChangeListener(listener);
	}

	public void show() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (window == null) {
					window = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
				}
				final IWorkbenchPage page = window.getActivePage();
				try {
					final IConsoleView view = (IConsoleView) page.showView(IConsoleConstants.ID_CONSOLE_VIEW);
					view.display(GsConsole.this);
				} catch (final PartInitException e) {
					GemDevUiPlugin.getDefault().logError(e);
				}
			}
		});
	}
}
