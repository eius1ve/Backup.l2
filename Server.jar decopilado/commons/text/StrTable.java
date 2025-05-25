/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StrTable {
    private final Map<Integer, Map<String, String>> w = new HashMap<Integer, Map<String, String>>();
    private final Map<String, Integer> x = new LinkedHashMap<String, Integer>();
    private final List<String> W = new ArrayList<String>();

    public StrTable(String string) {
        if (string != null) {
            this.W.add(string);
        }
    }

    public StrTable() {
        this(null);
    }

    public StrTable set(int n, String string, boolean bl) {
        return this.set(n, string, Boolean.toString(bl));
    }

    public StrTable set(int n, String string, byte by) {
        return this.set(n, string, Byte.toString(by));
    }

    public StrTable set(int n, String string, char c) {
        return this.set(n, string, String.valueOf(c));
    }

    public StrTable set(int n, String string, short s) {
        return this.set(n, string, Short.toString(s));
    }

    public StrTable set(int n, String string, int n2) {
        return this.set(n, string, Integer.toString(n2));
    }

    public StrTable set(int n, String string, long l) {
        return this.set(n, string, Long.toString(l));
    }

    public StrTable set(int n, String string, float f) {
        return this.set(n, string, Float.toString(f));
    }

    public StrTable set(int n, String string, double d) {
        return this.set(n, string, Double.toString(d));
    }

    public StrTable set(int n, String string, Object object) {
        return this.set(n, string, String.valueOf(object));
    }

    public StrTable set(int n, String string, String string2) {
        int n2;
        Map<Object, Object> map;
        if (this.w.containsKey(n)) {
            map = this.w.get(n);
        } else {
            map = new HashMap();
            this.w.put(n, map);
        }
        map.put(string, string2);
        if (!this.x.containsKey(string)) {
            n2 = Math.max(string.length(), string2.length());
        } else {
            n2 = string2.length();
            if (this.x.get(string) >= n2) {
                return this;
            }
        }
        this.x.put(string, n2);
        return this;
    }

    public StrTable addTitle(String string) {
        this.W.add(string);
        return this;
    }

    private static StringBuilder a(StringBuilder stringBuilder, String string, int n) {
        stringBuilder.append(string);
        if ((n -= string.length()) > 0) {
            for (int i = 0; i < n; ++i) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder;
    }

    private static StringBuilder b(StringBuilder stringBuilder, String string, int n) {
        int n2;
        int n3 = stringBuilder.length();
        stringBuilder.append(string);
        while ((n2 = n - (stringBuilder.length() - n3)) > 0) {
            stringBuilder.append(" ");
            if (n2 <= 1) continue;
            stringBuilder.insert(n3, " ");
        }
        return stringBuilder;
    }

    private static StringBuilder c(StringBuilder stringBuilder, String string, int n) {
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string);
        }
        return stringBuilder;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.x.isEmpty()) {
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder("|");
        StringBuilder stringBuilder3 = new StringBuilder("|");
        for (String object : this.x.keySet()) {
            StrTable.b(stringBuilder2, object, this.x.get(object) + 2).append("|");
            StrTable.c(stringBuilder3, "-", this.x.get(object) + 2).append("|");
        }
        if (!this.W.isEmpty()) {
            stringBuilder.append(" ");
            StrTable.c(stringBuilder, "-", stringBuilder2.length() - 2).append(" ").append("\n");
            for (String string : this.W) {
                stringBuilder.append("| ");
                StrTable.a(stringBuilder, string, stringBuilder2.length() - 3).append("|").append("\n");
            }
        }
        stringBuilder.append(" ");
        StrTable.c(stringBuilder, "-", stringBuilder2.length() - 2).append(" ").append("\n");
        stringBuilder.append((CharSequence)stringBuilder2).append("\n");
        stringBuilder.append((CharSequence)stringBuilder3).append("\n");
        for (Map map : this.w.values()) {
            stringBuilder.append("|");
            for (String string : this.x.keySet()) {
                StrTable.b(stringBuilder, map.containsKey(string) ? (String)map.get(string) : "-", this.x.get(string) + 2).append("|");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append(" ");
        StrTable.c(stringBuilder, "-", stringBuilder2.length() - 2).append(" ").append("\n");
        return stringBuilder.toString();
    }
}
