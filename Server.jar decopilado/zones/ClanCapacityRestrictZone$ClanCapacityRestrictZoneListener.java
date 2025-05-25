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
 *  l2.gameserver.model.pledge.Alliance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.utils.Location
 */
package zones;

import java.util.HashSet;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.utils.Location;

private static class ClanCapacityRestrictZone.ClanCapacityRestrictZoneListener
implements OnZoneEnterLeaveListener {
    private final int bGV;
    private final int bGW;
    private final int bGX;
    private final int bGY;
    private final int bGZ;
    private final Location al;
    private Zone a;

    private ClanCapacityRestrictZone.ClanCapacityRestrictZoneListener(int n, int n2, int n3, int n4, int n5, Location location) {
        this.bGV = n;
        this.bGW = n2;
        this.bGX = n3;
        this.bGZ = n5;
        this.bGY = n4;
        this.al = location;
    }

    public ClanCapacityRestrictZone.ClanCapacityRestrictZoneListener addTo(Zone zone) {
        zone.addListener((Listener)this);
        this.a = zone;
        return this;
    }

    public boolean remove() {
        return this.a.removeListener((Listener)this);
    }

    private void aB(Player player) {
        HardReference hardReference = player.getPlayer().getRef();
        ThreadPoolManager.getInstance().execute(() -> {
            Player player = (Player)hardReference.get();
            if (player == null) {
                return;
            }
            if (this.al == null) {
                player.teleToClosestTown();
            } else {
                player.teleToLocation(this.al);
            }
        });
    }

    private int a(Zone zone) {
        HashSet<Clan> hashSet = new HashSet<Clan>();
        for (Player player : zone.getInsidePlayers()) {
            Clan clan = player.getClan();
            if (clan == null) continue;
            hashSet.add(clan);
        }
        return hashSet.size();
    }

    private int b(Zone zone) {
        int n = 0;
        for (Player player : zone.getInsidePlayers()) {
            Clan clan = player.getClan();
            if (clan != null) continue;
            ++n;
        }
        return n;
    }

    private int a(Zone zone, Clan clan) {
        int n = 0;
        if (clan != null) {
            for (Player player : zone.getInsidePlayers()) {
                Clan clan2 = player.getClan();
                if (clan2 == null || clan != clan2) continue;
                ++n;
            }
        }
        return n;
    }

    private int a(Zone zone, Alliance alliance) {
        int n = 0;
        if (alliance != null) {
            for (Player player : zone.getInsidePlayers()) {
                Clan clan = player.getClan();
                if (clan == null || clan.getAlliance() != alliance) continue;
                ++n;
            }
        }
        return n;
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        if (!creature.isPlayer() || creature.getPlayer().isGM()) {
            return;
        }
        Player player = creature.getPlayer();
        Clan clan = player.getClan();
        if (clan == null && this.bGY > -1 && this.b(zone) >= this.bGY) {
            String string = new CustomMessage("zone.services.ClanCapacityRestrictZone.NoClan", player, new Object[0]).addNumber((long)this.bGY).toString();
            player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
            player.sendMessage(string);
            this.aB(player);
            return;
        }
        if (clan != null) {
            String string;
            if (this.bGZ > 0 && clan.getLevel() < this.bGZ) {
                string = new CustomMessage("zone.services.ClanCapacityRestrictZone.ClanLevelLimit", player, new Object[0]).addNumber((long)this.bGZ).toString();
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                player.sendMessage(string);
                this.aB(player);
            }
            if (clan.getAlliance() != null && (this.a(zone) > this.bGV || this.a(zone, clan) > this.bGW || this.a(zone, clan.getAlliance()) > this.bGX)) {
                string = new CustomMessage("zone.services.ClanCapacityRestrictZone.CountLimit", player, new Object[0]).toString();
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                player.sendMessage(string);
                this.aB(player);
            }
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
    }
}
