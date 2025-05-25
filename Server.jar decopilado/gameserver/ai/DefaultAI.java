/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.ai;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ScheduledFuture;
import l2.commons.collections.CollectionUtils;
import l2.commons.collections.LazyArrayList;
import l2.commons.lang.reference.HardReference;
import l2.commons.math.random.RndSelector;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.MinionList;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.WorldRegion;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.model.quest.QuestEventType;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.stats.Stats;
import l2.gameserver.taskmanager.AiTaskManager;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAI
extends CharacterAI {
    protected static final Logger _log = LoggerFactory.getLogger(DefaultAI.class);
    public static final int TaskDefaultWeight = 10000;
    protected long AI_TASK_ATTACK_DELAY = Config.AI_TASK_ATTACK_DELAY;
    protected long AI_TASK_ACTIVE_DELAY;
    protected long AI_TASK_DELAY_CURRENT = this.AI_TASK_ACTIVE_DELAY = Config.AI_TASK_ACTIVE_DELAY;
    protected int MAX_PURSUE_RANGE;
    protected ScheduledFuture<?> _aiTask;
    protected ScheduledFuture<?> _runningTask;
    protected ScheduledFuture<?> _madnessTask;
    private boolean n = false;
    protected boolean _def_think = false;
    protected long _globalAggro;
    protected long _randomAnimationEnd;
    protected int _pathfindFails;
    protected final NavigableSet<Task> _tasks = new ConcurrentSkipListSet<Task>(TaskComparator.getInstance());
    protected final Skill[] _damSkills;
    protected final Skill[] _dotSkills;
    protected final Skill[] _debuffSkills;
    protected final Skill[] _healSkills;
    protected final Skill[] _buffSkills;
    protected final Skill[] _stunSkills;
    protected long _lastActiveCheck;
    protected long _checkAggroTimestamp = 0L;
    protected long _attackTimeout;
    protected long _lastFactionNotifyTime = 0L;
    protected long _minFactionNotifyInterval;
    protected final Comparator<Creature> _nearestTargetComparator;

    public void addTaskCast(Creature creature, Skill skill) {
        Task task = new Task();
        task.type = TaskType.CAST;
        task.target = creature.getRef();
        task.skill = skill;
        this._tasks.add(task);
        this._def_think = true;
    }

    public void addTaskBuff(Creature creature, Skill skill) {
        Task task = new Task();
        task.type = TaskType.BUFF;
        task.target = creature.getRef();
        task.skill = skill;
        this._tasks.add(task);
        this._def_think = true;
    }

    public void addTaskAttack(Creature creature) {
        Task task = new Task();
        task.type = TaskType.ATTACK;
        task.target = creature.getRef();
        this._tasks.add(task);
        this._def_think = true;
    }

    public void addTaskAttack(Creature creature, Skill skill, int n) {
        Task task = new Task();
        task.type = skill.isOffensive() ? TaskType.CAST : TaskType.BUFF;
        task.target = creature.getRef();
        task.skill = skill;
        task.weight = n;
        this._tasks.add(task);
        this._def_think = true;
    }

    public void addTaskMove(Location location, boolean bl) {
        Task task = new Task();
        task.type = TaskType.MOVE;
        task.loc = location;
        task.pathfind = bl;
        this._tasks.add(task);
        this._def_think = true;
    }

    protected void addTaskMove(int n, int n2, int n3, boolean bl) {
        this.addTaskMove(new Location(n, n2, n3), bl);
    }

    public DefaultAI(NpcInstance npcInstance) {
        super(npcInstance);
        this.setAttackTimeout(Long.MAX_VALUE);
        NpcInstance npcInstance2 = this.getActor();
        this._damSkills = npcInstance2.getTemplate().getDamageSkills();
        this._dotSkills = npcInstance2.getTemplate().getDotSkills();
        this._debuffSkills = npcInstance2.getTemplate().getDebuffSkills();
        this._buffSkills = npcInstance2.getTemplate().getBuffSkills();
        this._stunSkills = npcInstance2.getTemplate().getStunSkills();
        this._healSkills = npcInstance2.getTemplate().getHealSkills();
        this._nearestTargetComparator = new NearestTargetComparator(npcInstance);
        this.MAX_PURSUE_RANGE = npcInstance.getParameter("MaxPursueRange", npcInstance.isRaid() ? Config.MAX_PURSUE_RANGE_RAID : (npcInstance2.isUnderground() ? Config.MAX_PURSUE_UNDERGROUND_RANGE : Config.MAX_PURSUE_RANGE));
        this._minFactionNotifyInterval = npcInstance.getParameter("FactionNotifyInterval", Config.FACTION_NOTIFY_INTERVAL);
    }

    @Override
    public void runImpl() throws Exception {
        if (this._aiTask == null) {
            return;
        }
        if (!this.isGlobalAI() && System.currentTimeMillis() - this._lastActiveCheck > 60000L) {
            WorldRegion worldRegion;
            this._lastActiveCheck = System.currentTimeMillis();
            NpcInstance npcInstance = this.getActor();
            WorldRegion worldRegion2 = worldRegion = npcInstance == null ? null : npcInstance.getCurrentRegion();
            if (worldRegion == null || !worldRegion.isActive()) {
                this.stopAITask();
                return;
            }
        }
        this.onEvtThink();
    }

    @Override
    public synchronized void startAITask() {
        if (this._aiTask == null) {
            this.AI_TASK_DELAY_CURRENT = this.AI_TASK_ACTIVE_DELAY;
            this._aiTask = AiTaskManager.getInstance().scheduleAtFixedRate(this, 0L, this.AI_TASK_DELAY_CURRENT);
        }
    }

    protected synchronized void switchAITask(long l) {
        if (this._aiTask == null) {
            return;
        }
        if (this.AI_TASK_DELAY_CURRENT != l) {
            this._aiTask.cancel(false);
            this.AI_TASK_DELAY_CURRENT = l;
            this._aiTask = AiTaskManager.getInstance().scheduleAtFixedRate(this, 0L, this.AI_TASK_DELAY_CURRENT);
        }
    }

    @Override
    public final synchronized void stopAITask() {
        if (this._aiTask != null) {
            this._aiTask.cancel(false);
            this._aiTask = null;
        }
    }

    protected boolean canSeeInSilentMove(Playable playable) {
        if (this.getActor().getParameter("canSeeInSilentMove", false)) {
            return true;
        }
        return !playable.isSilentMoving();
    }

    protected boolean canSeeInHide(Playable playable) {
        if (this.getActor().getParameter("canSeeInHide", false)) {
            return true;
        }
        return !playable.isInvisible();
    }

    protected boolean checkAggression(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        if (this.getIntention() != CtrlIntention.AI_INTENTION_ACTIVE || !this.isGlobalAggro()) {
            return false;
        }
        if (creature.isAlikeDead()) {
            return false;
        }
        if (creature.isNpc() && creature.isInvul()) {
            return false;
        }
        if (creature.isPlayable()) {
            if (!this.canSeeInSilentMove((Playable)creature)) {
                return false;
            }
            if (!this.canSeeInHide((Playable)creature)) {
                return false;
            }
            if (creature.isPlayer() && ((Player)creature).isGM() && creature.isInvisible()) {
                return false;
            }
            if (((Playable)creature).getNonAggroTime() > System.currentTimeMillis()) {
                return false;
            }
            if (creature.isPlayer() && !creature.getPlayer().isActive()) {
                return false;
            }
            if (npcInstance.isMonster() && creature.isInZonePeace()) {
                return false;
            }
        }
        if (!this.isInAggroRange(creature)) {
            return false;
        }
        if (!this.canAttackCharacter(creature)) {
            return false;
        }
        return GeoEngine.canSeeTarget(npcInstance, creature, false);
    }

    protected Location getPursueBaseLoc() {
        NpcInstance npcInstance = this.getActor();
        Location location = npcInstance.getSpawnedLoc();
        return location != null ? location : npcInstance.getLoc();
    }

    protected boolean isInAggroRange(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        AggroList.AggroInfo aggroInfo = npcInstance.getAggroList().get(creature);
        return !(aggroInfo != null && aggroInfo.hate > 0 ? !creature.isInRangeZ(this.getPursueBaseLoc(), (long)this.MAX_PURSUE_RANGE) : !this.isAggressive() || !creature.isInRangeZ(this.getPursueBaseLoc(), (long)npcInstance.getAggroRange()));
    }

    protected void setIsInRandomAnimation(long l) {
        this._randomAnimationEnd = System.currentTimeMillis() + l;
    }

    protected boolean randomAnimation() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.getParameter("noRandomAnimation", false)) {
            return false;
        }
        if (npcInstance.hasRandomAnimation() && !npcInstance.isActionsDisabled() && !npcInstance.isMoving() && !npcInstance.isInCombat() && Rnd.chance(Config.RND_ANIMATION_RATE)) {
            this.setIsInRandomAnimation(3000L);
            npcInstance.onRandomAnimation();
            return true;
        }
        return false;
    }

    protected Creature getNearestTarget(List<Creature> list) {
        NpcInstance npcInstance = this.getActor();
        Creature creature = null;
        long l = Long.MAX_VALUE;
        for (int i = 0; i < list.size(); ++i) {
            Creature creature2 = list.get(i);
            long l2 = npcInstance.getXYZDeltaSq(creature2.getX(), creature2.getY(), creature2.getZ());
            if (l2 >= l) continue;
            creature = creature2;
        }
        return creature;
    }

    protected boolean randomWalk() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.getParameter("noRandomWalk", false)) {
            return false;
        }
        return !npcInstance.isMoving() && this.maybeMoveToHome();
    }

    protected boolean thinkActive() {
        MonsterInstance monsterInstance;
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isActionsDisabled()) {
            return true;
        }
        if (this._randomAnimationEnd > System.currentTimeMillis()) {
            return true;
        }
        if (this._def_think) {
            if (this.doTask()) {
                this.clearTasks();
            }
            return true;
        }
        long l = System.currentTimeMillis();
        if (l - this._checkAggroTimestamp > (long)Config.AGGRO_CHECK_INTERVAL) {
            this._checkAggroTimestamp = l;
            boolean bl = Rnd.chance(npcInstance.getParameter("SelfAggressive", npcInstance.isAggressive() ? 100 : 0));
            if (!npcInstance.getAggroList().isEmpty() || bl) {
                List<Creature> list = World.getAroundCharacters(npcInstance);
                CollectionUtils.eqSort(list, this._nearestTargetComparator);
                for (Creature creature : list) {
                    if (!bl && npcInstance.getAggroList().get(creature) == null || !this.checkAggression(creature)) continue;
                    npcInstance.getAggroList().addDamageHate(creature, 0, 2);
                    if (creature.isSummon()) {
                        npcInstance.getAggroList().addDamageHate(creature.getPlayer(), 0, 1);
                    }
                    this.startRunningTask(this.AI_TASK_ATTACK_DELAY);
                    this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
                    return true;
                }
            }
        }
        if (npcInstance.isMinion() && (monsterInstance = ((MinionInstance)npcInstance).getLeader()) != null) {
            double d = npcInstance.getDistance(monsterInstance.getX(), monsterInstance.getY());
            if (d > 1000.0) {
                npcInstance.teleToLocation(monsterInstance.getRndMinionPosition());
                return true;
            }
            if (d > 200.0) {
                this.addTaskMove(monsterInstance.getRndMinionPosition(), false);
                return true;
            }
        }
        if (this.randomAnimation()) {
            return true;
        }
        return this.randomWalk();
    }

    @Override
    protected void onIntentionIdle() {
        NpcInstance npcInstance = this.getActor();
        this.clearTasks();
        npcInstance.stopMove();
        npcInstance.getAggroList().clear(true);
        if (npcInstance.hasAI()) {
            npcInstance.getAI().getTargetList().clear();
        }
        this.setAttackTimeout(Long.MAX_VALUE);
        this.setAttackTarget(null);
        this.changeIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
    }

    @Override
    protected void onIntentionActive() {
        NpcInstance npcInstance = this.getActor();
        npcInstance.stopMove();
        this.setAttackTimeout(Long.MAX_VALUE);
        if (this.getIntention() != CtrlIntention.AI_INTENTION_ACTIVE) {
            this.switchAITask(this.AI_TASK_ACTIVE_DELAY);
            this.changeIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
        }
        this.onEvtThink();
    }

    @Override
    protected void onIntentionAttack(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        this.clearTasks();
        npcInstance.stopMove();
        this.setAttackTarget(creature);
        this.setAttackTimeout((long)this.getMaxAttackTimeout() + System.currentTimeMillis());
        this.setGlobalAggro(0L);
        if (this.getIntention() != CtrlIntention.AI_INTENTION_ATTACK) {
            this.changeIntention(CtrlIntention.AI_INTENTION_ATTACK, creature, null);
            this.switchAITask(this.AI_TASK_ATTACK_DELAY);
        }
        this.onEvtThink();
    }

    protected boolean canAttackCharacter(Creature creature) {
        return creature.isPlayable();
    }

    protected boolean isAggressive() {
        return this.getActor().isAggressive();
    }

    protected boolean checkTarget(Creature creature, int n) {
        boolean bl;
        NpcInstance npcInstance = this.getActor();
        if (creature == null || creature.isAlikeDead() || !npcInstance.isInRangeZ(creature, (long)n)) {
            return false;
        }
        boolean bl2 = bl = creature.isPlayable() && !this.canSeeInHide((Playable)creature);
        if (!bl && npcInstance.isConfused()) {
            return true;
        }
        if (this.getIntention() == CtrlIntention.AI_INTENTION_ATTACK) {
            AggroList.AggroInfo aggroInfo = npcInstance.getAggroList().get(creature);
            if (aggroInfo != null) {
                if (bl) {
                    aggroInfo.hate = 0;
                    return false;
                }
                return aggroInfo.hate > 0;
            }
            return false;
        }
        return this.canAttackCharacter(creature);
    }

    public void setAttackTimeout(long l) {
        this._attackTimeout = l;
    }

    protected long getAttackTimeout() {
        return this._attackTimeout;
    }

    protected void thinkAttack() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return;
        }
        Location location = this.getPursueBaseLoc();
        if (!npcInstance.isInRange(location, (long)this.MAX_PURSUE_RANGE)) {
            this.teleportHome();
            return;
        }
        if (!(!this.doTask() || npcInstance.isAttackingNow() || npcInstance.isCastingNow() || this.createNewTask() || System.currentTimeMillis() <= this.getAttackTimeout() || npcInstance.isMovementDisabled())) {
            this.returnHome();
        }
    }

    @Override
    protected void onEvtSpawn() {
        this.setGlobalAggro(System.currentTimeMillis() + this.getActor().getParameter("globalAggro", Config.GLOBAL_AGGRO_CHECK_INTERVAL));
        this.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
    }

    @Override
    protected void onEvtReadyToAct() {
        this.onEvtThink();
    }

    @Override
    protected void onEvtArrivedTarget() {
        this.onEvtThink();
    }

    @Override
    protected void onEvtArrived() {
        this.onEvtThink();
    }

    protected boolean tryMoveToTarget(Creature creature) {
        return this.tryMoveToTarget(creature, DefaultAI.getIndentRange(creature.getActingRange()), creature.getActingRange());
    }

    protected boolean tryMoveToTarget(Creature creature, int n, int n2) {
        NpcInstance npcInstance = this.getActor();
        if (n2 > 16) {
            if (!npcInstance.moveToRelative(creature, n, n2)) {
                ++this._pathfindFails;
            }
        } else if (!npcInstance.moveToLocation(creature.getLoc(), n, true)) {
            ++this._pathfindFails;
        }
        if (this._pathfindFails >= this.getMaxPathfindFails() && System.currentTimeMillis() > this.getAttackTimeout() - (long)this.getMaxAttackTimeout() + (long)this.getTeleportTimeout() && npcInstance.isInRange(creature, (long)this.MAX_PURSUE_RANGE)) {
            Object object;
            this._pathfindFails = 0;
            if (creature.isPlayable() && ((object = npcInstance.getAggroList().get(creature)) == null || ((AggroList.AggroInfo)object).hate < 100 || creature.isInZonePeace() || !Config.MONSTER_TELEPORT_TO_PLAYER && npcInstance.isMonster() || npcInstance.isSevenSignsMonster() || Math.abs(npcInstance.getZ() - creature.getZ()) > 200 && npcInstance.getNpcId() == 29028 || npcInstance.getReflection() != ReflectionManager.DEFAULT && npcInstance instanceof RaidBossInstance)) {
                this.returnHome();
                return false;
            }
            object = GeoEngine.moveCheckForAI(creature.getLoc(), npcInstance.getLoc(), npcInstance.getGeoIndex());
            if (!GeoEngine.canMoveToCoord(npcInstance.getX(), npcInstance.getY(), npcInstance.getZ(), ((Location)object).x, ((Location)object).y, ((Location)object).z, npcInstance.getGeoIndex())) {
                object = creature.getLoc();
            }
            npcInstance.teleToLocation((Location)object);
        }
        return true;
    }

    protected boolean maybeNextTask(Task task) {
        this._tasks.remove(task);
        return this._tasks.size() == 0;
    }

    protected boolean onDoTaskMove(Task task) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isMovementDisabled() || !this.getIsMobile()) {
            return true;
        }
        if (npcInstance.isInRange(task.loc, 100L)) {
            return this.maybeNextTask(task);
        }
        if (npcInstance.isMoving()) {
            return false;
        }
        if (!npcInstance.moveToLocation(task.loc, 0, task.pathfind)) {
            this.clientStopMoving();
            this._pathfindFails = 0;
            npcInstance.teleToLocation(task.loc);
            return this.maybeNextTask(task);
        }
        return false;
    }

    protected boolean onDoTaskAttack(Task task) {
        NpcInstance npcInstance = this.getActor();
        Creature creature = task.target.get();
        if (!this.checkTarget(creature, this.MAX_PURSUE_RANGE)) {
            return true;
        }
        this.setAttackTarget(creature);
        if (npcInstance.isMoving()) {
            return Rnd.chance(25);
        }
        int n = npcInstance.getPhysicalAttackRange();
        int n2 = (int)(npcInstance.getCollisionRadius() + creature.getColRadius());
        boolean bl = npcInstance.isFlying() || npcInstance.isInWater() || creature.isFlying() || creature.isInWater();
        int n3 = (int)(!bl ? npcInstance.getDistance(creature) : npcInstance.getDistance3D(creature)) - n2;
        if (n3 <= n + 16 && GeoEngine.canSeeTarget(npcInstance, creature, bl)) {
            this.clientStopMoving();
            this._pathfindFails = 0;
            this.setAttackTimeout((long)this.getMaxAttackTimeout() + System.currentTimeMillis());
            npcInstance.doAttack(creature);
            return this.maybeNextTask(task);
        }
        if (npcInstance.isMovementDisabled() || !this.getIsMobile()) {
            return true;
        }
        return !this.tryMoveToTarget(creature, n2 + DefaultAI.getIndentRange(n), n2 + n);
    }

    protected boolean onDoTaskCast(Task task) {
        NpcInstance npcInstance = this.getActor();
        Creature creature = task.target.get();
        if (npcInstance.isMuted(task.skill) || npcInstance.isSkillDisabled(task.skill) || npcInstance.isUnActiveSkill(task.skill.getId())) {
            return true;
        }
        boolean bl = task.skill.getTargetType() == Skill.SkillTargetType.TARGET_AURA;
        int n = task.skill.getAOECastRange();
        if (!this.checkTarget(creature, this.MAX_PURSUE_RANGE + n)) {
            return true;
        }
        this.setAttackTarget(creature);
        int n2 = (int)(npcInstance.getCollisionRadius() + creature.getColRadius());
        boolean bl2 = npcInstance.isFlying() || npcInstance.isInWater() || creature.isFlying() || creature.isInWater();
        int n3 = (int)(!bl2 ? npcInstance.getDistance(creature) : npcInstance.getDistance3D(creature)) - n2;
        if (n3 <= n && GeoEngine.canSeeTarget(npcInstance, creature, bl2)) {
            this.clientStopMoving();
            this._pathfindFails = 0;
            this.setAttackTimeout((long)this.getMaxAttackTimeout() + System.currentTimeMillis());
            npcInstance.doCast(task.skill, bl ? npcInstance : creature, !creature.isPlayable());
            return this.maybeNextTask(task);
        }
        if (npcInstance.isMoving()) {
            return Rnd.chance(10);
        }
        if (npcInstance.isMovementDisabled() || !this.getIsMobile()) {
            return true;
        }
        return !this.tryMoveToTarget(creature, n2 + DefaultAI.getIndentRange(n), n2 + n);
    }

    protected boolean onDoTaskBuff(Task task) {
        NpcInstance npcInstance = this.getActor();
        Creature creature = task.target.get();
        if (npcInstance.isMuted(task.skill) || npcInstance.isSkillDisabled(task.skill) || npcInstance.isUnActiveSkill(task.skill.getId())) {
            return true;
        }
        if (creature == null || creature.isAlikeDead() || !npcInstance.isInRange(creature, 2000L)) {
            return true;
        }
        boolean bl = task.skill.getTargetType() == Skill.SkillTargetType.TARGET_AURA;
        int n = task.skill.getAOECastRange();
        if (npcInstance.isMoving()) {
            return Rnd.chance(10);
        }
        int n2 = (int)(npcInstance.getCollisionRadius() + creature.getColRadius());
        boolean bl2 = npcInstance.isFlying() || npcInstance.isInWater() || creature.isFlying() || creature.isInWater();
        int n3 = (int)(!bl2 ? npcInstance.getDistance(creature) : npcInstance.getDistance3D(creature)) - n2;
        if (n3 <= n && GeoEngine.canSeeTarget(npcInstance, creature, bl2)) {
            this.clientStopMoving();
            this._pathfindFails = 0;
            npcInstance.doCast(task.skill, bl ? npcInstance : creature, !creature.isPlayable());
            return this.maybeNextTask(task);
        }
        if (npcInstance.isMovementDisabled() || !this.getIsMobile()) {
            return true;
        }
        return !this.tryMoveToTarget(creature, n2 + DefaultAI.getIndentRange(n), n2 + n);
    }

    protected boolean doTask() {
        NpcInstance npcInstance = this.getActor();
        if (!this._def_think) {
            return true;
        }
        Task task = this._tasks.pollFirst();
        if (task == null) {
            this.clearTasks();
            return true;
        }
        if (npcInstance.isDead() || npcInstance.isAttackingNow() || npcInstance.isCastingNow()) {
            return false;
        }
        switch (task.type) {
            case MOVE: {
                return this.onDoTaskMove(task);
            }
            case ATTACK: {
                return this.onDoTaskAttack(task);
            }
            case CAST: {
                return this.onDoTaskCast(task);
            }
            case BUFF: {
                return this.onDoTaskBuff(task);
            }
        }
        return false;
    }

    protected boolean createNewTask() {
        return false;
    }

    protected boolean defaultNewTask() {
        Creature creature;
        this.clearTasks();
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null || (creature = this.prepareTarget()) == null) {
            return false;
        }
        double d = npcInstance.getDistance(creature);
        return this.chooseTaskAndTargets(null, creature, d);
    }

    @Override
    protected void onEvtThink() {
        NpcInstance npcInstance = this.getActor();
        if (this.n || npcInstance == null || npcInstance.isActionsDisabled() || npcInstance.isAfraid()) {
            return;
        }
        if (this._randomAnimationEnd > System.currentTimeMillis()) {
            return;
        }
        if (Config.RAID_TELE_TO_HOME_FROM_PVP_ZONES && npcInstance.isRaid() && (npcInstance.isInZoneBattle() || npcInstance.isInZone(Zone.ZoneType.SIEGE) || npcInstance.isInZone(Zone.ZoneType.fun))) {
            this.teleportHome();
            return;
        }
        if (Config.RAID_TELE_TO_HOME_FROM_TOWN_ZONES && npcInstance.isRaid() && npcInstance.isInZonePeace()) {
            this.teleportHome();
            return;
        }
        this.n = true;
        try {
            if (!Config.BLOCK_ACTIVE_TASKS && this.getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) {
                this.thinkActive();
            } else if (this.getIntention() == CtrlIntention.AI_INTENTION_ATTACK) {
                this.thinkAttack();
            }
        }
        finally {
            this.n = false;
        }
    }

    @Override
    protected void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
    }

    @Override
    protected void onEvtClanAttacked(Creature creature, Creature creature2, int n) {
        if (this.getIntention() != CtrlIntention.AI_INTENTION_ACTIVE || !this.isGlobalAggro()) {
            return;
        }
        this.notifyEvent(CtrlEvent.EVT_AGGRESSION, creature2, 2);
    }

    @Override
    protected void onEvtAttacked(Creature creature, int n) {
        Iterable<QuestState> iterable;
        NpcInstance npcInstance = this.getActor();
        if (creature == null || npcInstance.isDead()) {
            return;
        }
        Player player = creature.getPlayer();
        if (player != null) {
            if (Config.ALT_TELEPORT_FROM_SEVEN_SING_MONSTER && npcInstance.isSevenSignsMonster()) {
                if (SevenSigns.getInstance().isSealValidationPeriod()) {
                    if (SevenSigns.getInstance().getSealOwner(1) == 0 || SevenSigns.getInstance().getSealOwner(1) != SevenSigns.getInstance().getCabalHighestScore() || SevenSigns.getInstance().getCabalHighestScore() != SevenSigns.getInstance().getPlayerCabal(player)) {
                        player.sendMessage(new CustomMessage("defaultAI.CabalTeleported", player, new Object[0]));
                        player.teleToClosestTown();
                        return;
                    }
                } else if (SevenSigns.getInstance().getPlayerCabal(player) == 0) {
                    player.sendMessage(new CustomMessage("defaultAI.CabalTeleported", player, new Object[0]));
                    player.teleToClosestTown();
                    return;
                }
            }
            if (npcInstance.hasAI() && creature.isPlayer() && !npcInstance.getAI().getTargetList().contains(creature)) {
                npcInstance.getAI().addToTargetList(creature.getPlayer());
            }
            if ((iterable = player.getQuestsForEvent(npcInstance, QuestEventType.ATTACKED_WITH_QUEST)) != null) {
                for (QuestState questState : iterable) {
                    questState.getQuest().notifyAttack(npcInstance, questState);
                }
            }
        }
        npcInstance.getAggroList().addDamageHate(creature, 0, n);
        if (n > 0 && (creature.isSummon() || creature.isPet()) && (iterable = creature.getPlayer()) != null) {
            npcInstance.getAggroList().addDamageHate((Creature)((Object)iterable), 0, npcInstance.getParameter("searchingMaster", false) ? n : 1);
        }
        if (this.getIntention() != CtrlIntention.AI_INTENTION_ATTACK) {
            if (!npcInstance.isRunning()) {
                this.startRunningTask(this.AI_TASK_ATTACK_DELAY);
            }
            this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
        }
        this.notifyFriends(creature, n);
    }

    @Override
    protected void onEvtAggression(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature == null || npcInstance.isDead()) {
            return;
        }
        npcInstance.getAggroList().addDamageHate(creature, 0, n);
        if (n > 0 && (creature.isSummon() || creature.isPet())) {
            npcInstance.getAggroList().addDamageHate(creature.getPlayer(), 0, npcInstance.getParameter("searchingMaster", false) ? n : 1);
        }
        if (this.getIntention() != CtrlIntention.AI_INTENTION_ATTACK) {
            if (!npcInstance.isRunning()) {
                this.startRunningTask(this.AI_TASK_ATTACK_DELAY);
            }
            this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
        }
        this.notifyMinions(creature, n);
    }

    protected boolean maybeMoveToHome() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return false;
        }
        boolean bl = npcInstance.hasRandomWalk();
        Location location = this.getPursueBaseLoc();
        if (!(!bl || Config.RND_WALK && Rnd.chance(Config.RND_WALK_RATE))) {
            return false;
        }
        boolean bl2 = npcInstance.isInRangeZ(location, (long)Config.MAX_DRIFT_RANGE);
        if (!bl && bl2) {
            return false;
        }
        Location location2 = Location.findPointToStay(npcInstance, location, 0, Config.MAX_DRIFT_RANGE);
        npcInstance.setWalking();
        if (!npcInstance.moveToLocation(location2.x, location2.y, location2.z, 0, true) && !bl2) {
            this.teleportHome();
        }
        return true;
    }

    protected void returnHome() {
        this.returnHome(true, Config.ALWAYS_TELEPORT_HOME);
    }

    protected void teleportHome() {
        this.returnHome(true, true);
    }

    protected void returnHome(boolean bl, boolean bl2) {
        NpcInstance npcInstance = this.getActor();
        Location location = this.getPursueBaseLoc();
        this.clearTasks();
        npcInstance.stopMove();
        if (npcInstance.hasAI()) {
            npcInstance.getAI().getTargetList().clear();
        }
        if (bl) {
            npcInstance.getAggroList().clear(Config.RESET_HATE_ONLY_AFTER_PURSUE_RANGE);
        }
        this.setAttackTimeout(Long.MAX_VALUE);
        this.setAttackTarget(null);
        if (Config.RESTORE_HP_MP_ON_TELEPORT_HOME && !ArrayUtils.contains((int[])Config.RESTORE_HP_MP_ON_EXCLUDED_IDS, (int)npcInstance.getNpcId())) {
            npcInstance.setCurrentHpMp(npcInstance.getMaxHp(), npcInstance.getMaxMp(), true);
        }
        this.changeIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
        if (bl2) {
            npcInstance.broadcastPacketToOthers(new MagicSkillUse(npcInstance, npcInstance, 2036, 1, 500, 0L));
            npcInstance.teleToLocation(location.x, location.y, GeoEngine.getHeight(location, npcInstance.getGeoIndex()));
        } else {
            if (!bl) {
                npcInstance.setRunning();
            } else {
                npcInstance.setWalking();
            }
            this.addTaskMove(location, false);
        }
    }

    protected Creature prepareTarget() {
        Object object;
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isConfused()) {
            return this.getAttackTarget();
        }
        if (Rnd.chance(npcInstance.getParameter("isMadness", 0)) && (object = npcInstance.getAggroList().getRandomHated()) != null && Math.abs(npcInstance.getZ() - ((GameObject)object).getZ()) < 1000) {
            this.setAttackTarget((Creature)object);
            if (this._madnessTask == null && !npcInstance.isConfused()) {
                npcInstance.startConfused();
                this._madnessTask = ThreadPoolManager.getInstance().schedule(new MadnessTask(), 10000L);
            }
            return object;
        }
        object = npcInstance.getAggroList().getHateList(this.MAX_PURSUE_RANGE);
        Creature creature = null;
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            Creature creature2 = (Creature)iterator.next();
            if (!this.checkTarget(creature2, this.MAX_PURSUE_RANGE)) {
                npcInstance.getAggroList().remove(creature2, Config.RESET_HATE_ONLY_WHEN_LEAVING_OR_DYING);
                continue;
            }
            creature = creature2;
            break;
        }
        if (creature != null) {
            this.setAttackTarget(creature);
            return creature;
        }
        return null;
    }

    protected boolean canUseSkill(Skill skill, Creature creature, double d) {
        NpcInstance npcInstance = this.getActor();
        if (skill == null || skill.isNotUsedByAI()) {
            return false;
        }
        if (skill.getTargetType() == Skill.SkillTargetType.TARGET_SELF && creature != npcInstance) {
            return false;
        }
        int n = skill.getAOECastRange();
        if (n <= 200 && d > 200.0) {
            return false;
        }
        if (npcInstance.isSkillDisabled(skill) || npcInstance.isMuted(skill) || npcInstance.isUnActiveSkill(skill.getId())) {
            return false;
        }
        double d2 = skill.getMpConsume2();
        d2 = skill.isMagic() ? npcInstance.calcStat(Stats.MP_MAGIC_SKILL_CONSUME, d2, creature, skill) : npcInstance.calcStat(Stats.MP_PHYSICAL_SKILL_CONSUME, d2, creature, skill);
        if (npcInstance.getCurrentMp() < d2) {
            return false;
        }
        return creature.getEffectList().getEffectsCountForSkill(skill.getId()) == 0;
    }

    protected boolean canUseSkill(Skill skill, Creature creature) {
        return this.canUseSkill(skill, creature, 0.0);
    }

    protected Skill[] selectUsableSkills(Creature creature, double d, Skill[] skillArray) {
        if (skillArray == null || skillArray.length == 0 || creature == null) {
            return null;
        }
        Skill[] skillArray2 = null;
        int n = 0;
        for (Skill skill : skillArray) {
            if (!this.canUseSkill(skill, creature, d)) continue;
            if (skillArray2 == null) {
                skillArray2 = new Skill[skillArray.length];
            }
            skillArray2[n++] = skill;
        }
        if (skillArray2 == null || n == skillArray.length) {
            return skillArray2;
        }
        if (n == 0) {
            return null;
        }
        skillArray2 = (Skill[])Arrays.copyOf(skillArray2, n);
        return skillArray2;
    }

    protected static Skill selectTopSkillByDamage(Creature creature, Creature creature2, double d, Skill[] skillArray) {
        if (skillArray == null || skillArray.length == 0) {
            return null;
        }
        if (skillArray.length == 1) {
            return skillArray[0];
        }
        RndSelector<Skill> rndSelector = new RndSelector<Skill>(skillArray.length);
        for (Skill skill : skillArray) {
            double d2 = skill.getSimpleDamage(creature, creature2) * (double)skill.getAOECastRange() / d;
            if (d2 < 1.0) {
                d2 = 1.0;
            }
            rndSelector.add(skill, (int)d2);
        }
        return (Skill)rndSelector.select();
    }

    protected static Skill selectTopSkillByDebuff(Creature creature, Creature creature2, double d, Skill[] skillArray) {
        if (skillArray == null || skillArray.length == 0) {
            return null;
        }
        if (skillArray.length == 1) {
            return skillArray[0];
        }
        RndSelector<Skill> rndSelector = new RndSelector<Skill>(skillArray.length);
        for (Skill skill : skillArray) {
            double d2;
            if (skill.getSameByStackType(creature2) != null) continue;
            double d3 = 100.0 * (double)skill.getAOECastRange() / d;
            if (d2 <= 0.0) {
                d3 = 1.0;
            }
            rndSelector.add(skill, (int)d3);
        }
        return (Skill)rndSelector.select();
    }

    protected static Skill selectTopSkillByBuff(Creature creature, Skill[] skillArray) {
        if (skillArray == null || skillArray.length == 0) {
            return null;
        }
        if (skillArray.length == 1) {
            return skillArray[0];
        }
        RndSelector<Skill> rndSelector = new RndSelector<Skill>(skillArray.length);
        for (Skill skill : skillArray) {
            double d;
            if (skill.getSameByStackType(creature) != null) continue;
            double d2 = skill.getPower();
            if (d <= 0.0) {
                d2 = 1.0;
            }
            rndSelector.add(skill, (int)d2);
        }
        return (Skill)rndSelector.select();
    }

    protected static Skill selectTopSkillByHeal(Creature creature, Skill[] skillArray) {
        if (skillArray == null || skillArray.length == 0) {
            return null;
        }
        double d = (double)creature.getMaxHp() - creature.getCurrentHp();
        if (d < 1.0) {
            return null;
        }
        if (skillArray.length == 1) {
            return skillArray[0];
        }
        RndSelector<Skill> rndSelector = new RndSelector<Skill>(skillArray.length);
        for (Skill skill : skillArray) {
            double d2;
            double d3 = Math.abs(skill.getPower() - d);
            if (d2 <= 0.0) {
                d3 = 1.0;
            }
            rndSelector.add(skill, (int)d3);
        }
        return (Skill)rndSelector.select();
    }

    protected void addDesiredSkill(Map<Skill, Integer> map, Creature creature, double d, Skill[] skillArray) {
        if (skillArray == null || skillArray.length == 0 || creature == null) {
            return;
        }
        for (Skill skill : skillArray) {
            this.addDesiredSkill(map, creature, d, skill);
        }
    }

    protected void addDesiredSkill(Map<Skill, Integer> map, Creature creature, double d, Skill skill) {
        if (skill == null || creature == null || !this.canUseSkill(skill, creature)) {
            return;
        }
        int n = (int)(-Math.abs((double)skill.getAOECastRange() - d));
        if ((double)skill.getAOECastRange() >= d) {
            n += 1000000;
        } else if (skill.isNotTargetAoE() && skill.getTargets(this.getActor(), creature, false).size() == 0) {
            return;
        }
        map.put(skill, n);
    }

    protected void addDesiredHeal(Map<Skill, Integer> map, Skill[] skillArray) {
        if (skillArray == null || skillArray.length == 0) {
            return;
        }
        NpcInstance npcInstance = this.getActor();
        double d = (double)npcInstance.getMaxHp() - npcInstance.getCurrentHp();
        double d2 = npcInstance.getCurrentHpPercents();
        if (d < 1.0) {
            return;
        }
        for (Skill skill : skillArray) {
            if (!this.canUseSkill(skill, npcInstance) || !(skill.getPower() <= d)) continue;
            int n = (int)skill.getPower();
            if (d2 < 50.0) {
                n += 1000000;
            }
            map.put(skill, n);
        }
    }

    protected void addDesiredBuff(Map<Skill, Integer> map, Skill[] skillArray) {
        if (skillArray == null || skillArray.length == 0) {
            return;
        }
        NpcInstance npcInstance = this.getActor();
        for (Skill skill : skillArray) {
            if (!this.canUseSkill(skill, npcInstance)) continue;
            map.put(skill, 1000000);
        }
    }

    protected Skill selectTopSkill(Map<Skill, Integer> map) {
        int n;
        if (map == null || map.isEmpty()) {
            return null;
        }
        int n2 = Integer.MIN_VALUE;
        for (Skill object : map.keySet()) {
            n = map.get(object);
            if (n <= n2) continue;
            n2 = n;
        }
        if (n2 == Integer.MIN_VALUE) {
            return null;
        }
        Skill[] skillArray = new Skill[map.size()];
        n = 0;
        for (Map.Entry<Skill, Integer> entry : map.entrySet()) {
            if (entry.getValue() < n2) continue;
            skillArray[n++] = entry.getKey();
        }
        return skillArray[Rnd.get(n)];
    }

    protected boolean chooseTaskAndTargets(Skill skill, Creature creature, double d) {
        NpcInstance npcInstance = this.getActor();
        if (skill != null) {
            if (npcInstance.isMovementDisabled() && d > (double)(skill.getAOECastRange() + 60)) {
                creature = null;
                if (skill.isOffensive()) {
                    LazyArrayList<Creature> lazyArrayList = LazyArrayList.newInstance();
                    for (Creature creature2 : npcInstance.getAggroList().getHateList(this.MAX_PURSUE_RANGE)) {
                        if (!this.checkTarget(creature2, skill.getAOECastRange() + 60) || !this.canUseSkill(skill, creature2)) continue;
                        lazyArrayList.add(creature2);
                    }
                    if (!lazyArrayList.isEmpty()) {
                        creature = (Creature)lazyArrayList.get(Rnd.get(lazyArrayList.size()));
                    }
                    LazyArrayList.recycle(lazyArrayList);
                }
            }
            if (creature == null) {
                return false;
            }
            if (skill.isOffensive()) {
                this.addTaskCast(creature, skill);
            } else {
                this.addTaskBuff(creature, skill);
            }
            return true;
        }
        if (npcInstance.isMovementDisabled() && d > (double)(npcInstance.getPhysicalAttackRange() + 40)) {
            creature = null;
            LazyArrayList<Creature> lazyArrayList = LazyArrayList.newInstance();
            for (Creature creature3 : npcInstance.getAggroList().getHateList(this.MAX_PURSUE_RANGE)) {
                if (!this.checkTarget(creature3, npcInstance.getPhysicalAttackRange() + 40)) continue;
                lazyArrayList.add(creature3);
            }
            if (!lazyArrayList.isEmpty()) {
                creature = (Creature)lazyArrayList.get(Rnd.get(lazyArrayList.size()));
            }
            LazyArrayList.recycle(lazyArrayList);
        }
        if (creature == null) {
            return false;
        }
        this.addTaskAttack(creature);
        return true;
    }

    @Override
    public boolean isActive() {
        return this._aiTask != null;
    }

    protected void clearTasks() {
        this._def_think = false;
        this._tasks.clear();
    }

    protected void startRunningTask(long l) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance != null && this._runningTask == null && !npcInstance.isRunning()) {
            this._runningTask = ThreadPoolManager.getInstance().schedule(new RunningTask(), l);
        }
    }

    protected boolean isGlobalAggro() {
        if (this._globalAggro == 0L) {
            return true;
        }
        if (this._globalAggro <= System.currentTimeMillis()) {
            this._globalAggro = 0L;
            return true;
        }
        return false;
    }

    public void setGlobalAggro(long l) {
        this._globalAggro = l;
    }

    @Override
    public NpcInstance getActor() {
        return (NpcInstance)super.getActor();
    }

    protected boolean defaultThinkBuff(int n) {
        return this.defaultThinkBuff(n, 0);
    }

    protected void notifyFriends(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (System.currentTimeMillis() - this._lastFactionNotifyTime > this._minFactionNotifyInterval) {
            Object object;
            this._lastFactionNotifyTime = System.currentTimeMillis();
            if (npcInstance.isMinion() && (object = ((MinionInstance)npcInstance).getLeader()) != null) {
                Iterator<MinionInstance> iterator;
                if (!((Creature)object).isDead() && ((GameObject)object).isVisible()) {
                    ((NpcInstance)object).getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, creature, n);
                }
                if ((iterator = ((MonsterInstance)object).getMinionList()) != null) {
                    for (MinionInstance minionInstance : ((MinionList)((Object)iterator)).getAliveMinions()) {
                        if (minionInstance == npcInstance) continue;
                        minionInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, creature, n);
                    }
                }
            }
            if ((object = npcInstance.getMinionList()) != null && ((MinionList)object).hasAliveMinions()) {
                for (MinionInstance minionInstance : ((MinionList)object).getAliveMinions()) {
                    minionInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, creature, n);
                }
            }
            for (NpcInstance npcInstance2 : this.activeFactionTargets()) {
                npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_CLAN_ATTACKED, new Object[]{npcInstance, creature, n});
            }
        }
    }

    protected void notifyMinions(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        MinionList minionList = npcInstance.getMinionList();
        if (minionList != null && minionList.hasAliveMinions()) {
            for (MinionInstance minionInstance : minionList.getAliveMinions()) {
                minionInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, creature, n);
            }
        }
    }

    protected List<NpcInstance> activeFactionTargets() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.getFaction().isNone()) {
            return Collections.emptyList();
        }
        int n = npcInstance.getFaction().getRange();
        LazyArrayList<NpcInstance> lazyArrayList = new LazyArrayList<NpcInstance>();
        for (NpcInstance npcInstance2 : World.getAroundNpc(npcInstance)) {
            if (npcInstance2.isDead() || !npcInstance2.isInRangeZ(npcInstance, (long)n) || !npcInstance2.isInFaction(npcInstance)) continue;
            lazyArrayList.add(npcInstance2);
        }
        return lazyArrayList;
    }

    protected boolean defaultThinkBuff(int n, int n2) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        if (Rnd.chance(n)) {
            Skill[] skillArray;
            double d = npcInstance.getCurrentHpPercents();
            Skill[] skillArray2 = skillArray = d < 50.0 ? this.selectUsableSkills(npcInstance, 0.0, this._healSkills) : this.selectUsableSkills(npcInstance, 0.0, this._buffSkills);
            if (skillArray == null || skillArray.length == 0) {
                return false;
            }
            Skill skill = skillArray[Rnd.get(skillArray.length)];
            this.addTaskBuff(npcInstance, skill);
            return true;
        }
        if (Rnd.chance(n2)) {
            for (NpcInstance npcInstance2 : this.activeFactionTargets()) {
                double d = npcInstance2.getCurrentHpPercents();
                Skill[] skillArray = d < 50.0 ? this.selectUsableSkills(npcInstance, 0.0, this._healSkills) : this.selectUsableSkills(npcInstance, 0.0, this._buffSkills);
                if (skillArray == null || skillArray.length == 0) continue;
                Skill skill = skillArray[Rnd.get(skillArray.length)];
                this.addTaskBuff(npcInstance, skill);
                return true;
            }
        }
        return false;
    }

    protected boolean defaultFightTask() {
        Skill[] skillArray;
        Skill[] skillArray2;
        this.clearTasks();
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead() || npcInstance.isAMuted()) {
            return false;
        }
        Creature creature = this.prepareTarget();
        if (creature == null) {
            return false;
        }
        double d = npcInstance.getDistance(creature);
        double d2 = creature.getCurrentHpPercents();
        double d3 = npcInstance.getCurrentHpPercents();
        Skill[] skillArray3 = Rnd.chance(this.getRateDAM()) ? this.selectUsableSkills(creature, d, this._damSkills) : null;
        Skill[] skillArray4 = skillArray2 = Rnd.chance(this.getRateDOT()) ? this.selectUsableSkills(creature, d, this._dotSkills) : null;
        Skill[] skillArray5 = d2 > 10.0 ? (Rnd.chance(this.getRateDEBUFF()) ? this.selectUsableSkills(creature, d, this._debuffSkills) : null) : null;
        Skill[] skillArray6 = skillArray = Rnd.chance(this.getRateSTUN()) ? this.selectUsableSkills(creature, d, this._stunSkills) : null;
        Skill[] skillArray7 = d3 < 50.0 ? (Rnd.chance(this.getRateHEAL()) ? this.selectUsableSkills(npcInstance, 0.0, this._healSkills) : null) : null;
        Skill[] skillArray8 = Rnd.chance(this.getRateBUFF()) ? this.selectUsableSkills(npcInstance, 0.0, this._buffSkills) : null;
        RndSelector<Skill[]> rndSelector = new RndSelector<Skill[]>();
        if (!npcInstance.isAMuted()) {
            rndSelector.add(null, this.getRatePHYS());
        }
        rndSelector.add(skillArray3, this.getRateDAM());
        rndSelector.add(skillArray2, this.getRateDOT());
        rndSelector.add(skillArray5, this.getRateDEBUFF());
        rndSelector.add(skillArray7, this.getRateHEAL());
        rndSelector.add(skillArray8, this.getRateBUFF());
        rndSelector.add(skillArray, this.getRateSTUN());
        Skill[] skillArray9 = (Skill[])rndSelector.select();
        if (skillArray9 != null) {
            if (skillArray9 == skillArray3 || skillArray9 == skillArray2) {
                return this.chooseTaskAndTargets(DefaultAI.selectTopSkillByDamage(npcInstance, creature, d, skillArray9), creature, d);
            }
            if (skillArray9 == skillArray5 || skillArray9 == skillArray) {
                return this.chooseTaskAndTargets(DefaultAI.selectTopSkillByDebuff(npcInstance, creature, d, skillArray9), creature, d);
            }
            if (skillArray9 == skillArray8) {
                return this.chooseTaskAndTargets(DefaultAI.selectTopSkillByBuff(npcInstance, skillArray9), npcInstance, d);
            }
            if (skillArray9 == skillArray7) {
                return this.chooseTaskAndTargets(DefaultAI.selectTopSkillByHeal(npcInstance, skillArray9), npcInstance, d);
            }
        }
        return this.chooseTaskAndTargets(null, creature, d);
    }

    public int getRatePHYS() {
        return 100;
    }

    public int getRateDOT() {
        return 0;
    }

    public int getRateDEBUFF() {
        return 0;
    }

    public int getRateDAM() {
        return 0;
    }

    public int getRateSTUN() {
        return 0;
    }

    public int getRateBUFF() {
        return 0;
    }

    public int getRateHEAL() {
        return 0;
    }

    public boolean getIsMobile() {
        return !this.getActor().getParameter("isImmobilized", false);
    }

    public int getMaxPathfindFails() {
        return 3;
    }

    public int getMaxAttackTimeout() {
        return 15000;
    }

    public int getTeleportTimeout() {
        return 10000;
    }

    public static class Task {
        public TaskType type;
        public Skill skill;
        public HardReference<? extends Creature> target;
        public Location loc;
        public boolean pathfind;
        public int weight = 10000;
    }

    public static final class TaskType
    extends Enum<TaskType> {
        public static final /* enum */ TaskType MOVE = new TaskType();
        public static final /* enum */ TaskType ATTACK = new TaskType();
        public static final /* enum */ TaskType CAST = new TaskType();
        public static final /* enum */ TaskType BUFF = new TaskType();
        private static final /* synthetic */ TaskType[] a;

        public static TaskType[] values() {
            return (TaskType[])a.clone();
        }

        public static TaskType valueOf(String string) {
            return Enum.valueOf(TaskType.class, string);
        }

        private static /* synthetic */ TaskType[] a() {
            return new TaskType[]{MOVE, ATTACK, CAST, BUFF};
        }

        static {
            a = TaskType.a();
        }
    }

    private static class TaskComparator
    implements Comparator<Task> {
        private static final Comparator<Task> c = new TaskComparator();

        private TaskComparator() {
        }

        public static final Comparator<Task> getInstance() {
            return c;
        }

        @Override
        public int compare(Task task, Task task2) {
            if (task == null || task2 == null) {
                return 0;
            }
            return task2.weight - task.weight;
        }
    }

    protected class NearestTargetComparator
    implements Comparator<Creature> {
        private final Creature a;

        public NearestTargetComparator(Creature creature) {
            this.a = creature;
        }

        @Override
        public int compare(Creature creature, Creature creature2) {
            double d = this.a.getDistance3D(creature) - this.a.getDistance3D(creature2);
            if (d < 0.0) {
                return -1;
            }
            return d > 0.0 ? 1 : 0;
        }
    }

    public class MadnessTask
    extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            NpcInstance npcInstance = DefaultAI.this.getActor();
            if (npcInstance != null) {
                npcInstance.stopConfused();
            }
            DefaultAI.this._madnessTask = null;
        }
    }

    protected class RunningTask
    extends RunnableImpl {
        protected RunningTask() {
        }

        @Override
        public void runImpl() throws Exception {
            NpcInstance npcInstance = DefaultAI.this.getActor();
            if (npcInstance != null) {
                npcInstance.setRunning();
            }
            DefaultAI.this._runningTask = null;
        }
    }

    protected class Teleport
    extends RunnableImpl {
        Location _destination;
        boolean _updateSpawnLocation;

        public Teleport(Location location) {
            this._destination = location;
            this._updateSpawnLocation = false;
        }

        public Teleport(Location location, boolean bl) {
            this._destination = location;
            this._updateSpawnLocation = bl;
        }

        @Override
        public void runImpl() throws Exception {
            NpcInstance npcInstance = DefaultAI.this.getActor();
            if (npcInstance != null) {
                npcInstance.teleToLocation(this._destination);
                if (this._updateSpawnLocation) {
                    npcInstance.setSpawnedLoc(this._destination);
                }
            }
        }
    }
}
