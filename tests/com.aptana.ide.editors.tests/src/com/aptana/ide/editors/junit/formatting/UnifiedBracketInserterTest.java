/**
 * This file Copyright (c) 2005-2008 Aptana, Inc. This program is
 * dual-licensed under both the Aptana Public License and the GNU General
 * Public license. You may elect to use one or the other of these licenses.
 * 
 * This program is distributed in the hope that it will be useful, but
 * AS-IS and WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, TITLE, or
 * NONINFRINGEMENT. Redistribution, except as permitted by whichever of
 * the GPL or APL you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or modify this
 * program under the terms of the GNU General Public License,
 * Version 3, as published by the Free Software Foundation.  You should
 * have received a copy of the GNU General Public License, Version 3 along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Aptana provides a special exception to allow redistribution of this file
 * with certain other free and open source software ("FOSS") code and certain additional terms
 * pursuant to Section 7 of the GPL. You may view the exception and these
 * terms on the web at http://www.aptana.com/legal/gpl/.
 * 
 * 2. For the Aptana Public License (APL), this program and the
 * accompanying materials are made available under the terms of the APL
 * v1.0 which accompanies this distribution, and is available at
 * http://www.aptana.com/legal/apl/.
 * 
 * You may view the GPL, Aptana's exception and additional terms, and the
 * APL in the file titled license.html at the root of the corresponding
 * plugin containing this source file.
 * 
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.ide.editors.junit.formatting;

import com.aptana.ide.editors.formatting.UnifiedBracketInserter;

import junit.framework.TestCase;

/**
 * UnifiedBracketInserterTest
 * 
 * @author Ingo Muschenetz
 */
public class UnifiedBracketInserterTest extends TestCase
{
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/**
	 * Test method for 'com.aptana.ide.editors.formatting.UnifiedBracketInserter.UnifiedBracketInserter(ISourceViewer,
	 * IFileContext, UnifiedConfiguration)'
	 */
	public void testUnifiedBracketInserter()
	{

	}

	/**
	 * Test method for 'com.aptana.ide.editors.formatting.UnifiedBracketInserter.hasPeerCharacter(char)'
	 */
	public void testHasPeerCharacter()
	{

	}

	/**
	 * Test method for 'com.aptana.ide.editors.formatting.UnifiedBracketInserter.getPeerCharacter(char)'
	 */
	public void testGetPeerCharacter()
	{

	}

	/**
	 * Test method for 'com.aptana.ide.editors.formatting.UnifiedBracketInserter.isStringBalanced(String, char,
	 * boolean)'
	 */
	public void testIsStringBalanced()
	{
		assertFalse(UnifiedBracketInserter.isStringBalanced("var x = {}", '}', false)); // result would be var x = { //$NON-NLS-1$
		assertFalse(UnifiedBracketInserter.isStringBalanced("var x = {}", '}', true)); // result would be var x = {}} //$NON-NLS-1$
		assertTrue(UnifiedBracketInserter.isStringBalanced("var x = {", '}', true)); // result would be var x = {} //$NON-NLS-1$
		assertFalse(UnifiedBracketInserter.isStringBalanced("var x = {", '{', true)); // result would be var x = //$NON-NLS-1$

		assertFalse(UnifiedBracketInserter.isStringBalanced("var x = \"\"", '\"', true)); // result would be var x = //$NON-NLS-1$
		// """
		assertFalse(UnifiedBracketInserter.isStringBalanced("var x = \'\'", '\'', true)); // result would be var x = //$NON-NLS-1$
		// '''

		assertFalse(UnifiedBracketInserter.isStringBalanced("var x = \"\"", '\"', false)); // result would be var x = " //$NON-NLS-1$
		assertFalse(UnifiedBracketInserter.isStringBalanced("var x = \'\'", '\'', false)); // result would be var x = ' //$NON-NLS-1$

		assertTrue(UnifiedBracketInserter.isStringBalanced("var x = \"", '\"', true)); // result would be var x = "" //$NON-NLS-1$
		assertTrue(UnifiedBracketInserter.isStringBalanced("var x = \'", '\'', true)); // result would be var x = '' //$NON-NLS-1$
	}

	/**
	 * Test method for 'com.aptana.ide.editors.formatting.UnifiedBracketInserter.triggerContentAssistPopup()'
	 */
	public void testTriggerContentAssistPopup()
	{
	}
}
