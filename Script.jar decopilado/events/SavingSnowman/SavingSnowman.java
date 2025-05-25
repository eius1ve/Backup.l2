/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.data.xml.holder.MultiSellHolder
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.data.xml.holder.ZoneHolder
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.idfactory.IdFactory
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.Territory
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.CharMoveToLocation
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.RadarControl
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.ZoneTemplate
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.SavingSnowman;

import ai.FlyingSantaAI;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;
import l2.commons.listener.Listener;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.data.xml.holder.ZoneHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Summon;
import l2.gameserver.model.Territory;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.CharMoveToLocation;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.RadarControl;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.ZoneTemplate;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SavingSnowman
extends Functions
implements OnDeathListener,
OnPlayerEnterListener,
ScriptFile {
    private static final Logger t = LoggerFactory.getLogger(SavingSnowman.class);
    private static ScheduledFuture<?> A;
    private static ScheduledFuture<?> B;
    private static ScheduledFuture<?> C;
    private static ScheduledFuture<?> D;
    private static ScheduledFuture<?> E;
    public static SnowmanState _snowmanState;
    private static NpcInstance r;
    private static NpcInstance s;
    private static final String O = "[event_manager_saving_snowman]";
    private static final String P = "[event_tree_saving_snowman]";
    private static final File c;
    private static boolean U;
    private static boolean T;
    private static final Location[] h;
    private static final ReentrantLock a;

    public void onLoad() {
        if (SavingSnowman.isActive()) {
            T = true;
            this.spawnEventManagers();
            SavingSnowman.x();
            t.info("Loaded Event: SavingSnowman [state: activated]");
            B = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)new SaveTask(), Config.SAVING_SNOWMEN_CAPTURE_TIME, Config.SAVING_SNOWMEN_EVENT_INTERVAL);
            C = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)new SayTask(), Config.SAVING_SNOWMEN_SANTA_SAY_INTERVAL, Config.SAVING_SNOWMEN_SANTA_SAY_INTERVAL);
            E = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)new SantaTask(), Config.SAVING_SNOWMEN_ACTION_SPAWN_INTERVAL, Config.SAVING_SNOWMEN_ACTION_SPAWN_INTERVAL);
            _snowmanState = SnowmanState.SAVED;
            PlayerListenerList.addGlobal((Listener)this);
            CharListenerList.addGlobal((Listener)this);
        } else {
            t.info("Loaded Event: SavingSnowman [state: deactivated]");
        }
    }

    private static boolean isActive() {
        return SavingSnowman.IsActive((String)"SavingSnowman");
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (SavingSnowman.SetActive((String)"SavingSnowman", (boolean)true)) {
            SavingSnowman.x();
            this.spawnEventManagers();
            t.info("Event 'SavingSnowman' started.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.SavingSnowman.AnnounceEventStarted", null);
            if (B == null) {
                B = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)new SaveTask(), Config.SAVING_SNOWMEN_CAPTURE_TIME, Config.SAVING_SNOWMEN_EVENT_INTERVAL);
            }
            if (C == null) {
                C = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)new SayTask(), Config.SAVING_SNOWMEN_SANTA_SAY_INTERVAL, Config.SAVING_SNOWMEN_SANTA_SAY_INTERVAL);
            }
            if (E == null) {
                E = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)new SantaTask(), Config.SAVING_SNOWMEN_ACTION_SPAWN_INTERVAL, Config.SAVING_SNOWMEN_ACTION_SPAWN_INTERVAL);
            }
            _snowmanState = SnowmanState.SAVED;
            PlayerListenerList.addGlobal((Listener)this);
            CharListenerList.addGlobal((Listener)this);
        } else {
            player.sendMessage("Event 'SavingSnowman' already started.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (SavingSnowman.SetActive((String)"SavingSnowman", (boolean)false)) {
            this.unSpawnEventManagers();
            if (r != null) {
                r.deleteMe();
            }
            if (s != null) {
                s.deleteMe();
            }
            t.info("Event 'SavingSnowman' stopped.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.SavingSnowman.AnnounceEventStoped", null);
            if (B != null) {
                B.cancel(true);
                B = null;
            }
            if (C != null) {
                C.cancel(true);
                C = null;
            }
            if (D != null) {
                D.cancel(true);
                D = null;
            }
            if (E != null) {
                E.cancel(true);
                E = null;
            }
            _snowmanState = SnowmanState.SAVED;
            PlayerListenerList.removeGlobal((Listener)this);
            CharListenerList.removeGlobal((Listener)this);
        } else {
            player.sendMessage("Event 'SavingSnowman' not started.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    private void spawnEventManagers() {
        SpawnManager.getInstance().spawn(O);
        SpawnManager.getInstance().spawn(P);
    }

    private void unSpawnEventManagers() {
        SpawnManager.getInstance().despawn(O);
        SpawnManager.getInstance().despawn(P);
    }

    public void onReload() {
        this.unSpawnEventManagers();
        if (B != null) {
            B.cancel(true);
        }
        if (C != null) {
            C.cancel(true);
        }
        if (E != null) {
            E.cancel(true);
        }
        _snowmanState = SnowmanState.SAVED;
    }

    public void onShutdown() {
        PlayerListenerList.removeGlobal((Listener)this);
        CharListenerList.removeGlobal((Listener)this);
        this.unSpawnEventManagers();
    }

    public static void spawnRewarder(Player player) {
        for (NpcInstance npcInstance : player.getAroundNpc(1500, 300)) {
            if (npcInstance.getNpcId() != Config.SAVING_SNOWMAN_EVENT_FLYING_SANTA_ID) continue;
            return;
        }
        Location location = Location.findPointToStay((GameObject)player, (int)300, (int)400);
        for (int i = 0; i < 20 && !GeoEngine.canSeeCoord((GameObject)player, (int)location.x, (int)location.y, (int)location.z, (boolean)false); ++i) {
            location = Location.findPointToStay((GameObject)player, (int)300, (int)400);
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(Config.SAVING_SNOWMAN_EVENT_FLYING_SANTA_ID);
        if (npcTemplate == null) {
            t.info("WARNING! events.SavingSnowman.spawnRewarder template is null for npc: " + Config.SAVING_SNOWMAN_EVENT_FLYING_SANTA_ID);
            Thread.dumpStack();
            return;
        }
        NpcInstance npcInstance = new NpcInstance(IdFactory.getInstance().getNextId(), npcTemplate);
        npcInstance.setLoc(location);
        npcInstance.setHeading((int)(Math.atan2(location.y - player.getY(), location.x - player.getX()) * 10430.378350470453) + 32768);
        npcInstance.spawnMe();
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.events.SavingSnowman.RewarderPhrase1", (Object[])new Object[0]);
        Location location2 = Location.findPointToStay((GameObject)player, (int)40, (int)50);
        npcInstance.setSpawnedLoc(location2);
        npcInstance.broadcastPacket(new L2GameServerPacket[]{new CharMoveToLocation(npcInstance.getObjectId(), npcInstance.getLoc(), location2)});
        SavingSnowman.executeTask((String)"events.SavingSnowman.SavingSnowman", (String)"reward", (Object[])new Object[]{npcInstance, player}, (long)5000L);
    }

    public static void reward(NpcInstance npcInstance, Player player) {
        if (!T || npcInstance == null || player == null) {
            return;
        }
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.events.SavingSnowman.RewarderPhrase2", (Object[])new Object[]{player.getName()});
        Functions.addItem((Playable)player, (int)Config.SAVING_SNOWMAN_SNOWMAN_GIFT_FROM_SANTA, (long)1L);
        SavingSnowman.executeTask((String)"events.SavingSnowman.SavingSnowman", (String)"removeRewarder", (Object[])new Object[]{npcInstance}, (long)5000L);
    }

    public static void removeRewarder(NpcInstance npcInstance) {
        if (!T || npcInstance == null) {
            return;
        }
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.events.SavingSnowman.RewarderPhrase3", (Object[])new Object[0]);
        Location location = npcInstance.getSpawnedLoc();
        double d = PositionUtils.convertHeadingToRadian((int)npcInstance.getHeading());
        int n = location.x - (int)(Math.sin(d) * 300.0);
        int n2 = location.y + (int)(Math.cos(d) * 300.0);
        int n3 = location.z;
        npcInstance.broadcastPacket(new L2GameServerPacket[]{new CharMoveToLocation(npcInstance.getObjectId(), location, new Location(n, n2, n3))});
        SavingSnowman.executeTask((String)"events.SavingSnowman.SavingSnowman", (String)"unspawnRewarder", (Object[])new Object[]{npcInstance}, (long)2000L);
    }

    public static void unspawnRewarder(NpcInstance npcInstance) {
        if (!T || npcInstance == null) {
            return;
        }
        npcInstance.decayMe();
        npcInstance.deleteMe();
    }

    public void buff() {
        long l;
        Player player = this.getSelf();
        if (!T || player.isActionsDisabled() || player.isSitting() || player.getLastNpc() == null || player.getLastNpc().getDistance((GameObject)player) > 300.0) {
            return;
        }
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        if (_snowmanState != SnowmanState.SAVED) {
            this.show("events/saving_snowman/13184-3.htm", player);
            return;
        }
        long l2 = System.currentTimeMillis();
        if (l2 - (l = player.getVarLong("holiday_gift", 0L)) < Config.SAVING_SNOWMAN_BUFF_DELAY) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("events/saving_snowman/13184-4.htm");
            npcHtmlMessage.replace("%buff_reuse%", String.valueOf(Config.SAVING_SNOWMAN_BUFF_DELAY / 60000L));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, Config.SAVING_SNOWMAN_BUFF_SKILL_ID, 1, 0, 0L)});
        player.altOnMagicUseTimer((Creature)player, SkillTable.getInstance().getInfo(Config.SAVING_SNOWMAN_BUFF_SKILL_ID, 1));
        player.setVar("holiday_gift", l2, -1L);
        this.show("events/saving_snowman/13184.htm", player);
        Summon summon = player.getPet();
        if (summon != null) {
            summon.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)summon, (Creature)summon, Config.SAVING_SNOWMAN_BUFF_SKILL_ID, 1, 0, 0L)});
            summon.altOnMagicUseTimer((Creature)summon, SkillTable.getInstance().getInfo(Config.SAVING_SNOWMAN_BUFF_SKILL_ID, 1));
        }
    }

    public void locateSnowman() {
        Player player = this.getSelf();
        if (!T || player.isActionsDisabled() || player.isSitting() || player.getLastNpc() == null || player.getLastNpc().getDistance((GameObject)player) > 300.0) {
            return;
        }
        if (r != null) {
            player.sendPacket(new IStaticPacket[]{new RadarControl(2, 2, r.getLoc()), new RadarControl(0, 1, r.getLoc())});
            player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_S1).addZoneName(r.getLoc())).addString(new CustomMessage("scripts.events.Snowman.HostageAtS1", player, new Object[0]).toString()));
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_TARGET_CANNOT_BE_FOUND);
        }
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

    public void captureSnowman() {
        Player player2;
        Location location = Config.SAVING_SNOWMEN_LOCATIONS.isEmpty() ? SavingSnowman.b() : (Location)Rnd.get((List)Config.SAVING_SNOWMEN_LOCATIONS);
        for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player2, "scripts.events.SavingSnowman.AnnounceSnowmanCaptured", null, ChatType.ANNOUNCEMENT);
            player2.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_S1).addZoneName(location)).addString(new CustomMessage("scripts.events.Snowman.HostageLocateS1", player2, new Object[0]).toString()));
            player2.sendPacket(new IStaticPacket[]{new RadarControl(2, 2, location), new RadarControl(0, 1, location)});
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(Config.SAVING_SNOWMAN_EVENT_SNOWMAN_ID);
        if (npcTemplate == null) {
            t.info("WARNING! events.SavingSnowman.captureSnowman template is null for npc: " + Config.SAVING_SNOWMAN_EVENT_SNOWMAN_ID);
            Thread.dumpStack();
            return;
        }
        player2 = new SimpleSpawner(npcTemplate);
        player2.setLoc(location);
        player2.setAmount(1);
        player2.setRespawnDelay(0);
        r = player2.doSpawn(true);
        if (r == null) {
            return;
        }
        npcTemplate = NpcHolder.getInstance().getTemplate(Config.SAVING_SNOWMAN_EVENT_THOMAS_ID);
        if (npcTemplate == null) {
            t.info("WARNING! events.SavingSnowman.captureSnowman template is null for npc: " + Config.SAVING_SNOWMAN_EVENT_THOMAS_ID);
            Thread.dumpStack();
            return;
        }
        Location location2 = Location.findPointToStay((int)r.getX(), (int)r.getY(), (int)r.getZ(), (int)100, (int)120, (int)r.getReflection().getGeoIndex());
        SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
        simpleSpawner.setLoc(location2);
        simpleSpawner.setAmount(1);
        simpleSpawner.setRespawnDelay(0);
        s = simpleSpawner.doSpawn(true);
        if (s == null) {
            return;
        }
        _snowmanState = SnowmanState.CAPTURED;
        if (A != null) {
            A.cancel(true);
            A = null;
        }
        A = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)new ShoutTask(), 1L, Config.SAVING_SNOWMEN_SHOUT_INTERVAL);
        if (D != null) {
            D.cancel(true);
            D = null;
        }
        D = SavingSnowman.executeTask((String)"events.SavingSnowman.SavingSnowman", (String)"eatSnowman", (Object[])new Object[0], (long)Config.SAVING_SNOWMEN_THOMAS_EAT_DELAY);
    }

    public static void eatSnowman() {
        if (r == null || s == null) {
            return;
        }
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.SavingSnowman.AnnounceSnowmanKilled", null, ChatType.ANNOUNCEMENT);
        }
        _snowmanState = SnowmanState.KILLED;
        if (A != null) {
            A.cancel(true);
            A = null;
        }
        r.deleteMe();
        s.deleteMe();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void freeSnowman(Creature creature) {
        a.lock();
        try {
            Player player2;
            if (_snowmanState != SnowmanState.CAPTURED || r == null || creature == null || !creature.isPlayable()) {
                return;
            }
            _snowmanState = SnowmanState.SAVED;
            for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                Announcements.getInstance().announceToPlayerByCustomMessage(player2, "scripts.events.SavingSnowman.AnnounceSnowmanSaved", null, ChatType.ANNOUNCEMENT);
            }
            if (A != null) {
                A.cancel(true);
                A = null;
            }
            if (D != null) {
                D.cancel(true);
                D = null;
            }
            Player player3 = creature.getPlayer();
            Functions.npcSayCustomMessage((NpcInstance)r, (String)"scripts.events.SavingSnowman.SnowmanSayTnx", (Object[])new Object[]{player3.getName()});
            player2 = player3.getParty();
            if (player3.getParty() != null) {
                for (Player player4 : player2) {
                    if (player4 == null || player4.isDead() || player3.getDistance3D((GameObject)player4) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
                    SavingSnowman.addItem((Playable)player4, (int)Config.SAVING_SNOWMAN_SNOWMAN_LITTLE_GIFT, (long)1L);
                }
            } else {
                SavingSnowman.addItem((Playable)player3, (int)Config.SAVING_SNOWMAN_SNOWMAN_LITTLE_GIFT, (long)1L);
            }
            if (creature.isPlayer()) {
                SavingSnowman.spawnRewarder(creature.getPlayer());
            }
            r.decayMe();
            r.deleteMe();
        }
        finally {
            a.unlock();
        }
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (T && creature2 != null && SavingSnowman.simpleCheckDrop((Creature)creature, (Creature)creature2) && Rnd.chance((double)Config.SAVING_SNOWMAN_EVENT_DROP_CHANCE)) {
            int n = Rnd.get((int)Config.SAVING_SNOWMAN_EVENT_DROP_ID.length);
            ((NpcInstance)creature).dropItem(creature2.getPlayer(), Config.SAVING_SNOWMAN_EVENT_DROP_ID[n], (long)Config.SAVING_SNOWMAN_EVENT_DROP_COUNT[n]);
        }
    }

    public void onPlayerEnter(Player player) {
        if (T) {
            Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.SavingSnowman.AnnounceEventStarted", null);
        }
    }

    private static void x() {
        if (U) {
            return;
        }
        MultiSellHolder.getInstance().parseFile(c);
        U = true;
    }

    static {
        c = new File(Config.DATAPACK_ROOT, "data/html-en/events/saving_snowman/13184.xml");
        U = false;
        T = false;
        h = new Location[]{new Location(82632, 148712, -3472), new Location(147448, 28552, -2272), new Location(145873, -54756, -2807), new Location(16018, 142978, -2696)};
        a = new ReentrantLock();
    }

    public class SaveTask
    implements Runnable {
        @Override
        public void run() {
            if (!T || _snowmanState == SnowmanState.CAPTURED) {
                return;
            }
            SavingSnowman.this.captureSnowman();
        }
    }

    public class SayTask
    implements Runnable {
        @Override
        public void run() {
            if (!T) {
                return;
            }
            Functions.npcSayCustomMessage((NpcInstance)GameObjectsStorage.getByNpcId((int)Config.SAVING_SNOWMAN_EVENT_MANAGER_ID), (String)"scripts.events.SavingSnowman.SantaSay", (Object[])new Object[0]);
        }
    }

    public class SantaTask
    implements Runnable {
        @Override
        public void run() {
            if (!T) {
                return;
            }
            for (Location location : h) {
                NpcInstance npcInstance = NpcHolder.getInstance().getTemplate(Config.SAVING_SNOWMAN_EVENT_FLYING_SANTA_ID).getNewInstance();
                npcInstance.setAI((CharacterAI)new FlyingSantaAI(npcInstance));
                npcInstance.spawnMe(location);
                npcInstance.setSpawnedLoc(location);
            }
        }
    }

    public static final class SnowmanState
    extends Enum<SnowmanState> {
        public static final /* enum */ SnowmanState CAPTURED = new SnowmanState();
        public static final /* enum */ SnowmanState KILLED = new SnowmanState();
        public static final /* enum */ SnowmanState SAVED = new SnowmanState();
        private static final /* synthetic */ SnowmanState[] a;

        public static SnowmanState[] values() {
            return (SnowmanState[])a.clone();
        }

        public static SnowmanState valueOf(String string) {
            return Enum.valueOf(SnowmanState.class, string);
        }

        private static /* synthetic */ SnowmanState[] a() {
            return new SnowmanState[]{CAPTURED, KILLED, SAVED};
        }

        static {
            a = SnowmanState.a();
        }
    }

    public class ShoutTask
    implements Runnable {
        @Override
        public void run() {
            if (!T || r == null || _snowmanState != SnowmanState.CAPTURED) {
                return;
            }
            Functions.npcShoutCustomMessage((NpcInstance)r, (String)"scripts.events.SavingSnowman.SnowmanShout", (Object[])new Object[0]);
        }
    }
}
