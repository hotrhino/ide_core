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
package com.aptana.ide.syncing.ui.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.aptana.ide.core.FileUtils;
import com.aptana.ide.core.StringUtils;
import com.aptana.ide.core.io.IConnectionPoint;
import com.aptana.ide.core.ui.CoreUIPlugin;
import com.aptana.ide.core.ui.SWTUtils;
import com.aptana.ide.syncing.core.ISiteConnection;

/**
 * @author Ingo Muschenetz
 * @author Michael Xia (modified to extend from JFace Dialog)
 */
public class ChooseSiteConnectionDialog extends TrayDialog implements SelectionListener {

    private Combo fSiteCombo;
    private Label fSiteDescriptionLabel;
    private Button fRememberMyDecisionButton;

    private ISiteConnection[] fSites;
    private ISiteConnection fSelectedSite;

    // Show the remember my decision check box
    private boolean fShowRememberMyDecision;
    private boolean fRememberMyDecision;

    /**
     * @param parent
     *            the parent shell
     * @param sites
     *            the array of available sites
     */
    public ChooseSiteConnectionDialog(Shell parent, ISiteConnection[] sites) {
        this(parent, sites, false);
    }

    /**
     * @param parent
     *            the parent shell
     * @param sites
     *            the array of available sites
     * @param showRememberMyDecision
     *            true if to display the "remember my decision" checkbox, false
     *            otherwise
     */
    public ChooseSiteConnectionDialog(Shell parent, ISiteConnection[] sites,
            boolean showRememberMyDecision) {
        super(parent);
        fSites = sites;
        fShowRememberMyDecision = showRememberMyDecision;

        setShellStyle(getShellStyle() | SWT.RESIZE);
        setHelpAvailable(false);
    }

    /**
     * @return the selected site
     */
    public ISiteConnection getSelectedSite() {
        return fSelectedSite;
    }

    /**
     * @return true if the decision should be remembered, false otherwise
     */
    public boolean isRememberMyDecision() {
        if (fShowRememberMyDecision) {
            return fRememberMyDecision;
        }
        return false;
    }

    /**
     * Sets the selected site.
     * 
     * @param site
     *            the site connection to be selected
     */
    public void setSelectedSite(ISiteConnection site) {
        fSelectedSite = site;
    }

    public void setShowRememberMyDecision(boolean show) {
        fShowRememberMyDecision = show;
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        Object source = e.getSource();

        if (source == fSiteCombo) {
            updateDescriptiveText();
        } else if (source == fRememberMyDecisionButton) {
            fRememberMyDecision = fRememberMyDecisionButton.getSelection();
        }
    }

    /**
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(Messages.ChooseSiteConnectionDialog_Title);
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent) {
        Control control = super.createContents(parent);

        Shell shell = getShell();
        Shell parentShell = getShell().getParent().getShell();
        SWTUtils.centerAndPack(shell, parentShell);

        return control;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    protected Control createDialogArea(Composite parent) {
        Composite main = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        main.setLayout(layout);
        main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        Composite comp = new Composite(main, SWT.NONE);
        comp.setLayout(new GridLayout(2, false));
        comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        Label label = new Label(comp, SWT.RIGHT);
        label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
        label.setAlignment(SWT.RIGHT);
        label.setImage(SWTUtils.getImage(CoreUIPlugin.getDefault(), "icons/aptana_dialog_tag.png")); //$NON-NLS-1$

        label = new Label(comp, SWT.WRAP);
        label.setFont(SWTUtils.getDefaultSmallFont());
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        gridData.heightHint = 50;
        gridData.widthHint = 400;
        gridData.horizontalIndent = 5;
        gridData.verticalIndent = 3;
        label.setLayoutData(gridData);
        label.setText(Messages.ChooseSiteConnectionDialog_LBL_Message);

        comp = new Composite(main, SWT.NONE);
        comp.setLayout(new GridLayout(2, false));
        comp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        label = new Label(comp, SWT.NONE);
        label.setText(StringUtils.makeFormLabel(Messages.ChooseSiteConnectionDialog_LBL_Connection));
        fSiteCombo = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
        fSiteCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        fSiteCombo.addSelectionListener(this);

        // left padding
        new Label(comp, SWT.NONE);

        fSiteDescriptionLabel = new Label(comp, SWT.NONE);
        gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gridData.widthHint = 450;
        fSiteDescriptionLabel.setLayoutData(gridData);

        if (fShowRememberMyDecision) {
            fRememberMyDecisionButton = new Button(comp, SWT.CHECK);
            fRememberMyDecisionButton.setText(Messages.ChooseSiteConnectionDialog_LBL_RememberMyDecision);
            gridData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
            gridData.horizontalSpan = 2;
            fRememberMyDecisionButton.setLayoutData(gridData);
            fRememberMyDecisionButton.addSelectionListener(this);

            label = new Label(comp, SWT.WRAP);
            label.setText(Messages.ChooseSiteConnectionDialog_LBL_PropertyPage);
            gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, false);
            gridData.horizontalSpan = 2;
            label.setLayoutData(gridData);
        }

        initializeDefaultValues();

        return main;
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    protected void okPressed() {
        if (!validate()) {
            return;
        }

        // records the selected site
        int index = fSiteCombo.getSelectionIndex();
        if (index > -1) {
            fSelectedSite = fSites[index];
        }
        super.okPressed();
    }

    /**
     * @see org.eclipse.jface.dialogs.Dialog#cancelPressed()
     */
    protected void cancelPressed() {
        fSelectedSite = null;
        super.cancelPressed();
    }

    /**
     * Validates the fields to see if they are complete.
     * 
     * @return boolean true if the validation passed, false otherwise
     */
    private boolean validate() {
        boolean success = true;
        if (!SWTUtils.testWidgetValue(fSiteCombo, 0)) {
            success = false;
        }

        return success;
    }

    private void initializeDefaultValues() {
        int currentIndex = 0;
        int selectIndex = 0;
        for (ISiteConnection site : fSites) {
            fSiteCombo.add(site.getName() + ": " + site.getDestination().getName()); //$NON-NLS-1$
            if (site == fSelectedSite) {
                selectIndex = currentIndex;
            }
            currentIndex++;
        }

        fSiteCombo.select(selectIndex);
        updateDescriptiveText();
    }

    private void updateDescriptiveText() {
        int index = fSiteCombo.getSelectionIndex();
        if (index == -1) {
            return;
        }
        fSelectedSite = fSites[index];

        IConnectionPoint source = fSelectedSite.getSource();
        IConnectionPoint target = fSelectedSite.getDestination();

        try {
            fSiteDescriptionLabel.setText(FileUtils.compressPath(source.getRoot().toString(), 25)
                    + " <-> " + FileUtils.compressPath(target.getRoot().toString(), 25)); //$NON-NLS-1$
        } catch (CoreException e) {
            fSiteDescriptionLabel.setText(FileUtils.compressPath(source.getName(), 25) + " <-> " //$NON-NLS-1$
                    + FileUtils.compressPath(target.getName(), 25));
        }
    }
}
