/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.utils.Location
 */
package zones;

import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.utils.Location;

private static class ClanLimitSiegeZone.ClanLimitSiegeZoneListener
implements OnZoneEnterLeaveListener {
    private final Location am;

    private ClanLimitSiegeZone.ClanLimitSiegeZoneListener(Location location) {
        this.am = location;
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        if (creature != null && creature.isPlayer()) {
            Player player = creature.getPlayer();
            if (player.isGM()) {
                return;
            }
            SiegeEvent siegeEvent = (SiegeEvent)player.getEvent(SiegeEvent.class);
            if (siegeEvent == null || player.getClan() == null) {
                player.teleToLocation(this.am);
                String string = new CustomMessage("zone.services.ClanLimitSiegeZone", player, new Object[0]).toString();
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                player.sendMessage(string);
                Summon summon = player.getPet();
                if (summon != null) {
                    summon.teleToLocation(this.am);
                }
            }
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
    }
}
