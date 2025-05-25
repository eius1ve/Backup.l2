/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.data.xml.holder.ZoneHolder
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.idfactory.IdFactory
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.Territory
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.CharMoveToLocation
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.RadarControl
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.ZoneTemplate
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.Finder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.data.xml.holder.ZoneHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Territory;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.CharMoveToLocation;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.RadarControl;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.ZoneTemplate;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Finder
extends Functions
implements OnDeathListener,
ScriptFile {
    private static final Logger n = LoggerFactory.getLogger(Finder.class);
    private static final String D = "Finder";
    private static boolean T = false;
    private static final long Y = 60000L;
    private static final int bL = 40017;
    private static final int bM = 40016;
    private static final int bN = 40019;
    private static final String E = "[event_finder_list]";
    private static NpcInstance o;
    private static NpcInstance p;
    private static Location l;
    private static ScheduledFuture<?> s;
    private static ScheduledFuture<?> t;
    private static Map<String, ScheduledFuture<?>> h;

    private static boolean isActive() {
        return Finder.IsActive((String)D);
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (Finder.SetActive((String)D, (boolean)true)) {
            this.activate();
        } else {
            player.sendMessage("Event 'Finder' already active.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (Finder.SetActive((String)D, (boolean)false)) {
            this.z();
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.Finder.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'Finder' already diactivated.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    private void activate() {
        SpawnManager.getInstance().spawn(E);
        if (h != null) {
            for (ScheduledFuture<?> scheduledFuture : h.values()) {
                scheduledFuture.cancel(true);
            }
            h = null;
        }
        h = Finder.ScheduleTimeStarts((Runnable)new EventTask(), (String[])Config.EVENT_FinderHostageStartTime);
        n.info("Event 'Finder' started on " + Arrays.toString(h.keySet().toArray(new String[h.keySet().size()])));
    }

    private void z() {
        if (h != null) {
            for (ScheduledFuture<?> scheduledFuture : h.values()) {
                scheduledFuture.cancel(true);
            }
            h = null;
        }
        SpawnManager.getInstance().despawn(E);
    }

    private static Location b() {
        ArrayList<ZoneTemplate> arrayList = new ArrayList<ZoneTemplate>();
        for (ZoneTemplate zoneTemplate : ZoneHolder.getInstance().getZones().values()) {
            if (zoneTemplate == null || !zoneTemplate.isDefault()) continue;
            arrayList.add(zoneTemplate);
        }
        Territory territory = ((ZoneTemplate)arrayList.get(Rnd.get((int)arrayList.size()))).getTerritory();
        return territory.getRandomLoc(0);
    }

    public void spawnHostageAndRaider() {
        if (!T) {
            return;
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(40017);
        if (npcTemplate == null) {
            n.info("WARNING! events.Finder.Finder.spawnHostageAndRaider template is null for npc: 40017");
            Thread.dumpStack();
            return;
        }
        SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
        simpleSpawner.setLoc(Config.EVENT_FINDER_LOCATIONS.isEmpty() ? Finder.b() : (Location)Rnd.get((List)Config.EVENT_FINDER_LOCATIONS));
        simpleSpawner.setAmount(1);
        simpleSpawner.setRespawnDelay(0);
        simpleSpawner.stopRespawn();
        o = simpleSpawner.doSpawn(true);
        if (o == null) {
            return;
        }
        npcTemplate = NpcHolder.getInstance().getTemplate(40016);
        if (npcTemplate == null) {
            n.info("WARNING! events.Finder.Finder.spawnHostageAndRaider template is null for npc: 40016");
            Thread.dumpStack();
            return;
        }
        Location location = Location.findPointToStay((int)o.getX(), (int)o.getY(), (int)o.getZ(), (int)100, (int)120, (int)o.getReflection().getGeoIndex());
        SimpleSpawner simpleSpawner2 = new SimpleSpawner(npcTemplate);
        simpleSpawner2.setLoc(location);
        simpleSpawner2.setAmount(1);
        simpleSpawner2.setRespawnDelay(0);
        simpleSpawner2.stopRespawn();
        p = simpleSpawner2.doSpawn(true);
        p.addListener((Listener)this);
        if (s != null) {
            s.cancel(true);
            s = null;
        }
        s = ThreadPoolManager.getInstance().schedule((Runnable)new HostageKilledTask(), Config.EVENT_FINDER_CAPTURE_TIME);
        if (t != null) {
            t.cancel(true);
            t = null;
        }
        t = ThreadPoolManager.getInstance().schedule((Runnable)new ShoutTask(), 60000L);
        l = o.getLoc();
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.Finder.AnnounceHostageTaken", null, ChatType.ANNOUNCEMENT);
        }
    }

    private static void A() {
        if (o != null && o.getSpawn() != null) {
            o.deleteMe();
        }
        if (p != null && p.getSpawn() != null) {
            p.deleteMe();
        }
        o = null;
        p = null;
        l = null;
        if (t != null) {
            t.cancel(true);
            t = null;
        }
    }

    public void GetPoint() {
        Player player = this.getSelf();
        if (!T || player.isActionsDisabled() || player.isSitting() || player.getLastNpc() == null || player.getLastNpc().getDistance((GameObject)player) > 300.0) {
            return;
        }
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (l != null) {
            player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_S1).addZoneName(l)).addString(new CustomMessage("scripts.events.Finder.HostageAtS1", player, new Object[0]).toString()));
            player.sendPacket(new IStaticPacket[]{new RadarControl(2, 2, l), new RadarControl(0, 1, l)});
        } else {
            player.sendMessage(new CustomMessage("scripts.events.Finder.NoHostage", player, new Object[0]));
        }
    }

    public void onLoad() {
        if (Finder.isActive()) {
            T = true;
            this.activate();
        } else {
            n.info("Loaded Event: 'Finder' [state: deactivated]");
        }
    }

    public void onReload() {
        if (T) {
            this.z();
            this.activate();
        }
    }

    public void onShutdown() {
        this.z();
    }

    public static void spawnRewarder(Player player) {
        for (NpcInstance npcInstance : player.getAroundNpc(1500, 300)) {
            if (npcInstance.getNpcId() != 40019) continue;
            return;
        }
        Location location = Location.findPointToStay((GameObject)player, (int)300, (int)400);
        for (int i = 0; i < 20 && !GeoEngine.canSeeCoord((GameObject)player, (int)location.x, (int)location.y, (int)location.z, (boolean)false); ++i) {
            location = Location.findPointToStay((GameObject)player, (int)300, (int)400);
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(40019);
        if (npcTemplate == null) {
            n.info("WARNING! events.SavingSnowman.spawnRewarder template is null for npc: 40019");
            Thread.dumpStack();
            return;
        }
        NpcInstance npcInstance = new NpcInstance(IdFactory.getInstance().getNextId(), npcTemplate);
        npcInstance.setLoc(location);
        npcInstance.setHeading((int)(Math.atan2(location.y - player.getY(), location.x - player.getX()) * 10430.378350470453) + 32768);
        npcInstance.spawnMe();
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.events.Finder.RewarderPhrase1", (Object[])new Object[0]);
        Location location2 = Location.findFrontPosition((GameObject)player, (GameObject)player, (int)40, (int)50);
        npcInstance.setSpawnedLoc(location2);
        npcInstance.broadcastPacket(new L2GameServerPacket[]{new CharMoveToLocation(npcInstance.getObjectId(), npcInstance.getLoc(), location2)});
        Finder.executeTask((String)"events.Finder.Finder", (String)"reward", (Object[])new Object[]{npcInstance, player}, (long)5000L);
    }

    public static void reward(NpcInstance npcInstance, Player player) {
        if (!T || npcInstance == null || player == null) {
            return;
        }
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.events.Finder.RewarderPhrase2", (Object[])new Object[]{player.getName()});
        Functions.addItem((Playable)player, (int)Config.EVENT_FINDER_REWARD_ID, (long)Config.EVENT_FINDER_ITEM_COUNT);
        Finder.executeTask((String)"events.Finder.Finder", (String)"removeRewarder", (Object[])new Object[]{npcInstance}, (long)5000L);
    }

    public static void removeRewarder(NpcInstance npcInstance) {
        if (!T || npcInstance == null) {
            return;
        }
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.events.Finder.RewarderPhrase3", (Object[])new Object[0]);
        Location location = npcInstance.getSpawnedLoc();
        double d = PositionUtils.convertHeadingToRadian((int)npcInstance.getHeading());
        int n = location.x - (int)(Math.sin(d) * 300.0);
        int n2 = location.y + (int)(Math.cos(d) * 300.0);
        int n3 = location.z;
        npcInstance.broadcastPacket(new L2GameServerPacket[]{new CharMoveToLocation(npcInstance.getObjectId(), location, new Location(n, n2, n3))});
        Finder.executeTask((String)"events.Finder.Finder", (String)"unspawnRewarder", (Object[])new Object[]{npcInstance}, (long)2000L);
    }

    public static void unspawnRewarder(NpcInstance npcInstance) {
        if (!T || npcInstance == null) {
            return;
        }
        npcInstance.decayMe();
        npcInstance.deleteMe();
        Finder.A();
    }

    public static void OnDie(Creature creature, Creature creature2) {
        if (!T) {
            return;
        }
        if (creature2.isPlayer()) {
            n.info("killed 0 " + creature.getName());
        }
        if (creature.isNpc() && creature == p && creature2.isPlayer()) {
            n.info("killed 1 " + creature.getName());
            Player player = creature2.getPlayer();
            if (s != null) {
                s.cancel(true);
                s = null;
            }
            if (t != null) {
                t.cancel(true);
                t = null;
            }
            Finder.spawnRewarder(player);
            n.info("killed 2 " + creature.getName());
            for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                Announcements.getInstance().announceToPlayerByCustomMessage(player2, "scripts.events.Finder.AnnounceHostageSavedByS1", new String[]{player.getName()}, ChatType.ANNOUNCEMENT);
            }
            for (Player player2 : player.getAroundNpc(1500, 300)) {
                if (player2.getNpcId() != 40017) continue;
                n.info("killed 3 " + creature.getName());
                Functions.npcSayCustomMessage((NpcInstance)player2, (String)"scripts.events.Finder.HostageThx", (Object[])new Object[0]);
                return;
            }
        }
    }

    public void onDeath(Creature creature, Creature creature2) {
        creature.removeListener((Listener)this);
        if (!T || creature2 == null) {
            return;
        }
        if (creature2.isPlayer()) {
            n.info("killed 0 " + creature.getName());
        }
        if (creature.isNpc() && creature == p && creature2.isPlayer()) {
            n.info("killed 1 " + creature.getName());
            Player player = creature2.getPlayer();
            if (s != null) {
                s.cancel(true);
                s = null;
            }
            if (t != null) {
                t.cancel(true);
                t = null;
            }
            Finder.spawnRewarder(player);
            n.info("killed 2 " + creature.getName());
            for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                Announcements.getInstance().announceToPlayerByCustomMessage(player2, "scripts.events.Finder.AnnounceHostageSavedByS1", new String[]{player.getName()}, ChatType.ANNOUNCEMENT);
            }
            for (Player player2 : player.getAroundNpc(1500, 300)) {
                if (player2.getNpcId() != 40017) continue;
                n.info("killed 3 " + creature.getName());
                Functions.npcSayCustomMessage((NpcInstance)player2, (String)"scripts.events.Finder.HostageThx", (Object[])new Object[0]);
                return;
            }
        }
    }

    private class EventTask
    implements Runnable {
        private EventTask() {
        }

        @Override
        public void run() {
            Finder.this.spawnHostageAndRaider();
        }
    }

    private class HostageKilledTask
    implements Runnable {
        private HostageKilledTask() {
        }

        @Override
        public void run() {
            if (!T || o == null || p == null) {
                return;
            }
            Finder.A();
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.Finder.AnnounceHostageKilled", null, ChatType.ANNOUNCEMENT);
            }
        }
    }

    public class ShoutTask
    implements Runnable {
        @Override
        public void run() {
            if (!T || o == null || l == null) {
                return;
            }
            Functions.npcShoutCustomMessage((NpcInstance)o, (String)"scripts.events.Finder.HostageShout", (Object[])new Object[0]);
        }
    }
}
