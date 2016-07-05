/*
 * The MIT License
 *
 * Copyright 2016 Chypsylon <therealchypsylon@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package eu.chypsylon.me3toolkit.toolkit;

import eu.chypsylon.me3toolkit.ui.MainUi;
import eu.chypsylon.me3toolkit.util.Constants;
import eu.chypsylon.me3toolkit.util.Util;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Chypsylon <therealchypsylon@gmail.com>
 */
public class Me3Toolkit {

    private static final Logger LOG = Logger.getLogger(Me3Toolkit.class.getName());

    public static void main(String[] args) {
        Me3Toolkit me3Toolkit = new Me3Toolkit();

        me3Toolkit.setMe3InstallPath(Util.getMe3InstallPath());
        
        if (me3Toolkit.getMe3InstallPath() == null) {
            JOptionPane.showMessageDialog(null, "Couldn't find Mass Effect 3 installation directory. Closing now.", "Me3 Toolkit", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        me3Toolkit.startMainUi();
    }

    private MainUi mainUi;
    private Path me3InstallPath;

    public Me3Toolkit() {
        LOG.log(Level.INFO, "Me3Toolkit starting");
    }

    public MainUi getMainUi() {
        return mainUi;
    }

    public Path getMe3InstallPath() {
        return me3InstallPath;
    }

    public void setMe3InstallPath(Path me3InstallPath) {
        this.me3InstallPath = me3InstallPath;
    }

    public void startMainUi() {
        MainUi.setLookAndFeel();

        java.awt.EventQueue.invokeLater(() -> {
            mainUi = new MainUi();
            
            mainUi.getAboutPanel().getBackupButton().addActionListener((ActionEvent e) -> {
                Path backupCoalesced = Util.backupCoalesced(me3InstallPath);
                if (backupCoalesced != null) {
                    mainUi.print("Backed up Coalesced.bin to " + backupCoalesced.toString());
                } else {
                    mainUi.print("ERROR: Couldn't back up Coalesced.bin");
                }
            });
            
            mainUi.getAboutPanel().getRestoreButton().addActionListener((ActionEvent e) -> {
                Path restoreCoalesced = Util.restoreCoalesced(me3InstallPath);
                if (restoreCoalesced != null) {
                    mainUi.print("Restored Coalesced.bin from " + restoreCoalesced.toString());
                } else {
                    mainUi.print("ERROR: couldnt restore Coalesced.bin");
                }
            });
            
            mainUi.getFovFixPanel().getApplyFovButton().addActionListener((ActionEvent e) -> {
                try {
                    mainUi.printSeperator();
                    int newFovValue = (int)mainUi.getFovFixPanel().getFovSpinner().getValue();
                    LOG.log(Level.INFO, "Setting FOV to {0}", newFovValue);
                    if (Fixes.applyFovFix(this, newFovValue)) {
                        LOG.log(Level.INFO, "Set FOV to {0}", newFovValue);
                        mainUi.print("Set FOV to " + newFovValue);
                    } else {
                        LOG.log(Level.SEVERE, "Couldn't apply new FOV value");
                        mainUi.print("ERROR: Couldn't apply new FOV value");
                    }
                } catch (Exception ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
                mainUi.printSeperator();
            });
            
            mainUi.getTextChatPanel().getActivateButton().addActionListener((ActionEvent e) -> {
                mainUi.printSeperator();
                String hotkey = mainUi.getTextChatPanel().getHotkeyTextField().getText();
                LOG.log(Level.INFO, "Activating text chat on hotkey {0}", hotkey);
                if (Fixes.activateTextChat(this, hotkey)) {
                    LOG.log(Level.INFO, "Set text chat hotkey to {0}", hotkey);
                    mainUi.print("Set text chat hotkey to " + hotkey);
                } else {
                    LOG.log(Level.SEVERE, "Couldn't set text chat hotkey");
                    mainUi.print("ERROR: Couldn't set text chat hotkey");
                }
                mainUi.printSeperator();
            });
            
            mainUi.getSplitOmnikeyPanel().getApplyButton().addActionListener((ActionEvent e) -> {
                mainUi.printSeperator();
                LOG.log(Level.INFO, "Splitting omnikey ...");
                if (Fixes.seperateOmniKey(this)) {
                    LOG.log(Level.INFO, "Split omnikey");
                    mainUi.print("Split Omnikey");
                } else {
                    LOG.log(Level.SEVERE, "Couldn't split omnikey");
                    mainUi.print("ERROR: Couldn't split omnikey");
                }
                mainUi.printSeperator();
            });
            
            try {
                Util.deleteDirectory(Constants.JSON_DIRECTORY.toFile());
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Couldn't delete json directory", ex);
                mainUi.print("Couldn't remove json directory. Please check if any files in it are currently opended in another program");
            }

            mainUi.setVisible(true);

            mainUi.print("Me3 Toolkit started");
            mainUi.print("Found Me3 install directory: " + me3InstallPath.toString());
        });
    }
}
