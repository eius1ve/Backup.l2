/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.utils.PositionUtils
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.utils.PositionUtils;

public class TeleToGH.ZoneListener
implements OnZoneEnterLeaveListener {
    public void onZoneEnter(Zone zone, Creature creature) {
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        Player player = creature.getPlayer();
        if (player != null && Config.SERVICES_GIRAN_HARBOR_ENABLED && player.getReflection() == ReflectionManager.GIRAN_HARBOR && player.isVisible()) {
            double d = PositionUtils.convertHeadingToDegree((int)creature.getHeading());
            double d2 = Math.toRadians(d - 90.0);
            creature.teleToLocation((int)((double)creature.getX() + 50.0 * Math.sin(d2)), (int)((double)creature.getY() - 50.0 * Math.cos(d2)), creature.getZ());
        }
    }
}
