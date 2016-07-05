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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chypsylon <therealchypsylon@gmail.com>
 */
public class Coalesced {
    private static final Logger LOG = Logger.getLogger(Coalesced.class.getName());
    
    /**
     * 
     * @param me3InstallPath 
     * @throws java.io.IOException 
     * @throws java.lang.InterruptedException 
     */
    public static void binToJson(Path me3InstallPath) throws IOException, InterruptedException {
        binToJson(me3InstallPath.resolve(Constants.COALESCED_DIRECTORY), Constants.JSON_DIRECTORY);
    }
    
    /**
     * 
     * @param coalescedPath
     * @param targetDir 
     * @throws java.io.IOException 
     * @throws java.lang.InterruptedException 
     */
    public static void binToJson(Path coalescedPath, Path targetDir) throws IOException, InterruptedException {
        if (Files.notExists(coalescedPath)) {
            throw new IOException("Coalesced doesn't exist");
        }
        
        if (Files.notExists(targetDir)) {
            try {
                Files.createDirectories(targetDir);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Couldn't create target directory for unpacking", ex);
                throw ex;
            }
        }

        List<String> params = java.util.Arrays.asList(Constants.GIBBED_BINARY.toAbsolutePath().toString(), "--bin2json", coalescedPath.toAbsolutePath().toString(), targetDir.toAbsolutePath().toString());
        ProcessBuilder processBuilder = new ProcessBuilder(params);
        Process gibbed = processBuilder.start();
        if (gibbed.waitFor() == 0) {
            LOG.log(Level.INFO, "Gibbed unpacked target {0} to {1}", new Object[]{coalescedPath.toString(), targetDir.toString()});
        } else {
            throw new IOException("Gibbed couldn't unnpack target " + coalescedPath.toString() + " to " + targetDir.toString());
        }
    }

    /**
     * 
     * @param me3InstallPath 
     * @throws java.io.IOException 
     * @throws java.lang.InterruptedException 
     */
    public static void jsonToBin(Path me3InstallPath) throws IOException, InterruptedException {
        jsonToBin(Constants.JSON_DIRECTORY, me3InstallPath.resolve(Constants.COALESCED_DIRECTORY), true);
    }
    
    /**
     * 
     * @param jsonDir
     * @param coalescedPath
     * @param deleteJsonDir  
     * @throws java.io.IOException  
     * @throws java.lang.InterruptedException  
     */
    public static void jsonToBin(Path jsonDir, Path coalescedPath, java.lang.Boolean deleteJsonDir) throws IOException, InterruptedException {
        if (Files.notExists(jsonDir)) {
            throw new IOException("Source directory for unpacking doesn't exist");
        }

        List<String> params = java.util.Arrays.asList(Constants.GIBBED_BINARY.toAbsolutePath().toString(), "--json2bin", jsonDir.toAbsolutePath().toString(), coalescedPath.toAbsolutePath().toString());
        ProcessBuilder processBuilder = new ProcessBuilder(params);
        Process gibbed = processBuilder.start();
        if (gibbed.waitFor() == 0) {
            LOG.log(Level.INFO, "Gibbed packed target {0} to {1}", new Object[]{jsonDir.toString(), coalescedPath.toString()});
            if (deleteJsonDir) {
                Util.deleteDirectory(jsonDir.toFile());
            }
        } else {
            throw new IOException("Gibbed couldn't pack target " + jsonDir.toString() + " to " + coalescedPath.toString());
        }
    }
}
