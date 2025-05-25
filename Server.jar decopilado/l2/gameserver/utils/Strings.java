/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import l2.gameserver.Config;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Strings {
    private static final Logger dQ = LoggerFactory.getLogger(Strings.class);
    private static String[] aR;
    private static String[] aS;
    private static String[] aT;

    public static String stripSlashes(String string) {
        if (string == null) {
            return "";
        }
        string = string.replace("\\'", "'");
        string = string.replace("\\\\", "\\");
        return string;
    }

    public static Boolean parseBoolean(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Number) {
            return ((Number)object).intValue() > 0;
        }
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        if (object instanceof Double) {
            return Math.abs((Double)object) < 1.0E-5;
        }
        return !String.valueOf(object).isEmpty();
    }

    public static void reload() {
        try {
            String[] stringArray;
            int n;
            String[] stringArray2 = FileUtils.readFileToString((File)new File(Config.DATAPACK_ROOT, "data/translit.txt")).split("\n");
            aR = new String[stringArray2.length * 2];
            for (n = 0; n < stringArray2.length; ++n) {
                stringArray = stringArray2[n].split(" +");
                Strings.aR[n * 2] = stringArray[0];
                Strings.aR[n * 2 + 1] = stringArray[1];
            }
            stringArray2 = FileUtils.readFileToString((File)new File(Config.DATAPACK_ROOT, "data/translit_back.txt")).split("\n");
            aS = new String[stringArray2.length * 2];
            for (n = 0; n < stringArray2.length; ++n) {
                stringArray = stringArray2[n].split(" +");
                Strings.aS[n * 2] = stringArray[0];
                Strings.aS[n * 2 + 1] = stringArray[1];
            }
            stringArray2 = FileUtils.readFileToString((File)new File(Config.DATAPACK_ROOT, "data/transcode.txt")).split("\n");
            aT = new String[stringArray2.length * 2];
            for (n = 0; n < stringArray2.length; ++n) {
                stringArray = stringArray2[n].split(" +");
                Strings.aT[n * 2] = stringArray[0];
                Strings.aT[n * 2 + 1] = stringArray[1];
            }
        }
        catch (IOException iOException) {
            dQ.error("", (Throwable)iOException);
        }
        dQ.info("Loaded " + (aR.length + aR.length + aT.length) + " translit entries.");
    }

    public static String translit(String string) {
        for (int i = 0; i < aR.length; i += 2) {
            string = string.replace(aR[i], aR[i + 1]);
        }
        return string;
    }

    public static String fromTranslit(String string, int n) {
        block3: {
            block2: {
                if (n != 1) break block2;
                for (int i = 0; i < aS.length; i += 2) {
                    string = string.replace(aS[i], aS[i + 1]);
                }
                break block3;
            }
            if (n != 2) break block3;
            for (int i = 0; i < aT.length; i += 2) {
                string = string.replace(aT[i], aT[i + 1]);
            }
        }
        return string;
    }

    public static String replace(String string, String string2, int n, String string3) {
        return Pattern.compile(string2, n).matcher(string).replaceAll(string3);
    }

    public static boolean matches(String string, String string2, int n) {
        return Pattern.compile(string2, n).matcher(string).matches();
    }

    public static String bbParse(String string) {
        if (string == null) {
            return null;
        }
        string = string.replace("\r", "");
        string = string.replaceAll("(\\s|\"|'|\\(|^|\n)\\*(.*?)\\*(\\s|\"|'|\\)|\\?|\\.|!|:|;|,|$|\n)", "$1<font color=\"LEVEL\">$2</font>$3");
        string = string.replaceAll("(\\s|\"|'|\\(|^|\n)\\$(.*?)\\$(\\s|\"|'|\\)|\\?|\\.|!|:|;|,|$|\n)", "$1<font color=\"00FFFF\">$2</font>$3");
        string = Strings.replace(string, "^!(.*?)$", 8, "<font color=\"FFFFFF\">$1</font>\n\n");
        string = string.replaceAll("%%\\s*\n", "<br1>");
        string = string.replaceAll("\n\n+", "<br>");
        string = Strings.replace(string, "\\[npc_%objectId%_Quest\\|([^\\]]*?)\\]", 32, "<button ALIGN=LEFT ICON=\"QUEST\" action=\"bypass -h npc_%objectId%_Quest\">$1</button>");
        string = Strings.replace(string, "\\[([^\\]\\|]*?)\\|([^\\]]*?)\\]", 32, "<button ALIGN=LEFT ICON=\"NORMAL\" action=\"bypass -h $1\">$2</button>");
        string = string.replaceAll(" @", "\" msg=\"");
        return string;
    }

    public static String joinStrings(String string, String[] stringArray, int n, int n2) {
        Object object = "";
        if (n < 0 && (n += stringArray.length) < 0) {
            return object;
        }
        while (n < stringArray.length && n2 != 0) {
            if (!((String)object).isEmpty() && string != null && !string.isEmpty()) {
                object = (String)object + string;
            }
            object = (String)object + stringArray[n++];
            --n2;
        }
        return object;
    }

    public static String joinStrings(String string, String[] stringArray, int n) {
        return Strings.joinStrings(string, stringArray, n, -1);
    }

    public static String joinStrings(String string, String[] stringArray) {
        return Strings.joinStrings(string, stringArray, 0);
    }

    public static String stripToSingleLine(String string) {
        if (string.isEmpty()) {
            return string;
        }
        int n = (string = string.replaceAll("\\\\n", "\n")).indexOf("\n");
        if (n > -1) {
            string = string.substring(0, n);
        }
        return string;
    }

    public static List<Pair<Integer, Long>> parseIntLongPairs(String string) {
        LinkedList<Pair<Integer, Long>> linkedList = new LinkedList<Pair<Integer, Long>>();
        for (String string2 : StringUtils.split((String)string, (String)";")) {
            if ((string2 = StringUtils.trimToEmpty((String)string2)).isEmpty()) continue;
            int n = string2.indexOf(44);
            if (n < 1) {
                throw new IllegalArgumentException("Can't parse \"" + string + "\"");
            }
            int n2 = Integer.parseInt(StringUtils.trimToEmpty((String)string2.substring(0, n)));
            long l = Long.parseLong(StringUtils.trimToEmpty((String)string2.substring(n + 1)));
            linkedList.add((Pair<Integer, Long>)Pair.of((Object)n2, (Object)l));
        }
        return linkedList;
    }
}
