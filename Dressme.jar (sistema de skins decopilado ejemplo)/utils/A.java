/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.s2c.ShowBoard
 */
package utils;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.ShowBoard;

public class A {
    public static void A(String str, Player Player2) {
        if (str == null) {
            return;
        }
        String replace = str.replace("\t", "");
        ShowBoard.separateAndSend((String)replace, (Player)Player2);
    }
}
