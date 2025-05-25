/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.DeleteObject
 *  l2.gameserver.network.l2.s2c.UserInfoType
 */
package utils;

import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.CharInfo;
import l2.gameserver.network.l2.s2c.DeleteObject;
import l2.gameserver.network.l2.s2c.UserInfoType;

public class Visuals {
    public static void refreshAppStatus(Player Player2) {
        for (Creature Player22 : Player2.getAroundCharacters(0, 400)) {
            if (Player22 == null || !Player22.isPlayer()) continue;
            ((Player)Player22).broadcastUserInfo(false, new UserInfoType[0]);
            Player2.sendPacket((IStaticPacket)new CharInfo(Player22));
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
        }
    }

    public static void refreshStoreStatus(Player Player2, boolean z) {
        for (Creature Player22 : Player2.getAroundCharacters(0, 400)) {
            if (Player22 == null || !Player22.isPlayer() || ((Player)Player22).getPrivateStoreType() == 0) continue;
            if (z) {
                Player2.sendPacket((IStaticPacket)new DeleteObject((GameObject)Player22));
                continue;
            }
            ((Player)Player22).broadcastUserInfo(false, new UserInfoType[0]);
            Player2.sendPacket((IStaticPacket)new CharInfo(Player22));
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
        }
        Player2.broadcastUserInfo(false, new UserInfoType[0]);
    }
}
