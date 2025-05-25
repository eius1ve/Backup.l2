/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Announcements
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.data.xml.holder.InstantZoneHolder
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Effect
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExEventMatchMessage
 *  l2.gameserver.network.l2.s2c.ExPVPMatchRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchRecord$PVPMatchAction
 *  l2.gameserver.network.l2.s2c.ExPVPMatchUserDie
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.NickNameChanged
 *  l2.gameserver.network.l2.s2c.SkillCoolTime
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.stats.Env
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.InstantZone
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.tuple.ImmutablePair
 *  org.apache.commons.lang3.tuple.Pair
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import events.TvT2.PvPEventProperties;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Announcements;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExEventMatchMessage;
import l2.gameserver.network.l2.s2c.ExPVPMatchRecord;
import l2.gameserver.network.l2.s2c.ExPVPMatchUserDie;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NickNameChanged;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.stats.Env;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import services.GlobalServices;

private static class PvPEvent.TvTParticipantController
implements PvPEvent.IParticipantController {
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

    private PvPEvent.TvTParticipantController() {
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
        threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportAndReviveTask(pvPEvent, player, this.a(player.getTeam()), this.getReflection())), (long)(PvPEvent.getInstance().h() * 1000));
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
            threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportTask(pvPEvent, player, location.correctGeoZ(), this.getReflection())), 3000L);
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
            threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportTask(pvPEvent, player, location, this.getReflection())), 3000L);
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
        private PvPEvent.TvTParticipantController a;

        public RankBroadcastTask(PvPEvent.TvTParticipantController tvTParticipantController2) {
            this.a = tvTParticipantController2;
        }

        public void runImpl() throws Exception {
            if (PvPEvent.getInstance().a() != PvPEvent.PvPEventState.COMPETITION) {
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
