/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Player
 */
package community;

import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Player;
import utils.A;

public class GabrielCBB {
    protected static GabrielCBB z;
    private static String C;
    private static String B;
    private static String D;
    private static String E;

    static {
        C = "wardrobe/services/wardrobe/";
        B = "gab_";
        D = "itembroker";
        E = "special";
    }

    public static GabrielCBB getInstance() {
        if (z == null) {
            z = new GabrielCBB();
        }
        return z;
    }

    public void parseCommand(String str, Player Player2) {
        String substring = str.substring(B.length());
        String htm = HtmCache.getInstance().getNotNull(String.valueOf(C) + "template/sideTemplate.htm", Player2);
        String str2 = "";
        String str3 = "";
        if (substring.startsWith("dressme")) {
            str3 = HtmCache.getInstance().getNotNull(String.valueOf(C) + "specials.htm", Player2);
            str2 = HtmCache.getInstance().getNotNull(String.valueOf(C) + "mainDressmeTemplate.htm", Player2);
        }
        A.A(str3.replace("%backBypass%", "gab_dressme").replace("%template%", htm).replace("%CHANGE%", str2).replace("%name%", Player2.getName()), Player2);
    }
}
