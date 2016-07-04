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
package eu.chypsylon.me3toolkit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Chypsylon <therealchypsylon@gmail.com>
 */
public class Util {

    private static final Logger LOG = Logger.getLogger(Util.class.getName());
    /**
     * Find the base directory of the current Mass Effect 3 installation. Searchs in the registry 
     * first and then prompts the user for the path if nothing was found there.
     * @return The Path of the installation or null if none was found
     */
    public static Path getMe3InstallPath() {
        Path me3BinaryPath = Paths.get(".\\Binaries\\Win32\\MassEffect3.exe");
        Path me3RootPath = findInstallPathRegistry();
        
        if (me3RootPath != null && Files.exists(me3RootPath.resolve(me3BinaryPath))) {
            LOG.log(Level.INFO, "Found install path in registry: {0}", me3RootPath.toString());
            return me3RootPath;
        }
        
        LOG.log(Level.INFO, "Prompting user for install path");
        while (true) {            
            JOptionPane.showMessageDialog(null, "Please select the Mass Effect installation directory. Usually it's at 'C:\\Program Files\\Origin Games\\Mass Effect 3'", "Me3 Toolkit", JOptionPane.INFORMATION_MESSAGE);
            
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            int ret = chooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                me3RootPath = chooser.getSelectedFile().toPath();
                if (Files.exists(me3RootPath.resolve(me3BinaryPath))) {
                    LOG.log(Level.INFO, "Found Me3 install path: {0}", me3RootPath.toString());
                    return me3RootPath;
                } else {
                    ret = JOptionPane.showConfirmDialog(null, "Couldn't find the Mass Effect 3 executable at the specified path. Do you want to try again?", "Me3 Toolkit", JOptionPane.YES_NO_OPTION);
                    if (ret == JOptionPane.NO_OPTION) {
                        return null;
                    }
                }
            } else {
                return null;                
            }
        }
    }
    /**
     * Search the root directory of the current Mass Effect 3 installation in the registry
     * @return The Path of the installation or null if none was found
     */
    private static Path findInstallPathRegistry() {
        try {
            ProcessBuilder builder = new ProcessBuilder("reg", "query", "HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\BioWare\\Mass Effect 3", "/v", "\"Install Dir\"");
            LOG.log(Level.FINE, "reg cmd: {0}", builder.command());
            Process reg = builder.start();

            BufferedReader output = new BufferedReader(new InputStreamReader(reg.getInputStream()));

            if (reg.waitFor() == 0) {
                String line;
                while ((line = output.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("Install Dir")) {
                        return Paths.get(line.substring(line.lastIndexOf("    ") +4));
                    }
                }
            }
        } catch (IOException | InterruptedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
