/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

private static class ClanHelperService.ClanHelperEntry {
    private final int bFD;
    private final int bFE;
    private final List<Pair<Integer, Long>> dN;

    private ClanHelperService.ClanHelperEntry(int n, int n2, List<Pair<Integer, Long>> list) {
        this.bFD = n;
        this.bFE = n2;
        this.dN = list;
    }

    public ClanHelperService.ClanHelperEntry(int n, int n2) {
        this.bFD = n;
        this.bFE = n2;
        this.dN = Collections.emptyList();
    }

    public int getRequiredClanSize() {
        return this.bFD;
    }

    public int getRewardLevel() {
        return this.bFE;
    }

    public List<Pair<Integer, Long>> getRewardItems() {
        return this.dN;
    }

    public static ClanHelperService.ClanHelperEntry parse(String string) {
        int n = string.indexOf(58);
        if (n <= 0) {
            throw new RuntimeException("Can't parse requirements of \"" + string + "\"");
        }
        String string2 = string.substring(0, n).trim();
        String string3 = string.substring(n + 1).trim();
        int n2 = string2.indexOf(44);
        int n3 = Integer.parseInt(n2 > 0 ? string2.substring(0, n2).trim() : string2);
        int n4 = string3.indexOf(44, 0);
        if (n4 <= 0) {
            int n5 = Integer.parseInt(string3);
            return new ClanHelperService.ClanHelperEntry(n3, n5);
        }
        int n6 = Integer.parseInt(string3.substring(0, n4));
        String string4 = string3.substring(n4 + 1).trim();
        if (string4.isEmpty()) {
            return new ClanHelperService.ClanHelperEntry(n3, n6);
        }
        ArrayList<Pair<Integer, Long>> arrayList = new ArrayList<Pair<Integer, Long>>();
        if (!string4.startsWith(",")) {
            if (!string4.startsWith("[") || !string4.endsWith("]")) {
                throw new RuntimeException("Can't parse reward of \"" + string + "\"");
            }
            string4 = string4.substring(1, string4.length() - 1).trim();
            for (String string5 : string4.split(",")) {
                if ((string5 = string5.trim()).isEmpty()) continue;
                int n7 = string5.indexOf(45, 1);
                arrayList.add((Pair<Integer, Long>)Pair.of((Object)Integer.parseInt(string5.substring(0, n7).trim()), (Object)Long.parseLong(string5.substring(n7 + 1).trim())));
            }
        }
        return new ClanHelperService.ClanHelperEntry(n3, n6, arrayList);
    }
}
