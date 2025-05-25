/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package instances;

import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class Frintezza.ZoneListener
implements OnZoneEnterLeaveListener {
    public void onZoneEnter(Zone zone, Creature creature) {
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        if (creature.isNpc() && (creature.getNpcId() == 29046 || creature.getNpcId() == 29047)) {
            creature.teleToLocation(new Location(174240, -88020, -5112));
            ((NpcInstance)creature).getAggroList().clear(true);
            creature.setCurrentHpMp((double)creature.getMaxHp(), (double)creature.getMaxMp());
            creature.broadcastCharInfo();
        }
    }
}
