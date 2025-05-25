/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.data.xml.holder.InstantZoneHolder
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.idfactory.IdFactory
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerExitListener
 *  l2.gameserver.listener.actor.player.OnTeleportListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Effect
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.entity.oly.ParticipantPool
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.ItemStateFlags
 *  l2.gameserver.model.items.attachment.FlagItemAttachment
 *  l2.gameserver.model.items.attachment.ItemAttachment
 *  l2.gameserver.network.l2.GameClient
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExEventMatchMessage
 *  l2.gameserver.network.l2.s2c.ExPVPMatchCCMyRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord$PVPMatchCCAction
 *  l2.gameserver.network.l2.s2c.ExPVPMatchRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchRecord$PVPMatchAction
 *  l2.gameserver.network.l2.s2c.ExPVPMatchUserDie
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.NickNameChanged
 *  l2.gameserver.network.l2.s2c.Revive
 *  l2.gameserver.network.l2.s2c.SkillCoolTime
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.scripts.Scripts
 *  l2.gameserver.skills.AbnormalEffect
 *  l2.gameserver.stats.Env
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.InstantZone
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 *  l2.gameserver.utils.TimeUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.tuple.ImmutablePair
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.TvT2;

import events.TvT2.PvPEventProperties;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerExitListener;
import l2.gameserver.listener.actor.player.OnTeleportListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.ItemStateFlags;
import l2.gameserver.model.items.attachment.FlagItemAttachment;
import l2.gameserver.model.items.attachment.ItemAttachment;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExEventMatchMessage;
import l2.gameserver.network.l2.s2c.ExPVPMatchCCMyRecord;
import l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord;
import l2.gameserver.network.l2.s2c.ExPVPMatchRecord;
import l2.gameserver.network.l2.s2c.ExPVPMatchUserDie;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NickNameChanged;
import l2.gameserver.network.l2.s2c.Revive;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.stats.Env;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import l2.gameserver.utils.TimeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.GlobalServices;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PvPEvent
extends Functions
implements ScriptFile {
    private static PvPEvent a = null;
    private static final Logger y = LoggerFactory.getLogger(PvPEvent.class);
    public static final String EVENT_NAME = "PvP";
    private static final Skill o = SkillTable.getInstance().getInfo(1323, 1);
    private static final Skill p = SkillTable.getInstance().getInfo(4083, 1);
    private DieListner a;
    private ZoneEnterLeaveListner a;
    private ExitListner a;
    private static final long ad = 20000L;
    private boolean W;
    private boolean X;
    private boolean Y;
    private String U;
    private Map<PvPEventRule, String> i;
    private int co;
    private int cp;
    private PvPEventState a;
    private PvPEventRule a;
    private ScheduledFuture<?> I;
    private ScheduledFuture<?> J;
    private Collection<Integer> a;
    private RegisrationState a = null;
    private Collection<Integer> b;

    public static PvPEvent getInstance() {
        return a;
    }

    public void LoadVars() {
        PvPEventProperties.loadPvPEventsProperties();
        this.W = PvPEventProperties.getBooleanProperty(EVENT_NAME, "EventEnabled", false);
        this.X = PvPEventProperties.getBooleanProperty(EVENT_NAME, "EventCountdown", true);
        this.Y = PvPEventProperties.getBooleanProperty(EVENT_NAME, "EventRegistrationWindow", true);
        this.U = PvPEventProperties.getStringProperty(EVENT_NAME, "EventStartTime", "");
        this.co = PvPEventProperties.getIntProperty(EVENT_NAME, "EventAnnounceTime", 5);
        this.cp = PvPEventProperties.getIntProperty(EVENT_NAME, "EventAnnounceCountdown", 1);
        LinkedHashMap<PvPEventRule, String> linkedHashMap = new LinkedHashMap<PvPEventRule, String>();
        for (PvPEventRule pvPEventRule : PvPEventRule.VALUES) {
            linkedHashMap.put(pvPEventRule, PvPEventProperties.getStringProperty(pvPEventRule.toString(), "EventStartTime", ""));
        }
        this.i = linkedHashMap;
    }

    private PvPEventState a() {
        return this.a;
    }

    private synchronized void a(PvPEventState pvPEventState) {
        y.info("PvPEventState changet to " + pvPEventState.name());
        this.a = pvPEventState;
    }

    public PvPEventRule getRule() {
        return this.a;
    }

    public void setRule(PvPEventRule pvPEventRule) {
        this.a = pvPEventRule;
    }

    public PvPEventRule getNextRule(PvPEventRule pvPEventRule) {
        if (pvPEventRule == null) {
            if (this.a(PvPEventRule.TVT)) {
                return PvPEventRule.TVT;
            }
            if (this.a(PvPEventRule.CTF)) {
                return PvPEventRule.CTF;
            }
            if (this.a(PvPEventRule.DM)) {
                return PvPEventRule.DM;
            }
            return null;
        }
        switch (pvPEventRule) {
            case TVT: {
                if (this.a(PvPEventRule.CTF)) {
                    return PvPEventRule.CTF;
                }
                if (this.a(PvPEventRule.DM)) {
                    return PvPEventRule.DM;
                }
                if (!this.a(PvPEventRule.TVT)) break;
                return PvPEventRule.TVT;
            }
            case CTF: {
                if (this.a(PvPEventRule.DM)) {
                    return PvPEventRule.DM;
                }
                if (this.a(PvPEventRule.TVT)) {
                    return PvPEventRule.TVT;
                }
                if (!this.a(PvPEventRule.CTF)) break;
                return PvPEventRule.CTF;
            }
            case DM: {
                if (this.a(PvPEventRule.TVT)) {
                    return PvPEventRule.TVT;
                }
                if (this.a(PvPEventRule.CTF)) {
                    return PvPEventRule.CTF;
                }
                if (!this.a(PvPEventRule.DM)) break;
                return PvPEventRule.DM;
            }
        }
        return null;
    }

    private boolean g() {
        return PvPEventProperties.getBooleanProperty(this.getRule().name(), "UseCaptcha", false);
    }

    private boolean h() {
        return PvPEventProperties.getBooleanProperty(this.getRule().name(), "HideIdentity", false);
    }

    private int g() {
        return PvPEventProperties.getIntProperty(this.getRule().name(), "MaxParticipants", 100);
    }

    private List<Pair<ItemTemplate, Long>> b() {
        return PvPEventProperties.getRewardItems(this.getRule().name(), "RewardPerKill");
    }

    private int h() {
        return PvPEventProperties.getIntProperty(this.getRule().name(), "ReviveDelay", 1);
    }

    private boolean a(PvPEventRule pvPEventRule) {
        return PvPEventProperties.getBooleanProperty(pvPEventRule.name(), "Enabled", false);
    }

    private boolean i() {
        return PvPEventProperties.getBooleanProperty(this.getRule().name(), "BuffProtection", false);
    }

    private boolean j() {
        return PvPEventProperties.getBooleanProperty(this.getRule().name(), "AfkProtection", false);
    }

    private int i() {
        return PvPEventProperties.getIntProperty(this.getRule().name(), "MinParticipants", 50);
    }

    private int j() {
        return PvPEventProperties.getIntProperty(this.getRule().name(), "MinLevel", 1);
    }

    private int k() {
        return PvPEventProperties.getIntProperty(this.getRule().name(), "MaxLevel", 86);
    }

    private List<Pair<ItemTemplate, Long>> c() {
        return PvPEventProperties.getRewardItems(this.getRule().name(), "TeamReward");
    }

    private List<Pair<ItemTemplate, Long>> d() {
        return PvPEventProperties.getRewardItems(this.getRule().name(), "LoseTeamReward");
    }

    private List<Pair<ItemTemplate, Long>> e() {
        return PvPEventProperties.getRewardItems(this.getRule().name(), "TieTeamReward");
    }

    private List<Pair<ItemTemplate, Long>> f() {
        return PvPEventProperties.getRewardItems(this.getRule().name(), "TopKillerReward");
    }

    private List<Pair<ItemTemplate, Long>> g() {
        return PvPEventProperties.getRewardItems(this.getRule().name(), "TopKillerLoseTeamReward");
    }

    private List<Pair<ItemTemplate, Long>> h() {
        return PvPEventProperties.getRewardItems(this.getRule().name(), "TopKillerTieTeamReward");
    }

    private boolean k() {
        return PvPEventProperties.getBooleanProperty(this.getRule().name(), "DispelBuffs", true);
    }

    private boolean l() {
        return PvPEventProperties.getBooleanProperty(this.getRule().name(), "DispelBuffsAfter", true);
    }

    private int l() {
        return PvPEventProperties.getIntProperty(this.getRule().name(), "EventTime", 5);
    }

    private int m() {
        return PvPEventProperties.getRandomIntFromPropertyArray(this.getRule().name(), "EventInstancesIds", new int[]{801, 802, 803});
    }

    private int n() {
        return PvPEventProperties.getIntProperty(this.getRule().name(), "HeroRewardTime", 0);
    }

    private synchronized void a(PvPEventState pvPEventState, long l) {
        this.I = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new PvPStateTask(pvPEventState)), l);
    }

    private synchronized void H() {
        if (this.I != null) {
            this.I.cancel(false);
            this.I = null;
        }
    }

    private void a(long l, PvPEventRule pvPEventRule) {
        this.setRule(pvPEventRule);
        this.a(PvPEventState.REGISTRATION, l);
        y.info("PvPEvent: Scheduled {} next at {}", (Object)pvPEventRule, (Object)TimeUtils.toSimpleFormat((long)(System.currentTimeMillis() + l)));
    }

    private void I() {
        this.a(PvPEventState.STANDBY);
        Pair<PvPEventRule, Long> pair = this.a();
        if (pair != null && (Long)pair.getRight() > 0L) {
            PvPEventRule pvPEventRule = (PvPEventRule)((Object)pair.getLeft());
            if (pvPEventRule != null) {
                this.a((Long)pair.getRight(), pvPEventRule);
            } else {
                y.info("PvPEvent: No active next event");
            }
        } else {
            y.warn("PvPEvent: Wrong event time: " + this.U);
        }
    }

    public void goRegistration() {
        this.a(PvPEventState.REGISTRATION);
        PvPEvent.getInstance().a((Runnable)((Object)new RegisrationTask(RegisrationState.ANNOUNCE, this.co)), 1000L);
    }

    private void J() {
        this.a(PvPEventState.PREPARE_TO);
        this.getRule().getParticipantController().initReflection();
        this.getRule().getParticipantController().prepareParticipantsTo();
        this.a(PvPEventState.PORTING_TO, 2000L);
    }

    private void K() {
        this.a(PvPEventState.PORTING_TO);
        this.getRule().getParticipantController().portParticipantsTo();
        PvPEvent.getInstance().a((Runnable)((Object)new CompetitionRunTask(30)), 1000L);
    }

    private void L() {
        this.a(PvPEventState.COMPETITION);
        this.getRule().getParticipantController().initParticipant();
        this.a(PvPEventState.WINNER, (long)(this.l() * 60 * 1000));
    }

    private void M() {
        this.a(PvPEventState.WINNER);
        this.getRule().getParticipantController().MakeWinner();
        this.a(PvPEventState.PREPARE_FROM, 1000L);
    }

    private void N() {
        this.a(PvPEventState.PREPARE_FROM);
        this.getRule().getParticipantController().prepareParticipantsFrom();
        this.a(PvPEventState.PORTING_FROM, 10000L);
    }

    private void O() {
        this.a(PvPEventState.PORTING_FROM);
        this.getRule().getParticipantController().portParticipantsBack();
        this.getRule().getParticipantController().doneParicipant();
        this.getRule().getParticipantController().doneReflection();
        this.a.clear();
        this.a = null;
        this.a(PvPEventState.STANDBY, 5000L);
    }

    private synchronized void a(Runnable runnable, long l) {
        this.J = ThreadPoolManager.getInstance().schedule(runnable, l);
    }

    private synchronized void P() {
        if (this.J != null) {
            this.J.cancel(false);
            this.J = null;
        }
    }

    private Collection<Player> getPlayers() {
        ArrayList<Player> arrayList = new ArrayList<Player>(this.a.size());
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null) continue;
            Iterator iterator = this.a.iterator();
            while (iterator.hasNext()) {
                Integer n = (Integer)iterator.next();
                if (n.intValue() != player.getObjectId()) continue;
                arrayList.add(player);
            }
        }
        return arrayList;
    }

    private void Q() {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Player player : GameObjectsStorage.getAllPlayers()) {
            if (!PvPEvent.b(player)) continue;
            arrayList.add(player);
        }
        if (this.Y) {
            for (Player player : arrayList) {
                if (player.getPlayer().getVarB("blockpvpevent@")) continue;
                player.scriptRequest(new CustomMessage("events.PvPEvent.AskToS1Participation", player, new Object[]{this.getRule().name()}).toString(), "events.TvT2.PvPEvent:addDesire", new Object[0]);
            }
        }
    }

    private void R() {
        if (this.a != RegisrationState.CAPCHA || this.b == null) {
            return;
        }
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Integer n : this.b) {
            Player player = GameObjectsStorage.getPlayer((int)n);
            if (!PvPEvent.b(player)) continue;
            arrayList.add(player);
        }
        this.b.clear();
        this.b = null;
        PvPEvent.getInstance().b = new ConcurrentSkipListSet<Integer>();
        for (Player player : arrayList) {
            Scripts.getInstance().callScripts(player, "Util", "RequestCapcha", new Object[]{"events.TvT2.PvPEvent:addDesire", player.getStoredId(), 30});
        }
    }

    private static boolean b(Player player) {
        if (player == null || player.getNetConnection() == null || !player.isConnected()) {
            return false;
        }
        if (player.isDead() || player.isBlocked() || player.isInZone(Zone.ZoneType.epic) || player.isInZone(Zone.ZoneType.SIEGE) || player.isInZone(Zone.ZoneType.SIEGE) || player.getReflectionId() != 0) {
            return false;
        }
        if (player.isFishing() || player.getTransformation() != 0 || player.isCursedWeaponEquipped()) {
            return false;
        }
        if (player.getLevel() < PvPEvent.getInstance().j() || player.getLevel() > PvPEvent.getInstance().k()) {
            return false;
        }
        if (player.isOlyParticipant() || ParticipantPool.getInstance().isRegistred(player)) {
            return false;
        }
        if (PvPEventProperties.PVP_EVENT_RESTRICT_HWID && PvPEvent.getInstance().isHWIDRegistred(player.getNetConnection().getHwid(), player)) {
            return false;
        }
        if (PvPEventProperties.PVP_EVENT_RESTRICT_IP && PvPEvent.getInstance().isIPRegistered(player.getNetConnection().getIpAddr(), player)) {
            return false;
        }
        if (PvPEventProperties.PVP_EVENT_RESTRICT_CLASS_IDS.length > 0 && ArrayUtils.contains((int[])PvPEventProperties.PVP_EVENT_RESTRICT_CLASS_IDS, (int)player.getActiveClassId())) {
            return false;
        }
        return player.getTeam() == TeamType.NONE && !player.isInDuel();
    }

    private static boolean c(Player player) {
        for (int i = 0; i < PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_ID.length; ++i) {
            if (PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_ID[i] == 0 || PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_COUNT[i] <= 0) continue;
            if (ItemFunctions.getItemCount((Playable)player, (int)PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_ID[i]) < (long)PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_COUNT[i]) {
                if (Config.SERVICES_EXPAND_INVENTORY_ITEM == 57) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                } else {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                }
                return false;
            }
            ItemFunctions.removeItem((Playable)player, (int)PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_ID[i], (long)PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_COUNT[i], (boolean)true);
        }
        return true;
    }

    public boolean isHWIDRegistred(String string, Player player) {
        if (string == null || string.isEmpty() || this.b == null) {
            return false;
        }
        for (Integer n : this.b) {
            GameClient gameClient;
            Player player2 = GameObjectsStorage.getPlayer((int)n);
            if (player2 == null || player2 == player || (gameClient = player2.getNetConnection()) == null || !gameClient.isConnected() || gameClient.getHwid() == null || !string.equalsIgnoreCase(gameClient.getHwid())) continue;
            return true;
        }
        return false;
    }

    public boolean isIPRegistered(String string, Player player) {
        if (string == null || string.isEmpty() || this.b == null) {
            return false;
        }
        for (Integer n : this.b) {
            GameClient gameClient;
            Player player2 = GameObjectsStorage.getPlayer((int)n);
            if (player2 == null || player2 == player || (gameClient = player2.getNetConnection()) == null || !gameClient.isConnected() || gameClient.getIpAddr() == null || !string.equalsIgnoreCase(gameClient.getIpAddr())) continue;
            return true;
        }
        return false;
    }

    public void addDesire() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (PvPEvent.getInstance().a != RegisrationState.REQUEST && PvPEvent.getInstance().a != RegisrationState.CAPCHA) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireInappropriateState", player, new Object[0]));
            return;
        }
        if (!PvPEvent.b(player) || PvPEvent.getInstance().b == null) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireInsufficientConditions", player, new Object[0]));
            return;
        }
        if (PvPEvent.getInstance().b.contains(player.getObjectId())) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireAlreadyAccepted", player, new Object[0]));
            return;
        }
        if (PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_ID.length != 0 && PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_COUNT.length != 0 && !PvPEvent.c(player)) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireInsufficientConditions", player, new Object[0]));
            return;
        }
        player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireAccepted", player, new Object[0]));
        PvPEvent.getInstance().b.add(player.getObjectId());
    }

    public void addDesireDuringAnnounce() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (PvPEvent.getInstance().a != RegisrationState.ANNOUNCE) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireInappropriateState", player, new Object[0]));
            return;
        }
        if (!PvPEvent.b(player) || PvPEvent.getInstance().b == null) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireInsufficientConditions", player, new Object[0]));
            return;
        }
        if (PvPEvent.getInstance().b.contains(player.getObjectId())) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireAlreadyAccepted", player, new Object[0]));
            return;
        }
        if (PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_ID.length != 0 && PvPEventProperties.PVP_EVENTS_REGISTRATION_ITEM_COUNT.length != 0 && !PvPEvent.c(player)) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireInsufficientConditions", player, new Object[0]));
            return;
        }
        player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireAccepted", player, new Object[0]));
        PvPEvent.getInstance().b.add(player.getObjectId());
    }

    private void S() {
        Object object;
        if (this.a != RegisrationState.MORPH || this.b == null) {
            return;
        }
        LinkedList<Player> linkedList = new LinkedList<Player>();
        for (Integer n : this.b) {
            object = GameObjectsStorage.getPlayer((int)n);
            if (!PvPEvent.b((Player)object)) continue;
            linkedList.add((Player)object);
        }
        this.b.clear();
        this.b = null;
        ArrayList arrayList = new ArrayList();
        int n = this.g();
        while (arrayList.size() < n && !linkedList.isEmpty()) {
            arrayList.add((Player)linkedList.remove(Rnd.get((int)linkedList.size())));
        }
        if (arrayList.size() < this.i()) {
            Announcements.getInstance().announceByCustomMessage("events.PvPEvent.EventS1LackParticipants", new String[]{this.getRule().name()});
            this.I();
            return;
        }
        this.a = new ConcurrentSkipListSet();
        for (Player player : linkedList) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipantAskLater", player, new Object[0]));
        }
        object = arrayList.iterator();
        while (object.hasNext()) {
            Player player;
            player = (Player)object.next();
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipantAccepted", player, new Object[0]));
            this.a.add(player.getObjectId());
        }
        linkedList.clear();
        this.a(PvPEventState.PREPARE_TO, 10000L);
    }

    public void Activate() {
        this.W = true;
        PvPEvent.getInstance().a(PvPEventState.STANDBY, 1000L);
        y.info("PvPEvent: [state: active]");
    }

    public void Deativate() {
        this.W = false;
        PvPEvent.getInstance().LoadVars();
        PvPEvent.getInstance().H();
        y.info("PvPEvent: [state: inactive]");
    }

    public boolean isActive() {
        return this.W;
    }

    public void onLoad() {
        a = this;
        this.LoadVars();
        if (this.W) {
            this.Activate();
            this.T();
        } else {
            y.info("PvPEvent: [state: inactive]");
        }
    }

    private void T() {
        this.a = new DieListner();
        this.a = new ZoneEnterLeaveListner();
        this.a = new ExitListner();
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private Pair<PvPEventRule, Long> a() {
        long l = this.a(this.U);
        PvPEventRule pvPEventRule = this.getNextRule(this.getRule());
        for (PvPEventRule pvPEventRule2 : PvPEventRule.VALUES) {
            long l2;
            String string = this.i.get((Object)pvPEventRule2);
            if (!this.a(pvPEventRule2) || string == null || (l2 = this.a(string)) <= -1L || l >= 0L && l2 >= l) continue;
            l = l2;
            pvPEventRule = pvPEventRule2;
        }
        return Pair.of((Object)((Object)pvPEventRule), (Object)l);
    }

    @Deprecated
    private long a(String string) {
        Matcher matcher = Pattern.compile("(\\d{2})\\:(\\d{2});*").matcher(string);
        long l = System.currentTimeMillis();
        long l2 = Long.MAX_VALUE;
        while (matcher.find()) {
            long l3;
            String string2 = matcher.group(1);
            String string3 = matcher.group(2);
            int n = Integer.parseInt(string2);
            int n2 = Integer.parseInt(string3);
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, n);
            calendar.set(12, n2);
            calendar.set(13, 0);
            calendar.set(14, 0);
            if (calendar.getTimeInMillis() < l) {
                calendar.add(5, 1);
            }
            if ((l3 = calendar.getTimeInMillis() - l) <= 0L || l3 >= l2) continue;
            l2 = l3;
        }
        return l2 < Long.MAX_VALUE ? l2 : -1L;
    }

    private void broadcast(L2GameServerPacket ... l2GameServerPacketArray) {
        Collection<Player> collection = this.getPlayers();
        for (Player player : collection) {
            player.sendPacket((IStaticPacket[])l2GameServerPacketArray);
        }
    }

    public void removeParticipant() {
        Player player = this.getSelf();
        if (PvPEvent.getInstance().a != RegisrationState.ANNOUNCE) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireInappropriateState", player, new Object[0]));
            return;
        }
        if (!PvPEvent.getInstance().b.contains(player.getObjectId())) {
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireDoesNotExist", player, new Object[0]));
        } else {
            PvPEvent.getInstance().b.remove(player.getObjectId());
            player.sendMessage(new CustomMessage("events.PvPEvent.ParticipationDesireRemove", player, new Object[0]));
        }
    }

    public boolean isEventParticipant() {
        Player player = this.getSelf();
        if (PvPEvent.getInstance() == null || PvPEvent.getInstance().a == null || player == null) {
            return false;
        }
        int n = player.getObjectId();
        Iterator iterator = PvPEvent.getInstance().a.iterator();
        while (iterator.hasNext()) {
            Integer n2 = (Integer)iterator.next();
            if (n2 != n) continue;
            return true;
        }
        return false;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class PvPEventRule
    extends Enum<PvPEventRule> {
        public static final /* enum */ PvPEventRule TVT = new PvPEventRule(new TvTParticipantController());
        public static final /* enum */ PvPEventRule CTF = new PvPEventRule(new CTFParticipantController());
        public static final /* enum */ PvPEventRule DM = new PvPEventRule(new DMParticipantController());
        public static PvPEventRule[] VALUES;
        private final IParticipantController a;
        private static final /* synthetic */ PvPEventRule[] a;

        public static PvPEventRule[] values() {
            return (PvPEventRule[])a.clone();
        }

        public static PvPEventRule valueOf(String string) {
            return Enum.valueOf(PvPEventRule.class, string);
        }

        private PvPEventRule(IParticipantController iParticipantController) {
            this.a = iParticipantController;
        }

        public IParticipantController getParticipantController() {
            return this.a;
        }

        private static /* synthetic */ PvPEventRule[] a() {
            return new PvPEventRule[]{TVT, CTF, DM};
        }

        static {
            a = PvPEventRule.a();
            VALUES = PvPEventRule.values();
        }
    }

    protected static final class PvPEventState
    extends Enum<PvPEventState> {
        public static final /* enum */ PvPEventState STANDBY = new PvPEventState();
        public static final /* enum */ PvPEventState REGISTRATION = new PvPEventState();
        public static final /* enum */ PvPEventState PORTING_TO = new PvPEventState();
        public static final /* enum */ PvPEventState PREPARE_TO = new PvPEventState();
        public static final /* enum */ PvPEventState COMPETITION = new PvPEventState();
        public static final /* enum */ PvPEventState WINNER = new PvPEventState();
        public static final /* enum */ PvPEventState PREPARE_FROM = new PvPEventState();
        public static final /* enum */ PvPEventState PORTING_FROM = new PvPEventState();
        private static final /* synthetic */ PvPEventState[] a;

        public static PvPEventState[] values() {
            return (PvPEventState[])a.clone();
        }

        public static PvPEventState valueOf(String string) {
            return Enum.valueOf(PvPEventState.class, string);
        }

        private static /* synthetic */ PvPEventState[] a() {
            return new PvPEventState[]{STANDBY, REGISTRATION, PORTING_TO, PREPARE_TO, COMPETITION, WINNER, PREPARE_FROM, PORTING_FROM};
        }

        static {
            a = PvPEventState.a();
        }
    }

    private class PvPStateTask
    extends RunnableImpl {
        private final PvPEventState b;

        public PvPStateTask(PvPEventState pvPEventState) {
            this.b = pvPEventState;
        }

        public void runImpl() throws Exception {
            try {
                switch (this.b) {
                    case STANDBY: {
                        PvPEvent.getInstance().I();
                        break;
                    }
                    case REGISTRATION: {
                        PvPEvent.getInstance().goRegistration();
                        break;
                    }
                    case PORTING_TO: {
                        PvPEvent.getInstance().K();
                        break;
                    }
                    case PREPARE_TO: {
                        PvPEvent.getInstance().J();
                        break;
                    }
                    case COMPETITION: {
                        PvPEvent.getInstance().L();
                        break;
                    }
                    case WINNER: {
                        PvPEvent.getInstance().M();
                        break;
                    }
                    case PREPARE_FROM: {
                        PvPEvent.getInstance().N();
                        break;
                    }
                    case PORTING_FROM: {
                        PvPEvent.getInstance().O();
                    }
                }
            }
            catch (Exception exception) {
                _log.warn("PvPEvent: Exception on changing state to " + this.b + " state.", (Throwable)exception);
                throw new RuntimeException(exception);
            }
        }
    }

    private class RegisrationTask
    extends RunnableImpl {
        private final int cs;
        private final RegisrationState b;

        public RegisrationTask(RegisrationState regisrationState, int n) {
            this.cs = n;
            this.b = regisrationState;
        }

        public void runImpl() throws Exception {
            if (PvPEvent.getInstance().a != this.b && this.b == RegisrationState.ANNOUNCE) {
                if (PvPEvent.getInstance().b != null) {
                    PvPEvent.getInstance().b.clear();
                    PvPEvent.getInstance().b = null;
                }
                PvPEvent.getInstance().b = new ConcurrentSkipListSet<Integer>();
            }
            PvPEvent.getInstance().a = this.b;
            switch (this.b) {
                case ANNOUNCE: {
                    if (this.cs > 0) {
                        Announcements.getInstance().announceByCustomMessage("events.PvPEvent.EventS1StartAtS2Minutes", new String[]{PvPEvent.this.getRule().name(), String.valueOf(this.cs)});
                        PvPEvent.getInstance().a((Runnable)((Object)new RegisrationTask(RegisrationState.ANNOUNCE, Math.max(0, this.cs - PvPEvent.getInstance().cp))), (long)(PvPEvent.getInstance().cp * 60 * 1000));
                        break;
                    }
                    PvPEvent.getInstance().a((Runnable)((Object)new RegisrationTask(RegisrationState.REQUEST, 0)), 1000L);
                    break;
                }
                case REQUEST: {
                    PvPEvent.getInstance().Q();
                    PvPEvent.getInstance().a((Runnable)((Object)new RegisrationTask(PvPEvent.getInstance().g() ? RegisrationState.CAPCHA : RegisrationState.MORPH, 0)), 40000L);
                    break;
                }
                case CAPCHA: {
                    PvPEvent.getInstance().R();
                    PvPEvent.getInstance().a((Runnable)((Object)new RegisrationTask(RegisrationState.MORPH, 0)), 40000L);
                    break;
                }
                case MORPH: {
                    PvPEvent.getInstance().S();
                }
            }
        }
    }

    private static final class RegisrationState
    extends Enum<RegisrationState> {
        public static final /* enum */ RegisrationState ANNOUNCE = new RegisrationState();
        public static final /* enum */ RegisrationState REQUEST = new RegisrationState();
        public static final /* enum */ RegisrationState MORPH = new RegisrationState();
        public static final /* enum */ RegisrationState CAPCHA = new RegisrationState();
        private static final /* synthetic */ RegisrationState[] a;

        public static RegisrationState[] values() {
            return (RegisrationState[])a.clone();
        }

        public static RegisrationState valueOf(String string) {
            return Enum.valueOf(RegisrationState.class, string);
        }

        private static /* synthetic */ RegisrationState[] a() {
            return new RegisrationState[]{ANNOUNCE, REQUEST, MORPH, CAPCHA};
        }

        static {
            a = RegisrationState.a();
        }
    }

    private static interface IParticipantController {
        public void prepareParticipantsTo();

        public void prepareParticipantsFrom();

        public void initParticipant();

        public void doneParicipant();

        public void portParticipantsTo();

        public void portParticipantsBack();

        public void initReflection();

        public void doneReflection();

        public Reflection getReflection();

        public void OnPlayerDied(Player var1, Player var2);

        public void OnEnter(Player var1, Zone var2);

        public void OnLeave(Player var1, Zone var2);

        public void OnExit(Player var1);

        public void OnTeleport(Player var1, int var2, int var3, int var4, Reflection var5);

        public void MakeWinner();
    }

    private class CompetitionRunTask
    extends RunnableImpl {
        private int cr;

        public CompetitionRunTask(int n) {
            this.cr = n;
        }

        public void runImpl() throws Exception {
            switch (this.cr) {
                case 30: {
                    PvPEvent.getInstance().a((Runnable)((Object)new CompetitionRunTask(this.cr - 25)), 25000L);
                    return;
                }
                case 0: {
                    PvPEvent.getInstance().a(PvPEventState.COMPETITION, 100L);
                    if (PvPEvent.this.X) {
                        PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.START});
                    }
                    return;
                }
                case 5: {
                    if (PvPEvent.this.X) {
                        PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT5});
                    }
                    for (Player player : PvPEvent.getInstance().getPlayers()) {
                        player.broadcastUserInfo(true, new UserInfoType[0]);
                    }
                    break;
                }
                case 4: {
                    if (!PvPEvent.this.X) break;
                    PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT4});
                    break;
                }
                case 3: {
                    if (!PvPEvent.this.X) break;
                    PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT3});
                    break;
                }
                case 2: {
                    if (!PvPEvent.this.X) break;
                    PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT2});
                    break;
                }
                case 1: {
                    if (!PvPEvent.this.X) break;
                    PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.COUNT1});
                }
            }
            PvPEvent.getInstance().a((Runnable)((Object)new CompetitionRunTask(this.cr - 1)), 1000L);
        }
    }

    private class DieListner
    implements OnDeathListener {
        private DieListner() {
        }

        public void onDeath(Creature creature, Creature creature2) {
            try {
                if (PvPEvent.getInstance().a() != PvPEventState.COMPETITION) {
                    return;
                }
                Player player = creature.getPlayer();
                Player player2 = creature2.getPlayer();
                if (player != null) {
                    PvPEvent.getInstance().getRule().getParticipantController().OnPlayerDied(player, player2);
                }
            }
            catch (Exception exception) {
                y.warn("PVPEvent.onDeath :", (Throwable)exception);
            }
        }
    }

    private class ZoneEnterLeaveListner
    implements OnZoneEnterLeaveListener {
        private ZoneEnterLeaveListner() {
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            try {
                if (PvPEvent.getInstance().a() != PvPEventState.COMPETITION || !creature.isPlayer()) {
                    return;
                }
                PvPEvent.getInstance().getRule().getParticipantController().OnEnter(creature.getPlayer(), zone);
            }
            catch (Exception exception) {
                y.warn("PVPEvent.onZoneEnter :", (Throwable)exception);
            }
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            try {
                if (PvPEvent.getInstance().a() != PvPEventState.COMPETITION || !creature.isPlayer()) {
                    return;
                }
                PvPEvent.getInstance().getRule().getParticipantController().OnLeave(creature.getPlayer(), zone);
            }
            catch (Exception exception) {
                y.warn("PVPEvent.onZoneLeave :", (Throwable)exception);
            }
        }
    }

    private class ExitListner
    implements OnPlayerExitListener {
        private ExitListner() {
        }

        public void onPlayerExit(Player player) {
            try {
                if (PvPEvent.getInstance().a() == PvPEventState.STANDBY) {
                    return;
                }
                PvPEvent.getInstance().getRule().getParticipantController().OnExit(player);
                PvPEvent.getInstance().a.remove(player.getObjectId());
            }
            catch (Exception exception) {
                y.warn("PVPEvent.onPlayerExit :", (Throwable)exception);
            }
        }
    }

    private class TeleportAndReviveTask
    extends RunnableImpl {
        private final HardReference<Player> i;
        private Location _loc;
        private Reflection a;

        public TeleportAndReviveTask(Player player, Location location, Reflection reflection) {
            this.i = player.getRef();
            this._loc = location;
            this.a = reflection;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void runImpl() throws Exception {
            Player player = (Player)this.i.get();
            if (player == null) {
                return;
            }
            Player player2 = player;
            synchronized (player2) {
                player.teleToLocation(this._loc, this.a);
                if (!player.isConnected()) {
                    player.onTeleported();
                }
                if (player.isDead()) {
                    player.setCurrentHp((double)player.getMaxHp(), true, true);
                    player.setCurrentCp((double)player.getMaxCp());
                    player.setCurrentMp((double)player.getMaxMp());
                    player.broadcastPacket(new L2GameServerPacket[]{new Revive((GameObject)player)});
                    HashSet<Skill> hashSet = new HashSet<Skill>();
                    for (Effect effect : player.getEffectList().getAllEffects()) {
                        if (!effect.getSkill().isOffensive()) continue;
                        hashSet.add(effect.getSkill());
                    }
                    for (Skill skill : hashSet) {
                        for (Effect effect : player.getEffectList().getEffectsBySkill(skill)) {
                            effect.exit();
                        }
                    }
                    if (PvPEvent.getInstance().i()) {
                        o.getEffects((Creature)player, (Creature)player, false, false, false);
                    }
                    if (!PvPEventProperties.PVP_EVENTS_MAGE_BUFF_ON_REVIVE.isEmpty() || !PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF_ON_REVIVE.isEmpty()) {
                        for (Pair pair : player.isMageClass() ? PvPEventProperties.PVP_EVENTS_MAGE_BUFF_ON_REVIVE : PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF_ON_REVIVE) {
                            Skill skill = SkillTable.getInstance().getInfo(((Integer)pair.getLeft()).intValue(), ((Integer)pair.getRight()).intValue());
                            skill.getEffects((Creature)player, (Creature)player, false, false, PvPEventProperties.PVP_EVENTS_BUFF_ON_REVIVE_TIME, 1.0, false);
                        }
                    }
                }
            }
        }
    }

    private class TeleportTask
    extends RunnableImpl {
        private Player _player;
        private Location _loc;
        private Reflection a;

        public TeleportTask(Player player, Location location, Reflection reflection) {
            this._player = player;
            this._loc = location;
            this.a = reflection;
        }

        public void runImpl() throws Exception {
            this._player.teleToLocation(this._loc, this.a);
        }
    }

    @Deprecated
    private class TeleportListner
    implements OnTeleportListener {
        private TeleportListner() {
        }

        public void onTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
            try {
                if (PvPEvent.getInstance().a() != PvPEventState.COMPETITION) {
                    return;
                }
                PvPEvent.getInstance().getRule().getParticipantController().OnTeleport(player, n, n2, n3, reflection);
            }
            catch (Exception exception) {
                y.warn("PVPEvent.onTeleport :", (Throwable)exception);
            }
        }
    }

    private static class DMParticipantController
    implements IParticipantController {
        private static final RankComparator a = new RankComparator();
        private Map<Integer, AtomicInteger> m;
        private static final String ad = "pvp_dm_title";
        private ScheduledFuture<?> K;
        private Reflection _reflection = null;
        private int cq = 0;
        private String X = "[pvp_%d_dm_default]";
        private String ae = "[pvp_%d_dm_spawn]";
        private String ac = "backCoords";
        private Zone q = null;
        private Zone v = null;
        private Map<Integer, List<Effect>> l;

        private DMParticipantController() {
        }

        @Override
        public void prepareParticipantsTo() {
            this.m = new ConcurrentHashMap<Integer, AtomicInteger>();
            this.l = new ConcurrentHashMap<Integer, List<Effect>>();
            boolean bl = PvPEvent.getInstance().k();
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                if (PvPEvent.b(player)) continue;
                PvPEvent.getInstance().a.remove(player.getObjectId());
                this.OnExit(player);
            }
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                try {
                    List<Effect> list;
                    Skill skill;
                    player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                    if (player.isAttackingNow()) {
                        player.abortAttack(true, false);
                    }
                    if (player.isCastingNow()) {
                        player.abortCast(true, false);
                    }
                    player.sendActionFailed();
                    player.stopMove();
                    player.sitDown(null);
                    player.block();
                    HashSet<Effect> hashSet = new HashSet<Effect>();
                    if (bl) {
                        hashSet.addAll(player.getEffectList().getAllEffects());
                        if (player.getPet() != null) {
                            player.getPet().getEffectList().stopAllEffects();
                        }
                    }
                    boolean bl2 = false;
                    if (player.getClan() != null && PvPEventProperties.PVP_EVENTS_RESTRICTED_CLAN_SKILLS) {
                        player.getClan().disableSkills(player);
                    }
                    for (int i = 0; i < PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS.length; ++i) {
                        int n = PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS[i];
                        skill = player.getKnownSkill(n);
                        if (skill == null) continue;
                        if (skill.isToggle() && (list = player.getEffectList().getEffectsBySkill(skill)) != null && !list.isEmpty()) {
                            hashSet.addAll(list);
                        }
                        player.addUnActiveSkill(skill);
                        bl2 = true;
                    }
                    for (Effect effect : hashSet) {
                        skill = effect.getSkill();
                        if (!skill.isToggle()) {
                            Effect effect2;
                            list = this.l.get(player.getObjectId());
                            if (list == null) {
                                list = new ArrayList<Effect>();
                                this.l.put(player.getObjectId(), list);
                            }
                            if (!(effect2 = effect.getTemplate().getEffect(new Env(effect.getEffector(), effect.getEffected(), skill))).isSaveable()) continue;
                            effect2.setCount(effect.getCount());
                            effect2.setPeriod(effect.getCount() == 1 ? effect.getPeriod() - effect.getTime() : effect.getPeriod());
                            list.add(effect2);
                        }
                        effect.exit();
                    }
                    if (!PvPEventProperties.PVP_EVENTS_MAGE_BUFF.isEmpty() || !PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF.isEmpty()) {
                        for (Pair pair : player.isMageClass() ? PvPEventProperties.PVP_EVENTS_MAGE_BUFF : PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF) {
                            skill = SkillTable.getInstance().getInfo(((Integer)pair.getLeft()).intValue(), ((Integer)pair.getRight()).intValue());
                            skill.getEffects((Creature)player, (Creature)player, false, false, PvPEventProperties.PVP_EVENTS_BUFF_TIME, 1.0, false);
                        }
                    }
                    if (bl2) {
                        player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                        player.updateStats();
                        player.updateEffectIcons();
                    }
                    player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                    player.setCurrentCp((double)player.getMaxCp());
                    player.setVar(ad, player.getTitle() != null ? player.getTitle() : "", -1L);
                    this.m.put(player.getObjectId(), new AtomicInteger(0));
                    this.b(player, 0);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        private void b(Player player, int n) {
            player.setTransformationTitle(String.format("Kills: %d", n));
            player.setTitle(player.getTransformationTitle());
            player.broadcastPacket(new L2GameServerPacket[]{new NickNameChanged((Creature)player)});
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void prepareParticipantsFrom() {
            try {
                boolean bl = PvPEvent.getInstance().l();
                for (Player player : PvPEvent.getInstance().getPlayers()) {
                    try {
                        String string;
                        player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                        if (player.isAttackingNow()) {
                            player.abortAttack(true, false);
                        }
                        if (player.isCastingNow()) {
                            player.abortCast(true, false);
                        }
                        player.sendActionFailed();
                        player.stopMove();
                        player.sitDown(null);
                        player.block();
                        if (bl) {
                            player.getEffectList().stopAllEffects();
                        }
                        if (player.getClan() != null && PvPEventProperties.PVP_EVENTS_RESTRICTED_CLAN_SKILLS) {
                            player.getClan().enableSkills(player);
                        }
                        for (int i = 0; i < PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS.length; ++i) {
                            Skill skill;
                            int n = PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS[i];
                            if (!player.isUnActiveSkill(n) || (skill = player.getKnownSkill(n)) == null) continue;
                            player.removeUnActiveSkill(skill);
                        }
                        List<Effect> list = this.l.get(player.getObjectId());
                        if (list != null) {
                            for (Effect effect : list) {
                                if (player.getEffectList().getEffectsBySkill(effect.getSkill()) != null) continue;
                                player.getEffectList().addEffect(effect);
                            }
                        }
                        player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp(), true);
                        player.setCurrentCp((double)player.getMaxCp());
                        if (PvPEvent.getInstance().h() && player.getTransformation() == 0) {
                            player.setTransformationName(null);
                            player.setTransformationTitle(null);
                        }
                        if ((string = player.getVar(ad)) != null) {
                            player.setTitle(string);
                            player.unsetVar(ad);
                        }
                        player.sendUserInfo(true);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            finally {
                this.m.clear();
                if (this.l != null) {
                    this.l.clear();
                }
                this.m = null;
                this.l = null;
            }
        }

        @Override
        public void initParticipant() {
            ExPVPMatchCCRecord exPVPMatchCCRecord = new ExPVPMatchCCRecord(ExPVPMatchCCRecord.PVPMatchCCAction.INIT);
            this.a(exPVPMatchCCRecord);
            ExPVPMatchCCMyRecord exPVPMatchCCMyRecord = new ExPVPMatchCCMyRecord(0);
            boolean bl = PvPEvent.getInstance().i();
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                player.addListener((Listener)PvPEvent.getInstance().a);
                player.addListener((Listener)PvPEvent.getInstance().a);
                player.setResurectProhibited(true);
                player.unblock();
                player.standUp();
                player.sendPacket(new IStaticPacket[]{exPVPMatchCCRecord, exPVPMatchCCMyRecord});
                if (!bl) continue;
                o.getEffects((Creature)player, (Creature)player, false, false, false);
            }
            this.K = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new RankBroadcastTask(this)), 20000L);
        }

        private void a(ExPVPMatchCCRecord exPVPMatchCCRecord) {
            TreeSet<Map.Entry<Integer, AtomicInteger>> treeSet = new TreeSet<Map.Entry<Integer, AtomicInteger>>(a);
            treeSet.addAll(this.m.entrySet());
            int n = 0;
            for (Map.Entry<Integer, AtomicInteger> entry : treeSet) {
                int n2 = entry.getKey();
                int n3 = entry.getValue().get();
                Player player = GameObjectsStorage.getPlayer((int)n2);
                if (player == null) continue;
                exPVPMatchCCRecord.addPlayer(player, n3);
                if (++n < 25) continue;
                break;
            }
        }

        @Override
        public void doneParicipant() {
            if (this.K != null) {
                this.K.cancel(true);
                this.K = null;
            }
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                player.removeListener((Listener)PvPEvent.getInstance().a);
                player.removeListener((Listener)PvPEvent.getInstance().a);
                player.setResurectProhibited(false);
                player.unblock();
                if (player.isDead()) {
                    player.doRevive(100.0);
                }
                player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp(), true);
                player.setCurrentCp((double)player.getMaxCp());
                player.standUp();
            }
        }

        @Override
        public void portParticipantsTo() {
            int n = 0;
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                player.setVar(this.ac, player.getLoc().toXYZString(), -1L);
                if (player.getParty() != null) {
                    player.getParty().removePartyMember(player, false);
                }
                if (PvPEvent.getInstance().h()) {
                    player.setTransformationName(String.format("Player %d", ++n));
                }
                player.teleToLocation(this.c(), this.getReflection());
            }
        }

        @Override
        public void portParticipantsBack() {
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                String string;
                if (PvPEvent.getInstance().h() && player.getTransformation() == 0) {
                    player.setTransformationName(null);
                }
                if ((string = player.getVar(this.ac)) != null) {
                    player.unsetVar(this.ac);
                    player.teleToLocation(Location.parseLoc((String)string), ReflectionManager.DEFAULT);
                    continue;
                }
                player.teleToClosestTown();
            }
        }

        @Override
        public void initReflection() {
            this.cq = PvPEvent.getInstance().m();
            InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(this.cq);
            this.X = String.format("[pvp_%d_dm_default]", this.cq);
            this.ae = String.format("[pvp_%d_dm_spawn]", this.cq);
            this._reflection = new Reflection();
            this._reflection.init(instantZone);
            this.q = this._reflection.getZone(this.X);
            this.q.addListener((Listener)PvPEvent.getInstance().a);
            this.v = this._reflection.getZone(this.ae);
        }

        private Location c() {
            return this.v.getTerritory().getRandomLoc(this._reflection.getGeoIndex());
        }

        @Override
        public void doneReflection() {
            this.q.removeListener((Listener)PvPEvent.getInstance().a);
            this.q = null;
            this.v = null;
            this._reflection.collapse();
            this._reflection = null;
        }

        @Override
        public Reflection getReflection() {
            return this._reflection;
        }

        @Override
        public void OnPlayerDied(Player player, Player player2) {
            if (player != null && player2 != null && this.m.containsKey(player.getObjectId()) && this.m.containsKey(player2.getObjectId())) {
                int n = 0;
                AtomicInteger atomicInteger = this.m.get(player.getObjectId());
                AtomicInteger atomicInteger2 = this.m.get(player2.getObjectId());
                n = atomicInteger2.addAndGet(atomicInteger.getAndSet(0) + 1);
                player.sendPacket((IStaticPacket)new ExPVPMatchCCMyRecord(0));
                this.b(player, 0);
                player2.sendPacket((IStaticPacket)new ExPVPMatchCCMyRecord(n));
                this.b(player2, n);
                player2.broadcastUserInfo(true, new UserInfoType[0]);
            }
            ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
            PvPEvent pvPEvent = PvPEvent.getInstance();
            Objects.requireNonNull(pvPEvent);
            threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportAndReviveTask(player, this.c(), this.getReflection())), (long)(PvPEvent.getInstance().h() * 1000));
        }

        @Override
        public void OnEnter(Player player, Zone zone) {
            if (player != null && !this.m.containsKey(player.getObjectId())) {
                if (PvPEvent.getInstance().h() && player.getTransformation() == 0) {
                    player.setTransformationName(null);
                }
                player.teleToClosestTown();
            }
        }

        @Override
        public void OnLeave(Player player, Zone zone) {
            if (player != null && !this.q.checkIfInZone(player.getX(), player.getY(), player.getZ(), this.getReflection()) && this.m.containsKey(player.getObjectId())) {
                double d = Math.PI * 2 - PositionUtils.convertHeadingToRadian((int)player.getHeading());
                double d2 = Math.cos(d);
                double d3 = Math.sin(d);
                Location location = zone.getTerritory().getRandomLoc(player.getGeoIndex());
                for (int i = 32; i < 512; i += 32) {
                    int n = (int)Math.floor((double)player.getX() - (double)i * d2);
                    int n2 = (int)Math.floor((double)player.getY() + (double)i * d3);
                    if (!zone.getTerritory().isInside(n, n2)) continue;
                    location.set(n, n2, player.getZ(), player.getHeading());
                    break;
                }
                ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
                PvPEvent pvPEvent = PvPEvent.getInstance();
                Objects.requireNonNull(pvPEvent);
                threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportTask(player, location.correctGeoZ(), this.getReflection())), 3000L);
            }
        }

        @Override
        public void OnExit(Player player) {
            String string = player.getVar(ad);
            if (string != null) {
                player.setTitle(player.getVar(ad));
                player.unsetVar(ad);
            }
            this.m.remove(player.getObjectId());
        }

        @Override
        public void OnTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
            Location location;
            if (player != null && !this.q.checkIfInZone(n, n2, n3, this.getReflection()) && (location = this.c()) != null) {
                ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
                PvPEvent pvPEvent = PvPEvent.getInstance();
                Objects.requireNonNull(pvPEvent);
                threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportTask(player, location, this.getReflection())), 3000L);
            }
        }

        private void U() {
            List<Pair<ItemTemplate, Long>> list = PvPEvent.getInstance().b();
            if (list.isEmpty()) {
                return;
            }
            for (Map.Entry<Integer, AtomicInteger> entry : this.m.entrySet()) {
                int n = entry.getValue().get();
                int n2 = entry.getKey();
                Player player = GameObjectsStorage.getPlayer((int)n2);
                if (n <= 0 || player == null) continue;
                for (Pair<ItemTemplate, Long> pair : list) {
                    Functions.addItem((Playable)player, (int)((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight() * (long)n));
                }
            }
        }

        @Override
        public void MakeWinner() {
            int n = 0;
            int n2 = Integer.MIN_VALUE;
            for (Map.Entry<Integer, AtomicInteger> player : this.m.entrySet()) {
                int n3 = player.getValue().get();
                if (n3 <= n2) continue;
                n = player.getKey();
                n2 = n3;
            }
            ExPVPMatchCCRecord exPVPMatchCCRecord = new ExPVPMatchCCRecord(ExPVPMatchCCRecord.PVPMatchCCAction.DONE);
            this.a(exPVPMatchCCRecord);
            if (n != 0 && n2 > 0) {
                Player player = GameObjectsStorage.getPlayer((int)n);
                for (Pair<ItemTemplate, Long> pair : PvPEvent.getInstance().f()) {
                    Functions.addItem((Playable)player, (int)((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight()));
                }
                if (PvPEvent.getInstance().n() > 0) {
                    GlobalServices.makeCustomHero(player, (long)(PvPEvent.getInstance().n() * 60) * 60L);
                }
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{new ExEventMatchMessage("'" + player.getName() + "' winns!"), exPVPMatchCCRecord, new SystemMessage(SystemMsg.CONGRATULATIONS_C1_YOU_WIN_THE_MATCH).addName((GameObject)player)});
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.PlayerS1WonTheDMGame", new String[]{player.getName()});
            } else {
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{new ExEventMatchMessage("Tie"), exPVPMatchCCRecord, new SystemMessage(SystemMsg.THERE_IS_NO_VICTOR_THE_MATCH_ENDS_IN_A_TIE)});
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.TheDMGameEndedInATie", ArrayUtils.EMPTY_STRING_ARRAY);
            }
            this.U();
        }

        private class RankBroadcastTask
        extends RunnableImpl {
            private DMParticipantController a;

            public RankBroadcastTask(DMParticipantController dMParticipantController2) {
                this.a = dMParticipantController2;
            }

            public void runImpl() throws Exception {
                if (PvPEvent.getInstance().a() != PvPEventState.COMPETITION) {
                    return;
                }
                ExPVPMatchCCRecord exPVPMatchCCRecord = new ExPVPMatchCCRecord(ExPVPMatchCCRecord.PVPMatchCCAction.UPDATE);
                this.a.a(exPVPMatchCCRecord);
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{exPVPMatchCCRecord});
                this.a.K = ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 20000L);
            }
        }

        private static class RankComparator
        implements Comparator<Map.Entry<Integer, AtomicInteger>> {
            private RankComparator() {
            }

            @Override
            public int compare(Map.Entry<Integer, AtomicInteger> entry, Map.Entry<Integer, AtomicInteger> entry2) {
                try {
                    int n;
                    if (entry == null && entry2 == null) {
                        return 0;
                    }
                    if (entry == null) {
                        return 1;
                    }
                    if (entry2 == null) {
                        return -1;
                    }
                    int n2 = entry.getKey();
                    int n3 = entry2.getKey();
                    int n4 = entry.getValue().get();
                    return n4 != (n = entry2.getValue().get()) ? n - n4 : n3 - n2;
                }
                catch (Exception exception) {
                    return 0;
                }
            }
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class CTFParticipantController
    implements IParticipantController {
        private Map<Integer, AtomicInteger> j;
        private Map<Integer, AtomicInteger> k;
        private AtomicInteger c;
        private AtomicInteger d;
        private static final String V = "pvp_ctf_title";
        private String X = "[pvp_%d_ctf_default]";
        private String Y = "[pvp_%d_ctf_spawn_blue]";
        private String Z = "[pvp_%d_ctf_spawn_red]";
        private String aa = "[pvp_%d_ctf_spawn_flag_blue]";
        private String ab = "[pvp_%d_ctf_spawn_flag_red]";
        private String ac = "backCoords";
        private Reflection _reflection = null;
        private Zone q = null;
        private Zone r = null;
        private Zone s = null;
        private Zone t = null;
        private Zone u = null;
        private int cq = 0;
        private Map<Integer, List<Effect>> l;
        private WeakReference<CTFFlagInstance> b;
        private WeakReference<CTFFlagInstance> c;
        private ScheduledFuture<?> K;

        private CTFParticipantController() {
        }

        @Override
        public void prepareParticipantsTo() {
            this.j = new ConcurrentHashMap<Integer, AtomicInteger>();
            this.k = new ConcurrentHashMap<Integer, AtomicInteger>();
            this.l = new ConcurrentHashMap<Integer, List<Effect>>();
            this.c = new AtomicInteger(0);
            this.d = new AtomicInteger(0);
            TeamType teamType = TeamType.BLUE;
            for (Player object : PvPEvent.getInstance().getPlayers()) {
                if (PvPEvent.b(object)) continue;
                PvPEvent.getInstance().a.remove(object.getObjectId());
                this.OnExit(object);
            }
            boolean bl = PvPEvent.getInstance().k();
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                try {
                    List<Effect> list;
                    Skill skill;
                    player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                    if (player.isAttackingNow()) {
                        player.abortAttack(true, false);
                    }
                    if (player.isCastingNow()) {
                        player.abortCast(true, false);
                    }
                    player.sendActionFailed();
                    player.stopMove();
                    player.sitDown(null);
                    player.block();
                    boolean exception = false;
                    if (player.getClan() != null && PvPEventProperties.PVP_EVENTS_RESTRICTED_CLAN_SKILLS) {
                        player.getClan().disableSkills(player);
                    }
                    LinkedList<Effect> linkedList = new LinkedList<Effect>();
                    for (int i = 0; i < PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS.length; ++i) {
                        int effect = PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS[i];
                        skill = player.getKnownSkill(effect);
                        if (skill == null) continue;
                        if (skill.isToggle() && (list = player.getEffectList().getEffectsBySkill(skill)) != null && !list.isEmpty()) {
                            linkedList.addAll(list);
                        }
                        player.addUnActiveSkill(skill);
                        exception = true;
                    }
                    if (bl) {
                        linkedList.addAll(player.getEffectList().getAllEffects());
                        if (player.getPet() != null) {
                            player.getPet().getEffectList().stopAllEffects();
                        }
                    }
                    for (Effect pair : linkedList) {
                        skill = pair.getSkill();
                        if (!skill.isToggle()) {
                            Effect effect;
                            list = this.l.get(player.getObjectId());
                            if (list == null) {
                                list = new ArrayList<Effect>();
                                this.l.put(player.getObjectId(), list);
                            }
                            if (!(effect = pair.getTemplate().getEffect(new Env(pair.getEffector(), pair.getEffected(), skill))).isSaveable()) continue;
                            effect.setCount(pair.getCount());
                            effect.setPeriod(pair.getCount() == 1 ? pair.getPeriod() - pair.getTime() : pair.getPeriod());
                            list.add(effect);
                        }
                        pair.exit();
                    }
                    if (!PvPEventProperties.PVP_EVENTS_MAGE_BUFF.isEmpty() || !PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF.isEmpty()) {
                        for (Pair pair : player.isMageClass() ? PvPEventProperties.PVP_EVENTS_MAGE_BUFF : PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF) {
                            skill = SkillTable.getInstance().getInfo(((Integer)pair.getLeft()).intValue(), ((Integer)pair.getRight()).intValue());
                            skill.getEffects((Creature)player, (Creature)player, false, false, PvPEventProperties.PVP_EVENTS_BUFF_TIME, 1.0, false);
                        }
                    }
                    if (exception) {
                        player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                        player.updateStats();
                        player.updateEffectIcons();
                    }
                    player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                    player.setCurrentCp((double)player.getMaxCp());
                    player.sendChanges();
                    player.setVar(V, player.getTitle() != null ? player.getTitle() : "", -1L);
                    if (teamType == TeamType.BLUE) {
                        player.setTeam(TeamType.BLUE);
                        this.k.put(player.getObjectId(), new AtomicInteger(0));
                        teamType = TeamType.RED;
                        continue;
                    }
                    player.setTeam(TeamType.RED);
                    this.j.put(player.getObjectId(), new AtomicInteger(0));
                    teamType = TeamType.BLUE;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void prepareParticipantsFrom() {
            try {
                ((CTFFlagInstance)((Object)this.b.get())).removeFlag(null);
                ((CTFFlagInstance)((Object)((Reference)((Object)this.c)).get())).removeFlag(null);
                boolean bl = PvPEvent.getInstance().l();
                for (Player player : PvPEvent.getInstance().getPlayers()) {
                    player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                    if (player.isAttackingNow()) {
                        player.abortAttack(true, false);
                    }
                    if (player.isCastingNow()) {
                        player.abortCast(true, false);
                    }
                    player.sendActionFailed();
                    player.stopMove();
                    player.sitDown(null);
                    player.block();
                    if (bl) {
                        player.getEffectList().stopAllEffects();
                    }
                    if (player.getClan() != null && PvPEventProperties.PVP_EVENTS_RESTRICTED_CLAN_SKILLS) {
                        player.getClan().enableSkills(player);
                    }
                    for (int i = 0; i < PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS.length; ++i) {
                        Skill skill;
                        int n = PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS[i];
                        if (!player.isUnActiveSkill(n) || (skill = player.getKnownSkill(n)) == null) continue;
                        player.removeUnActiveSkill(skill);
                    }
                    List<Effect> list = this.l.get(player.getObjectId());
                    if (list != null) {
                        for (Effect effect : list) {
                            if (player.getEffectList().getEffectsBySkill(effect.getSkill()) != null) continue;
                            player.getEffectList().addEffect(effect);
                        }
                    }
                    player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                    player.setCurrentCp((double)player.getMaxCp());
                    player.sendChanges();
                    if (PvPEvent.getInstance().h() && player.getTransformation() == 0) {
                        player.setTransformationName(null);
                        player.setTransformationTitle(null);
                    }
                    player.setTeam(TeamType.NONE);
                    String string = player.getVar(V);
                    if (string != null) {
                        player.setTitle(string);
                        player.unsetVar(V);
                    }
                    player.sendUserInfo(true);
                }
            }
            finally {
                this.j.clear();
                this.k.clear();
                if (this.l != null) {
                    this.l.clear();
                }
                this.j = null;
                this.k = null;
                this.c = null;
                this.d = null;
                this.l = null;
            }
        }

        @Override
        public void initParticipant() {
            ExPVPMatchRecord exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.INIT, TeamType.NONE, 0, 0);
            ExPVPMatchRecord exPVPMatchRecord2 = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.UPDATE, TeamType.NONE, 0, 0);
            boolean bl = PvPEvent.getInstance().i();
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                player.addListener((Listener)PvPEvent.getInstance().a);
                player.addListener((Listener)PvPEvent.getInstance().a);
                player.setResurectProhibited(true);
                player.unblock();
                player.standUp();
                player.sendPacket(new IStaticPacket[]{exPVPMatchRecord, exPVPMatchRecord2});
                if (!bl) continue;
                o.getEffects((Creature)player, (Creature)player, false, false, false);
            }
        }

        @Override
        public void doneParicipant() {
            if (this.K != null) {
                this.K.cancel(true);
                this.K = null;
            }
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                player.removeListener((Listener)PvPEvent.getInstance().a);
                player.removeListener((Listener)PvPEvent.getInstance().a);
                player.setResurectProhibited(false);
                player.unblock();
                if (player.isDead()) {
                    player.doRevive(100.0);
                    player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                    player.setCurrentCp((double)player.getMaxCp());
                }
                player.standUp();
            }
        }

        @Override
        public void portParticipantsTo() {
            int n = 0;
            int n2 = 0;
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                TeamType teamType = player.getTeam();
                if (teamType != TeamType.BLUE && teamType != TeamType.RED) {
                    PvPEvent.getInstance().a.remove(player.getObjectId());
                    this.OnExit(player);
                    continue;
                }
                player.setVar(this.ac, player.getLoc().toXYZString(), -1L);
                if (player.getParty() != null) {
                    player.getParty().removePartyMember(player, false);
                }
                if (PvPEvent.getInstance().h()) {
                    switch (teamType) {
                        case RED: {
                            player.setTransformationName(String.format("Red %d", ++n));
                            break;
                        }
                        case BLUE: {
                            player.setTransformationName(String.format("Blue %d", ++n2));
                        }
                    }
                }
                player.teleToLocation(this.a(teamType), this.getReflection());
            }
        }

        @Override
        public void portParticipantsBack() {
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                String string = player.getVar(this.ac);
                if (string != null) {
                    player.unsetVar(this.ac);
                    player.teleToLocation(Location.parseLoc((String)string), ReflectionManager.DEFAULT);
                    continue;
                }
                player.teleToClosestTown();
            }
        }

        @Override
        public void initReflection() {
            this.cq = PvPEvent.getInstance().m();
            InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(this.cq);
            this.X = String.format("[pvp_%d_ctf_default]", this.cq);
            this.Y = String.format("[pvp_%d_ctf_spawn_blue]", this.cq);
            this.Z = String.format("[pvp_%d_ctf_spawn_red]", this.cq);
            this.aa = String.format("[pvp_%d_ctf_spawn_flag_blue]", this.cq);
            this.ab = String.format("[pvp_%d_ctf_spawn_flag_red]", this.cq);
            this._reflection = new Reflection();
            this._reflection.init(instantZone);
            this.q = this._reflection.getZone(this.X);
            this.r = this._reflection.getZone(this.Y);
            this.s = this._reflection.getZone(this.Z);
            this.t = this._reflection.getZone(this.aa);
            this.u = this._reflection.getZone(this.ab);
            this.q.addListener((Listener)PvPEvent.getInstance().a);
            this.r.addListener((Listener)PvPEvent.getInstance().a);
            this.s.addListener((Listener)PvPEvent.getInstance().a);
            CTFFlagInstance cTFFlagInstance = new CTFFlagInstance(TeamType.RED, this);
            cTFFlagInstance.setSpawnedLoc(this.u.getTerritory().getRandomLoc(this._reflection.getGeoIndex()));
            cTFFlagInstance.setReflection(this.getReflection());
            cTFFlagInstance.setCurrentHpMp(cTFFlagInstance.getMaxHp(), cTFFlagInstance.getMaxMp(), true);
            cTFFlagInstance.spawnMe(cTFFlagInstance.getSpawnedLoc());
            this.b = new WeakReference<CTFFlagInstance>(cTFFlagInstance);
            CTFFlagInstance cTFFlagInstance2 = new CTFFlagInstance(TeamType.BLUE, this);
            cTFFlagInstance2.setSpawnedLoc(this.t.getTerritory().getRandomLoc(this._reflection.getGeoIndex()));
            cTFFlagInstance2.setReflection(this.getReflection());
            cTFFlagInstance2.setCurrentHpMp(cTFFlagInstance2.getMaxHp(), cTFFlagInstance2.getMaxMp(), true);
            cTFFlagInstance2.spawnMe(cTFFlagInstance2.getSpawnedLoc());
            this.c = new WeakReference<CTFFlagInstance>(cTFFlagInstance2);
        }

        @Override
        public void doneReflection() {
            if (((Reference)((Object)this.c)).get() != null) {
                ((CTFFlagInstance)((Object)((Reference)((Object)this.c)).get())).destroy();
                ((Reference)((Object)this.c)).clear();
            }
            if (this.b.get() != null) {
                ((CTFFlagInstance)((Object)this.b.get())).destroy();
                this.b.clear();
            }
            this.b = null;
            this.c = null;
            this.q.removeListener((Listener)PvPEvent.getInstance().a);
            this.r.removeListener((Listener)PvPEvent.getInstance().a);
            this.s.removeListener((Listener)PvPEvent.getInstance().a);
            this.q = null;
            this.r = null;
            this.s = null;
            this._reflection.collapse();
            this._reflection = null;
        }

        @Override
        public Reflection getReflection() {
            return this._reflection;
        }

        @Override
        public void OnPlayerDied(Player player, Player player2) {
            ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
            PvPEvent pvPEvent = PvPEvent.getInstance();
            Objects.requireNonNull(pvPEvent);
            threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportAndReviveTask(player, this.a(player.getTeam()), this.getReflection())), (long)(PvPEvent.getInstance().h() * 1000));
        }

        @Override
        public void OnEnter(Player player, Zone zone) {
            if (player != null && !player.isDead()) {
                if (zone == this.q && player.getTeam() != TeamType.BLUE && player.getTeam() != TeamType.RED) {
                    player.teleToClosestTown();
                    y.warn("PvPEvent.CTF: '" + player.getName() + "' in zone.");
                } else if (zone == this.r && player.getTeam() == TeamType.BLUE && player.getObjectId() == ((CTFFlagInstance)((Object)this.b.get())).getOwnerOid()) {
                    ((CTFFlagInstance)((Object)this.b.get())).removeFlag(null);
                    this.d.incrementAndGet();
                    PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{new ExPVPMatchUserDie(this.d.get(), this.c.get())});
                } else if (zone == this.s && player.getTeam() == TeamType.RED && player.getObjectId() == ((CTFFlagInstance)((Object)((Reference)((Object)this.c)).get())).getOwnerOid()) {
                    ((CTFFlagInstance)((Object)((Reference)((Object)this.c)).get())).removeFlag(null);
                    this.c.incrementAndGet();
                    PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{new ExPVPMatchUserDie(this.d.get(), this.c.get())});
                }
            }
        }

        @Override
        public void OnLeave(Player player, Zone zone) {
            if (player != null && !this.q.checkIfInZone(player.getX(), player.getY(), player.getZ(), this.getReflection()) && zone == this.q) {
                if (player.getTeam() != TeamType.BLUE && player.getTeam() != TeamType.RED) {
                    if (PvPEvent.getInstance().h() && player.getTransformation() == 0) {
                        player.setTransformationName(null);
                    }
                    player.teleToClosestTown();
                    return;
                }
                double d = Math.PI * 2 - PositionUtils.convertHeadingToRadian((int)player.getHeading());
                double d2 = Math.cos(d);
                double d3 = Math.sin(d);
                Location location = zone.getTerritory().getRandomLoc(player.getGeoIndex());
                for (int i = 32; i < 512; i += 32) {
                    int n = (int)Math.floor((double)player.getX() - (double)i * d2);
                    int n2 = (int)Math.floor((double)player.getY() + (double)i * d3);
                    if (!zone.getTerritory().isInside(n, n2)) continue;
                    location.set(n, n2, player.getZ(), player.getHeading());
                    break;
                }
                ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
                PvPEvent pvPEvent = PvPEvent.getInstance();
                Objects.requireNonNull(pvPEvent);
                threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportTask(player, location.correctGeoZ(), this.getReflection())), 3000L);
                if (player.getObjectId() == ((CTFFlagInstance)((Object)((Reference)((Object)this.c)).get())).getOwnerOid()) {
                    ((CTFFlagInstance)((Object)((Reference)((Object)this.c)).get())).removeFlag(player);
                } else if (player.getObjectId() == ((CTFFlagInstance)((Object)this.b.get())).getOwnerOid()) {
                    ((CTFFlagInstance)((Object)this.b.get())).removeFlag(player);
                }
            }
        }

        @Override
        public void OnExit(Player player) {
            String string;
            if (this.k.containsKey(player.getObjectId())) {
                this.k.remove(player.getObjectId());
            } else if (this.j.containsKey(player.getObjectId())) {
                this.j.remove(player.getObjectId());
            }
            if (player.getTransformation() == 0) {
                player.setTransformationName(null);
                player.setTransformationTitle(null);
            }
            if ((string = player.getVar(V)) != null) {
                player.setTitle(player.getVar(V));
                player.unsetVar(V);
            }
        }

        @Override
        public void OnTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
            Location location;
            if (player != null && !this.q.checkIfInZone(n, n2, n3, reflection) && (location = this.a(player.getTeam())) != null) {
                ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
                PvPEvent pvPEvent = PvPEvent.getInstance();
                Objects.requireNonNull(pvPEvent);
                threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportTask(player, location, this.getReflection())), 3000L);
            }
        }

        private void a(Map<Integer, AtomicInteger> map, List<Pair<ItemTemplate, Long>> list) {
            for (Map.Entry<Integer, AtomicInteger> entry : map.entrySet()) {
                int n = entry.getKey();
                Player player = GameObjectsStorage.getPlayer((int)n);
                if (player == null) continue;
                for (Pair<ItemTemplate, Long> pair : list) {
                    Functions.addItem((Playable)player, (int)((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight()));
                }
            }
        }

        @Override
        public void MakeWinner() {
            int n = this.d.get();
            int n2 = this.c.get();
            ExPVPMatchRecord exPVPMatchRecord = null;
            SystemMessage systemMessage = null;
            if (n > n2) {
                exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.DONE, TeamType.BLUE, n, n2);
                systemMessage = (SystemMessage)new SystemMessage(SystemMsg.CONGRATULATIONS_C1_YOU_WIN_THE_MATCH).addString("Blue Team");
                this.a(this.k, PvPEvent.getInstance().c());
                this.a(this.j, PvPEvent.getInstance().d());
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.TeamBlueWonTheCTFGameCountIsS1S2", new String[]{String.valueOf(n), String.valueOf(n2)});
            } else if (n < n2) {
                exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.DONE, TeamType.RED, n, n2);
                systemMessage = (SystemMessage)new SystemMessage(SystemMsg.CONGRATULATIONS_C1_YOU_WIN_THE_MATCH).addString("Red Team");
                this.a(this.j, PvPEvent.getInstance().c());
                this.a(this.k, PvPEvent.getInstance().d());
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.TeamRedWonTheCTFGameCountIsS1S2", new String[]{String.valueOf(n2), String.valueOf(n)});
            } else if (n == n2) {
                exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.DONE, TeamType.NONE, n, n2);
                systemMessage = new SystemMessage(SystemMsg.THERE_IS_NO_VICTOR_THE_MATCH_ENDS_IN_A_TIE);
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.TheCTFGameEndedInATie", ArrayUtils.EMPTY_STRING_ARRAY);
                this.a(this.j, PvPEvent.getInstance().e());
                this.a(this.k, PvPEvent.getInstance().e());
            }
            PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.FINISH, systemMessage, exPVPMatchRecord});
        }

        private Location a(TeamType teamType) {
            if (teamType == TeamType.BLUE) {
                return this.r.getTerritory().getRandomLoc(this._reflection.getGeoIndex());
            }
            if (teamType == TeamType.RED) {
                return this.s.getTerritory().getRandomLoc(this._reflection.getGeoIndex());
            }
            return null;
        }

        private Location b(TeamType teamType) {
            if (teamType == TeamType.BLUE) {
                return this.t.getTerritory().getRandomLoc(this._reflection.getGeoIndex());
            }
            if (teamType == TeamType.RED) {
                return this.u.getTerritory().getRandomLoc(this._reflection.getGeoIndex());
            }
            return null;
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private class CTFFlagInstance
        extends MonsterInstance
        implements FlagItemAttachment {
            private ItemInstance a;
            private final TeamType _team;
            private CTFParticipantController a;

            public CTFFlagInstance(TeamType teamType, CTFParticipantController cTFParticipantController2) {
                super(IdFactory.getInstance().getNextId(), NpcHolder.getInstance().getTemplate(teamType == TeamType.BLUE ? PvPEventProperties.CTF_EVENT_BLUE_FLAG_NPC : (teamType == TeamType.RED ? PvPEventProperties.CTF_EVENT_RED_FLAG_NPC : -1)));
                this._team = teamType;
                this.a = ItemFunctions.createItem((int)(teamType == TeamType.BLUE ? PvPEventProperties.CTF_EVENT_BLUE_FLAG_ITEM : (teamType == TeamType.RED ? PvPEventProperties.CTF_EVENT_RED_FLAG_ITEM : -1)));
                this.a.setAttachment((ItemAttachment)this);
                this.a.getItemStateFlag().set((Enum)ItemStateFlags.STATE_CHANGED, false);
                this.a = cTFParticipantController2;
            }

            public void destroy() {
                Player player = GameObjectsStorage.getPlayer((int)this.a.getOwnerId());
                if (player != null) {
                    player.getInventory().destroyItem(this.a);
                    player.sendDisarmMessage(this.a);
                    player.stopAbnormalEffect(AbnormalEffect.ANTHARAS_RAGE_AVE);
                }
                this.a.setAttachment(null);
                this.a.deleteMe();
                this.a.delete();
                this.a = null;
                this.deleteMe();
            }

            public boolean isAutoAttackable(Creature creature) {
                return this.isAttackable(creature);
            }

            public boolean isAttackable(Creature creature) {
                return creature != null && creature.getTeam() != null && creature.getTeam() != TeamType.NONE && creature.getTeam() != this._team;
            }

            public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
                if (this.getDistance((GameObject)creature) > (double)PvPEventProperties.CTF_FLAG_MIN_ATTACK_DISTANCE) {
                    d = 0.0;
                }
                super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
            }

            protected void onDeath(Creature creature) {
                Player player;
                boolean bl = PvPEvent.getInstance().j();
                if (this.isAttackable(creature) && (player = creature.getPlayer()) != null && (this._team == TeamType.RED && creature.isInZone(this.a.s) || this._team == TeamType.BLUE && creature.isInZone(this.a.r))) {
                    player.getInventory().addItem(this.a);
                    player.startAbnormalEffect(AbnormalEffect.ANTHARAS_RAGE_AVE);
                    player.getInventory().equipItem(this.a);
                    this.a.getItemStateFlag().set((Enum)ItemStateFlags.STATE_CHANGED, false);
                    if (bl && creature.getEffectList().getEffectsBySkill(p) == null) {
                        p.getEffects(creature, creature, false, false, false);
                    }
                    player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("events.PvPEvent.TheCTFCaptureTheFlag", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
                    this.decayMe();
                    if (this._team == TeamType.RED) {
                        for (Integer n : this.a.j.keySet()) {
                            Player player2 = GameObjectsStorage.getPlayer((int)n);
                            ExShowScreenMessage exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("events.PvPEvent.RedTeamCaptureTheFlag", player2, new Object[0]).addString(player.getName()).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true);
                            if (player2 == null) continue;
                            player2.sendPacket((IStaticPacket)exShowScreenMessage);
                        }
                    } else if (this._team == TeamType.BLUE) {
                        for (Integer n : this.a.k.keySet()) {
                            Player player3 = GameObjectsStorage.getPlayer((int)n);
                            ExShowScreenMessage exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("events.PvPEvent.BlueTeamCaptureTheFlag", player3, new Object[0]).addString(player.getName()).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true);
                            if (player3 == null) continue;
                            player3.sendPacket((IStaticPacket)exShowScreenMessage);
                        }
                    }
                    return;
                }
                this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
            }

            public int getOwnerOid() {
                return this.a.getOwnerId();
            }

            public void removeFlag(Player player) {
                boolean bl = PvPEvent.getInstance().j();
                if (player == null) {
                    player = GameObjectsStorage.getPlayer((int)this.a.getOwnerId());
                }
                if (player != null) {
                    player.getInventory().removeItem(this.a);
                    player.sendDisarmMessage(this.a);
                    player.stopAbnormalEffect(AbnormalEffect.ANTHARAS_RAGE_AVE);
                    if (bl && player.getEffectList().getEffectsBySkill(p) != null) {
                        player.getEffectList().stopEffect(p);
                    }
                }
                this.a.setOwnerId(0);
                this.a.delete();
                this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
                this.spawnMe(this.a.b(this._team));
            }

            public boolean canPickUp(Player player) {
                return false;
            }

            public void pickUp(Player player) {
            }

            public void setItem(ItemInstance itemInstance) {
                if (itemInstance != null) {
                    itemInstance.setCustomFlags(39);
                }
            }

            public void onLogout(Player player) {
                player.getInventory().removeItem(this.a);
                this.a.setOwnerId(0);
                this.a.delete();
                this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
                this.spawnMe(this.a.b(this._team));
            }

            public void onDeath(Player player, Creature creature) {
                player.getInventory().removeItem(this.a);
                player.sendDisarmMessage(this.a);
                player.stopAbnormalEffect(AbnormalEffect.ANTHARAS_RAGE_AVE);
                this.a.setOwnerId(0);
                this.a.delete();
                this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
                this.spawnMe(this.a.b(this._team));
            }

            public void onEnterPeace(Player player) {
            }

            public boolean canAttack(Player player) {
                player.sendPacket((IStaticPacket)SystemMsg.THAT_WEAPON_CANNOT_PERFORM_ANY_ATTACKS);
                return false;
            }

            public boolean canCast(Player player, Skill skill) {
                player.sendPacket((IStaticPacket)SystemMsg.THAT_WEAPON_CANNOT_USE_ANY_OTHER_SKILL_EXCEPT_THE_WEAPONS_SKILL);
                return false;
            }

            public boolean isEffectImmune() {
                return true;
            }

            public boolean isDebuffImmune() {
                return true;
            }
        }
    }

    private static class TvTParticipantController
    implements IParticipantController {
        private static final RankComparator a = new RankComparator();
        private Map<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> j;
        private Map<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> k;
        private static final String af = "pvp_tvt_title";
        private int cq = 0;
        private String X = "[pvp_%d_tvt_default]";
        private String Y = "[pvp_%d_tvt_spawn_blue]";
        private String Z = "[pvp_%d_tvt_spawn_red]";
        private String ac = "backCoords";
        private Reflection _reflection = null;
        private Zone q = null;
        private AtomicInteger c;
        private AtomicInteger d;
        private Map<Integer, List<Effect>> l;
        private ScheduledFuture<?> K;

        private TvTParticipantController() {
        }

        public int getKills(TeamType teamType) {
            int n = 0;
            if (teamType == TeamType.RED) {
                for (ImmutablePair<AtomicInteger, AtomicInteger> immutablePair : this.j.values()) {
                    n += ((AtomicInteger)immutablePair.getLeft()).get();
                }
            }
            if (teamType == TeamType.BLUE) {
                for (ImmutablePair<AtomicInteger, AtomicInteger> immutablePair : this.k.values()) {
                    n += ((AtomicInteger)immutablePair.getLeft()).get();
                }
            }
            return n;
        }

        @Override
        public void prepareParticipantsTo() {
            this.j = new ConcurrentHashMap<Integer, ImmutablePair<AtomicInteger, AtomicInteger>>();
            this.k = new ConcurrentHashMap<Integer, ImmutablePair<AtomicInteger, AtomicInteger>>();
            this.c = new AtomicInteger(0);
            this.d = new AtomicInteger(0);
            this.l = new ConcurrentHashMap<Integer, List<Effect>>();
            TeamType teamType = TeamType.BLUE;
            boolean bl = PvPEvent.getInstance().k();
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                if (PvPEvent.b(player)) continue;
                PvPEvent.getInstance().a.remove(player.getObjectId());
                this.OnExit(player);
            }
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                List<Effect> list;
                Skill skill;
                player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                if (player.isAttackingNow()) {
                    player.abortAttack(true, false);
                }
                if (player.isCastingNow()) {
                    player.abortCast(true, false);
                }
                player.sendActionFailed();
                player.stopMove();
                player.sitDown(null);
                player.block();
                boolean bl2 = false;
                if (player.getClan() != null && PvPEventProperties.PVP_EVENTS_RESTRICTED_CLAN_SKILLS) {
                    player.getClan().disableSkills(player);
                }
                HashSet<Effect> hashSet = new HashSet<Effect>();
                for (int i = 0; i < PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS.length; ++i) {
                    int n = PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS[i];
                    skill = player.getKnownSkill(n);
                    if (skill == null) continue;
                    if (skill.isToggle() && (list = player.getEffectList().getEffectsBySkill(skill)) != null && !list.isEmpty()) {
                        hashSet.addAll(list);
                    }
                    player.addUnActiveSkill(skill);
                    bl2 = true;
                }
                player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                player.setCurrentCp((double)player.getMaxCp());
                player.sendChanges();
                player.setVar(af, player.getTitle() != null ? player.getTitle() : "", -1L);
                if (bl) {
                    hashSet.addAll(player.getEffectList().getAllEffects());
                    if (player.getPet() != null) {
                        player.getPet().getEffectList().stopAllEffects();
                    }
                }
                for (Effect effect : hashSet) {
                    skill = effect.getSkill();
                    if (!skill.isToggle()) {
                        Effect effect2;
                        list = this.l.get(player.getObjectId());
                        if (list == null) {
                            list = new ArrayList<Effect>();
                            this.l.put(player.getObjectId(), list);
                        }
                        if (!(effect2 = effect.getTemplate().getEffect(new Env(effect.getEffector(), effect.getEffected(), skill))).isSaveable()) continue;
                        effect2.setCount(effect.getCount());
                        effect2.setPeriod(effect.getCount() == 1 ? effect.getPeriod() - effect.getTime() : effect.getPeriod());
                        list.add(effect2);
                    }
                    effect.exit();
                }
                if (!PvPEventProperties.PVP_EVENTS_MAGE_BUFF.isEmpty() || !PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF.isEmpty()) {
                    for (Pair pair : player.isMageClass() ? PvPEventProperties.PVP_EVENTS_MAGE_BUFF : PvPEventProperties.PVP_EVENTS_WARRIOR_BUFF) {
                        skill = SkillTable.getInstance().getInfo(((Integer)pair.getLeft()).intValue(), ((Integer)pair.getRight()).intValue());
                        skill.getEffects((Creature)player, (Creature)player, false, false, PvPEventProperties.PVP_EVENTS_BUFF_TIME, 1.0, false);
                    }
                }
                if (bl2) {
                    player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                    player.updateStats();
                    player.updateEffectIcons();
                }
                if (teamType == TeamType.BLUE) {
                    player.setTeam(TeamType.BLUE);
                    this.k.put(player.getObjectId(), (ImmutablePair<AtomicInteger, AtomicInteger>)new ImmutablePair((Object)new AtomicInteger(0), (Object)new AtomicInteger(0)));
                    teamType = TeamType.RED;
                } else {
                    player.setTeam(TeamType.RED);
                    this.j.put(player.getObjectId(), (ImmutablePair<AtomicInteger, AtomicInteger>)new ImmutablePair((Object)new AtomicInteger(0), (Object)new AtomicInteger(0)));
                    teamType = TeamType.BLUE;
                }
                this.b(player, 0);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void prepareParticipantsFrom() {
            try {
                boolean bl = PvPEvent.getInstance().l();
                for (Player player : PvPEvent.getInstance().getPlayers()) {
                    try {
                        player.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                        if (player.isAttackingNow()) {
                            player.abortAttack(true, false);
                        }
                        if (player.isCastingNow()) {
                            player.abortCast(true, false);
                        }
                        player.sendActionFailed();
                        player.stopMove();
                        player.sitDown(null);
                        player.block();
                        if (bl) {
                            player.getEffectList().stopAllEffects();
                        }
                        if (player.getClan() != null && PvPEventProperties.PVP_EVENTS_RESTRICTED_CLAN_SKILLS) {
                            player.getClan().enableSkills(player);
                        }
                        for (int i = 0; i < PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS.length; ++i) {
                            Skill skill;
                            int n = PvPEventProperties.PVP_EVENTS_RESTRICTED_SKILL_IDS[i];
                            if (!player.isUnActiveSkill(n) || (skill = player.getKnownSkill(n)) == null) continue;
                            player.removeUnActiveSkill(skill);
                        }
                        List<Effect> list = this.l.get(player.getObjectId());
                        if (list != null) {
                            for (Effect effect : list) {
                                if (player.getEffectList().getEffectsBySkill(effect.getSkill()) != null) continue;
                                player.getEffectList().addEffect(effect);
                            }
                        }
                        player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                        player.setCurrentCp((double)player.getMaxCp());
                        player.sendChanges();
                        player.setTeam(TeamType.NONE);
                        String string = player.getVar(af);
                        if (string != null) {
                            player.setTitle(string);
                            player.unsetVar(af);
                        }
                        if (PvPEvent.getInstance().h() && player.getTransformation() == 0) {
                            player.setTransformationName(null);
                            player.setTransformationTitle(null);
                        }
                        player.sendUserInfo(true);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            finally {
                this.j.clear();
                this.k.clear();
                if (this.l != null) {
                    this.l.clear();
                }
                this.j = null;
                this.k = null;
                this.c = null;
                this.d = null;
                this.l = null;
            }
        }

        @Override
        public void initParticipant() {
            ExPVPMatchRecord exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.INIT, TeamType.NONE, 0, 0);
            ExPVPMatchRecord exPVPMatchRecord2 = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.UPDATE, TeamType.NONE, 0, 0);
            this.a(exPVPMatchRecord2);
            boolean bl = PvPEvent.getInstance().i();
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                player.addListener((Listener)PvPEvent.getInstance().a);
                player.addListener((Listener)PvPEvent.getInstance().a);
                player.setResurectProhibited(true);
                player.unblock();
                player.standUp();
                player.sendPacket(new IStaticPacket[]{exPVPMatchRecord, exPVPMatchRecord2});
                if (!bl) continue;
                o.getEffects((Creature)player, (Creature)player, false, false, false);
            }
            if (this.K != null) {
                this.K.cancel(false);
            }
            this.K = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new RankBroadcastTask(this)), 20000L);
        }

        @Override
        public void doneParicipant() {
            if (this.K != null) {
                this.K.cancel(true);
                this.K = null;
            }
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                player.removeListener((Listener)PvPEvent.getInstance().a);
                player.removeListener((Listener)PvPEvent.getInstance().a);
                player.setResurectProhibited(false);
                player.unblock();
                if (player.isDead()) {
                    player.doRevive(100.0);
                    player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                    player.setCurrentCp((double)player.getMaxCp());
                }
                player.standUp();
            }
        }

        private void b(Player player, int n) {
            player.setTransformationTitle(String.format("Kills: %d", n));
            player.setTitle(player.getTransformationTitle());
            player.broadcastPacket(new L2GameServerPacket[]{new NickNameChanged((Creature)player)});
        }

        @Override
        public void OnPlayerDied(Player player, Player player2) {
            ImmutablePair<AtomicInteger, AtomicInteger> immutablePair;
            if (player2 != null && player2.getTeam() != player.getTeam()) {
                if (player2.getTeam() == TeamType.RED && this.j.containsKey(player2.getObjectId())) {
                    immutablePair = this.j.get(player2.getObjectId());
                    AtomicInteger atomicInteger = (AtomicInteger)immutablePair.getLeft();
                    this.b(player2, atomicInteger.incrementAndGet());
                    this.c.incrementAndGet();
                } else if (player2.getTeam() == TeamType.BLUE && this.k.containsKey(player2.getObjectId())) {
                    immutablePair = this.k.get(player2.getObjectId());
                    AtomicInteger atomicInteger = (AtomicInteger)immutablePair.getLeft();
                    this.b(player2, atomicInteger.incrementAndGet());
                    this.d.incrementAndGet();
                } else if (player2.getTeam() != TeamType.NONE) {
                    y.warn("PvPEvent.TVT: '" + player2.getName() + "' got color but not at list.");
                }
                player2.sendUserInfo(true);
            }
            if (player.getTeam() == TeamType.RED && this.j.containsKey(player.getObjectId())) {
                immutablePair = this.j.get(player.getObjectId());
                ((AtomicInteger)immutablePair.getRight()).incrementAndGet();
            } else if (player.getTeam() == TeamType.BLUE && this.k.containsKey(player.getObjectId())) {
                immutablePair = this.k.get(player.getObjectId());
                ((AtomicInteger)immutablePair.getRight()).incrementAndGet();
            }
            ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
            PvPEvent pvPEvent = PvPEvent.getInstance();
            Objects.requireNonNull(pvPEvent);
            threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportAndReviveTask(player, this.a(player.getTeam()), this.getReflection())), (long)(PvPEvent.getInstance().h() * 1000));
            PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{new ExPVPMatchUserDie(this.d.get(), this.c.get())});
        }

        @Override
        public void portParticipantsTo() {
            int n = 0;
            int n2 = 0;
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                TeamType teamType = player.getTeam();
                if (teamType != TeamType.BLUE && teamType != TeamType.RED) {
                    PvPEvent.getInstance().a.remove(player.getObjectId());
                    this.OnExit(player);
                    continue;
                }
                player.setVar(this.ac, player.getLoc().toXYZString(), -1L);
                if (player.getParty() != null) {
                    player.getParty().removePartyMember(player, false);
                }
                if (PvPEvent.getInstance().h()) {
                    switch (teamType) {
                        case RED: {
                            player.setTransformationName(String.format("Red %d", ++n));
                            break;
                        }
                        case BLUE: {
                            player.setTransformationName(String.format("Blue %d", ++n2));
                        }
                    }
                }
                player.teleToLocation(this.a(teamType), this.getReflection());
            }
        }

        @Override
        public void portParticipantsBack() {
            for (Player player : PvPEvent.getInstance().getPlayers()) {
                String string;
                if (player.getTransformation() == 0) {
                    player.setTransformationName(null);
                }
                if ((string = player.getVar(this.ac)) != null) {
                    player.unsetVar(this.ac);
                    player.teleToLocation(Location.parseLoc((String)string), ReflectionManager.DEFAULT);
                    continue;
                }
                player.teleToClosestTown();
            }
        }

        @Override
        public void initReflection() {
            this.cq = PvPEvent.getInstance().m();
            InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(this.cq);
            this.X = String.format("[pvp_%d_tvt_default]", this.cq);
            this.Y = String.format("[pvp_%d_tvt_spawn_blue]", this.cq);
            this.Z = String.format("[pvp_%d_tvt_spawn_red]", this.cq);
            this._reflection = new Reflection();
            this._reflection.init(instantZone);
            this.q = this._reflection.getZone(this.X);
            this.q.addListener((Listener)PvPEvent.getInstance().a);
        }

        @Override
        public void doneReflection() {
            this.q.removeListener((Listener)PvPEvent.getInstance().a);
            this._reflection.collapse();
            this._reflection = null;
        }

        @Override
        public Reflection getReflection() {
            return this._reflection;
        }

        private Location a(TeamType teamType) {
            if (teamType == TeamType.BLUE) {
                return this._reflection.getZone(this.Y).getTerritory().getRandomLoc(this._reflection.getGeoIndex());
            }
            if (teamType == TeamType.RED) {
                return this._reflection.getZone(this.Z).getTerritory().getRandomLoc(this._reflection.getGeoIndex());
            }
            return null;
        }

        @Override
        public void OnEnter(Player player, Zone zone) {
            if (player != null && !player.isGM() && player.getTeam() != TeamType.BLUE && player.getTeam() != TeamType.RED && zone == this.q) {
                player.teleToClosestTown();
            }
        }

        @Override
        public void OnLeave(Player player, Zone zone) {
            if (player != null && !this.q.checkIfInZone(player.getX(), player.getY(), player.getZ(), this.getReflection())) {
                if (player.getTeam() != TeamType.BLUE && player.getTeam() != TeamType.RED && zone == this.q) {
                    player.teleToClosestTown();
                    return;
                }
                double d = Math.PI * 2 - PositionUtils.convertHeadingToRadian((int)player.getHeading());
                double d2 = Math.cos(d);
                double d3 = Math.sin(d);
                Location location = zone.getTerritory().getRandomLoc(player.getGeoIndex());
                for (int i = 32; i < 512; i += 32) {
                    int n = (int)Math.floor((double)player.getX() - (double)i * d2);
                    int n2 = (int)Math.floor((double)player.getY() + (double)i * d3);
                    if (!zone.getTerritory().isInside(n, n2)) continue;
                    location.set(n, n2, player.getZ(), player.getHeading());
                    break;
                }
                ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
                PvPEvent pvPEvent = PvPEvent.getInstance();
                Objects.requireNonNull(pvPEvent);
                threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportTask(player, location.correctGeoZ(), this.getReflection())), 3000L);
            }
        }

        @Override
        public void OnExit(Player player) {
            if (this.k.containsKey(player.getObjectId())) {
                this.k.remove(player.getObjectId());
            } else if (this.j.containsKey(player.getObjectId())) {
                this.j.remove(player.getObjectId());
            }
            String string = player.getVar(af);
            if (string != null) {
                player.setTitle(player.getVar(af));
                player.unsetVar(af);
            }
        }

        @Override
        public void OnTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
            Location location;
            if (player != null && !this.q.checkIfInZone(n, n2, n3, this.getReflection()) && (location = this.a(player.getTeam())) != null) {
                ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
                PvPEvent pvPEvent = PvPEvent.getInstance();
                Objects.requireNonNull(pvPEvent);
                threadPoolManager.schedule((Runnable)((Object)pvPEvent.new TeleportTask(player, location, this.getReflection())), 3000L);
            }
        }

        private void a(Map<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> map) {
            for (Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> entry : map.entrySet()) {
                int n = entry.getKey();
                ImmutablePair<AtomicInteger, AtomicInteger> immutablePair = entry.getValue();
                int n2 = ((AtomicInteger)immutablePair.getLeft()).get();
                Player player = GameObjectsStorage.getPlayer((int)n);
                if (n2 <= 0 || player == null) continue;
                this.a(player, PvPEvent.getInstance().b(), n2);
            }
        }

        private void a(Player player, List<Pair<ItemTemplate, Long>> list, int n) {
            for (Pair<ItemTemplate, Long> pair : list) {
                Functions.addItem((Playable)player, (int)((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight() * (long)n));
            }
        }

        private void a(Map<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> map, List<Pair<ItemTemplate, Long>> list, List<Pair<ItemTemplate, Long>> list2) {
            int n = -1;
            int n2 = Integer.MIN_VALUE;
            Player player = map.entrySet().iterator();
            while (player.hasNext()) {
                Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> entry = player.next();
                int n3 = entry.getKey();
                ImmutablePair<AtomicInteger, AtomicInteger> immutablePair = entry.getValue();
                int n4 = ((AtomicInteger)immutablePair.getLeft()).get();
                Player player2 = GameObjectsStorage.getPlayer((int)n3);
                if (player2 == null) continue;
                boolean bl = false;
                if (n4 >= PvPEventProperties.PVP_EVENT_CHECK_MIN_KILL_COUNT_FOR_REWARD) {
                    for (Pair<ItemTemplate, Long> pair : list) {
                        bl = true;
                        Functions.addItem((Playable)player2, (int)((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight()));
                    }
                }
                if (n2 < n4) {
                    n2 = n4;
                    n = n3;
                }
                if (bl) continue;
                player2.sendMessage(new CustomMessage("PVPEVENTS_NO_PRIZE", player2, new Object[0]).addNumber((long)PvPEventProperties.PVP_EVENT_CHECK_MIN_KILL_COUNT_FOR_REWARD));
            }
            if (n > 0 && n2 > 0 && list2 != null && (player = GameObjectsStorage.getPlayer((int)n)) != null) {
                for (Pair pair : list2) {
                    Functions.addItem((Playable)player, (int)((ItemTemplate)pair.getLeft()).getItemId(), (long)((Long)pair.getRight()));
                }
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.TheTvTGameTopPlayerIsS1", new String[]{player.getName(), player.getTeam().name()});
                if (PvPEvent.getInstance().n() > 0) {
                    GlobalServices.makeCustomHero(player, (long)(PvPEvent.getInstance().n() * 60) * 60L);
                }
            }
        }

        @Override
        public void MakeWinner() {
            boolean bl = PvPEventProperties.getBooleanProperty(PvPEvent.EVENT_NAME, "EventCountdown", true);
            int n = this.d.get();
            int n2 = this.c.get();
            ExPVPMatchRecord exPVPMatchRecord = null;
            SystemMessage systemMessage = null;
            if (n > n2) {
                exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.DONE, TeamType.BLUE, n, n2);
                systemMessage = (SystemMessage)new SystemMessage(SystemMsg.CONGRATULATIONS_C1_YOU_WIN_THE_MATCH).addString("Blue Team");
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.TeamBlueWonTheTvTGameCountIsS1S2", new String[]{String.valueOf(n), String.valueOf(n2)});
                this.a(this.k, PvPEvent.getInstance().c(), PvPEvent.getInstance().f());
                this.a(this.j, PvPEvent.getInstance().d(), PvPEvent.getInstance().g());
            } else if (n < n2) {
                exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.DONE, TeamType.RED, n, n2);
                systemMessage = (SystemMessage)new SystemMessage(SystemMsg.CONGRATULATIONS_C1_YOU_WIN_THE_MATCH).addString("Red Team");
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.TeamRedWonTheTvTGameCountIsS1S2", new String[]{String.valueOf(n2), String.valueOf(n)});
                this.a(this.j, PvPEvent.getInstance().c(), PvPEvent.getInstance().f());
                this.a(this.k, PvPEvent.getInstance().d(), PvPEvent.getInstance().g());
            } else if (n == n2) {
                exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.DONE, TeamType.NONE, n, n2);
                systemMessage = new SystemMessage(SystemMsg.THERE_IS_NO_VICTOR_THE_MATCH_ENDS_IN_A_TIE);
                Announcements.getInstance().announceByCustomMessage("events.PvPEvent.TheTvTGameEndedInATie", ArrayUtils.EMPTY_STRING_ARRAY);
                this.a(this.j, PvPEvent.getInstance().e(), PvPEvent.getInstance().h());
                this.a(this.k, PvPEvent.getInstance().e(), PvPEvent.getInstance().h());
            }
            this.a(this.j);
            this.a(this.k);
            this.a(exPVPMatchRecord);
            if (bl) {
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{ExEventMatchMessage.FINISH});
            }
            PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{systemMessage, exPVPMatchRecord});
        }

        private void a(ExPVPMatchRecord exPVPMatchRecord) {
            int n;
            TreeSet<Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>>> treeSet = new TreeSet<Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>>>(a);
            treeSet.addAll(this.k.entrySet());
            int n2 = 0;
            for (Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> entry : treeSet) {
                int n3 = entry.getKey();
                int n4 = ((AtomicInteger)entry.getValue().getLeft()).get();
                n = ((AtomicInteger)entry.getValue().getRight()).get();
                Player player = GameObjectsStorage.getPlayer((int)n3);
                if (player == null) continue;
                exPVPMatchRecord.addRecord(player, n4, n);
                if (++n2 < 12) continue;
                break;
            }
            treeSet.clear();
            treeSet = null;
            Object object = new TreeSet<Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>>>(a);
            ((TreeSet)object).addAll(this.j.entrySet());
            int n5 = 0;
            Iterator iterator = ((TreeSet)object).iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                n = (Integer)entry.getKey();
                int n6 = ((AtomicInteger)((ImmutablePair)entry.getValue()).getLeft()).get();
                int n7 = ((AtomicInteger)((ImmutablePair)entry.getValue()).getRight()).get();
                Player player = GameObjectsStorage.getPlayer((int)n);
                if (player == null) continue;
                exPVPMatchRecord.addRecord(player, n6, n7);
                if (++n5 < 12) continue;
                break;
            }
            ((TreeSet)object).clear();
            object = null;
        }

        private class RankBroadcastTask
        extends RunnableImpl {
            private TvTParticipantController a;

            public RankBroadcastTask(TvTParticipantController tvTParticipantController2) {
                this.a = tvTParticipantController2;
            }

            public void runImpl() throws Exception {
                if (PvPEvent.getInstance().a() != PvPEventState.COMPETITION) {
                    return;
                }
                ExPVPMatchRecord exPVPMatchRecord = new ExPVPMatchRecord(ExPVPMatchRecord.PVPMatchAction.UPDATE, TeamType.NONE, this.a.d.get(), this.a.c.get());
                this.a.a(exPVPMatchRecord);
                PvPEvent.getInstance().broadcast(new L2GameServerPacket[]{exPVPMatchRecord});
                this.a.K = ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 20000L);
            }
        }

        private static class RankComparator
        implements Comparator<Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>>> {
            private RankComparator() {
            }

            @Override
            public int compare(Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> entry, Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> entry2) {
                try {
                    int n;
                    if (entry == null && entry2 == null) {
                        return 1;
                    }
                    if (entry == null) {
                        return 1;
                    }
                    if (entry2 == null) {
                        return -1;
                    }
                    int n2 = entry.getKey();
                    int n3 = entry2.getKey();
                    int n4 = ((AtomicInteger)entry.getValue().getLeft()).get();
                    return n4 != (n = ((AtomicInteger)entry2.getValue().getLeft()).get()) ? n - n4 : n3 - n2;
                }
                catch (Exception exception) {
                    return 0;
                }
            }
        }
    }
}
