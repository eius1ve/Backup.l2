/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ArabicConv {
    private static final char b = '\u0622';
    private static final char c = '\u0623';
    private static final char d = '\u0625';
    private static final char e = '\u0627';
    private static final char f = '\u0644';
    private static final char[][] a = new char[][]{{'\u0622', '\ufe81', '\ufe81', '\ufe82', '\ufe82', '\u0002'}, {'\u0623', '\ufe82', '\ufe83', '\ufe84', '\ufe84', '\u0002'}, {'\u0624', '\ufe85', '\ufe85', '\ufe86', '\ufe86', '\u0002'}, {'\u0625', '\ufe87', '\ufe87', '\ufe88', '\ufe88', '\u0002'}, {'\u0626', '\ufe89', '\ufe8b', '\ufe8c', '\ufe8a', '\u0004'}, {'\u0627', '\u0627', '\u0627', '\ufe8e', '\ufe8e', '\u0002'}, {'\u0628', '\ufe8f', '\ufe91', '\ufe92', '\ufe90', '\u0004'}, {'\u0629', '\ufe93', '\ufe93', '\ufe94', '\ufe94', '\u0002'}, {'\u062a', '\ufe95', '\ufe97', '\ufe98', '\ufe96', '\u0004'}, {'\u062b', '\ufe99', '\ufe9b', '\ufe9c', '\ufe9a', '\u0004'}, {'\u062c', '\ufe9d', '\ufe9f', '\ufea0', '\ufe9e', '\u0004'}, {'\u062d', '\ufea1', '\ufea3', '\ufea4', '\ufea2', '\u0004'}, {'\u062e', '\ufea5', '\ufea7', '\ufea8', '\ufea6', '\u0004'}, {'\u062f', '\ufea9', '\ufea9', '\ufeaa', '\ufeaa', '\u0002'}, {'\u0630', '\ufeab', '\ufeab', '\ufeac', '\ufeac', '\u0002'}, {'\u0631', '\ufead', '\ufead', '\ufeae', '\ufeae', '\u0002'}, {'\u0632', '\ufeaf', '\ufeaf', '\ufeb0', '\ufeb0', '\u0002'}, {'\u0633', '\ufeb1', '\ufeb3', '\ufeb4', '\ufeb2', '\u0004'}, {'\u0634', '\ufeb5', '\ufeb7', '\ufeb8', '\ufeb6', '\u0004'}, {'\u0635', '\ufeb9', '\ufebb', '\ufebc', '\ufeba', '\u0004'}, {'\u0636', '\ufebd', '\ufebf', '\ufec0', '\ufebe', '\u0004'}, {'\u0637', '\ufec1', '\ufec3', '\ufec2', '\ufec4', '\u0004'}, {'\u0638', '\ufec5', '\ufec7', '\ufec6', '\ufec6', '\u0004'}, {'\u0639', '\ufec9', '\ufecb', '\ufecc', '\ufeca', '\u0004'}, {'\u063a', '\ufecd', '\ufecf', '\ufed0', '\ufece', '\u0004'}, {'\u0641', '\ufed1', '\ufed3', '\ufed4', '\ufed2', '\u0004'}, {'\u0642', '\ufed5', '\ufed7', '\ufed8', '\ufed6', '\u0004'}, {'\u0643', '\ufed9', '\ufedb', '\ufedc', '\ufeda', '\u0004'}, {'\u0644', '\ufedd', '\ufedf', '\ufee0', '\ufede', '\u0004'}, {'\u0645', '\ufee1', '\ufee3', '\ufee4', '\ufee2', '\u0004'}, {'\u0646', '\ufee5', '\ufee7', '\ufee8', '\ufee6', '\u0004'}, {'\u0647', '\ufee9', '\ufeeb', '\ufeec', '\ufeea', '\u0004'}, {'\u0648', '\ufeed', '\ufeed', '\ufeee', '\ufeee', '\u0002'}, {'\u0649', '\ufeef', '\ufeef', '\ufef0', '\ufef0', '\u0002'}, {'\u0671', '\u0671', '\u0671', '\ufb51', '\ufb51', '\u0002'}, {'\u064a', '\ufef1', '\ufef3', '\ufef4', '\ufef2', '\u0004'}, {'\u066e', '\ufbe4', '\ufbe8', '\ufbe9', '\ufbe5', '\u0004'}, {'\u0671', '\u0671', '\u0671', '\ufb51', '\ufb51', '\u0002'}, {'\u06aa', '\ufb8e', '\ufb90', '\ufb91', '\ufb8f', '\u0004'}, {'\u06c1', '\ufba6', '\ufba8', '\ufba9', '\ufba7', '\u0004'}, {'\u06e4', '\u06e4', '\u06e4', '\u06e4', '\ufeee', '\u0002'}, {'\u0686', '\ufb7a', '\ufb7c', '\ufb7d', '\ufb7b', '\u0004'}, {'\u067e', '\ufb56', '\ufb58', '\ufb59', '\ufb57', '\u0004'}, {'\u0698', '\ufb8a', '\ufb8a', '\ufb8b', '\ufb8b', '\u0002'}, {'\u06af', '\ufb92', '\ufb94', '\ufb95', '\ufb93', '\u0004'}, {'\u06cc', '\ufeef', '\ufef3', '\ufef4', '\ufef0', '\u0004'}, {'\u06a9', '\ufb8e', '\ufb90', '\ufb91', '\ufb8f', '\u0004'}};
    private static final Map<Character, char[]> bY;
    private static final char[] a;
    private static final char[][] b;

    private static char a(char c, char c2, boolean bl) {
        int n = 1;
        if (bl) {
            ++n;
        }
        if ('\u0644' == c2) {
            switch (c) {
                case '\u0622': {
                    return b[0][n];
                }
                case '\u0623': {
                    return b[1][n];
                }
                case '\u0625': {
                    return b[2][n];
                }
                case '\u0627': {
                    return b[3][n];
                }
            }
        }
        return '\u0000';
    }

    private static final char a(char c, int n) {
        char[] cArray = bY.get(Character.valueOf(c));
        if (cArray != null) {
            if (c != cArray[0]) {
                throw new RuntimeException();
            }
            return cArray[n];
        }
        return c;
    }

    private static final char a(char c) {
        char[] cArray = bY.get(Character.valueOf(c));
        if (cArray != null) {
            if (c != cArray[0]) {
                throw new RuntimeException();
            }
            return cArray[5];
        }
        return '\u0002';
    }

    private static String h(String string) {
        char c;
        int n;
        char c2;
        if (string.isEmpty()) {
            return "";
        }
        switch (string.length()) {
            case 0: {
                return "";
            }
            case 1: {
                return new String(new char[]{ArabicConv.a(string.charAt(0), 0)});
            }
            case 2: {
                char c3 = string.charAt(0);
                c2 = string.charAt(1);
                n = ArabicConv.a(c2, c3, true);
                if (n <= 0) break;
                return new String(new char[]{n});
            }
        }
        char[] cArray = new char[string.length()];
        c2 = string.charAt(0);
        cArray[0] = ArabicConv.a(c2, 2);
        for (n = 1; n < string.length() - 1; ++n) {
            c = ArabicConv.a(string.charAt(n), c2, true);
            if (c > '\u0000') {
                if (n - 2 < 0 || n - 2 >= 0 && ArabicConv.a(string.charAt(n - 2)) == '\u0002') {
                    cArray[n - 1] = '\u0000';
                    cArray[n] = c;
                } else {
                    cArray[n - 1] = '\u0000';
                    cArray[n] = ArabicConv.a(string.charAt(n), c2, false);
                }
            } else {
                cArray[n] = ArabicConv.a(string.charAt(n - 1)) == '\u0002' ? ArabicConv.a(string.charAt(n), 2) : ArabicConv.a(string.charAt(n), 3);
            }
            c2 = string.charAt(n);
        }
        n = string.length();
        c = ArabicConv.a(string.charAt(n - 1), string.charAt(n - 2), true);
        if (c > '\u0000') {
            if (n > 3 && ArabicConv.a(string.charAt(n - 3)) == '\u0002') {
                cArray[n - 2] = '\u0000';
                cArray[n - 1] = c;
            } else {
                cArray[n - 2] = '\u0000';
                cArray[n - 1] = ArabicConv.a(string.charAt(n - 1), string.charAt(n - 2), false);
            }
        } else {
            cArray[n - 1] = ArabicConv.a(string.charAt(n - 2)) == '\u0002' ? ArabicConv.a(string.charAt(n - 1), 1) : ArabicConv.a(string.charAt(n - 1), 4);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (char c4 : cArray) {
            if (c4 == '\u0000') continue;
            stringBuilder.append(c4);
        }
        return stringBuilder.toString();
    }

    public static boolean isArChar(char c) {
        char[] cArray = bY.get(Character.valueOf(c));
        return cArray != null;
    }

    public static String shapeArabic(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (ArabicConv.isArChar(string.charAt(i))) {
                int n2 = i;
                while (i < n && ArabicConv.isArChar(string.charAt(i))) {
                    ++i;
                }
                stringBuilder.append(ArabicConv.h(string.substring(n2, i)));
                if (i >= n) continue;
                stringBuilder.append(string.charAt(i));
                continue;
            }
            stringBuilder.append(string.charAt(i));
        }
        return stringBuilder.toString();
    }

    public static final void main(String ... stringArray) {
    }

    static {
        a = new char[]{'\u064b', '\u064c', '\u064d', '\u064e', '\u064f', '\u0650', '\u0651', '\u0652', '\u0653', '\u0654', '\u0655', '\u0656'};
        b = new char[][]{{'\u3ba6', '\ufef6', '\ufef5'}, {'\u3ba7', '\ufef8', '\ufef7'}, {'\u0625', '\ufefa', '\ufef9'}, {'\u0627', '\ufefc', '\ufefb'}};
        HashMap<Character, char[]> hashMap = new HashMap<Character, char[]>();
        for (char[] cArray : a) {
            hashMap.put(Character.valueOf(cArray[0]), cArray);
        }
        bY = Collections.unmodifiableMap(hashMap);
    }
}
