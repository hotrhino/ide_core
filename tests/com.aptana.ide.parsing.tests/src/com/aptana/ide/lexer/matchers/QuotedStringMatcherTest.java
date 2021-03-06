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
package com.aptana.ide.lexer.matchers;

import junit.framework.TestCase;

import com.aptana.ide.lexer.matcher.QuotedStringMatcher;

/**
 * @author Kevin Lindsey
 */
// CHECKSTYLE:OFF
public class QuotedStringMatcherTest extends TestCase
{
	private QuotedStringMatcher _matcher;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		this._matcher = new QuotedStringMatcher();
	}

	/**
	 * testNothing
	 */
	public void testNothing()
	{
		char[] source = "".toCharArray();
		
		int result = this._matcher.match(source, 0, source.length);
		
		assertEquals(-1, result);
	}
	
	/**
	 * testDoubleQuotedString
	 */
	public void testDoubleQuotedString()
	{
		char[] source = "\"this is a test\"".toCharArray();
		
		int result = this._matcher.match(source, 0, source.length);
		
		assertEquals(16, result);
	}
	
	/**
	 * testSingleQuotedString
	 */
	public void testSingleQuotedString()
	{
		char[] source = "'this is a test'".toCharArray();
		
		this._matcher.appendText("'");
		
		int result = this._matcher.match(source, 0, source.length);
		
		assertEquals(16, result);
	}
	
	/**
	 * testMultipleQuotes
	 */
	public void testMultipleQuotes()
	{
		char[] source1 = "'this `is a test'".toCharArray();
		char[] source2 = "`this 'is a test`".toCharArray();
		
		this._matcher.appendText("'`");
		
		int result = this._matcher.match(source1, 0, source1.length);
		assertEquals(17, result);
		
		result = this._matcher.match(source2, 0, source2.length);
		assertEquals(17, result);
	}
		
	/**
	 * testEscape
	 */
	public void testEscape()
	{
		char[] source = "'test\\\ring'".toCharArray();
		
		this._matcher.appendText("'");
		
		int result = this._matcher.match(source, 0, source.length);
		
		assertEquals(11, result);
	}
	
	/**
	 * testEscapedQuote
	 */
	public void testEscapedQuote()
	{
		char[] source = "'test\\'int'".toCharArray();
		
		this._matcher.appendText("'");
		
		int result = this._matcher.match(source, 0, source.length);
		
		assertEquals(11, result);
	}
	
	/**
	 * testUnclosed
	 */
	public void testUnclosed()
	{
		char[] source = "'test".toCharArray();
		
		this._matcher.appendText("'");
		
		int result = this._matcher.match(source, 0, source.length);
		
		assertEquals(5, result);
	}
	
	/**
	 * testMultiLine
	 */
	public void testMultiLine()
	{
		char[] source = "'test\ring'".toCharArray();
		
		this._matcher.appendText("'");
		
		int result = this._matcher.match(source, 0, source.length);
		assertEquals(5, result);
		
		this._matcher.setMultiLine(true);
		result = this._matcher.match(source, 0, source.length);
		assertEquals(10, result);
	}
}
