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
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExEventMatchMessage
 *  l2.gameserver.network.l2.s2c.ExPVPMatchCCMyRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord
 *  l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord$PVPMatchCCAction
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.NickNameChanged
 *  l2.gameserver.network.l2.s2c.SkillCoolTime
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.stats.Env
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.InstantZone
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import events.TvT2.PvPEventProperties;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExEventMatchMessage;
import l2.gameserver.network.l2.s2c.ExPVPMatchCCMyRecord;
import l2.gameserver.network.l2.s2c.ExPVPMatchCCRecord;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NickNameChanged;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.stats.Env;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import services.GlobalServices;

private static class PvPEvent.DMParticipantController
implements PvPEvent.IParticipantController {
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

    private PvPEvent.DMParticipantController() {
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
        threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportAndReviveTask(pvPEvent, player, this.c(), this.getReflection())), (long)(PvPEvent.getInstance().h() * 1000));
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
            threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportTask(pvPEvent, player, location.correctGeoZ(), this.getReflection())), 3000L);
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
            threadPoolManager.schedule((Runnable)((Object)new PvPEvent.TeleportTask(pvPEvent, player, location, this.getReflection())), 3000L);
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
        private PvPEvent.DMParticipantController a;

        public RankBroadcastTask(PvPEvent.DMParticipantController dMParticipantController2) {
            this.a = dMParticipantController2;
        }

        public void runImpl() throws Exception {
            if (PvPEvent.getInstance().a() != PvPEvent.PvPEventState.COMPETITION) {
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
