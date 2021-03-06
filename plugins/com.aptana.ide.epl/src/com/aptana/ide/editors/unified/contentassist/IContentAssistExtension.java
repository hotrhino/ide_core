/**
 * Copyright (c) 2005-2006 Aptana, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html. If redistributing this code,
 * this entire header must remain intact.
 */
package com.aptana.ide.editors.unified.contentassist;

/**
 * 
 * @author Ingo Muschenetz
 *
 */
public interface IContentAssistExtension
{
	/**
	 * Was the editor hot key activated
	 * @param state
	 */
	void setHotkeyActivated(boolean state);

	/**
	 * Is the editor hot key activated
	 * @return
	 */
	boolean isHotkeyActivated();
}
