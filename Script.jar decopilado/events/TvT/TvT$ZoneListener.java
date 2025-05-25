/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 */
package events.TvT;

import events.TvT.TvT;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;

private static class TvT.ZoneListener
implements OnZoneEnterLeaveListener {
    private TvT.ZoneListener() {
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        if (creature == null) {
            return;
        }
        Player player = creature.getPlayer();
        if (_status > 0 && player != null && !J.contains(player.getStoredId()) && !K.contains(player.getStoredId())) {
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)new TvT.TeleportTask(creature, new Location(147451, 46728, -3410))), 3000L);
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        if (creature == null) {
            return;
        }
        Player player = creature.getPlayer();
        if (_status > 1 && player != null && player.getTeam() != TeamType.NONE && (J.contains(player.getStoredId()) || K.contains(player.getStoredId()))) {
            double d = PositionUtils.convertHeadingToDegree((int)creature.getHeading());
            double d2 = Math.toRadians(d - 90.0);
            int n = (int)((double)creature.getX() + 50.0 * Math.sin(d2));
            int n2 = (int)((double)creature.getY() - 50.0 * Math.cos(d2));
            int n3 = creature.getZ();
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)new TvT.TeleportTask(creature, new Location(n, n2, n3))), 3000L);
        }
    }
}
