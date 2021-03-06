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
package com.aptana.ide.editor.css.tests;

import com.aptana.ide.editor.css.lexing.CSSTokenTypes;
import com.aptana.ide.editor.css.parsing.CSSMimeType;
import com.aptana.ide.editor.css.parsing.CSSParser;
import com.aptana.ide.lexer.ILexer;
import com.aptana.ide.lexer.TokenCategories;
import com.aptana.ide.lexer.tests.TestTokenBase;

/**
 * @author Kevin Lindsey
 */
public class TestKeywordTokens extends TestTokenBase
{
	/**
	 * @see com.aptana.ide.lexer.tests.TestTokenBase#createLexer()
	 */
	protected ILexer createLexer() throws Exception
	{
		CSSParser parser = new CSSParser();

		return parser.getLexer();
	}
	
	/**
	 * @see com.aptana.ide.lexer.tests.TestTokenBase#getLanguage()
	 */
	protected String getLanguage()
	{
		return CSSMimeType.MimeType;
	}

	/**
	 * testImportKeyword
	 */
	public void testImportKeyword()
	{
		this.lexemeTest("@import", TokenCategories.KEYWORD, CSSTokenTypes.IMPORT); //$NON-NLS-1$
		this.lexemeTest("@imports", TokenCategories.LITERAL, CSSTokenTypes.AT_KEYWORD); //$NON-NLS-1$
	}

	/**
	 * testPageKeyword
	 */
	public void testPageKeyword()
	{
		this.lexemeTest("@page", TokenCategories.KEYWORD, CSSTokenTypes.PAGE); //$NON-NLS-1$
		this.lexemeTest("@pages", TokenCategories.LITERAL, CSSTokenTypes.AT_KEYWORD); //$NON-NLS-1$
	}

	/**
	 * testMediaKeyword
	 */
	public void testMediaKeyword()
	{
		this.lexemeTest("@media", TokenCategories.KEYWORD, CSSTokenTypes.MEDIA); //$NON-NLS-1$
		this.lexemeTest("@medias", TokenCategories.LITERAL, CSSTokenTypes.AT_KEYWORD); //$NON-NLS-1$
	}

	/**
	 * testCharSetKeyword
	 */
	public void testCharSetKeyword()
	{
		this.lexemeTest("@charset", TokenCategories.KEYWORD, CSSTokenTypes.CHARSET); //$NON-NLS-1$
		this.lexemeTest("@charsets", TokenCategories.LITERAL, CSSTokenTypes.AT_KEYWORD); //$NON-NLS-1$
	}

	/**
	 * testUrlKeyword
	 */
	public void testUrlKeyword()
	{
		this.lexemeTest("url(test.css)", TokenCategories.KEYWORD, CSSTokenTypes.URL); //$NON-NLS-1$
	}

	/**
	 * testImportantKeyword
	 */
	public void testImportantKeyword()
	{
		this.lexemeTest("! important", TokenCategories.KEYWORD, CSSTokenTypes.IMPORTANT); //$NON-NLS-1$
		this.noLexemeTest("! importants"); //$NON-NLS-1$
	}
}
