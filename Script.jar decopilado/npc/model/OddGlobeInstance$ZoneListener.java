/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 */
package npc.model;

import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;

public class OddGlobeInstance.ZoneListener
implements OnZoneEnterLeaveListener {
    private boolean hu = false;

    public void onZoneEnter(Zone zone, Creature creature) {
        Player player = creature.getPlayer();
        if (player == null || !creature.isPlayer() || this.hu) {
            return;
        }
        this.hu = true;
        player.showQuestMovie(24);
    }

    public void onZoneLeave(Zone zone, Creature creature) {
    }
}
