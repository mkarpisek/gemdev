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
package net.karpisek.gemdev.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "net.karpisek.gemdev.ui.messages"; //$NON-NLS-1$
	public static String ABORT_TRANSACTION;
	public static String BROKER_SERVER;
	public static String BROKER_SERVER_CONNECTION_ERROR;
	public static String BROWSE_BUTTON_LABEL;
	public static String CATEGORY_ALREADY_EXISTS_ERROR;
	public static String CATEGORY_NAME_IS_EMPTY_ERROR;
	public static String COMMIT_TRANSACTION;
	public static String CONFIRM_DELETE_MULTIPLE_ELEMENTS_MESSAGE;
	public static String CONFIRM_DELETE_TITLE;
	public static String CREATE_PROJECT_JOB_NAME;
	public static String DELETE_CATEGORY_COMMIT_ERROR;
	public static String DELETE_CATEGORY_DIALOG_MESSAGE_SINGLE;
	public static String DELETE_CLASS_DIALOG_MESSAGE;
	public static String DELETE_CLASS_FAILED;
	public static String DELETE_METHOD_DIALOG_MESSAGE_SINGLE;
	public static String DELETE_METHOD_ERROR;
	public static String EXPORT;
	public static String EXPORT_ERROR;
	public static String EXPORT_OBJECTS_NOT_SELECTED_ERROR;
	public static String FIND_CLASS_DIALOG_TITLE;
	public static String FIND_DIALOG_MESSAGE_MULTIPLE_PROJECTS;
	public static String FIND_DIALOG_MESSAGE_SINGLE_PROJECT;
	public static String FIND_IMPLEMENTORS_DIALOG_TITLE;
	public static String FIND_METHOD_DIALOG_ERROR;
	public static String FIND_METHOD_DIALOG_MESSAGE;
	public static String FIND_METHOD_DIALOG_TITLE;
	public static String FIND_SENDERS_DIALOG_TITLE;
	public static String GET_METHOD_TIMESTAMP_JOB_NAME;
	public static String IMPLEMENTORS;
	public static String IMPLEMENTORS_SEARCH_LABEL;
	public static String INSPECTOR_EMPTY_OOP_ERROR;
	public static String INSPECTOR_FIND_OBJECT_BY_OOP_DIALOG_MESSAGE;
	public static String INSPECTOR_FIND_OBJECT_BY_OOP_DIALOG_TITLE;
	public static String INSPECTOR_INSPECTED_OBJECTS_DIALOG_MESSAGE;
	public static String INSPECTOR_INSPECTED_OBJECTS_HISTORY_DIALOG_TITLE;
	public static String INSPECTOR_INVALID_OOP_FORMAT_ERROR;
	public static String INSPECTOR_NO_SESSIONS_FOUND_ERROR;
	public static String INSPECTOR_OBJECT_AS_STRING_MESSAGE;
	public static String INSPECTOR_OBJECT_AS_STRING_TITLE;
	public static String INSPECTOR_OBJECT_WITH_OOP_NOT_FOUND_ERROR;
	public static String INSPECTOR_SELECTED_OBJECT_INFO;
	public static String INSPECT_JOB_NAME;
	public static String INVALID_METHOD_SELECTOR;
	public static String MESSAGES_DIALOG_MESSAGE;
	public static String MESSAGES_DIALOG_TITLE;
	public static String METHODS;
	public static String METHODS_SEARCH_LABEL;
	public static String METHODS_SEARCH_RESULT_LABEL;
	public static String METHOD_ALREADY_EXISTS_ERROR;
	public static String METHOD_EDITOR_COMMIT_ERROR;
	public static String METHOD_NAME_IS_EMPTY_ERROR;
	public static String MOVE_METHOD_DIALOG_COMMIT_ERROR;
	public static String MOVE_METHOD_DIALOG_MULTIPLE_ELEMENTS;
	public static String MOVE_METHOD_DIALOG_SINGLE_ELEMENT;
	public static String MOVE_METHOD_DIALOG_TITLE;
	public static String NAME_IS_EMPTY_ERROR;
	public static String NEW_CATEGORY_DIALOG_INSTANCE_SIDE_CHECKBOX;
	public static String NEW_CATEGORY_DIALOG_MESSAGE;
	public static String NEW_CATEGORY_DIALOG_TITLE;
	public static String NEW_CATEGORY_FAILED;
	public static String NEW_CLASS_DIALOG_COMMIT_ERROR;
	public static String NEW_CLASS_DIALOG_ERROR;
	public static String NEW_CLASS_DIALOG_MESSAGE;
	public static String NEW_CLASS_DIALOG_TITLE;
	public static String NEW_METHOD_DIALOG_COMMIT_ERROR;
	public static String NEW_METHOD_DIALOG_COMPILATION_ERROR;
	public static String NEW_METHOD_DIALOG_MESSAGE;
	public static String NEW_METHOD_DIALOG_TITLE;
	public static String NEW_PROJECT_WIZARD_CONNECTION_WORKS;
	public static String NEW_PROJECT_WIZARD_DESCRIPTION;
	public static String NEW_PROJECT_WIZARD_GS_PARAMETER_MISSING_ERROR;
	public static String NEW_PROJECT_WIZARD_IP_ADDRESS_IS_EMPTY_ERROR;
	public static String NEW_PROJECT_WIZARD_PORT_NUMBER_ERROR;
	public static String NEW_PROJECT_WIZARD_PROJECT_ALREADY_EXISTS_ERROR;
	public static String NEW_PROJECT_WIZARD_PROJECT_NAME;
	public static String NEW_PROJECT_WIZARD_PROJECT_NAME_IS_EMPTY_ERROR;
	public static String NEW_PROJECT_WIZARD_TITLE;
	public static String NOT_IN_SYMBOL_LIST_WARNING;
	public static String PREFERENCES_AUTHOR_INITIALS;
	public static String PREFERENCES_COLOR;
	public static String PREFERENCES_ELEMENT;
	public static String PREFERENCES_ENABLE;
	public static String PREFERENCES_IP_ADDRESS;
	public static String PREFERENCES_MARK_UNDECLARED_IDENTIFIERS;
	public static String PREFERENCES_MARK_UNIMPLEMENTED_MESSAGES;
	public static String PREFERENCES_MARK_UNUSED_PARAMETERS;
	public static String PREFERENCES_PORT;
	public static String PREFERENCES_PREVIEW;
	public static String PREFERENCES_SESSION_INITIALIZATION_EXPRESSION;
	public static String PREFERENCES_SHOW_SYNTAX_ERRORS;
	public static String PREFERENCES_VALIDATE;
	public static String PROFILE_NAME;
	public static String PROFILE_PASSWORD;
	public static String PROFILE_TITLE;
	public static String PROFILE_USER_NAME;
	public static String PROJECTS_VIEW_AUTHOR_INITIALS_DIALOG_MESSAGE;
	public static String PROJECTS_VIEW_AUTHOR_INITIALS_DIALOG_TITLE;
	public static String PROJECTS_VIEW_AUTHOR_INITIALS_MUST_NOT_BE_EMPTY;
	public static String PROJECTS_VIEW_CONNECT_ERROR;
	public static String PROJECTS_VIEW_CONNECT_JOB_NAME;
	public static String PROJECTS_VIEW_EXPIRATION_DIALOG_TITLE;
	public static String PROJECTS_VIEW_OPEN_EDITORS_WARNING_DIALOG_MESSAGE;
	public static String PROJECTS_VIEW_OPEN_EDITORS_WARNING_DIALOG_TITLE;
	public static String PROJECTS_VIEW_VERSION_EXPIRED_MESSAGE;
	public static String PROJECTS_VIEW_VERSION_WILL_EXPIRE_MESSAGE;
	public static String RECENT_CLASSES_HISTORY_MESSAGE;
	public static String RECENT_CLASSES_HISTORY_TITLE;
	public static String REFERENCES;
	public static String REFERENCES_SEARCH_LABEL;
	public static String REFRESH;
	public static String RENAME_CATEGORY_COMMIT_ERROR;
	public static String RENAME_CATEGORY_DIALOG_MESSAGE;
	public static String RENAME_CATEGORY_DIALOG_TITLE;
	public static String RENAME_DIALOG_LABEL;
	public static String RENAME_DIALOG_TITLE;
	public static String RENAME_ELEMENT_TITLE;
	public static String RENAME_LOCAL_IDENTIFIER_TITLE;
	public static String RENAME_REFACTORING_NO_INPUT_DOCUMENT_FOUND_ERROR;
	public static String RENAME_REFACTORING_NO_METHOD_MODEL_FOUND_ERROR;
	public static String RENAME_REFACTORING_WRONG_ELEMENT_SELECTED_MESSAGE;
	public static String SELECTED_EXPORT_DIRECTORY_DIALOG_MESSAGE;
	public static String SELECT_PROFILE_DIALOG_MESSAGE;
	public static String SELECT_PROFILE_DIALOG_TITLE;
	public static String SENDERS;
	public static String SENDERS_SEARCH_LABEL;
	public static String SYNCHRONIZE;
	public static String TEXT_SEARCH_CASE_SENSITIVE_CHECKBOX;
	public static String TEXT_SEARCH_CONNECTED_PROJECTS;
	public static String TEXT_SEARCH_EMPTY_TEXT_ERROR;
	public static String TEXT_SEARCH_INPUT_DIALOG;
	public static String TEXT_SEARCH_NO_PROJECT_SELECTED_ERROR;
	public static String TEXT_SEARCH_WARNING;
	public static String UNHANDLED_EXCEPTION_DIALOG_TITLE;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
