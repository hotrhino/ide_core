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

package com.aptana.ide.filesystem.ftp.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.aptana.ide.filesystem.ftp.FTPConnectionPoint;
import com.aptana.ide.core.io.tests.CommonConnectionTest;


/**
 * @author Max Stepanov
 *
 */
public class FTPConnectionWithBasePathTest extends CommonConnectionTest {

	private static FTPConnectionPoint setupConnection() {
		FTPConnectionPoint ftpcp = new FTPConnectionPoint();
		ftpcp.setHost("aptana.ftpstream.com"); //$NON-NLS-1$
		ftpcp.setLogin("test5"); //$NON-NLS-1$
		ftpcp.setPassword(new char[] { 'n', 'o', 'c', '$', '$', '1' });
		return ftpcp;
	}
	
	@Override
	@Before
	public final void initialize() throws CoreException {
		FTPConnectionPoint ftpcp = setupConnection();
		ftpcp.setPath(Path.ROOT.append(getClass().getSimpleName()));
		cp = ftpcp;
		super.initialize();
	}
	
	@BeforeClass
	public static void initBasePath() throws CoreException {
		FTPConnectionPoint ftpcp = setupConnection();
		IFileStore fs = ftpcp.getRoot().getFileStore(Path.ROOT.append(FTPConnectionWithBasePathTest.class.getSimpleName()));
		assertNotNull(fs);
		if (!fs.fetchInfo().exists()) {
			fs.mkdir(EFS.NONE, null);
		}
		ftpcp.disconnect(null);
		assertFalse(ftpcp.isConnected());
	}
	
	@AfterClass
	public static void cleanupBasePath() throws CoreException {
		FTPConnectionPoint ftpcp = setupConnection();
		IFileStore fs = ftpcp.getRoot().getFileStore(Path.ROOT.append(FTPConnectionWithBasePathTest.class.getSimpleName()));
		assertNotNull(fs);
		if (fs.fetchInfo().exists()) {
			fs.delete(EFS.NONE, null);
		}
		ftpcp.disconnect(null);
		assertFalse(ftpcp.isConnected());
	}

	/* (non-Javadoc)
	 * @see com.aptana.ide.core.io.tests.CommonConnectionTest#supportsChangeGroup()
	 */
	@Override
	protected boolean supportsChangeGroup() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.aptana.ide.core.io.tests.CommonConnectionTest#supportsChangePermissions()
	 */
	@Override
	protected boolean supportsChangePermissions() {
		return false;
	}

}
