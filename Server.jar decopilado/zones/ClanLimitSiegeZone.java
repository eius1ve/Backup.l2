/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.instancemanager.ReflectionManager
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
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.Collection;
import l2.commons.listener.Listener;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClanLimitSiegeZone
implements ScriptFile {
    private static final Logger ey = LoggerFactory.getLogger(ClanLimitSiegeZone.class);
    private static final String iD = "clanSiegeLimitBackLoc";
    private static final String iE = "clanSiegeLimitZone";

    private static void init() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            if (!zone.getParams().getBool((Object)iE, false)) continue;
            boolean bl = zone.getParams().isSet((Object)iD);
            if (!bl) {
                ey.warn("Back coord not set for clan siege limit zone " + zone);
                continue;
            }
            Location location = Location.parseLoc((String)zone.getParams().getString((Object)iD));
            zone.addListener((Listener)new ClanLimitSiegeZoneListener(location));
            ++n;
        }
        ey.info("Siege Limit Zone: Loaded " + n + " clan siege limit zone(s).");
    }

    public void onLoad() {
        ClanLimitSiegeZone.init();
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private static class ClanLimitSiegeZoneListener
    implements OnZoneEnterLeaveListener {
        private final Location am;

        private ClanLimitSiegeZoneListener(Location location) {
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
}
