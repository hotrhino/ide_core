/**
 * This file Copyright (c) 2005-2009 Aptana, Inc. This program is
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
package com.aptana.ide.syncing.ui.editors;

import java.text.MessageFormat;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.aptana.ide.core.ui.CoreUIUtils;
import com.aptana.ide.syncing.core.ISiteConnection;
import com.aptana.ide.syncing.ui.SyncingUIPlugin;

/**
 * @author Michael Xia (mxia@aptana.com)
 */
public class EditorUtils {

    /**
     * Opens the connection editor on a specific site connection.
     * 
     * @param site
     *            the connection
     */
    public static void openConnectionEditor(final ISiteConnection site) {
        CoreUIUtils.getDisplay().asyncExec(new Runnable() {

            public void run() {
                IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                if (window != null) {
                    IWorkbenchPage page = window.getActivePage();
                    try {
                        IEditorPart editorPart = page.openEditor(new ConnectionEditorInput(site),
                                ConnectionEditor.ID);
                        if (editorPart instanceof ConnectionEditor) {
                            // in case the site information has changed
                            ((ConnectionEditor) editorPart).setSelectedSite(site);
                        }
                    } catch (PartInitException e) {
                        SyncingUIPlugin.log(MessageFormat.format(
                                Messages.EditorUtils_FailedToOpenEditor, site.getName()), e);
                    }
                }
            }
        });
    }

    /**
     * Closes the connection editor corresponding to the specific site
     * connection.
     * 
     * @param site
     *            the connection
     */
    public static void closeConnectionEditor(final ISiteConnection site) {
        CoreUIUtils.getDisplay().asyncExec(new Runnable() {

            public void run() {
                IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                if (window != null) {
                    IWorkbenchPage page = window.getActivePage();
                    IEditorPart editor = page.findEditor(new ConnectionEditorInput(site));
                    if (editor != null) {
                        page.closeEditor(editor, false);
                    }
                }
            }
        });
    }
}
