/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CapacityRestrictZone
extends Functions
implements ScriptFile {
    private static final Logger ev = LoggerFactory.getLogger(CapacityRestrictZone.class);
    private static final CapacityRestrictZone a = new CapacityRestrictZone();
    private static final String it = "restrictPlayerCapacityValue";
    private static final String iu = "restrictPlayerCapacityBackLoc";
    private volatile boolean hC = false;
    private List<CapacityRestrictZoneListener> eb = new ArrayList<CapacityRestrictZoneListener>();

    private void init() throws Exception {
        if (!this.hC) {
            for (Zone zone : ReflectionManager.DEFAULT.getZones()) {
                Location location;
                boolean bl = zone.getParams().isSet((Object)it);
                if (!bl) continue;
                int n = zone.getParams().getInteger((Object)it);
                if (n == 0) {
                    ev.warn("Capacity restricted for zone {} is 0.", (Object)zone);
                    continue;
                }
                Location location2 = location = zone.getParams().isSet((Object)iu) ? Location.parseLoc((String)zone.getParams().getString((Object)iu)) : null;
                if (location == null) {
                    ev.warn("Capacity restricted zone {} have no back loc. Will use closest town.", (Object)zone);
                }
                CapacityRestrictZoneListener capacityRestrictZoneListener = new CapacityRestrictZoneListener(n, location);
                capacityRestrictZoneListener.addTo(zone);
                this.eb.add(capacityRestrictZoneListener);
            }
            this.hC = true;
            ev.info("Player limit zone : Loaded {} capacity restricted zones.", (Object)this.eb.size());
        }
    }

    private void done() throws Exception {
        if (this.hC) {
            for (CapacityRestrictZoneListener capacityRestrictZoneListener : this.eb) {
                capacityRestrictZoneListener.remove();
            }
            this.eb.clear();
            this.hC = false;
        }
    }

    public void onLoad() {
        try {
            a.init();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        try {
            a.done();
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static class CapacityRestrictZoneListener
    implements OnZoneEnterLeaveListener {
        private final AtomicInteger v;
        private final int bGU;
        private final Location ak;
        private Zone a;

        private CapacityRestrictZoneListener(int n, int n2, Location location) {
            this.v = new AtomicInteger(n);
            this.bGU = n2;
            this.ak = location;
        }

        public CapacityRestrictZoneListener(int n, Location location) {
            this(0, n, location);
        }

        public void addTo(Zone zone) {
            this.v.set(zone.getInsidePlayers().size());
            zone.addListener((Listener)this);
            this.a = zone;
        }

        public boolean remove() {
            return this.a.removeListener((Listener)this);
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            if (!creature.isPlayer() || creature.getPlayer().isGM()) {
                return;
            }
            HardReference hardReference = creature.getPlayer().getRef();
            if (this.v.incrementAndGet() > this.bGU) {
                ThreadPoolManager.getInstance().execute(() -> {
                    Player player = (Player)hardReference.get();
                    if (player == null) {
                        return;
                    }
                    player.sendMessage(new CustomMessage("zone.playerslimit", player, new Object[0]).addNumber((long)this.bGU));
                    if (this.ak == null) {
                        String string = new CustomMessage("zone.services.CapacityRestrictZone.CountLimit", player, new Object[0]).toString();
                        player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                        player.sendMessage(string);
                        player.teleToClosestTown();
                    } else {
                        String string = new CustomMessage("zone.services.CapacityRestrictZone.CountLimit", player, new Object[0]).toString();
                        player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                        player.sendMessage(string);
                        player.teleToLocation(this.ak);
                    }
                });
            }
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            if (!creature.isPlayer() || creature.getPlayer().isGM()) {
                return;
            }
            this.v.decrementAndGet();
        }
    }
}
