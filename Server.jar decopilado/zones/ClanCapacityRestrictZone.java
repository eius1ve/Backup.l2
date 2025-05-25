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
 *  l2.gameserver.model.pledge.Alliance
 *  l2.gameserver.model.pledge.Clan
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
import java.util.HashSet;
import java.util.List;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClanCapacityRestrictZone
extends Functions
implements ScriptFile {
    private static final Logger ex = LoggerFactory.getLogger(ClanCapacityRestrictZone.class);
    private static final ClanCapacityRestrictZone a = new ClanCapacityRestrictZone();
    private static final String ix = "restrictInsideClanCount";
    private static final String iy = "restrictInsidePerClanPlayerCount";
    private static final String iz = "restrictInsidePerAlliancePlayerCount";
    private static final String iA = "restrictClanCapacityBackLoc";
    private static final String iB = "restrictClanLevelMin";
    private static final String iC = "restrictInsideNonClanCount";
    private volatile boolean hC = false;
    private final List<ClanCapacityRestrictZoneListener> ec = new ArrayList<ClanCapacityRestrictZoneListener>();

    public void init() {
        if (!this.hC) {
            for (Zone zone : ReflectionManager.DEFAULT.getZones()) {
                boolean bl = zone.getParams().isSet((Object)ix);
                boolean bl2 = zone.getParams().isSet((Object)iy);
                boolean bl3 = zone.getParams().isSet((Object)iz);
                if (!bl && !bl2 && !bl3) continue;
                int n = zone.getParams().getInteger((Object)ix, Integer.MAX_VALUE);
                int n2 = zone.getParams().getInteger((Object)iy, Integer.MAX_VALUE);
                int n3 = zone.getParams().getInteger((Object)iz, Integer.MAX_VALUE);
                int n4 = zone.getParams().getInteger((Object)iB, 0);
                Location location = zone.getParams().isSet((Object)iA) ? Location.parseLoc((String)zone.getParams().getString((Object)iA)) : null;
                int n5 = zone.getParams().getInteger((Object)iC, 0);
                if (location == null) {
                    ex.warn("Capacity Clan restricted zone {} have no back loc. Will use closest town.", (Object)zone);
                }
                this.ec.add(new ClanCapacityRestrictZoneListener(n, n2, n3, n5, n4, location).addTo(zone));
            }
            this.hC = true;
            ex.info("Player limit zone : Loaded {} capacity restricted zones.", (Object)this.ec.size());
        }
    }

    private void done() throws Exception {
        if (this.hC) {
            for (ClanCapacityRestrictZoneListener clanCapacityRestrictZoneListener : this.ec) {
                clanCapacityRestrictZoneListener.remove();
            }
            this.ec.clear();
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

    private static class ClanCapacityRestrictZoneListener
    implements OnZoneEnterLeaveListener {
        private final int bGV;
        private final int bGW;
        private final int bGX;
        private final int bGY;
        private final int bGZ;
        private final Location al;
        private Zone a;

        private ClanCapacityRestrictZoneListener(int n, int n2, int n3, int n4, int n5, Location location) {
            this.bGV = n;
            this.bGW = n2;
            this.bGX = n3;
            this.bGZ = n5;
            this.bGY = n4;
            this.al = location;
        }

        public ClanCapacityRestrictZoneListener addTo(Zone zone) {
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
}
