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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Chypsylon <therealchypsylon@gmail.com>
 */
public class Constants {
    public static final Path WORKING_DIRECTORY = Paths.get(new File(".").getAbsolutePath()).normalize();
    public static final Path BACKUP_DIRECTORY = Paths.get(WORKING_DIRECTORY + "\\backup\\").normalize();
    public static final Path BACKUP_DIRECTORY_BIN = BACKUP_DIRECTORY.resolve(".\\bin\\").normalize();
    
    public static final Path JSON_DIRECTORY = Paths.get(WORKING_DIRECTORY + "\\jsonTmp\\").normalize();
    
    public static final Path GIBBED_BINARY = Paths.get(WORKING_DIRECTORY + "\\lib\\gibbed\\Gibbed.MassEffect3.Coalesce.exe").normalize();
    
    public static final Path COALESCED_DIRECTORY = Paths.get("BIOGame\\CookedPCConsole\\Coalesced.bin");
}
