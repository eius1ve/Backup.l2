/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.network.l2.s2c.EventTrigger
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 */
package npc.model;

import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.s2c.EventTrigger;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class OddGlobeInstance.ZoneListener2
implements OnZoneEnterLeaveListener {
    private boolean hu = false;

    public void onZoneEnter(Zone zone, Creature creature) {
        Player player = creature.getPlayer();
        if (player == null || !creature.isPlayer()) {
            return;
        }
        player.broadcastPacket(new L2GameServerPacket[]{new EventTrigger(21100100, true)});
        if (!this.hu) {
            this.hu = true;
            player.showQuestMovie(26);
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
    }
}
