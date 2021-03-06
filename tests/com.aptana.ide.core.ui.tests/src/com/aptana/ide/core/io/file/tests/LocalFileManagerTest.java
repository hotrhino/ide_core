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
package com.aptana.ide.core.io.file.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import com.aptana.ide.core.FileUtils;
import com.aptana.ide.core.io.ConnectionException;
import com.aptana.ide.core.io.IVirtualFile;
import com.aptana.ide.core.io.IVirtualFileManager;
import com.aptana.ide.core.ui.io.file.LocalFile;
import com.aptana.ide.core.ui.io.file.LocalFileManager;
import com.aptana.ide.core.ui.io.file.LocalProtocolManager;

/**
 * Test case for local file manager
 * 
 * @author Ingo Muschenetz
 */
public class LocalFileManagerTest extends TestCase
{
	LocalFileManager _manager;

	/**
	 * onMac
	 */
	protected static boolean onMac = System.getProperty("os.name").startsWith("Mac OS"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		this._manager = new LocalFileManager(null);
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		this._manager = null;
		super.tearDown();
	}

	/**
	 * testLocalFileManager
	 * 
	 * @throws IOException
	 * @throws ConnectionException
	 */
	public void testLocalFileManager() throws IOException, ConnectionException
	{
		File f = File.createTempFile("localTest", ".js"); //$NON-NLS-1$ //$NON-NLS-2$
		String root = f.getParent() + this._manager.getFileSeparator() + "testdir" + this._manager.getFileSeparator(); //$NON-NLS-1$
		String name0 = f.getName();
		f.delete();
		f = new File(root + name0);
		String name0_b = "localTest_b.js"; //$NON-NLS-1$
		String name0_copy = "localTest_copy.js"; //$NON-NLS-1$

		String dir0 = "dir0"; //$NON-NLS-1$
		String dir1 = "dir1"; //$NON-NLS-1$

		// check if temp exists, so we can clean up
		File froot = new File(root);
		FileUtils.deleteDirectory(froot);
		assertFalse(froot.exists());
		froot.mkdirs();
		assertTrue(froot.exists());
		IVirtualFile vroot = this._manager.createVirtualDirectory(root);

		// create file via manager
		IVirtualFile vf0 = this._manager.createVirtualFile(root + name0);
		assertNotNull(vf0);
		assertTrue(vf0 instanceof LocalFile);
		File f0 = new File(root + name0);
		f0.createNewFile();
		assertTrue(f0.exists());

		// LocalFile tests
		// check extension
		assertTrue(vf0.getExtension().equals(".js")); //$NON-NLS-1$
		// check name
		assertEquals(name0, vf0.getName());
		assertEquals(f.getCanonicalPath(), vf0.getAbsolutePath());
		assertEquals(f.getAbsolutePath(), vf0.getPath());
		assertFalse(vf0.isDirectory());
		assertEquals(froot.getCanonicalPath(), vf0.getParentFile().getAbsolutePath());

		// create 2 folders
		IVirtualFile vd0 = this._manager.createVirtualDirectory(root + dir0);
		IVirtualFile vd1 = this._manager.createVirtualDirectory(root + dir1);
		File d0 = new File(root + dir0);
		File d1 = new File(root + dir1);
		d0.mkdir();
		assertTrue(d0.exists());
		d1.mkdir();
		assertTrue(d1.exists());

		// copy file to new name
		File dest_f0_b = new File(root + name0_copy);
		IVirtualFile vdest_f0_b = new LocalFile(this._manager, dest_f0_b);
		// this._manager.copyFile(vf0, vdest_f0_b);
		f0.createNewFile();
		assertTrue(f0.exists());
		dest_f0_b.createNewFile();
		assertTrue(dest_f0_b.exists());

		// copy file to folder
		File dest_f0 = new File(root + dir0 + this._manager.getFileSeparator() + name0);
		IVirtualFile vdest_f0 = new LocalFile(this._manager, dest_f0);
		// this._manager.copyFile(vf0, vdest_f0);
		f0.createNewFile();
		assertTrue(f0.exists());
		dest_f0.createNewFile();
		assertTrue(dest_f0.exists());

		// move file
		File dest_f1 = new File(root + dir1 + this._manager.getFileSeparator() + name0);
		IVirtualFile vdest_f1 = new LocalFile(this._manager, dest_f1);
		this._manager.moveFile(vf0, vdest_f1);
		assertFalse(f0.exists());
		dest_f1.createNewFile();
		assertTrue(dest_f1.exists());

		// rename file *** Note, how can we get an IVirtualFile here?
		boolean rename = this._manager.renameFile(vdest_f1, root + dir1 + "/" + name0_b); //$NON-NLS-1$
		assertTrue(rename);
		File f0b = new File(root + dir1 + "/" + name0_b); //$NON-NLS-1$
		f0b.createNewFile();
		assertTrue(f0b.exists());
		assertEquals(f0b.getCanonicalPath(), vdest_f1.getAbsolutePath());

		// before cloaking, not recursive
		assertEquals(3, vroot.getFiles().length);
		
		// before cloaking, recursive
		assertEquals(5, vroot.getFiles(true, true).length);
		
		// Check file cloaking
		assertFalse(vdest_f0_b.isCloaked());
		vdest_f0_b.setCloaked(true);
		assertTrue(vdest_f0_b.isCloaked());
		assertEquals(1, this._manager.getCloakedFiles().length);
		vdest_f0_b.setCloaked(false);
		assertFalse(vdest_f0_b.isCloaked());
		assertEquals(0, this._manager.getCloakedFiles().length);

		// cloaking based on file extension
		this._manager.addCloakExpression("(?i).*\\.JS"); //$NON-NLS-1$
		assertTrue(vdest_f0_b.isCloaked());
		
		// after cloaking, not recursive
		assertEquals(3, vroot.getFiles().length);		
		// after cloaking, recursive
		assertEquals(5, vroot.getFiles(true, true).length);

		// after cloaking, not recursive (so 1 JS, and 2 directories)
		assertEquals(3, vroot.getFiles(false, true).length);
		// after cloaking, not recursive (so 2 directories)
		assertEquals(2, vroot.getFiles(false, false).length);
		// after cloaking, recursive (so 2 directories)
		assertEquals(2, vroot.getFiles(true, false).length);

		this._manager.removeCloakExpression("(?i).*\\.JS"); //$NON-NLS-1$
		assertFalse(vdest_f0_b.isCloaked());

		// after cloaking removed, not recursive
		assertEquals(3, vroot.getFiles().length);		
		// after cloaking removed, recursive
		assertEquals(5, vroot.getFiles(true, true).length);

		// Now try cloaking based on file path
		this._manager.addCloakExpression("dir0"); //$NON-NLS-1$
		this._manager.addCloakExpression("dir1"); //$NON-NLS-1$
		assertTrue(vd0.isCloaked());
		assertTrue(vd1.isCloaked());
		this._manager.removeCloakExpression("dir0"); //$NON-NLS-1$
		this._manager.removeCloakExpression("dir1"); //$NON-NLS-1$
		assertFalse(vd0.isCloaked());
		assertFalse(vd1.isCloaked());
		
		// remove files via manager
		this._manager.deleteFile(vdest_f0_b);
		assertFalse(dest_f0_b.exists());
		this._manager.deleteFile(vdest_f0);
		assertFalse(dest_f0.exists());

		// remove folders via manager
		this._manager.deleteFile(vd0);
		this._manager.deleteFile(vd1);
		assertFalse(d0.exists());
		assertFalse(d1.exists());

		// clean up temp if we created it
		if (froot.exists())
		{
			FileUtils.deleteDirectory(froot);
		}
	}

	/**
	 * Test method for 'com.aptana.ide.io.ftp.FtpVirtualFileManager.fromSerializableString(String)'
	 * 
	 * @throws ConnectionException
	 */
	public void testFromSerializableString() throws ConnectionException
	{
		IVirtualFileManager fileManager = LocalProtocolManager.getInstance().createFileManager();
		LocalFileManager ftp = (LocalFileManager) fileManager;
		ftp.setNickName("nickname"); //$NON-NLS-1$
		ftp.setBasePath("/base/path"); //$NON-NLS-1$
		// ftp.setId(0); // Id is auto-calculated
		ftp.setAutoCalculateServerTimeOffset(!ftp.isAutoCalculateServerTimeOffset());
		ftp.setTimeOffset(100000); // requires a valid server, so we have to fake a value

		IVirtualFile f1 = ftp.createVirtualFile("/test.txt"); //$NON-NLS-1$
		IVirtualFile f2 = ftp.createVirtualDirectory("/test_directory"); //$NON-NLS-1$
		ftp.addCloakedFile(f1);
		ftp.addCloakedFile(f2);

		ftp.addCloakExpression(".*\\.js"); //$NON-NLS-1$
		ftp.addCloakExpression("\\.svn"); //$NON-NLS-1$

		String serialized = ftp.getHashString();
		IVirtualFileManager fileManager2 = LocalProtocolManager.getInstance().createFileManager();
		LocalFileManager ftp2 = (LocalFileManager) fileManager2;
		ftp2.fromSerializableString(serialized);

		String[] strings = serialized.split(LocalFileManager.DELIMITER);
		assertEquals(7, strings.length);

		assertEquals(ftp.getNickName(), ftp2.getNickName());
		assertEquals(ftp.getBasePath(), ftp2.getBasePath());
		assertEquals(ftp.isAutoCalculateServerTimeOffset(), ftp2.isAutoCalculateServerTimeOffset());
		assertEquals(ftp.getId(), ftp2.getId());
		assertEquals(ftp.getTimeOffset(), ftp2.getTimeOffset());
		assertEquals(ftp2.getCloakedFiles().length, ftp2.getCloakedFiles().length);
		assertEquals(ftp2.getCloakedFileExpressions().length, ftp2.getCloakedFileExpressions().length);
	}
}
