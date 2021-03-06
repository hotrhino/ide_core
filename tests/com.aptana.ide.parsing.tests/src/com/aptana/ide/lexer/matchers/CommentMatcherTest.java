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

import com.aptana.ide.lexer.matcher.CommentMatcher;
import com.aptana.ide.lexer.matcher.OrMatcher;
import com.aptana.ide.lexer.matcher.StringMatcher;

/**
 * @author Kevin Lindsey
 */
public class CommentMatcherTest extends TestCase
{
	private CommentMatcher _matcher;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		this._matcher = new CommentMatcher();
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
	 * testDefault
	 */
	public void testDefault()
	{
		char[] source = "// abc\n// def".toCharArray();
		
		int result = this._matcher.match(source, 0, source.length);
		
		assertEquals(6, result);
	}
	
	/**
	 * testStartCommentString
	 */
	public void testStartCommentString()
	{
		char[] source = "## testing\n## testing".toCharArray();
		
		int result = this._matcher.match(source, 0, source.length);
		assertEquals(-1, result);
		
		this._matcher.setStart("##");
		result = this._matcher.match(source, 0, source.length);
		assertEquals(10, result);
	}
	
	/**
	 * testStartCommentMatcher
	 */
	public void testStartCommentMatcher()
	{
		char[] source1 = "## testing\n## testing".toCharArray();
		char[] source2 = "!! testing\n## testing".toCharArray();
		
		OrMatcher or = new OrMatcher();
		or.appendChild(new StringMatcher("##"));
		or.appendChild(new StringMatcher("!!"));
		
		int result = this._matcher.match(source1, 0, source1.length);
		assertEquals(-1, result);
		
		result = this._matcher.match(source2, 0, source2.length);
		assertEquals(-1, result);
		
		this._matcher.setStart(or);
		
		result = this._matcher.match(source1, 0, source1.length);
		assertEquals(10, result);
		
		result = this._matcher.match(source2, 0, source2.length);
		assertEquals(10, result);
	}
	
	/**
	 * testEndCommentString
	 */
	public void testEndCommentString()
	{
		char[] source = "// test\n## test".toCharArray();
		
		this._matcher.setEnd("\n");
		
		int result = this._matcher.match(source, 0, source.length);
		
		assertEquals(8, result);
	}
	
	/**
	 * testEndCommentMatcher
	 */
	public void testEndCommentMatcher()
	{
		char[] source1 = "// test\n## test".toCharArray();
		char[] source2 = "// test\n!! test".toCharArray();
		
		OrMatcher or = new OrMatcher();
		or.appendChild(new StringMatcher("##"));
		or.appendChild(new StringMatcher("!!"));
		
		this._matcher.setEnd(or);
		
		int result = this._matcher.match(source1, 0, source1.length);
		assertEquals(10, result);
		
		result = this._matcher.match(source2, 0, source2.length);
		assertEquals(10, result);
	}
}
