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
package net.karpisek.gemdev.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import net.karpisek.gemdev.lang.model.Element;
import net.karpisek.gemdev.lang.model.Message;
import net.karpisek.gemdev.lang.model.MessageReference;
import net.karpisek.gemdev.lang.model.MethodModel;
import net.karpisek.gemdev.ui.tests.IUnitTests;
import net.karpisek.gemdev.ui.tests.UiTestsPlugin;

@Category({ IUnitTests.class })
public class MessageReferenceTest {
	private String sourceCode;
	private MethodModel model;

	@Before
	public void tearDown() throws Exception {
		sourceCode = UiTestsPlugin.getDefault().getFileContents("/resources/parser/methodReceivers.gsm");
		model = ParserUtils.build(sourceCode);
	}

	@Test
	public void testReceivers() throws Exception {
		// {68=keywordMsg x:y:z:[[(5:11),(5:16),(5:21)],[(7:2),(8:2),(9:2)]],
		// 48=identifier receiver3[(5:1)],
		// 20=unaryMsg msg[(2:11),(4:2)],
		// 97=keywordMsg x:y:z:[[(5:11),(5:16),(5:21)],[(7:2),(8:2),(9:2)]],
		// 76=identifier receiver4[(6:1)],
		// 58=keywordMsg x:y:z:[[(5:11),(5:16),(5:21)],[(7:2),(8:2),(9:2)]],
		// 27=identifier receiver2[(3:1)], 10=identifier receiver1[(2:1)],
		// 41=unaryMsg msg[(2:11),(4:2)],
		// 89=keywordMsg x:y:z:[[(5:11),(5:16),(5:21)],[(7:2),(8:2),(9:2)]],
		// 63=keywordMsg x:y:z:[[(5:11),(5:16),(5:21)],[(7:2),(8:2),(9:2)]],
		// 105=keywordMsg x:y:z:[[(5:11),(5:16),(5:21)],[(7:2),(8:2),(9:2)]]
		// }

		assertMessageReferenceHasReceiverNamed(20, "receiver1"); // 1. occurence of #msg
		assertMessageReferenceHasReceiverNamed(41, "receiver2"); // 2. occurence of #msg
		assertMessageReferenceHasReceiverNamed(58, "receiver3"); // 1. occurence of #x:y:z, x part
		assertMessageReferenceHasReceiverNamed(63, "receiver3"); // 1. occurence of #x:y:z, y part
		assertMessageReferenceHasReceiverNamed(68, "receiver3"); // 1. occurence of #x:y:z, z part
		assertMessageReferenceHasReceiverNamed(89, "receiver4"); // 2. occurence of #x:y:z, x part
		assertMessageReferenceHasReceiverNamed(97, "receiver4"); // 2. occurence of #x:y:z, y part
		assertMessageReferenceHasReceiverNamed(105, "receiver4"); // 2. occurence of #x:y:z, z part

	}

	private void assertMessageReferenceHasReceiverNamed(final int offsetOfMessageReference, final String string) {
		final Element element = model.getElement(offsetOfMessageReference);
		assertTrue(element instanceof Message);

		final Message msg = (Message) element;
		final MessageReference ref = msg.getReferenceAtOffset(offsetOfMessageReference);
		assertNotNull(ref);

		final Element rec = ref.getReceiver();
		assertNotNull(rec);
		assertEquals(rec.getName(), string);
	}
}
