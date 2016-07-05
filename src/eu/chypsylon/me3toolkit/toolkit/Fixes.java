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

import eu.chypsylon.me3toolkit.util.Coalesced;
import eu.chypsylon.me3toolkit.util.Constants;
import eu.chypsylon.me3toolkit.util.Util;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chypsylon <therealchypsylon@gmail.com>
 */
public class Fixes {
    private static final Logger LOG = Logger.getLogger(Fixes.class.getName());
    
    /**
     * 
     * @param me3Toolkit
     * @param fovValue
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException 
     */
    public static void applyFovFix(Me3Toolkit me3Toolkit, int fovValue) throws IOException, InterruptedException, ParseException {
        preFix(me3Toolkit);
        applyFixJson("sfxgame.sfxgamemodebase", "Name=\"Shared_Aim\"", 
                "( Name=\"Shared_Aim\", Command=\"SwapWeaponIfEmpty | TightAim | FOV 0 | OnRelease FOV " + fovValue + " | OnRelease StopTightAim\" )");
        postFix(me3Toolkit);
    }

    /**
     * 
     * @param me3Toolkit
     * @param hotkey
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException 
     */
    public static final void activateTextChat(Me3Toolkit me3Toolkit, String hotkey) throws IOException, InterruptedException, ParseException {
        preFix(me3Toolkit);
        applyFixJson("sfxgame.sfxgamemodedefault", "Command=\"talk\"", "( Name=\"" + hotkey + "\", Command=\"talk\" )");
        postFix(me3Toolkit);
    }
    
    /**
     * 
     * @param me3Toolkit
     * @throws IOException
     * @throws InterruptedException
     * @throws ParseException 
     */
    public static final void seperateOmniKey(Me3Toolkit me3Toolkit) throws IOException, InterruptedException, ParseException {
        preFix(me3Toolkit);
        applyFixJson("sfxgame.sfxgamemodebase", "Name=\"Shared_Action\"", 
                "( Name=\"Shared_Action\", Command=\"Exclusive TryStandingJump | OnRelease StormOff | OnHold 0.2 StormOn | Exclusive PressAction | OnTap 0.3 TapAction | OnHold 0.3 HoldAction\")");
        applyFixJson("sfxgame.sfxgamemodebase", "Name=\"Shared_SquadAttack\"", 
                "( Name=\"Shared_SquadAttack\", Command=\"Exclusive Used\" )");
        postFix(me3Toolkit);
    }
    
    /**
     * Apply the specified fix to the extracted json
     * @param section
     * @param removeEntry
     * @param addEntry
     * @throws IOException
     * @throws ParseException 
     */
    private static void applyFixJson(String section, String removeEntry, String addEntry) throws IOException, ParseException {
        //TODO: filename as parameter
        //TODO: use jsonpath instead
        try (FileReader fileReader = new FileReader(Constants.JSON_DIRECTORY.resolve("06_bioinput.json").toString())) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONArray bindings = (JSONArray) ((JSONObject) ((JSONObject) jsonObject.get("sections")).get(section)).get("bindings");
            
            //delete old target entries
            for (Iterator it = bindings.iterator(); it.hasNext();) {
                Object binding = it.next();
                if (((String) binding).contains(removeEntry)) {
                    it.remove();
                }
            }
            
            bindings.add(addEntry);
            
            try (FileWriter fileWriter = new FileWriter(Constants.JSON_DIRECTORY.resolve("06_bioinput.json").toString())) {
                fileWriter.write(jsonObject.toJSONString());
                fileWriter.flush();
            }
        }
    }

    /**
     * 
     * @param me3Toolkit
     * @throws IOException
     * @throws InterruptedException 
     */
    private static void preFix(Me3Toolkit me3Toolkit) throws IOException, InterruptedException {
        Path backupCoalesced = Util.backupCoalesced(me3Toolkit.getMe3InstallPath());
        me3Toolkit.getMainUi().print("Backed up Coalesced.bin to " + backupCoalesced);
        
        me3Toolkit.getMainUi().print("Unpacking Coalesced.bin ...");
        Coalesced.binToJson(me3Toolkit.getMe3InstallPath());
    }
    
    /**
     * 
     * @param me3Toolkit
     * @throws IOException
     * @throws InterruptedException 
     */
    private static void postFix(Me3Toolkit me3Toolkit) throws IOException, InterruptedException {
        me3Toolkit.getMainUi().print("Packing Coalesced.bin ...");
        Coalesced.jsonToBin(me3Toolkit.getMe3InstallPath());
    }
}
