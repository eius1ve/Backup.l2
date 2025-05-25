/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Summon
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 */
package zones;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;

class LevelLimitZone.LevelLimitZoneListener.1
extends RunnableImpl {
    final /* synthetic */ Player val$player;

    LevelLimitZone.LevelLimitZoneListener.1(Player player) {
        this.val$player = player;
    }

    public void runImpl() throws Exception {
        this.val$player.sendMessage(new CustomMessage("scripts.zones.epic.banishMsg", this.val$player, new Object[0]));
        this.val$player.teleToLocation(LevelLimitZoneListener.this.ao);
        String string = new CustomMessage("zone.services.LevelLimitZone.CountLimit", this.val$player, new Object[0]).toString();
        this.val$player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
        this.val$player.sendMessage(string);
        Summon summon = this.val$player.getPet();
        if (summon != null) {
            summon.teleToLocation(LevelLimitZoneListener.this.ao);
        }
    }
}
