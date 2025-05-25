/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.Zone
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import gnu.trove.TIntHashSet;
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
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassIdLimitZone
implements ScriptFile {
    private static final Logger ez = LoggerFactory.getLogger(ClassIdLimitZone.class);
    private static final String iF = "playerClassIdsLimit";
    private static final String iG = "playerClassIdsLimitBackLoc";

    private static void init() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            boolean bl = zone.getParams().isSet((Object)iF);
            boolean bl2 = zone.getParams().isSet((Object)iG);
            if (!bl && !bl2) continue;
            if (!bl) {
                ez.warn("Class ids not set for zone " + zone.toString());
                continue;
            }
            if (!bl2) {
                ez.warn("Back location not set for \u0441lassId limit zone " + zone);
                continue;
            }
            int[] nArray = zone.getParams().getIntegerArray((Object)iF, ArrayUtils.EMPTY_INT_ARRAY);
            Location location = Location.parseLoc((String)zone.getParams().getString((Object)iG));
            zone.addListener((Listener)new ClassIdLimitZoneListener(nArray, location));
            ++n;
        }
        ez.info("ClassIdLimitZone: Loaded " + n + " player class id limit zone(s).");
    }

    public void onLoad() {
        ClassIdLimitZone.init();
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private static class ClassIdLimitZoneListener
    implements OnZoneEnterLeaveListener {
        private final TIntHashSet i;
        private final Location an;

        private ClassIdLimitZoneListener(int[] nArray, Location location) {
            this.i = new TIntHashSet(nArray);
            this.an = location;
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            if (creature != null && creature.isPlayer()) {
                final Player player = creature.getPlayer();
                if (player.isGM()) {
                    return;
                }
                if (this.i.contains(player.getActiveClassId())) {
                    ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

                        public void runImpl() throws Exception {
                            player.sendMessage(new CustomMessage("scripts.zones.epic.banishClassMsg", player, new Object[0]));
                            player.teleToLocation(an);
                            String string = new CustomMessage("zone.services.ClassIdLimitZone.ClassLimit", player, new Object[0]).toString();
                            player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                            player.sendMessage(string);
                            Summon summon = player.getPet();
                            if (summon != null) {
                                summon.teleToLocation(an);
                            }
                        }
                    });
                }
            }
        }

        public void onZoneLeave(Zone zone, Creature creature) {
        }
    }
}
