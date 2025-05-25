/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.geometry.Polygon
 *  l2.commons.geometry.Shape
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.instancemanager.ServerVariables
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerExitListener
 *  l2.gameserver.listener.actor.player.OnTeleportListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Territory
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.Revive
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 *  l2.gameserver.utils.ReflectionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.TvT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import l2.commons.geometry.Polygon;
import l2.commons.geometry.Shape;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerExitListener;
import l2.gameserver.listener.actor.player.OnTeleportListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Territory;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.Revive;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import l2.gameserver.utils.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TvT
extends Functions
implements OnDeathListener,
OnPlayerExitListener,
OnTeleportListener,
ScriptFile {
    private static final Logger x = LoggerFactory.getLogger(TvT.class);
    private static final String T = "TvT";
    private static ScheduledFuture<?> G;
    private static List<Long> H;
    private static List<Long> I;
    private static List<Long> J;
    private static List<Long> K;
    private static boolean V;
    private static int _status;
    private static int cl;
    private static int cm;
    private static int b;
    private static int c;
    private static int cn;
    private static ScheduledFuture<?> H;
    private static Zone _zone;
    private static ZoneListener a;
    private static Territory f;
    private static Territory g;
    private static boolean T;

    public void onLoad() {
        if (TvT.isActive()) {
            T = true;
            CharListenerList.addGlobal((Listener)this);
            _zone.addListener((Listener)a);
            G = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)((Object)new StartTask()), 3600000L, 3600000L);
            T = ServerVariables.getString((String)T, (String)"off").equalsIgnoreCase("on");
            x.info("Loaded Event: TvT [state: active]");
        } else {
            T = false;
            x.info("Loaded Event: TvT [state: deactivated]");
        }
    }

    public void onReload() {
        _zone.removeListener((Listener)a);
        if (G != null) {
            G.cancel(false);
            G = null;
        }
    }

    public void onShutdown() {
        this.onReload();
    }

    private static boolean isActive() {
        return TvT.IsActive((String)T);
    }

    public void activateEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (!TvT.isActive()) {
            if (G == null) {
                G = ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)((Object)new StartTask()), 3600000L, 3600000L);
            }
            ServerVariables.set((String)T, (String)"on");
            x.info("Event 'TvT' activated.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.TvT.AnnounceEventStarted", null);
        } else {
            player.sendMessage("Event 'TvT' already active.");
        }
        T = true;
        this.show("admin/events/events.htm", player);
    }

    public void deactivateEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (TvT.isActive()) {
            if (G != null) {
                G.cancel(false);
                G = null;
            }
            ServerVariables.unset((String)T);
            x.info("Event 'TvT' deactivated.");
            Announcements.getInstance().announceByCustomMessage("scripts.events.TvT.AnnounceEventStoped", null);
        } else {
            player.sendMessage("Event 'TvT' not active.");
        }
        T = false;
        this.show("admin/events/events.htm", player);
    }

    public static boolean isRunned() {
        return V || _status > 0;
    }

    public static int getMinLevelForCategory(int n) {
        switch (n) {
            case 1: {
                return 20;
            }
            case 2: {
                return 30;
            }
            case 3: {
                return 40;
            }
            case 4: {
                return 52;
            }
            case 5: {
                return 62;
            }
            case 6: {
                return 76;
            }
        }
        return 0;
    }

    public static int getMaxLevelForCategory(int n) {
        switch (n) {
            case 1: {
                return 29;
            }
            case 2: {
                return 39;
            }
            case 3: {
                return 51;
            }
            case 4: {
                return 61;
            }
            case 5: {
                return 75;
            }
            case 6: {
                return 85;
            }
        }
        return 0;
    }

    public static int getCategory(int n) {
        if (n >= 20 && n <= 29) {
            return 1;
        }
        if (n >= 30 && n <= 39) {
            return 2;
        }
        if (n >= 40 && n <= 51) {
            return 3;
        }
        if (n >= 52 && n <= 61) {
            return 4;
        }
        if (n >= 62 && n <= 75) {
            return 5;
        }
        if (n >= 76) {
            return 6;
        }
        return 0;
    }

    public void start(String[] stringArray) {
        Integer n;
        Integer n2;
        Player player = this.getSelf();
        if (stringArray.length != 2) {
            TvT.show((CustomMessage)new CustomMessage("common.Error", player, new Object[0]), (Player)player);
            return;
        }
        try {
            n2 = Integer.valueOf(stringArray[0]);
            n = Integer.valueOf(stringArray[1]);
        }
        catch (Exception exception) {
            TvT.show((CustomMessage)new CustomMessage("common.Error", player, new Object[0]), (Player)player);
            return;
        }
        cm = n2;
        cn = n;
        if (cm == -1) {
            b = 1;
            c = 85;
        } else {
            b = TvT.getMinLevelForCategory(cm);
            c = TvT.getMaxLevelForCategory(cm);
        }
        if (H != null) {
            TvT.show((CustomMessage)new CustomMessage("common.TryLater", player, new Object[0]), (Player)player);
            return;
        }
        _status = 0;
        V = true;
        cl = Config.EVENT_TvTTime;
        H = new CopyOnWriteArrayList<Long>();
        I = new CopyOnWriteArrayList<Long>();
        J = new CopyOnWriteArrayList<Long>();
        K = new CopyOnWriteArrayList<Long>();
        String[] stringArray2 = new String[]{String.valueOf(cl), String.valueOf(b), String.valueOf(c)};
        TvT.sayToAll("scripts.events.TvT.AnnouncePreStart", stringArray2);
        TvT.executeTask((String)"events.TvT.TvT", (String)"question", (Object[])new Object[0], (long)10000L);
        TvT.executeTask((String)"events.TvT.TvT", (String)"announce", (Object[])new Object[0], (long)60000L);
    }

    public static void sayToAll(String string, String[] stringArray) {
        Announcements.getInstance().announceByCustomMessage(string, stringArray, ChatType.CRITICAL_ANNOUNCE);
    }

    public static void question() {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null || player.isDead() || player.getLevel() < b || player.getLevel() > c || !player.getReflection().isDefault() || player.isOlyParticipant() || player.isInObserverMode()) continue;
            player.scriptRequest(new CustomMessage("scripts.events.TvT.AskPlayer", player, new Object[0]).toString(), "events.TvT.TvT:addPlayer", new Object[0]);
        }
    }

    public static void announce() {
        if (H.isEmpty() || I.isEmpty()) {
            TvT.sayToAll("scripts.events.TvT.AnnounceEventCancelled", null);
            V = false;
            _status = 0;
            TvT.executeTask((String)"events.TvT.TvT", (String)"autoContinue", (Object[])new Object[0], (long)10000L);
            return;
        }
        if (cl > 1) {
            String[] stringArray = new String[]{String.valueOf(--cl), String.valueOf(b), String.valueOf(c)};
            TvT.sayToAll("scripts.events.TvT.AnnouncePreStart", stringArray);
            TvT.executeTask((String)"events.TvT.TvT", (String)"announce", (Object[])new Object[0], (long)60000L);
        } else {
            _status = 1;
            V = false;
            TvT.sayToAll("scripts.events.TvT.AnnounceEventStarting", null);
            TvT.executeTask((String)"events.TvT.TvT", (String)"prepare", (Object[])new Object[0], (long)5000L);
        }
    }

    public void addPlayer() {
        int n;
        Player player = this.getSelf();
        if (player == null || !TvT.checkPlayer(player, true)) {
            return;
        }
        int n2 = 0;
        int n3 = H.size();
        n2 = n3 > (n = I.size()) ? 2 : (n3 < n ? 1 : Rnd.get((int)1, (int)2));
        if (n2 == 1) {
            H.add(player.getStoredId());
            J.add(player.getStoredId());
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.Registered", player, new Object[0]), (Player)player);
        } else if (n2 == 2) {
            I.add(player.getStoredId());
            K.add(player.getStoredId());
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.Registered", player, new Object[0]), (Player)player);
        } else {
            x.info("WTF??? Command id 0 in TvT...");
        }
    }

    public static boolean checkPlayer(Player player, boolean bl) {
        if (bl && (!V || player.isDead())) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.Late", player, new Object[0]), (Player)player);
            return false;
        }
        if (bl && (H.contains(player.getStoredId()) || I.contains(player.getStoredId()))) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.Cancelled", player, new Object[0]), (Player)player);
            return false;
        }
        if (player.getLevel() < b || player.getLevel() > c) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.CancelledLevel", player, new Object[0]), (Player)player);
            return false;
        }
        if (player.isMounted()) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.Cancelled", player, new Object[0]), (Player)player);
            return false;
        }
        if (player.isInDuel()) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.CancelledDuel", player, new Object[0]), (Player)player);
            return false;
        }
        if (player.getTeam() != TeamType.NONE) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.CancelledOtherEvent", player, new Object[0]), (Player)player);
            return false;
        }
        if (player != null && !player.isDead() && player.getLevel() >= b && player.getLevel() <= c && player.getReflection().isDefault() && !player.isOlyParticipant() && !player.isInObserverMode()) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.CancelledOlympiad", player, new Object[0]), (Player)player);
            return false;
        }
        if (player.isInParty() && player.getParty().isInDimensionalRift()) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.CancelledOtherEvent", player, new Object[0]), (Player)player);
            return false;
        }
        if (player.isTeleporting()) {
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.CancelledTeleport", player, new Object[0]), (Player)player);
            return false;
        }
        return true;
    }

    public static void prepare() {
        ReflectionUtils.getDoor((int)24190002).closeMe();
        ReflectionUtils.getDoor((int)24190003).closeMe();
        TvT.cleanPlayers();
        TvT.clearArena();
        TvT.executeTask((String)"events.TvT.TvT", (String)"ressurectPlayers", (Object[])new Object[0], (long)1000L);
        TvT.executeTask((String)"events.TvT.TvT", (String)"healPlayers", (Object[])new Object[0], (long)2000L);
        TvT.executeTask((String)"events.TvT.TvT", (String)"paralyzePlayers", (Object[])new Object[0], (long)4000L);
        TvT.executeTask((String)"events.TvT.TvT", (String)"teleportPlayersToColiseum", (Object[])new Object[0], (long)5000L);
        TvT.executeTask((String)"events.TvT.TvT", (String)"go", (Object[])new Object[0], (long)60000L);
        TvT.sayToAll("scripts.events.TvT.AnnounceFinalCountdown", null);
    }

    public static void go() {
        _status = 2;
        TvT.upParalyzePlayers();
        TvT.checkLive();
        TvT.clearArena();
        TvT.sayToAll("scripts.events.TvT.AnnounceFight", null);
        H = TvT.executeTask((String)"events.TvT.TvT", (String)"endBattle", (Object[])new Object[0], (long)300000L);
    }

    public static void endBattle() {
        ReflectionUtils.getDoor((int)24190002).openMe();
        ReflectionUtils.getDoor((int)24190003).openMe();
        _status = 0;
        TvT.removeAura();
        if (J.isEmpty()) {
            TvT.sayToAll("scripts.events.TvT.AnnounceFinishedBlueWins", null);
            TvT.giveItemsToWinner(false, true, 1.0);
        } else if (K.isEmpty()) {
            TvT.sayToAll("scripts.events.TvT.AnnounceFinishedRedWins", null);
            TvT.giveItemsToWinner(true, false, 1.0);
        } else if (J.size() < K.size()) {
            TvT.sayToAll("scripts.events.TvT.AnnounceFinishedBlueWins", null);
            TvT.giveItemsToWinner(false, true, 1.0);
        } else if (J.size() > K.size()) {
            TvT.sayToAll("scripts.events.TvT.AnnounceFinishedRedWins", null);
            TvT.giveItemsToWinner(true, false, 1.0);
        } else if (J.size() == K.size()) {
            TvT.sayToAll("scripts.events.TvT.AnnounceFinishedDraw", null);
            TvT.giveItemsToWinner(true, true, 0.5);
        }
        TvT.sayToAll("scripts.events.TvT.AnnounceEnd", null);
        TvT.executeTask((String)"events.TvT.TvT", (String)"end", (Object[])new Object[0], (long)30000L);
        V = false;
        if (H != null) {
            H.cancel(false);
            H = null;
        }
    }

    public static void end() {
        TvT.executeTask((String)"events.TvT.TvT", (String)"ressurectPlayers", (Object[])new Object[0], (long)1000L);
        TvT.executeTask((String)"events.TvT.TvT", (String)"healPlayers", (Object[])new Object[0], (long)2000L);
        TvT.executeTask((String)"events.TvT.TvT", (String)"teleportPlayers", (Object[])new Object[0], (long)3000L);
        TvT.executeTask((String)"events.TvT.TvT", (String)"autoContinue", (Object[])new Object[0], (long)10000L);
    }

    public void autoContinue() {
        J.clear();
        K.clear();
        H.clear();
        I.clear();
        if (cn > 0) {
            if (cn >= 6) {
                cn = 0;
                return;
            }
            this.start(new String[]{"" + (cn + 1), "" + (cn + 1)});
        }
    }

    public static void giveItemsToWinner(boolean bl, boolean bl2, double d) {
        if (bl) {
            for (Player player : TvT.c(H)) {
                TvT.addItem((Playable)player, (int)Config.EVENT_TvTItemID, (long)Math.round((double)(Config.EVENT_TvT_rate ? player.getLevel() : 1) * Config.EVENT_TvTItemCOUNT * d));
            }
        }
        if (bl2) {
            for (Player player : TvT.c(I)) {
                TvT.addItem((Playable)player, (int)Config.EVENT_TvTItemID, (long)Math.round((double)(Config.EVENT_TvT_rate ? player.getLevel() : 1) * Config.EVENT_TvTItemCOUNT * d));
            }
        }
    }

    public static void teleportPlayersToColiseum() {
        for (Player player : TvT.c(H)) {
            TvT.unRide((Player)player);
            TvT.unSummonPet((Player)player, (boolean)true);
            player.teleToLocation(Territory.getRandomLoc((Territory)f));
        }
        for (Player player : TvT.c(I)) {
            TvT.unRide((Player)player);
            TvT.unSummonPet((Player)player, (boolean)true);
            player.teleToLocation(Territory.getRandomLoc((Territory)g));
        }
    }

    public static void teleportPlayers() {
        for (Player player : TvT.c(H)) {
            player.teleToLocation(151480, 46712, -3400);
        }
        for (Player player : TvT.c(I)) {
            player.teleToLocation(147640, 46712, -3400);
        }
    }

    public static void paralyzePlayers() {
        Skill skill = SkillTable.getInstance().getInfo(4515, 1);
        for (Player player : TvT.c(H)) {
            player.getEffectList().stopEffect(1411);
            skill.getEffects((Creature)player, (Creature)player, false, false);
            if (player.getPet() == null) continue;
            skill.getEffects((Creature)player, (Creature)player.getPet(), false, false);
        }
        for (Player player : TvT.c(I)) {
            player.getEffectList().stopEffect(1411);
            skill.getEffects((Creature)player, (Creature)player, false, false);
            if (player.getPet() == null) continue;
            skill.getEffects((Creature)player, (Creature)player.getPet(), false, false);
        }
    }

    public static void upParalyzePlayers() {
        for (Player player : TvT.c(H)) {
            player.getEffectList().stopEffect(4515);
            if (player.getPet() != null) {
                player.getPet().getEffectList().stopEffect(4515);
            }
            player.leaveParty();
        }
        for (Player player : TvT.c(I)) {
            player.getEffectList().stopEffect(4515);
            if (player.getPet() != null) {
                player.getPet().getEffectList().stopEffect(4515);
            }
            player.leaveParty();
        }
    }

    public static void ressurectPlayers() {
        for (Player player : TvT.c(H)) {
            if (!player.isDead()) continue;
            player.restoreExp();
            player.setCurrentCp((double)player.getMaxCp());
            player.setCurrentHp((double)player.getMaxHp(), true);
            player.setCurrentMp((double)player.getMaxMp());
            player.broadcastPacket(new L2GameServerPacket[]{new Revive((GameObject)player)});
        }
        for (Player player : TvT.c(I)) {
            if (!player.isDead()) continue;
            player.restoreExp();
            player.setCurrentCp((double)player.getMaxCp());
            player.setCurrentHp((double)player.getMaxHp(), true);
            player.setCurrentMp((double)player.getMaxMp());
            player.broadcastPacket(new L2GameServerPacket[]{new Revive((GameObject)player)});
        }
    }

    public static void healPlayers() {
        for (Player player : TvT.c(H)) {
            player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
            player.setCurrentCp((double)player.getMaxCp());
        }
        for (Player player : TvT.c(I)) {
            player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
            player.setCurrentCp((double)player.getMaxCp());
        }
    }

    public static void cleanPlayers() {
        for (Player player : TvT.c(H)) {
            if (TvT.checkPlayer(player, false)) continue;
            TvT.removePlayer(player);
        }
        for (Player player : TvT.c(I)) {
            if (TvT.checkPlayer(player, false)) continue;
            TvT.removePlayer(player);
        }
    }

    public static void checkLive() {
        Player player;
        CopyOnWriteArrayList<Long> copyOnWriteArrayList = new CopyOnWriteArrayList<Long>();
        CopyOnWriteArrayList<Long> copyOnWriteArrayList2 = new CopyOnWriteArrayList<Long>();
        for (Long l : J) {
            player = GameObjectsStorage.getAsPlayer((long)l);
            if (player == null) continue;
            copyOnWriteArrayList.add(l);
        }
        for (Long l : K) {
            player = GameObjectsStorage.getAsPlayer((long)l);
            if (player == null) continue;
            copyOnWriteArrayList2.add(l);
        }
        J = copyOnWriteArrayList;
        K = copyOnWriteArrayList2;
        for (Player player2 : TvT.c(J)) {
            if (player2.isInZone(_zone) && !player2.isDead() && !player2.isLogoutStarted()) {
                player2.setTeam(TeamType.RED);
                continue;
            }
            TvT.l(player2);
        }
        for (Player player3 : TvT.c(K)) {
            if (player3.isInZone(_zone) && !player3.isDead() && !player3.isLogoutStarted()) {
                player3.setTeam(TeamType.BLUE);
                continue;
            }
            TvT.l(player3);
        }
        if (J.size() < 1 || K.size() < 1) {
            TvT.endBattle();
        }
    }

    public static void removeAura() {
        for (Player player : TvT.c(J)) {
            player.setTeam(TeamType.NONE);
        }
        for (Player player : TvT.c(K)) {
            player.setTeam(TeamType.NONE);
        }
    }

    public static void clearArena() {
        for (Creature creature : _zone.getObjects()) {
            Player player;
            if (creature == null || (player = creature.getPlayer()) == null || J.contains(player.getStoredId()) || K.contains(player.getStoredId())) continue;
            player.teleToLocation(147451, 46728, -3410);
        }
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (_status > 1 && creature.isPlayer() && creature.getTeam() != TeamType.NONE && (J.contains(creature.getStoredId()) || K.contains(creature.getStoredId()))) {
            TvT.l((Player)creature);
            TvT.checkLive();
        }
    }

    public void onTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
        if (_zone.checkIfInZone(n, n2, n3, reflection)) {
            return;
        }
        if (_status > 1 && player != null && player.getTeam() != TeamType.NONE && (J.contains(player.getStoredId()) || K.contains(player.getStoredId()))) {
            TvT.removePlayer(player);
            TvT.checkLive();
        }
    }

    public void onPlayerExit(Player player) {
        if (player.getTeam() == TeamType.NONE) {
            return;
        }
        if (_status == 0 && V && player.getTeam() != TeamType.NONE && (J.contains(player.getStoredId()) || K.contains(player.getStoredId()))) {
            TvT.removePlayer(player);
            return;
        }
        if (_status == 1 && (J.contains(player.getStoredId()) || K.contains(player.getStoredId()))) {
            TvT.removePlayer(player);
            try {
                String string = player.getVar("TvT_backCoords");
                if (string == null || string.equals("")) {
                    return;
                }
                String[] stringArray = string.split(" ");
                if (stringArray.length != 4) {
                    return;
                }
                player.teleToLocation(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]), Integer.parseInt(stringArray[3]));
                player.unsetVar("TvT_backCoords");
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return;
        }
        if (_status > 1 && player != null && player.getTeam() != TeamType.NONE && (J.contains(player.getStoredId()) || K.contains(player.getStoredId()))) {
            TvT.removePlayer(player);
            TvT.checkLive();
        }
    }

    private static void l(Player player) {
        if (player != null) {
            J.remove(player.getStoredId());
            K.remove(player.getStoredId());
            player.setTeam(TeamType.NONE);
            TvT.show((CustomMessage)new CustomMessage("scripts.events.TvT.YouLose", player, new Object[0]), (Player)player);
        }
    }

    private static void removePlayer(Player player) {
        if (player != null) {
            J.remove(player.getStoredId());
            K.remove(player.getStoredId());
            H.remove(player.getStoredId());
            I.remove(player.getStoredId());
            player.setTeam(TeamType.NONE);
        }
    }

    private static List<Player> c(List<Long> list) {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Long l : list) {
            Player player = GameObjectsStorage.getAsPlayer((long)l);
            if (player == null) continue;
            arrayList.add(player);
        }
        return arrayList;
    }

    static {
        H = new CopyOnWriteArrayList<Long>();
        I = new CopyOnWriteArrayList<Long>();
        J = new CopyOnWriteArrayList<Long>();
        K = new CopyOnWriteArrayList<Long>();
        V = false;
        _status = 0;
        cn = 0;
        _zone = ReflectionUtils.getZone((String)"[colosseum_battle]");
        a = new ZoneListener();
        f = new Territory().add((Shape)new Polygon().add(149878, 47505).add(150262, 47513).add(150502, 47233).add(150507, 46300).add(150256, 46002).add(149903, 46005).setZmin(-3408).setZmax(-3308));
        g = new Territory().add((Shape)new Polygon().add(149027, 46005).add(148686, 46003).add(148448, 46302).add(148449, 47231).add(148712, 47516).add(149014, 47527).setZmin(-3408).setZmax(-3308));
        T = false;
    }

    private static class ZoneListener
    implements OnZoneEnterLeaveListener {
        private ZoneListener() {
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            if (creature == null) {
                return;
            }
            Player player = creature.getPlayer();
            if (_status > 0 && player != null && !J.contains(player.getStoredId()) && !K.contains(player.getStoredId())) {
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new TeleportTask(creature, new Location(147451, 46728, -3410))), 3000L);
            }
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            if (creature == null) {
                return;
            }
            Player player = creature.getPlayer();
            if (_status > 1 && player != null && player.getTeam() != TeamType.NONE && (J.contains(player.getStoredId()) || K.contains(player.getStoredId()))) {
                double d = PositionUtils.convertHeadingToDegree((int)creature.getHeading());
                double d2 = Math.toRadians(d - 90.0);
                int n = (int)((double)creature.getX() + 50.0 * Math.sin(d2));
                int n2 = (int)((double)creature.getY() - 50.0 * Math.cos(d2));
                int n3 = creature.getZ();
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new TeleportTask(creature, new Location(n, n2, n3))), 3000L);
            }
        }
    }

    public class StartTask
    extends RunnableImpl {
        public void runImpl() {
            if (!T) {
                return;
            }
            if (Functions.isPvPEventStarted()) {
                _log.info("TvT not started: another event is already running");
                return;
            }
            if (!Rnd.chance((int)Config.EVENT_TvTChanceToStart)) {
                _log.debug("TvT not started: chance");
                return;
            }
            for (Residence residence : ResidenceHolder.getInstance().getResidenceList(Castle.class)) {
                if (residence.getSiegeEvent() == null || !residence.getSiegeEvent().isInProgress()) continue;
                _log.debug("TvT not started: CastleSiege in progress");
                return;
            }
            TvT.this.start(new String[]{"1", "1"});
        }
    }

    private static class TeleportTask
    extends RunnableImpl {
        Location loc;
        Creature target;

        public TeleportTask(Creature creature, Location location) {
            this.target = creature;
            this.loc = location;
            creature.block();
        }

        public void runImpl() throws Exception {
            this.target.unblock();
            this.target.teleToLocation(this.loc);
        }
    }
}
