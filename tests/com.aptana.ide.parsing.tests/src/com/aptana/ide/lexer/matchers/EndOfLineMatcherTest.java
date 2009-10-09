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

import com.aptana.ide.lexer.matcher.EndOfLineMatcher;

/**
 * @author Kevin Lindsey
 */
public class EndOfLineMatcherTest extends TestCase
{
	private EndOfLineMatcher _matcher;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		this._matcher = new EndOfLineMatcher();
	}

	/**
	 * testNothing
	 */
	public void testNothing()
	{
		char[] source = "".toCharArray();

		int result = this._matcher.match(source, 0, source.length);

		assertEquals(0, result);
	}

	/**
	 * testEndOfFile
	 */
	public void testEndOfFile()
	{
		char[] source = "abcdef".toCharArray();

		int result = this._matcher.match(source, source.length, source.length);

		assertEquals(source.length, result);
	}

	/**
	 * testBeforeReturn
	 */
	public void testBeforeReturn()
	{
		char[] source = "abc\ra".toCharArray();

		int result = this._matcher.match(source, 3, source.length);

		assertEquals(3, result);
	}

	/**
	 * testBeforeNewline
	 */
	public void testBeforeNewline()
	{
		char[] source = "abc\na".toCharArray();

		int result = this._matcher.match(source, 3, source.length);

		assertEquals(3, result);
	}

	/**
	 * testNegatedNothing
	 */
	public void testNegatedNothing()
	{
		char[] source = "".toCharArray();

		this._matcher.setNegate(true);

		int result = this._matcher.match(source, 0, source.length);

		assertEquals(-1, result);
	}

	/**
	 * testNegatedEndOfFile
	 */
	public void testNegatedStartOfFile()
	{
		char[] source = "abcdef".toCharArray();

		this._matcher.setNegate(true);

		int result = this._matcher.match(source, source.length, source.length);

		assertEquals(-1, result);
	}

	/**
	 * testNegatedBeforeReturn
	 */
	public void testNegatedBeforeReturn()
	{
		char[] source = "abc\ra".toCharArray();

		this._matcher.setNegate(true);

		int result = this._matcher.match(source, 3, source.length);

		assertEquals(-1, result);
	}

	/**
	 * testNegatedBeforeNewline
	 */
	public void testNegatedBeforeNewline()
	{
		char[] source = "abc\na".toCharArray();

		this._matcher.setNegate(true);

		int result = this._matcher.match(source, 3, source.length);

		assertEquals(-1, result);
	}

	/**
	 * testNegatedWithinLine
	 */
	public void testNegatedWithinLine()
	{
		char[] source = "abc\na".toCharArray();

		this._matcher.setNegate(true);

		int result = this._matcher.match(source, 1, source.length);

		assertEquals(1, result);
	}
}