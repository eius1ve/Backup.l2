/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ShowBoard
 */
package scriptsGab.utils;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ShowBoard;

public class BBS {
    public static void separateAndSend(String str, Player Player2) {
        if (str == null) {
            return;
        }
        String replace = str.replace("\t", "");
        if (replace.length() < 8180) {
            Player2.sendPacket((IStaticPacket)new ShowBoard(replace, "101", Player2));
            Player2.sendPacket((IStaticPacket)new ShowBoard(null, "102", Player2));
            Player2.sendPacket((IStaticPacket)new ShowBoard(null, "103", Player2));
        } else if (replace.length() < 16360) {
            Player2.sendPacket((IStaticPacket)new ShowBoard(replace.substring(0, 8180), "101", Player2));
            Player2.sendPacket((IStaticPacket)new ShowBoard(replace.substring(8180, replace.length()), "102", Player2));
            Player2.sendPacket((IStaticPacket)new ShowBoard(null, "103", Player2));
        } else if (replace.length() < 24540) {
            Player2.sendPacket((IStaticPacket)new ShowBoard(replace.substring(0, 8180), "101", Player2));
            Player2.sendPacket((IStaticPacket)new ShowBoard(replace.substring(8180, 16360), "102", Player2));
            Player2.sendPacket((IStaticPacket)new ShowBoard(replace.substring(16360, replace.length()), "103", Player2));
        }
    }
}
