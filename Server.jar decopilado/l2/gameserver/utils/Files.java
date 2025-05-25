/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Files {
    private static final Logger dF = LoggerFactory.getLogger(Files.class);

    public static void writeFile(String string, String string2) {
        try {
            FileUtils.writeStringToFile((File)new File(string), (String)string2, (String)"UTF-8");
        }
        catch (IOException iOException) {
            dF.error("Error while saving file : " + string, (Throwable)iOException);
        }
    }

    public static boolean copyFile(String string, String string2) {
        try {
            FileUtils.copyFile((File)new File(string), (File)new File(string2), (boolean)false);
            return true;
        }
        catch (IOException iOException) {
            dF.error("Error while copying file : " + string + " to " + string2, (Throwable)iOException);
            return false;
        }
    }
}
