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

import com.google.common.base.Preconditions;

import net.karpisek.gemdev.core.db.ISession;
import net.karpisek.gemdev.net.ActionException;

/**
 * Fetches basic information about some DB object identified by oop.
 */
public class GetObjectInfo extends BaseAction<ObjectInfo> {
	public GetObjectInfo(final ISession session, final String oop) {
		super(session, oop);
	}

	@Override
	public String asRequestString() {
		return createExecuteOperationRequest(newScriptWithArguments(getClass().getSimpleName(), oop));
	}

	@Override
	public ObjectInfo asResponse(final String responseString) throws ActionException {
		Preconditions.checkNotNull(responseString);

		if ("nil".equals(responseString)) { //$NON-NLS-1$
			return null;
		}

		// oop className objectType objectSize printString
		int index = 0;
		final char separator = '\n';
		final String oopString = responseString.substring(0, index = responseString.indexOf(separator));
		final String className = responseString.substring(index + 1, index = responseString.indexOf(separator, index + 1));
		final String objectType = responseString.substring(index + 1, index = responseString.indexOf(separator, index + 1));
		final int namedVarsSize = Integer.valueOf(responseString.substring(index + 1, index = responseString.indexOf(separator, index + 1)));
		final int unnamedVarSize = Integer.valueOf(responseString.substring(index + 1, index = responseString.indexOf(separator, index + 1)));
		final String printString = responseString.substring(index + 1);

		if ("UndefinedObject".equals(objectType)) { //$NON-NLS-1$
			return new UndefinedObjectInfo(session, oopString, className, printString, namedVarsSize, unnamedVarSize);
		}
		if ("Number".equals(objectType)) { //$NON-NLS-1$
			return new NumberInfo(session, oopString, className, printString, namedVarsSize, unnamedVarSize);
		}
		if ("String".equals(objectType)) { //$NON-NLS-1$
			return new StringInfo(session, oopString, className, printString, namedVarsSize, unnamedVarSize);
		}
		if ("SequenceableCollection".equals(objectType)) { //$NON-NLS-1$
			return new SequenceableCollectionInfo(session, oopString, className, printString, namedVarsSize, unnamedVarSize);
		}
		if ("AbstractDictionary".equals(objectType)) { //$NON-NLS-1$
			return new AbstractDictionaryInfo(session, oopString, className, printString, namedVarsSize, unnamedVarSize);
		}
		if ("UnorderedCollection".equals(objectType)) { //$NON-NLS-1$
			return new UnorderedCollectionInfo(session, oopString, className, printString, namedVarsSize, unnamedVarSize);
		}
		if ("Class".equals(objectType)) { //$NON-NLS-1$
			return new ClassInfo(session, oopString, className, printString, namedVarsSize, unnamedVarSize);
		}
		if ("Object".equals(objectType)) { //$NON-NLS-1$
			return new ObjectInfo(session, oopString, className, printString, namedVarsSize, unnamedVarSize);
		}
		throw new RuntimeException(String.format("Unknown objectType '%s' for object with oop '%s'", objectType, oop)); //$NON-NLS-1$
	}
}
