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
package bosses;

import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

private class FrintezzaManager.FrintessaEnterListener
implements OnZoneEnterLeaveListener {
    private FrintezzaManager.FrintessaEnterListener() {
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        FrintezzaManager.this.t();
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        if (creature.isNpc() && (creature == FrintezzaManager.this.i || creature == FrintezzaManager.this.j)) {
            creature.teleToLocation(new Location(174240, -88020, -5112));
            ((NpcInstance)creature).getAggroList().clear(true);
            creature.setCurrentHpMp((double)creature.getMaxHp(), (double)creature.getMaxMp());
            creature.broadcastCharInfo();
        }
    }
}
