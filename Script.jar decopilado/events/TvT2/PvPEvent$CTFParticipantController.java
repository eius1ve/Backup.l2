/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Announcements
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.data.xml.holder.InstantZoneHolder
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.idfactory.IdFactory
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Effect
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.ItemStateFlags
 *  l2.gameserver.model.items.attachment.FlagItemAttachment
 *  l2.gameserver.model.items.attachment.ItemAttachment
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExEventMatchMessage
 *  l2.gameserver.network.l2.s2c.ExPVPMatchRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchRecord$PVPMatchAction
 *  l2.gameserver.network.l2.s2c.ExPVPMatchUserDie
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.SkillCoolTime
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.skills.AbnormalEffect
 *  l2.gameserver.stats.Env
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.InstantZone
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import events.TvT2.PvPEventProperties;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.listener.Listener;
import l2.gameserver.Announcements;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.ReflectionManager;
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
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.ItemStateFlags;
import l2.gameserver.model.items.attachment.FlagItemAttachment;
import l2.gameserver.model.items.attachment.ItemAttachment;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExEventMatchMessage;
import l2.gameserver.network.l2.s2c.ExPVPMatchRecord;
import l2.gameserver.network.l2.s2c.ExPVPMatchUserDie;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.stats.Env;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
private static class PvPEvent.CTFParticipantController
implements PvPEvent.IParticipantController {
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

    private PvPEvent.CTFParticipantController() {
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
        threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportAndReviveTask(pvPEvent, player, this.a(player.getTeam()), this.getReflection())), (long)(PvPEvent.getInstance().h() * 1000));
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
            threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportTask(pvPEvent, player, location.correctGeoZ(), this.getReflection())), 3000L);
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
            threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportTask(pvPEvent, player, location, this.getReflection())), 3000L);
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
        private PvPEvent.CTFParticipantController a;

        public CTFFlagInstance(TeamType teamType, PvPEvent.CTFParticipantController cTFParticipantController2) {
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
