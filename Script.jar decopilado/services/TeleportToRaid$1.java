/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.Say2
 */
package services;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Say2;
import services.TeleportToRaid;

static class TeleportToRaid.1
extends RunnableImpl {
    final /* synthetic */ TeleportToRaid.RaidData val$rd;

    TeleportToRaid.1(TeleportToRaid.RaidData raidData) {
        this.val$rd = raidData;
    }

    public void runImpl() {
        String string = this.val$rd.ih;
        String string2 = this.val$rd.ii;
        string = string.replaceAll("%name_ru%", this.val$rd.ic);
        string2 = string2.replaceAll("%name_en%", this.val$rd.ic);
        Say2 say2 = new Say2(0, ChatType.ANNOUNCEMENT, "", string);
        Say2 say22 = new Say2(0, ChatType.ANNOUNCEMENT, "", string2);
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null || !this.val$rd.j(player.getLevel())) continue;
            player.sendPacket((IStaticPacket)(player.isLangRus() ? say2 : say22));
        }
    }
}
