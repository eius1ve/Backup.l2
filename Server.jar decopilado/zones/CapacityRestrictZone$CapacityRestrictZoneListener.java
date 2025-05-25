/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.utils.Location
 */
package zones;

import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.utils.Location;

private static class CapacityRestrictZone.CapacityRestrictZoneListener
implements OnZoneEnterLeaveListener {
    private final AtomicInteger v;
    private final int bGU;
    private final Location ak;
    private Zone a;

    private CapacityRestrictZone.CapacityRestrictZoneListener(int n, int n2, Location location) {
        this.v = new AtomicInteger(n);
        this.bGU = n2;
        this.ak = location;
    }

    public CapacityRestrictZone.CapacityRestrictZoneListener(int n, Location location) {
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
