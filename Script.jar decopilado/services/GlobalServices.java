/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;

public class GlobalServices
extends Functions {
    public static boolean makeCustomHero(Player player, long l) {
        if (player.isHero() || l <= 0L) {
            return false;
        }
        player.setCustomHero(true, l, Config.ALT_ALLOW_CUSTOM_HERO_SKILLS);
        player.broadcastPacket(new L2GameServerPacket[]{new SocialAction(player.getObjectId(), 20016)});
        player.broadcastUserInfo(false, new UserInfoType[0]);
        return true;
    }
}
