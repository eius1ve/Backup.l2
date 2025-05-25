/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.base.Experience
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
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.Experience;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelLimitZone
implements ScriptFile {
    private static final Logger eG = LoggerFactory.getLogger(LevelLimitZone.class);
    private static final String jd = "playerMinLevel";
    private static final String je = "playerMaxLevel";
    private static final String jf = "playerLevelLimitBackLoc";

    private static void init() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            boolean bl = zone.getParams().isSet((Object)jd);
            boolean bl2 = zone.getParams().isSet((Object)je);
            boolean bl3 = zone.getParams().isSet((Object)jf);
            if (bl != bl2) {
                eG.warn("Min or max level not set for zone " + zone.getName());
            }
            if (!bl || !bl2) continue;
            if (!bl3) {
                eG.warn("Back coord not set for player level limited zone " + zone);
                continue;
            }
            int n2 = zone.getParams().getInteger((Object)jd, 1);
            int n3 = zone.getParams().getInteger((Object)je, Experience.getMaxLevel());
            Location location = Location.parseLoc((String)zone.getParams().getString((Object)jf));
            zone.addListener((Listener)new LevelLimitZoneListener(n2, n3, location));
            ++n;
        }
        eG.info("LevelLimitZone: Loaded " + n + " player level limit zone(s).");
    }

    public void onLoad() {
        LevelLimitZone.init();
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private static class LevelLimitZoneListener
    implements OnZoneEnterLeaveListener {
        private final int bHa;
        private final int bHb;
        private final Location ao;

        private LevelLimitZoneListener(int n, int n2, Location location) {
            this.bHa = n;
            this.bHb = n2;
            this.ao = location;
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            Player player;
            if (creature == null) {
                return;
            }
            if (creature.isPlayer()) {
                player = creature.getPlayer();
                if (player.isGM()) {
                    return;
                }
                if (player.getLevel() < this.bHa || player.getLevel() > this.bHb) {
                    ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

                        public void runImpl() throws Exception {
                            player.sendMessage(new CustomMessage("scripts.zones.epic.banishMsg", player, new Object[0]));
                            player.teleToLocation(ao);
                            String string = new CustomMessage("zone.services.LevelLimitZone.CountLimit", player, new Object[0]).toString();
                            player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                            player.sendMessage(string);
                            Summon summon = player.getPet();
                            if (summon != null) {
                                summon.teleToLocation(ao);
                            }
                        }
                    });
                }
            }
            if ((creature.isSummon() || creature.isPet()) && ((player = (Summon)creature).getLevel() < this.bHa || player.getLevel() > this.bHb)) {
                Player player2 = player.getPlayer();
                player.unSummon();
                if (player2 != null) {
                    player2.sendMessage(new CustomMessage("scripts.zones.epic.banishSummonMsg", player2, new Object[0]));
                }
            }
        }

        public void onZoneLeave(Zone zone, Creature creature) {
        }
    }
}
