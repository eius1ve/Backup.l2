/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model;

import gnu.trove.TIntHashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.commons.collections.LazyArrayList;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.commons.util.concurrent.atomic.AtomicState;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.NextAction;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.geodata.GeoMove;
import l2.gameserver.instancemanager.DimensionalRiftManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Effect;
import l2.gameserver.model.EffectList;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectTasks;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.World;
import l2.gameserver.model.Zone;
import l2.gameserver.model.Zones;
import l2.gameserver.model.actor.instances.player.AutoFarmContext;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.actor.recorder.CharStatsChangeRecorder;
import l2.gameserver.model.base.InvisibleType;
import l2.gameserver.model.base.SpecialEffectState;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.quest.QuestEventType;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.model.reference.L2Reference;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.Attack;
import l2.gameserver.network.l2.s2c.AutoAttackStart;
import l2.gameserver.network.l2.s2c.AutoAttackStop;
import l2.gameserver.network.l2.s2c.ChangeMoveType;
import l2.gameserver.network.l2.s2c.CharMoveToLocation;
import l2.gameserver.network.l2.s2c.ExTeleportToLocationActivate;
import l2.gameserver.network.l2.s2c.FlyToLocation;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillCanceled;
import l2.gameserver.network.l2.s2c.MagicSkillLaunched;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.MoveToPawn;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.network.l2.s2c.SetupGauge;
import l2.gameserver.network.l2.s2c.StatusUpdate;
import l2.gameserver.network.l2.s2c.StopMove;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.TeleportToLocation;
import l2.gameserver.network.l2.s2c.ValidateLocation;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.skills.EffectType;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.stats.Calculator;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.StatFunctions;
import l2.gameserver.stats.StatTemplate;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.triggers.TriggerInfo;
import l2.gameserver.stats.triggers.TriggerType;
import l2.gameserver.taskmanager.LazyPrecisionTaskManager;
import l2.gameserver.taskmanager.RegenTaskManager;
import l2.gameserver.templates.CharTemplate;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class Creature
extends GameObject {
    private static final Logger bA = LoggerFactory.getLogger(Creature.class);
    public static final double HEADINGS_IN_PI = 10430.378350470453;
    private Skill q;
    private long aW;
    private long aX;
    public int _scheduledCastInterval;
    public Future<?> _skillTask;
    public Future<?> _skillLaunchedTask;
    private Future<?> f;
    private Runnable c;
    private long aY;
    public static final int CLIENT_BAR_SIZE = 352;
    private int gq = -1;
    private int gr = -1;
    private int gs = -1;
    protected double _currentCp = 0.0;
    protected double _currentHp = 1.0;
    protected double _currentMp = 1.0;
    protected boolean _isAttackAborted;
    protected long _attackEndTime;
    protected long _attackReuseEndTime;
    private int gt = 0;
    private static final double[] a = new double[]{1.0, 0.9, 0.0, 7.0, 0.2, 0.01};
    protected final Map<Integer, Skill> _skills = new ConcurrentSkipListMap<Integer, Skill>();
    protected Map<TriggerType, Set<TriggerInfo>> _triggers;
    protected IntObjectMap<TimeStamp> _skillReuses = new CHashIntObjectMap();
    protected volatile EffectList _effectList;
    protected volatile CharStatsChangeRecorder<? extends Creature> _statsRecorder;
    private List<AbnormalEffect> aM = new CopyOnWriteArrayList<AbnormalEffect>();
    private List<Stats> aN;
    private int gu;
    private int gv;
    private int gw;
    protected AtomicBoolean isDead = new AtomicBoolean();
    protected AtomicBoolean isTeleporting = new AtomicBoolean();
    private Map<Integer, Integer> aA;
    protected boolean _isInvul;
    private boolean bl;
    private int gx = -1;
    private boolean bm;
    private boolean bn;
    private boolean bo;
    private AtomicState a;
    private AtomicState b;
    private AtomicState c;
    private AtomicState d;
    private AtomicState e;
    private AtomicState f;
    private AtomicState g;
    private AtomicState h;
    private AtomicState i;
    private AtomicState j;
    private AtomicState k;
    private AtomicState l;
    private AtomicState m;
    private AtomicState n;
    private AtomicState o;
    private AtomicState p;
    private AtomicState q;
    private boolean bp;
    private boolean bq;
    protected MoveActionBase moveAction = null;
    private final Lock p;
    private Future<?> g;
    private Runnable d;
    private volatile HardReference<? extends GameObject> target;
    private volatile HardReference<? extends Creature> r;
    private volatile HardReference<? extends Creature> s;
    private int gy;
    private final Calculator[] a;
    protected CharTemplate _template;
    protected CharTemplate _baseTemplate;
    protected volatile CharacterAI _ai;
    protected String _name;
    protected String _title;
    protected TeamType _team;
    private boolean br;
    private final Lock q;
    private Future<?> h;
    private Runnable e;
    private volatile SpecialEffectState a;
    private Zones a;
    protected volatile CharListenerList listeners;
    private List<Player> aO;
    private final Lock r;
    protected Long _storedId;
    protected HardReference<? extends Creature> reference;
    private Location t;
    private TIntHashSet a = new AtomicState();

    @Override
    public int getActingRange() {
        return 150;
    }

    public final Long getStoredId() {
        return this._storedId;
    }

    public Creature(int n, CharTemplate charTemplate) {
        super(n);
        this.b = new AtomicState();
        this.c = new AtomicState();
        this.d = new AtomicState();
        this.e = new AtomicState();
        this.f = new AtomicState();
        this.g = new AtomicState();
        this.h = new AtomicState();
        this.i = new AtomicState();
        this.j = new AtomicState();
        this.k = new AtomicState();
        this.l = new AtomicState();
        this.m = new AtomicState();
        this.n = new AtomicState();
        this.o = new AtomicState();
        this.p = new AtomicState();
        this.q = new AtomicState();
        this.p = new ReentrantLock();
        this.target = HardReferences.emptyRef();
        this.r = HardReferences.emptyRef();
        this.s = HardReferences.emptyRef();
        this._team = TeamType.NONE;
        this.q = new ReentrantLock();
        this.a = SpecialEffectState.FALSE;
        this.a = new Zones();
        this.r = new ReentrantLock();
        this.a = new TIntHashSet();
        this._template = charTemplate;
        this._baseTemplate = charTemplate;
        this.a = new Calculator[Stats.NUM_STATS];
        StatFunctions.addPredefinedFuncs(this);
        this.reference = new L2Reference<Creature>(this);
        this._storedId = GameObjectsStorage.put(this);
    }

    public HardReference<? extends Creature> getRef() {
        return this.reference;
    }

    public boolean isAttackAborted() {
        return this._isAttackAborted;
    }

    public final void abortAttack(boolean bl, boolean bl2) {
        if (this.isAttackingNow() || this.getAI().getIntention() == CtrlIntention.AI_INTENTION_ATTACK) {
            this._attackEndTime = 0L;
            if (bl) {
                this._isAttackAborted = true;
            }
            this.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            if (this.isPlayer() && bl2) {
                this.sendActionFailed();
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1S_ATTACK_FAILED).addName(this));
            }
        }
    }

    public final void abortCast(boolean bl, boolean bl2) {
        if (this.isCastingNow() && (bl || this.canAbortCast())) {
            Skill skill = this.q;
            Future<?> future = this._skillTask;
            Future<?> future2 = this._skillLaunchedTask;
            this.aU();
            this.clearCastVars();
            if (future != null) {
                future.cancel(false);
            }
            if (future2 != null) {
                future2.cancel(false);
            }
            if (skill != null) {
                skill.onAbortCast(this, this.getAI().getAttackTarget());
                this.removeSkillMastery(skill.getId());
            }
            this.broadcastPacket(new MagicSkillCanceled(this));
            this.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
            if (this.isPlayer() && bl2) {
                this.sendPacket((IStaticPacket)SystemMsg.YOUR_CASTING_HAS_BEEN_INTERRUPTED);
            }
        }
    }

    public final boolean canAbortCast() {
        return this.aW >= System.currentTimeMillis();
    }

    public boolean absorbAndReflect(Creature creature, Skill skill, double d, boolean bl) {
        double d2;
        if (creature.isDead()) {
            return false;
        }
        boolean bl2 = this.getActiveWeaponItem() != null && this.getActiveWeaponItem().getItemType() == WeaponTemplate.WeaponType.BOW;
        double d3 = 0.0;
        if (skill != null && skill.isMagic()) {
            d3 = creature.calcStat(Stats.REFLECT_AND_BLOCK_MSKILL_DAMAGE_CHANCE, 0.0, this, skill);
        } else if (skill != null && skill.getCastRange() <= 200) {
            d3 = creature.calcStat(Stats.REFLECT_AND_BLOCK_PSKILL_DAMAGE_CHANCE, 0.0, this, skill);
        } else if (skill == null && !bl2) {
            d3 = creature.calcStat(Stats.REFLECT_AND_BLOCK_DAMAGE_CHANCE, 0.0, this, null);
        }
        if (d3 > 0.0 && Rnd.chance(d3)) {
            this.reduceCurrentHp(d, creature, null, true, true, false, false, false, false, true);
            return true;
        }
        if (skill != null && skill.isMagic()) {
            d3 = creature.calcStat(Stats.REFLECT_MSKILL_DAMAGE_PERCENT, 0.0, this, skill);
        } else if (skill != null && skill.getCastRange() <= 200) {
            d3 = creature.calcStat(Stats.REFLECT_PSKILL_DAMAGE_PERCENT, 0.0, this, skill);
        } else if (skill == null && !bl2) {
            d3 = creature.calcStat(Stats.REFLECT_DAMAGE_PERCENT, 0.0, this, null);
        }
        if (d3 > 0.0 && creature.getCurrentHp() + creature.getCurrentCp() > d) {
            d2 = d3 / 100.0 * d;
            this.reduceCurrentHp(d2, creature, null, true, true, false, false, false, false, bl);
            if (bl && creature.isPlayable()) {
                creature.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_DONE_S3_POINTS_OF_DAMAGE_TO_C2_S4).addName(this)).addName(creature)).addNumber((int)(-d2))).addVisibleDamage(this, creature, (int)(-d2)));
            }
        }
        if ((d2 = this.calcStat(Stats.ABSORB_MANA_DAMAGE_PERCENT, 0.0)) > 0.0 && skill != null && creature.isMonster() && !creature.isSummon() && !creature.isInvul() && Rnd.nextDouble() < this.calcStat(Stats.ABSORB_MANA_DAMAGE_CHANCE, 0.0)) {
            int n = (int)Math.min(d2 * d, (double)this.getMaxMp() - this.getCurrentMp());
            if ((n = Math.min(n, (int)creature.getCurrentMp())) > 0) {
                this.setCurrentMp(this.getCurrentMp() + (double)n);
            }
        }
        if (skill != null || bl2) {
            return false;
        }
        if ((d = (double)((int)(d - creature.getCurrentCp()))) <= 0.0) {
            return false;
        }
        double d4 = this.gt < a.length ? a[this.gt] : 0.0;
        double d5 = d4 * this.calcStat(Stats.ABSORB_DAMAGE_PERCENT, 0.0, creature, null);
        if (d5 > 0.0 && !creature.isDamageBlocked()) {
            double d6 = this.calcStat(Stats.HP_LIMIT, null, null) * (double)this.getMaxHp() / 100.0;
            if (this.getCurrentHp() < d6) {
                this.setCurrentHp(Math.min(this._currentHp + d * d5 * Config.ALT_ABSORB_DAMAGE_MODIFIER / 100.0, d6), false);
            }
        }
        return false;
    }

    public double absorbToEffector(Creature creature, double d) {
        double d2 = this.calcStat(Stats.TRANSFER_TO_EFFECTOR_DAMAGE_PERCENT, 0.0);
        if (d2 > 0.0) {
            Effect effect = this.getEffectList().getEffectByType(EffectType.AbsorbDamageToEffector);
            if (effect == null) {
                return d;
            }
            Creature creature2 = effect.getEffector();
            if (creature2 == this || creature2.isDead() || !this.isInRange(creature2, 1200L)) {
                return d;
            }
            Player player = this.getPlayer();
            Player player2 = creature2.getPlayer();
            if (player != null && player2 != null) {
                if (!(player == player2 || player.isOnline() && player.isInParty() && player.getParty() == player2.getParty())) {
                    return d;
                }
            } else {
                return d;
            }
            double d3 = d * d2 * 0.01;
            d -= d3;
            creature2.reduceCurrentHp(d3, creature2, null, false, false, !creature.isPlayable(), false, true, false, true);
        }
        return d;
    }

    public double absorbToSummon(Creature creature, double d) {
        double d2 = this.calcStat(Stats.TRANSFER_TO_SUMMON_DAMAGE_PERCENT, 0.0);
        if (d2 > 0.0) {
            Summon summon = this.getPet();
            double d3 = d * d2 * 0.01;
            if (summon == null || summon.isDead() || summon.getCurrentHp() < d3) {
                this.getEffectList().stopEffects(EffectType.AbsorbDamageToSummon);
            } else if (summon.isSummon() && summon.isInRangeZ(this, 1200L)) {
                d -= d3;
                summon.reduceCurrentHp(d3, summon, null, false, false, false, false, true, false, true);
            }
        }
        return d;
    }

    public void addBlockStats(List<Stats> list) {
        if (this.aN == null) {
            this.aN = new ArrayList<Stats>();
        }
        this.aN.addAll(list);
    }

    public Skill addSkill(Skill skill) {
        if (skill == null) {
            return null;
        }
        Skill skill2 = this._skills.get(skill.getId());
        if (skill2 != null && skill2.getLevel() == skill.getLevel()) {
            return skill;
        }
        this._skills.put(skill.getId(), skill);
        if (skill2 != null) {
            this.removeStatsOwner(skill2);
            this.removeTriggers(skill2);
            this.removeAbnormals(skill2);
        }
        this.addTriggers(skill);
        this.addStatFuncs(skill.getStatFuncs());
        this.addAbnormals(skill);
        return skill2;
    }

    public Calculator[] getCalculators() {
        return this.a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void addStatFunc(Func func) {
        if (func == null) {
            return;
        }
        int n = func.stat.ordinal();
        Calculator[] calculatorArray = this.a;
        synchronized (this.a) {
            if (this.a[n] == null) {
                this.a[n] = new Calculator(func.stat, this);
            }
            this.a[n].addFunc(func);
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return;
        }
    }

    public final void addStatFuncs(Func[] funcArray) {
        for (Func func : funcArray) {
            this.addStatFunc(func);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void removeStatFunc(Func func) {
        if (func == null) {
            return;
        }
        int n = func.stat.ordinal();
        Calculator[] calculatorArray = this.a;
        synchronized (this.a) {
            if (this.a[n] != null) {
                this.a[n].removeFunc(func);
            }
            // ** MonitorExit[var3_3] (shouldn't be in output)
            return;
        }
    }

    public final void removeStatFuncs(Func[] funcArray) {
        for (Func func : funcArray) {
            this.removeStatFunc(func);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void removeStatsOwner(Object object) {
        Calculator[] calculatorArray = this.a;
        synchronized (this.a) {
            for (int i = 0; i < this.a.length; ++i) {
                if (this.a[i] == null) continue;
                this.a[i].removeOwner(object);
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return;
        }
    }

    public void altOnMagicUseTimer(Creature creature, Skill skill) {
        if (this.isAlikeDead()) {
            return;
        }
        List<Creature> list = skill.getTargets(this, creature, true);
        double d = skill.getMpConsume2();
        if (d > 0.0) {
            if (this._currentMp < d) {
                this.sendPacket((IStaticPacket)SystemMsg.NOT_ENOUGH_MP);
                return;
            }
            if (skill.isMagic()) {
                this.reduceCurrentMp(this.calcStat(Stats.MP_MAGIC_SKILL_CONSUME, d, creature, skill), null);
            } else {
                this.reduceCurrentMp(this.calcStat(Stats.MP_PHYSICAL_SKILL_CONSUME, d, creature, skill), null);
            }
        }
        this.callSkill(skill, list, false);
        this.broadcastPacket(new MagicSkillLaunched(this, skill, list));
    }

    public void altUseSkill(Skill skill, Creature creature) {
        int n;
        int[] nArray;
        if (skill == null) {
            return;
        }
        int n2 = skill.getId();
        if (this.isUnActiveSkill(n2)) {
            return;
        }
        if (this.isSkillDisabled(skill)) {
            this.sendReuseMessage(skill);
            return;
        }
        if (creature == null && (creature = skill.getAimingTarget(this, this.getTarget())) == null) {
            return;
        }
        this.getListeners().onMagicUse(skill, creature, true);
        double d = skill.getMpConsume1();
        if (d > 0.0) {
            if (this._currentMp < d) {
                this.sendPacket((IStaticPacket)SystemMsg.NOT_ENOUGH_MP);
                return;
            }
            this.reduceCurrentMp(d, null);
        }
        if ((nArray = skill.getItemConsume())[0] > 0) {
            for (n = 0; n < nArray.length; ++n) {
                if (this.consumeItem(skill.getItemConsumeId()[n], nArray[n])) continue;
                this.sendPacket((IStaticPacket)(skill.isHandler() ? SystemMsg.INCORRECT_ITEM_COUNT : new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(skill.getDisplayId(), skill.getDisplayLevel())));
                return;
            }
        }
        if (skill.getReferenceItemId() > 0 && !this.consumeItemMp(skill.getReferenceItemId(), skill.getReferenceItemMpConsume())) {
            return;
        }
        if (skill.getSoulsConsume() > this.getConsumedSouls()) {
            this.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SOULS);
            return;
        }
        if (skill.getEnergyConsume() > this.getAgathionEnergy()) {
            this.sendPacket((IStaticPacket)SystemMsg.THE_SKILL_HAS_BEEN_CANCELED_BECAUSE_YOU_HAVE_INSUFFICIENT_ENERGY);
            return;
        }
        if (skill.getSoulsConsume() > 0) {
            this.setConsumedSouls(this.getConsumedSouls() - skill.getSoulsConsume(), null);
        }
        if (skill.getEnergyConsume() > 0) {
            this.setAgathionEnergy(this.getAgathionEnergy() - skill.getEnergyConsume());
        }
        n = Math.max(1, this.getSkillDisplayLevel(n2));
        Formulas.calcSkillMastery(skill, this);
        long l = Formulas.calcSkillReuseDelay(this, skill);
        if (!skill.isToggle()) {
            this.broadcastPacket(new MagicSkillUse(this, creature, skill, l));
        }
        if (!skill.isHideUseMessage()) {
            if (skill.getSkillType() == Skill.SkillType.PET_SUMMON) {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SUMMONING_YOUR_PET));
            } else if (!skill.isHandler()) {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_USE_S1).addSkillName(n2, n));
            } else {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_USE_S1).addItemName(skill.getItemConsumeId()[0]));
            }
        }
        if (!skill.isHandler()) {
            this.disableSkill(skill, l);
        }
        ThreadPoolManager.getInstance().schedule(new GameObjectTasks.AltMagicUseTask(this, creature, skill), skill.getHitTime());
    }

    public void sendReuseMessage(Skill skill) {
    }

    public void broadcastPacket(L2GameServerPacket ... l2GameServerPacketArray) {
        this.sendPacket(l2GameServerPacketArray);
        this.broadcastPacketToOthers(l2GameServerPacketArray);
    }

    public void broadcastPacket(List<L2GameServerPacket> list) {
        this.sendPacket(list);
        this.broadcastPacketToOthers(list);
    }

    public void broadcastPacketToOthers(L2GameServerPacket ... l2GameServerPacketArray) {
        if (!this.isVisible() || l2GameServerPacketArray.length == 0) {
            return;
        }
        List<Player> list = World.getAroundPlayers(this);
        for (int i = 0; i < list.size(); ++i) {
            Player player = list.get(i);
            player.sendPacket(l2GameServerPacketArray);
        }
    }

    public void broadcastPacketToOthers(List<L2GameServerPacket> list) {
        if (!this.isVisible() || list.isEmpty()) {
            return;
        }
        List<Player> list2 = World.getAroundPlayers(this);
        for (int i = 0; i < list2.size(); ++i) {
            Player player = list2.get(i);
            player.sendPacket(list);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void broadcastToStatusListeners(L2GameServerPacket ... l2GameServerPacketArray) {
        if (!this.isVisible() || l2GameServerPacketArray.length == 0) {
            return;
        }
        this.r.lock();
        try {
            if (this.aO == null || this.aO.isEmpty()) {
                return;
            }
            for (int i = 0; i < this.aO.size(); ++i) {
                Player player = this.aO.get(i);
                player.sendPacket(l2GameServerPacketArray);
            }
        }
        finally {
            this.r.unlock();
        }
    }

    public void broadCastCustomMessage(String string, Player player, Object ... objectArray) {
        for (Player player2 : World.getAroundPlayers(player)) {
            player2.sendMessage(new CustomMessage(string, player2, objectArray));
        }
        player.sendMessage(new CustomMessage(string, player, objectArray));
    }

    public void addStatusListener(Player player) {
        if (player == this) {
            return;
        }
        this.r.lock();
        try {
            if (this.aO == null) {
                this.aO = new LazyArrayList<Player>();
            }
            if (!this.aO.contains(player)) {
                this.aO.add(player);
            }
        }
        finally {
            this.r.unlock();
        }
    }

    public void removeStatusListener(Creature creature) {
        this.r.lock();
        try {
            if (this.aO == null) {
                return;
            }
            this.aO.remove(creature);
        }
        finally {
            this.r.unlock();
        }
    }

    public void clearStatusListeners() {
        this.r.lock();
        try {
            if (this.aO == null) {
                return;
            }
            this.aO.clear();
        }
        finally {
            this.r.unlock();
        }
    }

    public StatusUpdate makeStatusUpdate(int ... nArray) {
        StatusUpdate statusUpdate = new StatusUpdate(this.getObjectId());
        block10: for (int n : nArray) {
            switch (n) {
                case 9: {
                    statusUpdate.addAttribute(n, (int)this.getCurrentHp());
                    continue block10;
                }
                case 10: {
                    statusUpdate.addAttribute(n, this.getMaxHp());
                    continue block10;
                }
                case 11: {
                    statusUpdate.addAttribute(n, (int)this.getCurrentMp());
                    continue block10;
                }
                case 12: {
                    statusUpdate.addAttribute(n, this.getMaxMp());
                    continue block10;
                }
                case 27: {
                    statusUpdate.addAttribute(n, -this.getKarma());
                    continue block10;
                }
                case 33: {
                    statusUpdate.addAttribute(n, (int)this.getCurrentCp());
                    continue block10;
                }
                case 34: {
                    statusUpdate.addAttribute(n, this.getMaxCp());
                    continue block10;
                }
                case 26: {
                    statusUpdate.addAttribute(n, this.getPvpFlag());
                }
            }
        }
        return statusUpdate;
    }

    public void broadcastStatusUpdate() {
        if (!this.needStatusUpdate()) {
            return;
        }
        StatusUpdate statusUpdate = this.makeStatusUpdate(10, 12, 9, 11);
        this.broadcastToStatusListeners(statusUpdate);
    }

    public int calcHeading(int n, int n2) {
        return (int)(Math.atan2(this.getY() - n2, this.getX() - n) * 10430.378350470453) + 32768;
    }

    public final double calcStat(Stats stats, double d) {
        return this.calcStat(stats, d, null, null);
    }

    public final double calcStat(Stats stats, double d, Creature creature, Skill skill) {
        int n = stats.ordinal();
        Calculator calculator = this.a[n];
        if (calculator == null) {
            return d;
        }
        Env env = new Env();
        env.character = this;
        env.target = creature;
        env.skill = skill;
        env.value = d;
        calculator.calc(env);
        return env.value;
    }

    public final double calcStat(Stats stats, Creature creature, Skill skill) {
        Env env = new Env(this, creature, skill);
        env.value = stats.getInit();
        int n = stats.ordinal();
        Calculator calculator = this.a[n];
        if (calculator != null) {
            calculator.calc(env);
        }
        return env.value;
    }

    public int calculateAttackDelay() {
        return Formulas.calcPAtkSpd(this.getPAtkSpd());
    }

    public void callSkill(Skill skill, List<Creature> list, boolean bl) {
        try {
            if (bl && !skill.isUsingWhileCasting() && this._triggers != null) {
                if (skill.isOffensive()) {
                    if (skill.isMagic()) {
                        this.useTriggers(this.getTarget(), TriggerType.OFFENSIVE_MAGICAL_SKILL_USE, null, skill, 0.0);
                    } else {
                        this.useTriggers(this.getTarget(), TriggerType.OFFENSIVE_PHYSICAL_SKILL_USE, null, skill, 0.0);
                    }
                } else if (Config.BUFF_STICK_FOR_ALL || skill.isMagic()) {
                    boolean bl2 = skill.isAoE() || skill.isNotTargetAoE() || skill.getTargetType() == Skill.SkillTargetType.TARGET_SELF;
                    this.useTriggers(bl2 ? this : this.getTarget(), TriggerType.SUPPORT_MAGICAL_SKILL_USE, null, skill, 0.0);
                }
            }
            Player player = this.getPlayer();
            Iterator<Creature> iterator = list.iterator();
            while (iterator.hasNext()) {
                Object object;
                Object object2;
                Object object3;
                Object object4;
                Creature creature = iterator.next();
                if (skill.isOffensive() && creature.isInvul()) {
                    object4 = creature.getPlayer();
                    if ((!skill.isIgnoreInvul() || object4 != null && ((Player)object4).isGM()) && !creature.isArtefact()) {
                        iterator.remove();
                        continue;
                    }
                }
                if ((object4 = creature.getEffectList().getEffectByType(EffectType.IgnoreSkill)) != null && ArrayUtils.contains((int[])((Effect)object4).getTemplate().getParam().getIntegerArray("skillId"), (int)skill.getId())) {
                    iterator.remove();
                    continue;
                }
                creature.getListeners().onMagicHit(skill, this);
                if (player != null && creature != null && creature.isNpc() && (object3 = player.getQuestsForEvent((NpcInstance)(object2 = (NpcInstance)creature), QuestEventType.MOB_TARGETED_BY_SKILL)) != null) {
                    object = object3.iterator();
                    while (object.hasNext()) {
                        QuestState questState = (QuestState)object.next();
                        questState.getQuest().notifySkillUse((NpcInstance)object2, skill, questState);
                    }
                }
                if (skill.getNegateSkill() > 0) {
                    object2 = creature.getEffectList().getAllEffects().iterator();
                    while (object2.hasNext()) {
                        object3 = (Effect)object2.next();
                        object = ((Effect)object3).getSkill();
                        if (((Skill)object).getId() != skill.getNegateSkill() || !((Effect)object3).isCancelable() || skill.getNegatePower() > 0 && !(((Skill)object).getPower() <= (double)skill.getNegatePower())) continue;
                        ((Effect)object3).exit();
                    }
                }
                if (skill.getCancelTarget() <= 0 || !Rnd.chance(skill.getCancelTarget()) || creature.getCastingSkill() != null && creature.getCastingSkill().getSkillType() == Skill.SkillType.TAKECASTLE || creature.isRaid()) continue;
                creature.abortAttack(true, true);
                creature.abortCast(true, true);
                creature.setTarget(null);
            }
            if (skill.isOffensive()) {
                this.startAttackStanceTask();
            }
            if (!skill.isNotTargetAoE() || !skill.isOffensive() || list.size() != 0) {
                skill.getEffects(this, this, false, true);
            }
            skill.useSkill(this, list);
        }
        catch (Exception exception) {
            bA.error("", (Throwable)exception);
        }
    }

    public void useTriggers(GameObject gameObject, TriggerType triggerType, Skill skill, Skill skill2, double d) {
        if (this._triggers == null) {
            return;
        }
        Set<TriggerInfo> set = this._triggers.get((Object)triggerType);
        if (set != null) {
            for (TriggerInfo triggerInfo : set) {
                if (triggerInfo.getSkill() == skill) continue;
                this.useTriggerSkill(gameObject == null ? this.getTarget() : gameObject, triggerInfo, skill2, d);
            }
        }
    }

    public void useTriggerSkill(GameObject gameObject, TriggerInfo triggerInfo, Skill skill, double d) {
        Creature creature;
        Skill skill2 = triggerInfo.getSkill();
        if (skill2.getReuseDelay() > 0L && this.isSkillDisabled(skill2)) {
            return;
        }
        Creature creature2 = skill2.getAimingTarget(this, gameObject);
        if (creature2 == null || creature2.isDead()) {
            return;
        }
        int n = skill2.getCastRange();
        if (creature2 != this && n > 0 && n != Short.MAX_VALUE && this.getRealDistance3D(creature2) > (double)n) {
            return;
        }
        Creature creature3 = creature = gameObject != null && gameObject.isCreature() ? (Creature)gameObject : null;
        if (Rnd.chance(triggerInfo.getChance()) && triggerInfo.checkCondition(this, creature, creature2, skill, d) && skill2.checkCondition(this, creature2, false, true, true)) {
            int n2 = 0;
            int n3 = 0;
            if (skill2.hasEffects()) {
                n2 = skill2.getEffectTemplates()[0]._displayId;
                n3 = skill2.getEffectTemplates()[0]._displayLevel;
            }
            if (n2 == 0) {
                n2 = skill2.getDisplayId();
            }
            if (n3 == 0) {
                n3 = skill2.getDisplayLevel();
            }
            this.disableSkill(skill2, skill2.getReuseDelay());
            if (triggerInfo.getType() != TriggerType.SUPPORT_MAGICAL_SKILL_USE) {
                List<Creature> list = skill2.getTargets(this, creature2, false);
                for (Creature creature4 : list) {
                    this.broadcastPacket(new MagicSkillUse(this, creature4, n2, n3, 0, 0L));
                }
                ThreadPoolManager.getInstance().schedule(new GameObjectTasks.AltMagicUseTask(this, creature2, skill2), skill2.getHitTime());
            } else {
                ThreadPoolManager.getInstance().schedule(new GameObjectTasks.AltMagicUseTask(this, creature2, skill2), 25L);
            }
        }
    }

    public boolean checkBlockedStat(Stats stats) {
        return this.aN != null && this.aN.contains((Object)stats);
    }

    public boolean checkReflectSkill(Creature creature, Skill skill) {
        if (!skill.isReflectable()) {
            return false;
        }
        if (this.isInvul() || creature.isInvul() || !skill.isOffensive()) {
            return false;
        }
        if (skill.isMagic() && skill.getSkillType() != Skill.SkillType.MDAM) {
            return false;
        }
        if (Rnd.chance(this.calcStat(skill.isMagic() ? Stats.REFLECT_MAGIC_SKILL : Stats.REFLECT_PHYSIC_SKILL, 0.0, creature, skill))) {
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_COUNTERED_C1S_ATTACK).addName(creature));
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_DODGES_THE_ATTACK).addName(this));
            return true;
        }
        return false;
    }

    public void doCounterAttack(Skill skill, Creature creature, boolean bl) {
        if (this.isDead()) {
            return;
        }
        if (this.isDamageBlocked() || creature.isDamageBlocked()) {
            return;
        }
        if (skill == null || skill.hasEffects() || skill.isMagic() || !skill.isOffensive() || skill.getCastRange() > Config.COUNTERATTACK_MAX_SKILL_RANGE) {
            return;
        }
        if (Rnd.chance(this.calcStat(Stats.COUNTER_ATTACK, 0.0, creature, skill))) {
            double d = 700.94 * (double)this.getPAtk(creature) / (double)Math.max(creature.getPDef(this), 1);
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_IS_PERFORMING_A_COUNTERATTACK).addName(this));
            if (bl) {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HIT_FOR_S1_DAMAGE).addNumber((long)d));
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HIT_FOR_S1_DAMAGE).addNumber((long)d));
                creature.reduceCurrentHp(d, this, skill, true, true, false, false, false, false, true);
            } else {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HIT_FOR_S1_DAMAGE).addNumber((long)d));
            }
            creature.reduceCurrentHp(d, this, skill, true, true, false, false, false, false, true);
        }
    }

    public void disableSkill(Skill skill, long l) {
        this.getSkillReuses0().put(skill.hashCode(), (Object)new TimeStamp(skill, l));
    }

    public abstract boolean isAutoAttackable(Creature var1);

    public void doAttack(Creature creature) {
        if (creature == null || this.isAMuted() || this.isAttackingNow() || this.isAlikeDead() || creature.isAlikeDead() || !this.isInRange(creature, 2048L) || this.isPlayer() && this.getPlayer().isInMountTransform()) {
            return;
        }
        this.getListeners().onAttack(creature);
        if (this.isPlayer()) {
            Player player = this.getPlayer();
            player.triggerAfterTeleportProtection();
            player.triggerNoCarrierProtection();
        }
        int n = Math.max(this.calculateAttackDelay(), Config.MIN_ATK_DELAY);
        int n2 = 0;
        int n3 = n;
        this._attackEndTime = (long)n + System.currentTimeMillis() - (long)Config.ATTACK_END_DELAY;
        this._isAttackAborted = false;
        WeaponTemplate weaponTemplate = this.getActiveWeaponItem();
        if (weaponTemplate != null) {
            n3 = n + (int)((float)weaponTemplate.getAttackReuseDelay() / ((float)this.getPAtkSpd() / 333.0f));
            if (this.isPlayer() && weaponTemplate.getAttackReuseDelay() > 0 && n3 > 0) {
                this.sendPacket((IStaticPacket)new SetupGauge(this, 1, n3));
                this._attackReuseEndTime = (long)n3 + System.currentTimeMillis() - (long)Config.ATTACK_END_DELAY;
            }
            n2 = weaponTemplate.getCrystalType().gradeOrd();
        }
        Attack attack = new Attack(this, creature, this.getChargedSoulShot(), n2);
        this.setHeading(PositionUtils.calculateHeadingFrom(this, creature));
        int n4 = n3 / 2;
        if (weaponTemplate == null) {
            this.a(attack, creature, 1.0, !this.isPlayer(), n4, true);
        } else {
            switch (weaponTemplate.getItemType()) {
                case BOW: {
                    this.a(attack, creature, n4);
                    break;
                }
                case POLE: {
                    this.c(attack, creature, n4);
                    break;
                }
                case DUAL: 
                case DUALFIST: {
                    this.b(attack, creature, n4);
                    break;
                }
                default: {
                    this.a(attack, creature, 1.0, true, n4, true);
                }
            }
        }
        if (attack.hasHits()) {
            this.broadcastPacket(attack);
        }
    }

    private void a(Attack attack, Creature creature, double d, boolean bl, long l, boolean bl2) {
        int n = 0;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = Formulas.calcHitMiss(this, creature);
        if (!bl5) {
            Formulas.AttackInfo attackInfo = Formulas.calcPhysDam(this, creature, null, false, false, attack._soulshot, false);
            n = (int)(attackInfo.damage * d);
            bl3 = attackInfo.shld;
            bl4 = attackInfo.crit;
        }
        ThreadPoolManager.getInstance().schedule(new GameObjectTasks.HitTask(this, creature, n, bl4, bl5, attack._soulshot, bl3, bl, bl2, l), l);
        attack.addHit(creature, n, bl5, bl4, bl3);
    }

    private void a(Attack attack, Creature creature, long l) {
        WeaponTemplate weaponTemplate = this.getActiveWeaponItem();
        if (weaponTemplate == null) {
            return;
        }
        int n = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = Formulas.calcHitMiss(this, creature);
        if (Config.ALT_CONSUME_ARROWS && (this.isPlayable() && !this.getPlayer().hasBonus() || Config.ALT_PA_CONSUME_ARROWS)) {
            this.reduceArrowCount();
        }
        if (!bl3) {
            Formulas.AttackInfo attackInfo = Formulas.calcPhysDam(this, creature, null, false, false, attack._soulshot, false);
            n = (int)attackInfo.damage;
            bl = attackInfo.shld;
            bl2 = attackInfo.crit;
        }
        ThreadPoolManager.getInstance().schedule(new GameObjectTasks.HitTask(this, creature, n, bl2, bl3, attack._soulshot, bl, true, true, true, l), l);
        attack.addHit(creature, n, bl3, bl2, bl);
    }

    private void b(Attack attack, Creature creature, long l) {
        Formulas.AttackInfo attackInfo;
        int n = 0;
        int n2 = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = Formulas.calcHitMiss(this, creature);
        boolean bl6 = Formulas.calcHitMiss(this, creature);
        if (!bl5) {
            attackInfo = Formulas.calcPhysDam(this, creature, null, true, false, attack._soulshot, false);
            n = (int)attackInfo.damage;
            bl = attackInfo.shld;
            bl3 = attackInfo.crit;
        }
        if (!bl6) {
            attackInfo = Formulas.calcPhysDam(this, creature, null, true, false, attack._soulshot, false);
            n2 = (int)attackInfo.damage;
            bl2 = attackInfo.shld;
            bl4 = attackInfo.crit;
        }
        ThreadPoolManager.getInstance().schedule(new GameObjectTasks.HitTask(this, creature, n, bl3, bl5, attack._soulshot, bl, true, false), l / 2L);
        ThreadPoolManager.getInstance().schedule(new GameObjectTasks.HitTask(this, creature, n2, bl4, bl6, attack._soulshot, bl2, false, true, l), l);
        attack.addHit(creature, n, bl5, bl3, bl);
        attack.addHit(creature, n2, bl6, bl4, bl2);
    }

    private void c(Attack attack, Creature creature, long l) {
        int n = (int)this.calcStat(Stats.POLE_ATTACK_ANGLE, 90.0, creature, null);
        int n2 = (int)this.calcStat(Stats.POWER_ATTACK_RANGE, this.getTemplate().baseAtkRange, creature, null);
        int n3 = (int)Math.round(this.calcStat(Stats.POLE_TARGET_COUNT, 0.0, creature, null));
        if (this.isBoss()) {
            n3 += 27;
        } else if (this.isRaid()) {
            n3 += 12;
        } else if (this.isMonster() && this.getLevel() > 0) {
            n3 = (int)((double)n3 + (double)this.getLevel() / 7.5);
        }
        double d = 1.0;
        this.gt = 1;
        if (!this.isInZonePeace()) {
            for (Creature creature2 : this.getAroundCharacters(n2, 200)) {
                if (this.gt > n3) break;
                if (creature2 == creature || creature2.isDead() || !PositionUtils.isFacing(this, creature2, n) || !creature2.isAutoAttackable(this)) continue;
                this.a(attack, creature2, d, false, l, false);
                d *= Config.ALT_POLE_DAMAGE_MODIFIER;
                ++this.gt;
            }
        }
        this.gt = 0;
        this.a(attack, creature, 1.0, true, l, true);
    }

    public long getAnimationEndTime() {
        return this.aX;
    }

    public void doCast(Skill skill, Creature creature, boolean bl) {
        double d;
        int n;
        if (skill == null) {
            return;
        }
        int[] nArray = skill.getItemConsume();
        if (nArray[0] > 0) {
            for (n = 0; n < nArray.length; ++n) {
                if (this.consumeItem(skill.getItemConsumeId()[n], nArray[n])) continue;
                this.sendPacket((IStaticPacket)(skill.isHandler() ? SystemMsg.INCORRECT_ITEM_COUNT : new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(skill.getId(), skill.getLevel())));
                return;
            }
        }
        if (skill.getReferenceItemId() > 0 && !this.consumeItemMp(skill.getReferenceItemId(), skill.getReferenceItemMpConsume())) {
            return;
        }
        n = skill.getId();
        if (creature == null) {
            creature = skill.getAimingTarget(this, this.getTarget());
        }
        if (creature == null) {
            return;
        }
        this.getListeners().onMagicUse(skill, creature, false);
        if (this != creature) {
            this.setHeading(PositionUtils.calculateHeadingFrom(this, creature));
        }
        int n2 = Math.max(1, this.getSkillDisplayLevel(n));
        int n3 = skill.isSkillTimePermanent() ? skill.getHitTime() : Formulas.calcMAtkSpd(this, skill, skill.getHitTime());
        int n4 = skill.getSkillInterruptTime();
        int n5 = Math.min(Config.SKILLS_CAST_TIME_MIN, skill.getHitTime());
        if (n3 < n5) {
            n3 = n5;
            n4 = 0;
        }
        this.aX = System.currentTimeMillis() + (long)n3;
        if (skill.isMagic() && !skill.isSkillTimePermanent() && this.getChargedSpiritShot() > 0) {
            n3 = (int)(0.7 * (double)n3);
            n4 = (int)(0.7 * (double)n4);
        }
        Formulas.calcSkillMastery(skill, this);
        long l = Math.max(0L, Formulas.calcSkillReuseDelay(this, skill));
        this.broadcastPacket(new MagicSkillUse(this, creature, skill, n3, l));
        if (!skill.isHandler()) {
            this.disableSkill(skill, l);
        }
        if (this.isPlayer()) {
            if (skill.getSkillType() == Skill.SkillType.PET_SUMMON) {
                this.sendPacket((IStaticPacket)SystemMsg.SUMMONING_YOUR_PET);
            } else if (skill.getItemConsumeId()[0] == 0 || !skill.isHandler()) {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_USE_S1).addSkillName(n, n2));
            } else {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_USE_S1).addItemName(skill.getItemConsumeId()[0]));
            }
        }
        if (skill.getTargetType() == Skill.SkillTargetType.TARGET_HOLY) {
            creature.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, this, 1);
        }
        double d2 = d = skill.isUsingWhileCasting() ? skill.getMpConsume() : skill.getMpConsume1();
        if (d > 0.0) {
            if (this._currentMp < d) {
                this.sendPacket((IStaticPacket)SystemMsg.NOT_ENOUGH_MP);
                this.onCastEndTime();
                return;
            }
            this.reduceCurrentMp(d, null);
        }
        this.t = null;
        switch (skill.getFlyType()) {
            case DUMMY: 
            case CHARGE: {
                Location location = this.a(creature, skill);
                if (location != null) {
                    this.t = location;
                    this.broadcastPacket(new FlyToLocation(this, location, skill.getFlyType()));
                    break;
                }
                this.aX = 0L;
                this.sendPacket((IStaticPacket)SystemMsg.CANNOT_SEE_TARGET);
                return;
            }
        }
        this.q = skill;
        int n6 = n4 > 0 ? Math.max(0, n3 - n4) : 0;
        this.aW = System.currentTimeMillis() + (long)n6;
        this.setCastingTarget(creature);
        if (skill.isUsingWhileCasting()) {
            this.callSkill(skill, skill.getTargets(this, creature, bl), true);
        }
        this._scheduledCastInterval = n3;
        if (n3 > 333 && this.isPlayer()) {
            this.sendPacket((IStaticPacket)new SetupGauge(this, 0, n3));
        }
        this.scheduleSkillLaunchedTask(bl, n6);
        this.scheduleSkillUseTask(bl, n3);
    }

    protected void scheduleSkillLaunchedTask(boolean bl, int n) {
        this._skillLaunchedTask = ThreadPoolManager.getInstance().schedule(new GameObjectTasks.MagicLaunchedTask(this, bl), n);
    }

    protected void scheduleSkillUseTask(boolean bl, int n) {
        this._skillTask = ThreadPoolManager.getInstance().schedule(new GameObjectTasks.MagicUseTask(this, bl), n);
    }

    public void clearCastVars() {
        this.aX = 0L;
        this.aW = 0L;
        this.q = null;
        this._skillTask = null;
        this._skillLaunchedTask = null;
        this.t = null;
    }

    private Location a(GameObject gameObject, Skill skill) {
        if (gameObject != null && gameObject != this) {
            Location location;
            if (skill.isFlyToBack()) {
                double d = PositionUtils.convertHeadingToRadian(gameObject.getHeading());
                location = new Location(gameObject.getX() + (int)(Math.sin(d) * 40.0), gameObject.getY() - (int)(Math.cos(d) * 40.0), gameObject.getZ());
            } else {
                double d = Math.atan2(this.getY() - gameObject.getY(), this.getX() - gameObject.getX());
                location = new Location(gameObject.getX() + (int)Math.round(Math.cos(d) * 40.0), gameObject.getY() + (int)Math.round(Math.sin(d) * 40.0), gameObject.getZ());
            }
            if (this.isFlying()) {
                if (this.isPlayer() && ((Player)this).isInFlyingTransform() && (location.z <= 0 || location.z >= 6000)) {
                    return null;
                }
                if (GeoEngine.moveCheckInAir(this.getX(), this.getY(), this.getZ(), location.x, location.y, location.z, this.getColRadius(), this.getGeoIndex()) == null) {
                    return null;
                }
            } else {
                location.correctGeoZ();
                if (!GeoEngine.canMoveToCoord(this.getX(), this.getY(), this.getZ(), location.x, location.y, location.z, this.getGeoIndex())) {
                    location = gameObject.getLoc();
                    if (!GeoEngine.canMoveToCoord(this.getX(), this.getY(), this.getZ(), location.x, location.y, location.z, this.getGeoIndex())) {
                        return null;
                    }
                }
            }
            return location;
        }
        double d = PositionUtils.convertHeadingToRadian(this.getHeading());
        int n = -((int)(Math.sin(d) * (double)skill.getFlyRadius()));
        int n2 = (int)(Math.cos(d) * (double)skill.getFlyRadius());
        if (this.isFlying()) {
            return GeoEngine.moveCheckInAir(this.getX(), this.getY(), this.getZ(), this.getX() + n, this.getY() + n2, this.getZ(), this.getColRadius(), this.getGeoIndex());
        }
        return GeoEngine.moveCheck(this.getX(), this.getY(), this.getZ(), this.getX() + n, this.getY() + n2, this.getGeoIndex());
    }

    public final void doDie(Creature creature) {
        if (!this.isDead.compareAndSet(false, true)) {
            return;
        }
        this.onDeath(creature);
    }

    protected void onDeath(Creature creature) {
        if (creature != null) {
            Player player = creature.getPlayer();
            if (player != null) {
                player.getListeners().onKillIgnorePetOrSummon(this);
            }
            creature.getListeners().onKill(this);
            if (this.isPlayer() && creature.isPlayable()) {
                this._currentCp = 0.0;
            }
        }
        this.setTarget(null);
        this.stopMove();
        this.stopAttackStanceTask();
        this.stopRegeneration();
        this._currentHp = 0.0;
        if (this.isBlessedByNoblesse() || this.isSalvation()) {
            if (this.isSalvation() && this.isPlayer() && !this.getPlayer().isOlyParticipant() && !this.getPlayer().isResurectProhibited()) {
                this.getPlayer().reviveRequest(this.getPlayer(), 100.0, false, this.gx);
            }
            for (Effect effect : this.getEffectList().getAllEffects()) {
                if (effect.getEffectType() != EffectType.BlessNoblesse && effect.getSkill().getId() != 1325 && effect.getSkill().getId() != 2168) continue;
                effect.exit();
            }
        } else if (Config.ALT_PASSIVE_NOBLESS_ID == 0 || this.getKnownSkill(Config.ALT_PASSIVE_NOBLESS_ID) == null) {
            for (Effect effect : this.getEffectList().getAllEffects()) {
                if (effect.getEffectType() == EffectType.Transformation || effect.getSkill().isPreservedOnDeath()) continue;
                effect.exit();
            }
        }
        ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(this, CtrlEvent.EVT_DEAD, creature, null));
        this.getListeners().onDeath(creature);
        this.updateEffectIcons();
        this.updateStats();
        this.broadcastStatusUpdate();
    }

    protected void onRevive() {
    }

    public void enableSkill(Skill skill) {
        this.getSkillReuses0().remove(skill.hashCode());
    }

    public int getAbnormalEffect() {
        return this.gu;
    }

    public AbnormalEffect[] getAbnormalEffects() {
        return this.aM.toArray(AbnormalEffect.EMPTY_ARRAY);
    }

    public int getAccuracy() {
        return (int)this.calcStat(Stats.ACCURACY_COMBAT, 0.0, null, null);
    }

    public Collection<Skill> getAllSkills() {
        return this._skills.values();
    }

    public final Skill[] getAllSkillsArray() {
        Collection<Skill> collection = this._skills.values();
        return collection.toArray(new Skill[collection.size()]);
    }

    public final double getAttackSpeedMultiplier() {
        return 1.1 * (double)this.getPAtkSpd() / (double)this.getTemplate().basePAtkSpd;
    }

    public int getBuffLimit() {
        return (int)this.calcStat(Stats.BUFF_LIMIT, Config.ALT_BUFF_LIMIT, null, null);
    }

    public Skill getCastingSkill() {
        return this.q;
    }

    public int getCON() {
        return (int)this.calcStat(Stats.STAT_CON, this._template.baseCON, null, null);
    }

    public int getCriticalHit(Creature creature, Skill skill) {
        return (int)this.calcStat(Stats.CRITICAL_BASE, this._template.baseCritRate, creature, skill);
    }

    public double getMagicCriticalRate(Creature creature, Skill skill) {
        return this.calcStat(Stats.MCRITICAL_RATE, creature, skill);
    }

    public final double getCurrentCp() {
        return this._currentCp;
    }

    public final double getCurrentCpRatio() {
        return this.getCurrentCp() / (double)this.getMaxCp();
    }

    public final double getCurrentCpPercents() {
        return this.getCurrentCpRatio() * 100.0;
    }

    public final boolean isCurrentCpFull() {
        return this.getCurrentCp() >= (double)this.getMaxCp();
    }

    public final boolean isCurrentCpZero() {
        return this.getCurrentCp() < 1.0;
    }

    public final double getCurrentHp() {
        return this._currentHp;
    }

    public final double getCurrentHpRatio() {
        return this.getCurrentHp() / (double)this.getMaxHp();
    }

    public final double getCurrentHpPercents() {
        return this.getCurrentHpRatio() * 100.0;
    }

    public final boolean isCurrentHpFull() {
        return this.getCurrentHp() >= (double)this.getMaxHp();
    }

    public final boolean isCurrentHpZero() {
        return this.getCurrentHp() < 1.0;
    }

    public final double getCurrentMp() {
        return this._currentMp;
    }

    public final double getCurrentMpRatio() {
        return this.getCurrentMp() / (double)this.getMaxMp();
    }

    public final double getCurrentMpPercents() {
        return this.getCurrentMpRatio() * 100.0;
    }

    public final boolean isCurrentMpFull() {
        return this.getCurrentMp() >= (double)this.getMaxMp();
    }

    public final boolean isCurrentMpZero() {
        return this.getCurrentMp() < 1.0;
    }

    public int getDEX() {
        return (int)this.calcStat(Stats.STAT_DEX, this._template.baseDEX, null, null);
    }

    public int getEvasionRate(Creature creature) {
        return (int)this.calcStat(Stats.EVASION_RATE, 0.0, creature, null);
    }

    public int getINT() {
        return (int)this.calcStat(Stats.STAT_INT, this._template.baseINT, null, null);
    }

    public List<Creature> getAroundCharacters(int n, int n2) {
        if (!this.isVisible()) {
            return Collections.emptyList();
        }
        return World.getAroundCharacters(this, n, n2);
    }

    public List<NpcInstance> getAroundNpc(int n, int n2) {
        if (!this.isVisible()) {
            return Collections.emptyList();
        }
        return World.getAroundNpc(this, n, n2);
    }

    public boolean knowsObject(GameObject gameObject) {
        return World.getAroundObjectById(this, gameObject.getObjectId()) != null;
    }

    public final Skill getKnownSkill(int n) {
        return this._skills.get(n);
    }

    public final int getMagicalAttackRange(Skill skill) {
        if (skill != null) {
            return (int)this.calcStat(Stats.MAGIC_ATTACK_RANGE, skill.getCastRange(), null, skill);
        }
        return this.getTemplate().baseAtkRange;
    }

    public final int getMagicalAttackRange(double d, Skill skill) {
        if (skill != null) {
            return (int)this.calcStat(Stats.MAGIC_ATTACK_RANGE, d, null, skill);
        }
        return this.getTemplate().baseAtkRange;
    }

    public int getMAtk(Creature creature, Skill skill) {
        if (skill != null && skill.getMatak() > 0) {
            return skill.getMatak();
        }
        return (int)this.calcStat(Stats.MAGIC_ATTACK, this._template.baseMAtk, creature, skill);
    }

    public int getMAtkSpd() {
        return (int)this.calcStat(Stats.MAGIC_ATTACK_SPEED, this._template.baseMAtkSpd, null, null);
    }

    public final int getMaxCp() {
        return (int)this.calcStat(Stats.MAX_CP, this._template.baseCpMax, null, null);
    }

    public int getMaxHp() {
        return (int)this.calcStat(Stats.MAX_HP, this._template.baseHpMax, null, null);
    }

    public int getMaxMp() {
        return (int)this.calcStat(Stats.MAX_MP, this._template.baseMpMax, null, null);
    }

    public int getMDef(Creature creature, Skill skill) {
        return Math.max((int)this.calcStat(Stats.MAGIC_DEFENCE, this._template.baseMDef, creature, skill), 1);
    }

    public int getMEN() {
        return (int)this.calcStat(Stats.STAT_MEN, this._template.baseMEN, null, null);
    }

    public double getMinDistance(GameObject gameObject) {
        double d = this.getTemplate().collisionRadius;
        if (gameObject != null && gameObject.isCreature()) {
            d += ((Creature)gameObject).getTemplate().collisionRadius;
        }
        return d;
    }

    public double getMovementSpeedMultiplier() {
        return this.isRunning() ? (double)this.getRunSpeed() / (double)this._template.baseRunSpd : (double)this.getWalkSpeed() / (double)this._template.baseWalkSpd;
    }

    @Override
    public int getMoveSpeed() {
        if (this.isRunning()) {
            return this.getRunSpeed();
        }
        return this.getWalkSpeed();
    }

    @Override
    public String getName() {
        return StringUtils.defaultString((String)this._name);
    }

    public int getPAtk(Creature creature) {
        return (int)this.calcStat(Stats.POWER_ATTACK, this._template.basePAtk, creature, null);
    }

    public int getPAtkSpd() {
        return (int)this.calcStat(Stats.POWER_ATTACK_SPEED, this._template.basePAtkSpd, null, null);
    }

    public int getPDef(Creature creature) {
        return (int)this.calcStat(Stats.POWER_DEFENCE, this._template.basePDef, creature, null);
    }

    public int getPhysicalAttackRange() {
        WeaponTemplate weaponTemplate = this.getActiveWeaponItem();
        if (weaponTemplate == null) {
            return (int)this.calcStat(Stats.POWER_ATTACK_RANGE, this.getTemplate().baseAtkRange, null, null);
        }
        return (int)this.calcStat(Stats.POWER_ATTACK_RANGE, weaponTemplate.getAttackRange(), null, null);
    }

    @Deprecated
    public final int getRandomDamage() {
        WeaponTemplate weaponTemplate = this.getActiveWeaponItem();
        if (weaponTemplate == null) {
            return 5 + (int)Math.sqrt(this.getLevel());
        }
        return weaponTemplate.getRandomDamage();
    }

    public double getReuseModifier(Creature creature) {
        return this.calcStat(Stats.ATK_REUSE, 1.0, creature, null);
    }

    public int getRunSpeed() {
        return this.getSpeed(this._template.baseRunSpd);
    }

    public final int getShldDef() {
        if (this.isPlayer()) {
            return (int)this.calcStat(Stats.SHIELD_DEFENCE, 0.0, null, null);
        }
        return (int)this.calcStat(Stats.SHIELD_DEFENCE, this._template.baseShldDef, null, null);
    }

    public final int getSkillDisplayLevel(Integer n) {
        Skill skill = this._skills.get(n);
        if (skill == null) {
            return -1;
        }
        return skill.getDisplayLevel();
    }

    public final int getSkillLevel(Integer n) {
        return this.getSkillLevel(n, -1);
    }

    public final int getSkillLevel(Integer n, int n2) {
        Skill skill = this._skills.get(n);
        if (skill == null) {
            return n2;
        }
        return skill.getLevel();
    }

    public int getSkillMastery(Integer n) {
        if (this.aA == null) {
            return 0;
        }
        Integer n2 = this.aA.get(n);
        return n2 == null ? 0 : n2;
    }

    public void removeSkillMastery(Integer n) {
        if (this.aA != null) {
            this.aA.remove(n);
        }
    }

    public int getSpeed(int n) {
        if (this.isInWater()) {
            return this.getSwimSpeed();
        }
        return (int)this.calcStat(Stats.RUN_SPEED, n, null, null);
    }

    public int getSTR() {
        return (int)this.calcStat(Stats.STAT_STR, this._template.baseSTR, null, null);
    }

    public int getSwimSpeed() {
        return (int)this.calcStat(Stats.RUN_SPEED, Config.SWIMING_SPEED, null, null);
    }

    public GameObject getTarget() {
        return this.target.get();
    }

    public final int getTargetId() {
        GameObject gameObject = this.getTarget();
        return gameObject == null ? -1 : gameObject.getObjectId();
    }

    public CharTemplate getTemplate() {
        return this._template;
    }

    public CharTemplate getBaseTemplate() {
        return this._baseTemplate;
    }

    public String getTitle() {
        return StringUtils.defaultString((String)this._title);
    }

    public final int getWalkSpeed() {
        if (this.isInWater()) {
            return this.getSwimSpeed();
        }
        return this.getSpeed(this._template.baseWalkSpd);
    }

    public int getWIT() {
        return (int)this.calcStat(Stats.STAT_WIT, this._template.baseWIT, null, null);
    }

    public double headingToRadians(int n) {
        return (double)(n - 32768) / 10430.378350470453;
    }

    public boolean isAlikeDead() {
        return this.isDead();
    }

    public final boolean isAttackingNow() {
        return this._attackEndTime > System.currentTimeMillis();
    }

    public final boolean isBlessedByNoblesse() {
        return this.bl;
    }

    public final boolean isSalvation() {
        return this.gx >= 0;
    }

    public boolean isEffectImmune() {
        return this.p.get();
    }

    public boolean isBuffImmune() {
        return this.n.get();
    }

    public boolean isDebuffImmune() {
        return this.o.get();
    }

    public boolean isDead() {
        return this._currentHp < 0.5 || this.isDead.get();
    }

    @Override
    public final boolean isFlying() {
        return this.bp;
    }

    public final boolean isInCombat() {
        return System.currentTimeMillis() < this.aY;
    }

    public boolean isInvul() {
        return this._isInvul;
    }

    public boolean isMageClass() {
        return this.getTemplate().baseMAtk > 3;
    }

    public final boolean isRunning() {
        return this.bq;
    }

    public boolean isSkillDisabled(Skill skill) {
        TimeStamp timeStamp = (TimeStamp)this.getSkillReuses0().get(skill.hashCode());
        if (timeStamp == null) {
            return false;
        }
        if (timeStamp.hasNotPassed()) {
            return true;
        }
        this.getSkillReuses0().remove(skill.hashCode());
        return false;
    }

    public final boolean isTeleporting() {
        return this.isTeleporting.get();
    }

    public Location getDestination() {
        if (this.moveAction != null && this.moveAction instanceof MoveToLocationAction) {
            return ((MoveToLocationAction)this.moveAction).moveTo().clone();
        }
        return null;
    }

    public Location getFinalDestination() {
        if (this.moveAction != null && this.moveAction instanceof MoveToLocationAction) {
            return ((MoveToLocationAction)this.moveAction).getFinalDest().clone();
        }
        return null;
    }

    public boolean isMoving() {
        MoveActionBase moveActionBase = this.moveAction;
        return moveActionBase != null && !moveActionBase.isFinished();
    }

    public boolean isFollowing() {
        MoveActionBase moveActionBase = this.moveAction;
        return moveActionBase != null && moveActionBase instanceof MoveToRelativeAction && !moveActionBase.isFinished();
    }

    public int maxZDiff() {
        MoveActionBase moveActionBase = this.moveAction;
        if (moveActionBase != null) {
            Location location = moveActionBase.moveFrom();
            Location location2 = moveActionBase.moveTo();
            if (location.getZ() > location2.getZ()) {
                int n = location.getZ() - location2.getZ();
                return n;
            }
        }
        return Config.MAX_Z_DIFF;
    }

    public Creature getFollowTarget() {
        GameObject gameObject;
        MoveToRelativeAction moveToRelativeAction;
        MoveActionBase moveActionBase = this.moveAction;
        MoveToRelativeAction moveToRelativeAction2 = moveToRelativeAction = moveActionBase != null ? moveActionBase.getAsMoveToRelative() : null;
        if (moveToRelativeAction != null && !moveToRelativeAction.isFinished() && (gameObject = moveToRelativeAction.getTarget()) != null && gameObject instanceof Creature) {
            return (Creature)gameObject;
        }
        return null;
    }

    protected MoveToRelativeAction createMoveToRelative(GameObject gameObject, int n, int n2, boolean bl) {
        return new MoveToRelativeAction(this, gameObject, !Config.ALLOW_GEODATA, n, n2, bl);
    }

    protected MoveToLocationAction createMoveToLocation(Location location, int n, boolean bl) {
        return new MoveToLocationAction(this, this.getLoc(), location, this.isInBoat() || this.isBoat() || !Config.ALLOW_GEODATA, n, bl);
    }

    public boolean moveToLocation(Location location, int n, boolean bl) {
        return this.moveToLocation(location.x, location.y, location.z, n, bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean moveToLocation(int n, int n2, int n3, int n4, boolean bl) {
        this.p.lock();
        try {
            MoveToLocationAction moveToLocationAction;
            n4 = Math.max(n4, 0);
            Location location = new Location(n, n2, n3);
            MoveActionBase moveActionBase = this.moveAction;
            MoveToLocationAction moveToLocationAction2 = moveToLocationAction = moveActionBase != null ? moveActionBase.getAsMoveToLocation() : null;
            if (moveToLocationAction != null && moveToLocationAction.isSameDest(location)) {
                this.sendActionFailed();
                boolean bl2 = false;
                return bl2;
            }
            if (this.isMovementDisabled()) {
                this.getAI().setNextAction(NextAction.MOVE, new Location(n, n2, n3), n4, bl, false);
                this.sendActionFailed();
                boolean bl3 = false;
                return bl3;
            }
            this.getAI().clearNextAction();
            if (this.isPlayer()) {
                Player player = this.getPlayer();
                this.getAI().changeIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
                player.triggerAfterTeleportProtection();
                player.triggerNoCarrierProtection();
            }
            this.stopMove(false, false);
            moveToLocationAction = this.createMoveToLocation(location, n4, bl);
            this.moveAction = moveToLocationAction;
            if (!moveToLocationAction.start()) {
                if (!bl) {
                    this.stopMove(true, false);
                }
                this.moveAction = null;
                this.sendActionFailed();
                boolean bl4 = false;
                return bl4;
            }
            this.getListeners().onMove(location);
            moveToLocationAction.scheduleNextTick();
            boolean bl5 = true;
            return bl5;
        }
        finally {
            this.p.unlock();
        }
    }

    public boolean moveToRelative(GameObject gameObject, int n, int n2) {
        return this.moveToRelative(gameObject, n, n2, Config.ALLOW_PAWN_PATHFIND);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean moveToRelative(GameObject gameObject, int n, int n2, boolean bl) {
        this.p.lock();
        try {
            MoveToRelativeAction moveToRelativeAction;
            if (this.isMovementDisabled() || gameObject == null || this.isInBoat()) {
                boolean bl2 = false;
                return bl2;
            }
            MoveActionBase moveActionBase = this.moveAction;
            MoveToRelativeAction moveToRelativeAction2 = moveToRelativeAction = moveActionBase != null ? moveActionBase.getAsMoveToRelative() : null;
            if (moveToRelativeAction != null && !moveActionBase.isFinished() && moveToRelativeAction.isSameTarget(gameObject)) {
                this.sendActionFailed();
                boolean bl3 = false;
                return bl3;
            }
            n2 = Math.max(n2, 10);
            n = Math.min(n, n2);
            this.getAI().clearNextAction();
            if (this.isPlayer()) {
                Player player = this.getPlayer();
                player.triggerAfterTeleportProtection();
                player.triggerNoCarrierProtection();
            }
            this.stopMove(false, false);
            moveToRelativeAction = this.createMoveToRelative(gameObject, n, n2, bl);
            this.moveAction = moveToRelativeAction;
            if (!moveToRelativeAction.start()) {
                this.moveAction = null;
                this.sendActionFailed();
                boolean bl4 = false;
                return bl4;
            }
            this.getListeners().onMove(gameObject.getLoc());
            moveToRelativeAction.scheduleNextTick();
            boolean bl5 = true;
            return bl5;
        }
        finally {
            this.p.unlock();
        }
    }

    private void aT() {
        this.validateLocation(this.isPlayer() ? 2 : 1);
        this.broadcastPacket(this.movePacket());
    }

    public void stopMove() {
        this.stopMove(true, true);
    }

    public void stopMove(boolean bl) {
        this.stopMove(true, bl);
    }

    public void stopMove(boolean bl, boolean bl2) {
        this.stopMove(bl, bl2, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stopMove(boolean bl, boolean bl2, boolean bl3) {
        if (!this.isMoving()) {
            return;
        }
        this.p.lock();
        try {
            if (!this.isMoving()) {
                return;
            }
            if (bl3 && this.moveAction != null && !this.moveAction.isFinished()) {
                this.moveAction.interrupt();
                this.moveAction = null;
            }
            if (this.g != null) {
                this.g.cancel(false);
                this.g = null;
            }
            if (bl2) {
                this.validateLocation(this.isPlayer() ? 2 : 1);
            }
            if (bl) {
                this.broadcastPacket(this.stopMovePacket());
            }
        }
        finally {
            this.p.unlock();
        }
    }

    public int getWaterZ() {
        if (!this.isInWater()) {
            return Integer.MIN_VALUE;
        }
        return this.getZones().getWaterZ();
    }

    protected L2GameServerPacket stopMovePacket() {
        return new StopMove(this);
    }

    public L2GameServerPacket movePacket() {
        MoveActionBase moveActionBase = this.moveAction;
        if (moveActionBase != null) {
            return moveActionBase.movePacket();
        }
        return new CharMoveToLocation(this);
    }

    public boolean updateZones() {
        if (this.isInObserverMode()) {
            return false;
        }
        return this.getZones().update(this);
    }

    protected void onUpdateZones(List<Zone> list, List<Zone> list2) {
        list.forEach(zone -> zone.doLeave(this));
        list2.forEach(zone -> zone.doEnter(this));
    }

    public boolean isInZonePeace() {
        return this.isInZone(Zone.ZoneType.peace_zone) && !this.isInZoneBattle();
    }

    public boolean isInZoneBattle() {
        return this.isInZone(Zone.ZoneType.battle_zone);
    }

    public boolean isInWater() {
        return this.isInZone(Zone.ZoneType.water) && !this.isInBoat() && !this.isBoat() && !this.isFlying();
    }

    public Zones getZones() {
        return this.a;
    }

    public boolean isInZone(Zone.ZoneType zoneType) {
        return this.getZones().isInZone(zoneType);
    }

    public boolean isInAnyZone(Zone.ZoneType ... zoneTypeArray) {
        return this.getZones().isInAnyZone(zoneTypeArray);
    }

    public boolean isInZone(String string) {
        return this.getZones().isInZone(string);
    }

    public boolean isInZone(Zone zone) {
        return this.getZones().isInZone(zone);
    }

    public Zone getZone(Zone.ZoneType zoneType) {
        return this.getZones().getZone(zoneType);
    }

    public Location getRestartPoint() {
        for (Zone zone : this.getZones()) {
            if (zone.getRestartPoints() == null || !zone.isAnyType(Zone.ZoneType.battle_zone, Zone.ZoneType.peace_zone, Zone.ZoneType.offshore, Zone.ZoneType.dummy)) continue;
            return zone.getSpawn();
        }
        return null;
    }

    public Location getPKRestartPoint() {
        for (Zone zone : this.getZones()) {
            if (zone.getRestartPoints() == null || !zone.isAnyType(Zone.ZoneType.battle_zone, Zone.ZoneType.peace_zone, Zone.ZoneType.offshore, Zone.ZoneType.dummy)) continue;
            return zone.getPKSpawn();
        }
        return null;
    }

    @Override
    public int getGeoZ(Location location) {
        if (this.isFlying() || this.isInWater() || this.isInBoat() || this.isBoat() || this.isDoor()) {
            return location.z;
        }
        return super.getGeoZ(location);
    }

    protected boolean needStatusUpdate() {
        if (!this.isVisible()) {
            return false;
        }
        boolean bl = false;
        int n = (int)(this.getCurrentHp() * 352.0 / (double)this.getMaxHp());
        if (n == 0 || n != this.gr) {
            this.gr = n;
            bl = true;
        }
        if ((n = (int)(this.getCurrentMp() * 352.0 / (double)this.getMaxMp())) == 0 || n != this.gs) {
            this.gs = n;
            bl = true;
        }
        if (this.isPlayer() && ((n = (int)(this.getCurrentCp() * 352.0 / (double)this.getMaxCp())) == 0 || n != this.gq)) {
            this.gq = n;
            bl = true;
        }
        return bl;
    }

    @Override
    public void onForcedAttack(Player player, boolean bl) {
        player.sendPacket((IStaticPacket)new MyTargetSelected(this.getObjectId(), player.getLevel() - this.getLevel()));
        if (!this.isAttackable(player) || player.isConfused() || player.isBlocked()) {
            player.sendActionFailed();
            return;
        }
        player.getAI().Attack(this, true, bl);
    }

    public void onHitTimer(Creature creature, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        if (this.isAlikeDead()) {
            this.sendActionFailed();
            return;
        }
        if (creature.isDead() || !this.isInRange(creature, 2000L)) {
            this.sendActionFailed();
            return;
        }
        if (this.isPlayable() && creature.isPlayable() && this.isInZoneBattle() != creature.isInZoneBattle()) {
            Player player = this.getPlayer();
            if (player != null) {
                player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                player.sendActionFailed();
            }
            return;
        }
        creature.getListeners().onAttackHit(this);
        if (!bl2 && creature.isPlayer() && (this.isCursedWeaponEquipped() || this.getActiveWeaponInstance() != null && this.getActiveWeaponInstance().isHeroWeapon() && creature.isCursedWeaponEquipped())) {
            creature.setCurrentCp(0.0);
        }
        if (creature.isStunned() && Formulas.calcStunBreak(bl)) {
            creature.getEffectList().stopEffects(EffectType.Stun);
        }
        this.displayGiveDamageMessage(creature, n, bl, bl2, bl4, false);
        ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ATTACKED, this, n));
        boolean bl6 = this.checkPvP(creature, null);
        if (!bl2 && n > 0) {
            creature.reduceCurrentHp(n, this, null, true, true, false, true, false, false, true);
            if (!creature.isDead()) {
                if (bl) {
                    this.useTriggers(creature, TriggerType.CRIT, null, null, n);
                }
                this.useTriggers(creature, TriggerType.ATTACK, null, null, n);
                if (Formulas.calcCastBreak(creature, bl)) {
                    creature.abortCast(false, true);
                }
            }
            if (bl3 && bl5) {
                this.unChargeShots(false);
            }
        }
        if (bl2) {
            creature.useTriggers(this, TriggerType.UNDER_MISSED_ATTACK, null, null, n);
        }
        this.startAttackStanceTask();
        if (bl6) {
            this.startPvPFlag(creature);
        }
    }

    public void onMagicUseTimer(Creature creature, Skill skill, boolean bl) {
        double d;
        this.aW = 0L;
        if (skill.isUsingWhileCasting()) {
            creature.getEffectList().stopEffect(skill.getId());
            this.onCastEndTime();
            return;
        }
        if (!skill.isOffensive() && this.getAggressionTarget() != null) {
            bl = true;
        }
        if (!skill.checkCondition(this, creature, bl, false, false)) {
            if (skill.getSkillType() == Skill.SkillType.PET_SUMMON && this.isPlayer()) {
                this.getPlayer().setPetControlItem(null);
            }
            this.onCastEndTime();
            return;
        }
        List<Creature> list = skill.getTargets(this, creature, bl);
        int n = skill.getHpConsume();
        if (n > 0) {
            this.setCurrentHp(Math.max(0.0, this._currentHp - (double)n), false);
        }
        if ((d = skill.getMpConsume2()) > 0.0) {
            if (skill.isMusic()) {
                d += (double)this.getEffectList().getActiveMusicCount(skill.getId()) * d / 2.0;
                d = this.calcStat(Stats.MP_DANCE_SKILL_CONSUME, d, creature, skill);
            } else {
                d = skill.isMagic() ? this.calcStat(Stats.MP_MAGIC_SKILL_CONSUME, d, creature, skill) : this.calcStat(Stats.MP_PHYSICAL_SKILL_CONSUME, d, creature, skill);
            }
            if (this._currentMp < d && this.isPlayable()) {
                this.sendPacket((IStaticPacket)SystemMsg.NOT_ENOUGH_MP);
                this.onCastEndTime();
                return;
            }
            this.reduceCurrentMp(d, null);
        }
        this.callSkill(skill, list, true);
        if (skill.getNumCharges() > 0) {
            this.setIncreasedForce(this.getIncreasedForce() - skill.getNumCharges());
        }
        if (skill.isSoulBoost()) {
            this.setConsumedSouls(this.getConsumedSouls() - Math.min(this.getConsumedSouls(), 5), null);
        } else if (skill.getSoulsConsume() > 0) {
            this.setConsumedSouls(this.getConsumedSouls() - skill.getSoulsConsume(), null);
        }
        switch (skill.getFlyType()) {
            case THROW_UP: 
            case THROW_HORIZONTAL: {
                for (Creature creature2 : list) {
                    Location location = this.a(null, skill);
                    creature2.setLoc(location);
                    this.broadcastPacket(new FlyToLocation(creature2, location, skill.getFlyType()));
                }
                break;
            }
        }
        int n2 = Formulas.calcMAtkSpd(this, skill, skill.getCoolTime());
        GameObjectTasks.CastEndTimeTask castEndTimeTask = new GameObjectTasks.CastEndTimeTask(this);
        if (n2 > 0) {
            ThreadPoolManager.getInstance().schedule(castEndTimeTask, n2);
        } else if (skill.hasEffects()) {
            ThreadPoolManager.getInstance().schedule(castEndTimeTask, 33L);
        } else {
            ThreadPoolManager.getInstance().execute(castEndTimeTask);
        }
    }

    public void onCastEndTime() {
        this.aU();
        Skill skill = this.getCastingSkill();
        Creature creature = this.getCastingTarget();
        this.clearCastVars();
        this.getAI().notifyEvent(CtrlEvent.EVT_FINISH_CASTING, skill, creature);
    }

    private void aU() {
        Location location = this.t;
        this.t = null;
        if (location != null) {
            this.setLoc(location);
            this.validateLocation(1);
        }
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        if (creature == null || this.isDead() || creature.isDead() && !bl6) {
            return;
        }
        if (this.isDamageBlocked() && bl5) {
            return;
        }
        if (this.isDamageBlocked() && creature != this) {
            if (bl7) {
                creature.sendPacket((IStaticPacket)SystemMsg.THE_ATTACK_HAS_BEEN_BLOCKED);
            }
            return;
        }
        if (bl4) {
            if (creature.absorbAndReflect(this, skill, d, bl7)) {
                return;
            }
            d = this.absorbToEffector(creature, d);
            d = this.absorbToSummon(creature, d);
        }
        this.getListeners().onCurrentHpDamage(d, creature, skill);
        if (creature != this) {
            if (bl7) {
                this.displayReceiveDamageMessage(creature, (int)d);
            }
            if (!bl6) {
                this.useTriggers(creature, TriggerType.RECEIVE_DAMAGE, null, null, d);
            }
        }
        this.onReduceCurrentHp(d, creature, skill, bl, bl2, bl3);
    }

    protected void onReduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3) {
        if (bl && this.isSleeping()) {
            this.getEffectList().stopEffects(EffectType.Sleep);
        }
        boolean bl4 = this.isUndying(creature);
        if (creature != this || skill != null && skill.isOffensive()) {
            Effect effect;
            if (this.isMeditated() && (effect = this.getEffectList().getEffectByType(EffectType.Meditation)) != null) {
                this.getEffectList().stopEffect(effect.getSkill());
            }
            this.startAttackStanceTask();
            this.checkAndRemoveInvisible();
            if (this.getCurrentHp() - d < 0.5 && !bl4) {
                this.useTriggers(creature, TriggerType.DIE, null, null, d);
            }
        }
        this.setCurrentHp(Math.max(this.getCurrentHp() - d, bl4 ? 0.5 : 0.0), false);
        if (!bl4 && this.getCurrentHp() < 0.5) {
            this.doDie(creature);
        }
    }

    public void reduceCurrentMp(double d, Creature creature) {
        this.reduceCurrentMp(d, creature, false);
    }

    public void reduceCurrentMp(double d, Creature creature, boolean bl) {
        if (creature != null && creature != this) {
            Effect effect;
            if (this.isSleeping()) {
                this.getEffectList().stopEffects(EffectType.Sleep);
            }
            if (this.isMeditated() && (effect = this.getEffectList().getEffectByType(EffectType.Meditation)) != null) {
                this.getEffectList().stopEffect(effect.getSkill());
            }
        }
        if (this.isDamageBlocked() && creature != null && creature != this) {
            creature.sendPacket((IStaticPacket)SystemMsg.THE_ATTACK_HAS_BEEN_BLOCKED);
            return;
        }
        if (creature != null && creature.isPlayer() && Math.abs(creature.getLevel() - this.getLevel()) > 10) {
            if (creature.getKarma() > 0 && this.getEffectList().getEffectsBySkillId(5182) != null && !this.isInZone(Zone.ZoneType.SIEGE)) {
                return;
            }
            if (this.getKarma() > 0 && creature.getEffectList().getEffectsBySkillId(5182) != null && !creature.isInZone(Zone.ZoneType.SIEGE)) {
                return;
            }
        }
        this.getListeners().onCurrentMpReduce(d, creature);
        if (bl) {
            int n = (int)Math.min(this._currentMp, d);
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.MP_WAS_REDUCED_BY_S1).addNumber(n));
            if (creature != null && creature.isPlayer()) {
                creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_OPPONENTS_MP_WAS_REDUCED_BY_S1).addNumber(n));
            }
        }
        d = Math.max(0.0, this._currentMp - d);
        this.setCurrentMp(d);
        if (creature != null && creature != this) {
            this.startAttackStanceTask();
        }
    }

    public double relativeSpeed(GameObject gameObject) {
        return (double)this.getMoveSpeed() - (double)gameObject.getMoveSpeed() * Math.cos(this.headingToRadians(this.getHeading()) - this.headingToRadians(gameObject.getHeading()));
    }

    public void removeAllSkills() {
        for (Skill skill : this.getAllSkillsArray()) {
            this.removeSkill(skill);
        }
    }

    public void removeBlockStats(List<Stats> list) {
        if (this.aN != null) {
            this.aN.removeAll(list);
            if (this.aN.isEmpty()) {
                this.aN = null;
            }
        }
    }

    public Skill removeSkill(Skill skill) {
        if (skill == null) {
            return null;
        }
        return this.removeSkillById(skill.getId());
    }

    public Skill removeSkillById(Integer n) {
        Skill skill = this._skills.remove(n);
        if (skill != null) {
            this.removeTriggers(skill);
            this.removeStatsOwner(skill);
            this.removeAbnormals(skill);
            if (Config.ALT_DELETE_SA_BUFFS && (skill.isItemSkill() || skill.isHandler())) {
                Object object;
                List<Effect> list = this.getEffectList().getEffectsBySkill(skill);
                if (list != null) {
                    object = list.iterator();
                    while (object.hasNext()) {
                        Effect effect = (Effect)object.next();
                        effect.exit();
                    }
                }
                if ((object = this.getPet()) != null && (list = ((Creature)object).getEffectList().getEffectsBySkill(skill)) != null) {
                    for (Effect effect : list) {
                        effect.exit();
                    }
                }
            }
        }
        return skill;
    }

    public void addTriggers(StatTemplate statTemplate) {
        if (statTemplate.getTriggerList().isEmpty()) {
            return;
        }
        for (TriggerInfo triggerInfo : statTemplate.getTriggerList()) {
            this.addTrigger(triggerInfo);
        }
    }

    public void addAbnormals(Skill skill) {
        for (AbnormalEffect abnormalEffect : skill.getAbnormalEffects()) {
            if (abnormalEffect == AbnormalEffect.NULL) continue;
            this.startAbnormalEffect(abnormalEffect);
        }
    }

    public void addTrigger(TriggerInfo triggerInfo) {
        Set<TriggerInfo> set;
        if (this._triggers == null) {
            this._triggers = new ConcurrentHashMap<TriggerType, Set<TriggerInfo>>();
        }
        if ((set = this._triggers.get((Object)triggerInfo.getType())) == null) {
            set = new CopyOnWriteArraySet<TriggerInfo>();
            this._triggers.put(triggerInfo.getType(), set);
        }
        set.add(triggerInfo);
        if (triggerInfo.getType() == TriggerType.ADD) {
            this.useTriggerSkill(this, triggerInfo, null, 0.0);
        }
    }

    public void removeTriggers(StatTemplate statTemplate) {
        if (this._triggers == null || statTemplate.getTriggerList().isEmpty()) {
            return;
        }
        for (TriggerInfo triggerInfo : statTemplate.getTriggerList()) {
            this.removeTrigger(triggerInfo);
        }
    }

    public void removeAbnormals(Skill skill) {
        for (AbnormalEffect abnormalEffect : skill.getAbnormalEffects()) {
            if (abnormalEffect == AbnormalEffect.NULL) continue;
            this.stopAbnormalEffect(abnormalEffect);
        }
    }

    public void removeTrigger(TriggerInfo triggerInfo) {
        if (this._triggers == null) {
            return;
        }
        Set<TriggerInfo> set = this._triggers.get((Object)triggerInfo.getType());
        if (set == null) {
            return;
        }
        set.remove(triggerInfo);
    }

    public void sendActionFailed() {
        this.sendPacket((IStaticPacket)ActionFail.STATIC);
    }

    public boolean hasAI() {
        return this._ai != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CharacterAI getAI() {
        if (this._ai == null) {
            Creature creature = this;
            synchronized (creature) {
                if (this._ai == null) {
                    this._ai = new CharacterAI(this);
                }
            }
        }
        return this._ai;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setAI(CharacterAI characterAI) {
        if (characterAI == null) {
            return;
        }
        CharacterAI characterAI2 = this._ai;
        Creature creature = this;
        synchronized (creature) {
            this._ai = characterAI;
        }
        if (characterAI2 != null && characterAI2.isActive()) {
            characterAI2.stopAITask();
            characterAI.startAITask();
            characterAI.setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
        }
    }

    public final void setCurrentHp(double d, boolean bl, boolean bl2) {
        int n = this.getMaxHp();
        d = Math.min((double)n, Math.max(0.0, d));
        if (this._currentHp == d) {
            return;
        }
        if (d >= 0.5 && this.isDead() && !bl) {
            return;
        }
        double d2 = this._currentHp;
        this._currentHp = d;
        if (this.isDead.compareAndSet(true, false)) {
            this.onRevive();
        }
        this.checkHpMessages(d2, this._currentHp);
        if (bl2) {
            this.broadcastStatusUpdate();
            this.sendChanges();
        }
        if (this._currentHp < (double)n) {
            this.startRegeneration();
        }
    }

    public final void setCurrentHp(double d, boolean bl) {
        this.setCurrentHp(d, bl, true);
    }

    public final void setCurrentMp(double d, boolean bl) {
        int n = this.getMaxMp();
        d = Math.min((double)n, Math.max(0.0, d));
        if (this._currentMp == d) {
            return;
        }
        if (d >= 0.5 && this.isDead()) {
            return;
        }
        this._currentMp = d;
        if (bl) {
            this.broadcastStatusUpdate();
            this.sendChanges();
        }
        if (this._currentMp < (double)n) {
            this.startRegeneration();
        }
    }

    public final void setCurrentMp(double d) {
        this.setCurrentMp(d, true);
    }

    public final void setCurrentCp(double d, boolean bl) {
        if (!this.isPlayer()) {
            return;
        }
        int n = this.getMaxCp();
        d = Math.min((double)n, Math.max(0.0, d));
        if (this._currentCp == d) {
            return;
        }
        if (d >= 0.5 && this.isDead()) {
            return;
        }
        this._currentCp = d;
        if (bl) {
            this.broadcastStatusUpdate();
            this.sendChanges();
        }
        if (this._currentCp < (double)n) {
            this.startRegeneration();
        }
    }

    public final void setCurrentCp(double d) {
        this.setCurrentCp(d, true);
    }

    public void setCurrentHpMp(double d, double d2, boolean bl) {
        int n = this.getMaxHp();
        int n2 = this.getMaxMp();
        d = Math.min((double)n, Math.max(0.0, d));
        d2 = Math.min((double)n2, Math.max(0.0, d2));
        if (this._currentHp == d && this._currentMp == d2) {
            return;
        }
        if (d >= 0.5 && this.isDead() && !bl) {
            return;
        }
        double d3 = this._currentHp;
        this._currentHp = d;
        this._currentMp = d2;
        if (this.isDead.compareAndSet(true, false)) {
            this.onRevive();
        }
        this.checkHpMessages(d3, this._currentHp);
        this.broadcastStatusUpdate();
        this.sendChanges();
        if (this._currentHp < (double)n || this._currentMp < (double)n2) {
            this.startRegeneration();
        }
    }

    public void setCurrentHpMp(double d, double d2) {
        this.setCurrentHpMp(d, d2, false);
    }

    public final void setFlying(boolean bl) {
        this.bp = bl;
    }

    @Override
    public final int getHeading() {
        return this.gy;
    }

    public void setHeading(int n) {
        this.gy = n;
    }

    public final void setIsTeleporting(boolean bl) {
        this.isTeleporting.compareAndSet(!bl, bl);
    }

    public final void setName(String string) {
        this._name = string;
    }

    public Creature getCastingTarget() {
        return this.r.get();
    }

    public void setCastingTarget(Creature creature) {
        this.r = creature == null ? HardReferences.emptyRef() : creature.getRef();
    }

    public final void setRunning() {
        if (!this.bq) {
            this.bq = true;
            this.broadcastPacket(new ChangeMoveType(this));
        }
    }

    public void setSkillMastery(Integer n, int n2) {
        if (this.aA == null) {
            this.aA = new HashMap<Integer, Integer>();
        }
        this.aA.put(n, n2);
    }

    public void setAggressionTarget(Creature creature) {
        this.s = creature == null ? HardReferences.emptyRef() : creature.getRef();
    }

    public Creature getAggressionTarget() {
        return this.s.get();
    }

    public void setTarget(GameObject gameObject) {
        if (gameObject != null && !gameObject.isVisible()) {
            gameObject = null;
        }
        this.target = gameObject == null ? HardReferences.emptyRef() : gameObject.getRef();
    }

    public void setTitle(String string) {
        this._title = string;
    }

    public void setWalking() {
        if (this.bq) {
            this.bq = false;
            this.broadcastPacket(new ChangeMoveType(this));
        }
    }

    public void startAbnormalEffect(AbnormalEffect abnormalEffect) {
        if (abnormalEffect != AbnormalEffect.NULL) {
            this.aM.add(abnormalEffect);
        }
        this.sendChanges();
    }

    public void stopAbnormalEffect(AbnormalEffect abnormalEffect) {
        this.aM.remove((Object)abnormalEffect);
        this.sendChanges();
    }

    public void startAttackStanceTask() {
        this.startAttackStanceTask0();
    }

    protected void startAttackStanceTask0() {
        if (this.isInCombat()) {
            this.aY = System.currentTimeMillis() + 15000L;
            return;
        }
        this.aY = System.currentTimeMillis() + 15000L;
        this.broadcastPacket(new AutoAttackStart(this.getObjectId()));
        Future<?> future = this.f;
        if (future != null) {
            future.cancel(false);
        }
        this.f = LazyPrecisionTaskManager.getInstance().scheduleAtFixedRate(this.c == null ? (this.c = new AttackStanceTask()) : this.c, 1000L, 1000L);
    }

    public void stopAttackStanceTask() {
        this.aY = 0L;
        Future<?> future = this.f;
        if (future != null) {
            future.cancel(false);
            this.f = null;
            this.broadcastPacket(new AutoAttackStop(this.getObjectId()));
        }
    }

    protected void stopRegeneration() {
        this.q.lock();
        try {
            if (this.br) {
                this.br = false;
                if (this.h != null) {
                    this.h.cancel(false);
                    this.h = null;
                }
            }
        }
        finally {
            this.q.unlock();
        }
    }

    protected void startRegeneration() {
        if (!this.isVisible() || this.isDead() || this.getRegenTick() == 0L) {
            return;
        }
        if (this.br) {
            return;
        }
        this.q.lock();
        try {
            if (!this.br) {
                this.br = true;
                this.h = RegenTaskManager.getInstance().scheduleAtFixedRate(this.e == null ? (this.e = new RegenTask()) : this.e, 0L, this.getRegenTick());
            }
        }
        finally {
            this.q.unlock();
        }
    }

    public long getRegenTick() {
        return 3000L;
    }

    public void setUndying(SpecialEffectState specialEffectState) {
        this.a = specialEffectState;
    }

    public boolean isUndying(Creature creature) {
        return this.a != SpecialEffectState.FALSE;
    }

    public void block() {
        this.bo = true;
    }

    public void unblock() {
        this.bo = false;
    }

    public boolean startConfused() {
        return this.j.getAndSet(true);
    }

    public boolean stopConfused() {
        return this.j.setAndGet(false);
    }

    public boolean startFear() {
        return this.a.getAndSet(true);
    }

    public boolean stopFear() {
        return this.a.setAndGet(false);
    }

    public boolean startMuted() {
        return this.b.getAndSet(true);
    }

    public boolean stopMuted() {
        return this.b.setAndGet(false);
    }

    public boolean startPMuted() {
        return this.c.getAndSet(true);
    }

    public boolean stopPMuted() {
        return this.c.setAndGet(false);
    }

    public boolean startAMuted() {
        return this.d.getAndSet(true);
    }

    public boolean stopAMuted() {
        return this.d.setAndGet(false);
    }

    public boolean startRooted() {
        return this.f.getAndSet(true);
    }

    public boolean stopRooted() {
        return this.f.setAndGet(false);
    }

    public boolean startSleeping() {
        return this.g.getAndSet(true);
    }

    public boolean stopSleeping() {
        return this.g.setAndGet(false);
    }

    public boolean startStunning() {
        return this.h.getAndSet(true);
    }

    public boolean stopStunning() {
        return this.h.setAndGet(false);
    }

    public boolean startParalyzed() {
        return this.e.getAndSet(true);
    }

    public boolean stopParalyzed() {
        return this.e.setAndGet(false);
    }

    public boolean startImmobilized() {
        return this.i.getAndSet(true);
    }

    public boolean stopImmobilized() {
        return this.i.setAndGet(false);
    }

    public boolean startHealBlocked() {
        return this.l.getAndSet(true);
    }

    public boolean stopHealBlocked() {
        return this.l.setAndGet(false);
    }

    public boolean startDamageBlocked() {
        return this.m.getAndSet(true);
    }

    public boolean stopDamageBlocked() {
        return this.m.setAndGet(false);
    }

    public boolean startBuffImmunity() {
        return this.n.getAndSet(true);
    }

    public boolean stopBuffImmunity() {
        return this.n.setAndGet(false);
    }

    public boolean startDebuffImmunity() {
        return this.o.getAndSet(true);
    }

    public boolean stopDebuffImmunity() {
        return this.o.setAndGet(false);
    }

    public boolean startEffectImmunity() {
        return this.p.getAndSet(true);
    }

    public boolean stopEffectImmunity() {
        return this.p.setAndGet(false);
    }

    public boolean startWeaponEquipBlocked() {
        return this.q.getAndSet(true);
    }

    public boolean stopWeaponEquipBlocked() {
        return this.q.getAndSet(false);
    }

    public boolean startFrozen() {
        return this.k.getAndSet(true);
    }

    public boolean stopFrozen() {
        return this.k.setAndGet(false);
    }

    public void setMeditated(boolean bl) {
        this.bm = bl;
    }

    public final void setIsBlessedByNoblesse(boolean bl) {
        this.bl = bl;
    }

    public final void setSalvationWindowTime(int n) {
        this.gx = n;
    }

    public void setIsInvul(boolean bl) {
        this._isInvul = bl;
    }

    public void setLockedTarget(boolean bl) {
        this.bn = bl;
    }

    public boolean isConfused() {
        return this.j.get();
    }

    public boolean isAfraid() {
        return this.a.get();
    }

    public boolean isBlocked() {
        return this.bo;
    }

    public boolean isMuted(Skill skill) {
        if (skill == null || skill.isNotAffectedByMute()) {
            return false;
        }
        return this.isMMuted() && skill.isMagic() || this.isPMuted() && !skill.isMagic();
    }

    public boolean isPMuted() {
        return this.c.get();
    }

    public boolean isMMuted() {
        return this.b.get();
    }

    public boolean isAMuted() {
        return this.d.get();
    }

    public boolean isRooted() {
        return this.f.get();
    }

    public boolean isSleeping() {
        return this.g.get();
    }

    public boolean isStunned() {
        return this.h.get();
    }

    public boolean isMeditated() {
        return this.bm;
    }

    public boolean isWeaponEquipBlocked() {
        return this.q.get();
    }

    public boolean isParalyzed() {
        return this.e.get();
    }

    public boolean isFrozen() {
        return this.k.get();
    }

    public boolean isImmobilized() {
        return this.i.get() || this.getRunSpeed() < 1;
    }

    public boolean isHealBlocked() {
        return this.isAlikeDead() || this.l.get();
    }

    public boolean isDamageBlocked() {
        return this.isInvul() || this.m.get();
    }

    public boolean isCastingNow() {
        return this._skillTask != null;
    }

    public boolean isLockedTarget() {
        return this.bn;
    }

    public boolean isMovementDisabled() {
        return this.isBlocked() || this.isRooted() || this.isImmobilized() || this.isAlikeDead() || this.isStunned() || this.isSleeping() || this.isParalyzed() || this.isAttackingNow() || this.isCastingNow() || this.isFrozen();
    }

    public boolean isActionsDisabled() {
        return this.isBlocked() || this.isAlikeDead() || this.isStunned() || this.isSleeping() || this.isParalyzed() || this.isAttackingNow() || this.isCastingNow() || this.isFrozen();
    }

    public boolean isPotionsDisabled() {
        return this.isActionsDisabled() || this.isStunned() || this.isSleeping() || this.isParalyzed() || this.isAlikeDead() || this.isAfraid();
    }

    public final boolean isAttackingDisabled() {
        return this._attackReuseEndTime > System.currentTimeMillis();
    }

    public boolean isOutOfControl() {
        return this.isBlocked() || this.isConfused() || this.isAfraid() || this.isFrozen();
    }

    public void teleToLocation(Location location) {
        this.teleToLocation(location.x, location.y, location.z, this.getReflection());
    }

    public void teleToLocation(Location location, int n) {
        this.teleToLocation(location.x, location.y, location.z, n);
    }

    public void teleToLocation(Location location, Reflection reflection) {
        this.teleToLocation(location.x, location.y, location.z, reflection);
    }

    public void teleToLocation(int n, int n2, int n3) {
        this.teleToLocation(n, n2, n3, this.getReflection());
    }

    public void checkAndRemoveInvisible() {
        InvisibleType invisibleType = this.getInvisibleType();
        if (invisibleType == InvisibleType.EFFECT) {
            this.getEffectList().stopEffects(EffectType.Invisible);
        }
    }

    public void teleToLocation(int n, int n2, int n3, int n4) {
        Reflection reflection = ReflectionManager.getInstance().get(n4);
        if (reflection == null) {
            return;
        }
        this.teleToLocation(n, n2, n3, reflection);
    }

    public void teleToLocation(int n, int n2, int n3, Reflection reflection) {
        Player player;
        if (!this.isTeleporting.compareAndSet(false, true)) {
            return;
        }
        this.abortCast(true, false);
        if (!this.isLockedTarget()) {
            this.setTarget(null);
        }
        this.stopMove(true, true, false);
        if (!(this.isBoat() || this.isFlying() || World.isWater(new Location(n, n2, n3), reflection))) {
            n3 = GeoEngine.getHeight(n, n2, n3, reflection.getGeoIndex());
        }
        if (this.isPlayer() && DimensionalRiftManager.getInstance().checkIfInRiftZone(this.getLoc(), true) && (player = (Player)this).isInParty() && player.getParty().isInDimensionalRift()) {
            Location location = DimensionalRiftManager.getInstance().getRoom(0, 0).getTeleportCoords();
            n = location.x;
            n2 = location.y;
            n3 = location.z;
            player.getParty().getDimensionalRift().usedTeleport(player);
        }
        if (this.isPlayer()) {
            player = (Player)this;
            player.getListeners().onTeleport(n, n2, n3, reflection);
            this.decayMe();
            this.setXYZ(n, n2, n3);
            this.setReflection(reflection);
            player.setLastClientPosition(null);
            player.setLastServerPosition(null);
            player.sendPacket(new TeleportToLocation(player, n, n2, n3), new ExTeleportToLocationActivate(this, n, n2, n3));
        } else {
            this.setXYZ(n, n2, n3);
            this.setReflection(reflection);
            this.broadcastPacket(new TeleportToLocation(this, n, n2, n3), new ExTeleportToLocationActivate(this, n, n2, n3));
            this.onTeleported();
        }
    }

    public boolean onTeleported() {
        return this.isTeleporting.compareAndSet(true, false);
    }

    public void sendMessage(CustomMessage customMessage) {
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.getObjectId() + "]";
    }

    @Override
    public double getColRadius() {
        return this.getTemplate().collisionRadius;
    }

    @Override
    public double getColHeight() {
        return this.getTemplate().collisionHeight;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public EffectList getEffectList() {
        if (this._effectList == null) {
            Creature creature = this;
            synchronized (creature) {
                if (this._effectList == null) {
                    this._effectList = new EffectList(this);
                }
            }
        }
        return this._effectList;
    }

    public boolean paralizeOnAttack(Creature creature) {
        int n;
        MonsterInstance monsterInstance;
        int n2 = 65535;
        if (this.isRaid() || this.isMinion() && (monsterInstance = ((MinionInstance)this).getLeader()) != null && monsterInstance.isRaid()) {
            n2 = this.getLevel() + Config.RAID_MAX_LEVEL_DIFF;
        } else if (this.isNpc() && (n = ((NpcInstance)this).getParameter("ParalizeOnAttack", -1000)) != -1000) {
            n2 = this.getLevel() + n;
        }
        return creature.getLevel() > n2;
    }

    @Override
    protected void onDelete() {
        GameObjectsStorage.remove(this._storedId);
        this.getEffectList().stopAllEffects();
        super.onDelete();
    }

    public void addExpAndSp(long l, long l2) {
    }

    public void broadcastCharInfo() {
    }

    public void checkHpMessages(double d, double d2) {
    }

    public boolean checkPvP(Creature creature, Skill skill) {
        return false;
    }

    public boolean consumeItem(int n, long l) {
        return true;
    }

    public boolean consumeItemMp(int n, int n2) {
        return true;
    }

    public boolean isFearImmune() {
        return false;
    }

    public boolean isLethalImmune() {
        return this.getMaxHp() >= 50000;
    }

    public boolean getChargedSoulShot() {
        return false;
    }

    public int getChargedSpiritShot() {
        return 0;
    }

    public int getIncreasedForce() {
        return 0;
    }

    public int getConsumedSouls() {
        return 0;
    }

    public int getAgathionEnergy() {
        return 0;
    }

    public void setAgathionEnergy(int n) {
    }

    public int getKarma() {
        return 0;
    }

    public double getLevelMod() {
        return 1.0;
    }

    public int getNpcId() {
        return 0;
    }

    public Summon getPet() {
        return null;
    }

    public int getPvpFlag() {
        return 0;
    }

    public void setTeam(TeamType teamType) {
        this._team = teamType;
        this.sendChanges();
    }

    public TeamType getTeam() {
        return this._team;
    }

    public boolean isUndead() {
        return false;
    }

    public boolean isParalyzeImmune() {
        return false;
    }

    public void reduceArrowCount() {
    }

    public void sendChanges() {
        this.getStatsRecorder().sendChanges();
    }

    public void sendMessage(String string) {
    }

    public void sendPacket(IStaticPacket iStaticPacket) {
    }

    public void sendPacket(IStaticPacket ... iStaticPacketArray) {
    }

    public void sendPacket(List<? extends IStaticPacket> list) {
    }

    public void setIncreasedForce(int n) {
    }

    public void setConsumedSouls(int n, NpcInstance npcInstance) {
    }

    public void startPvPFlag(Creature creature) {
    }

    public boolean unChargeShots(boolean bl) {
        return false;
    }

    public void updateEffectIcons() {
    }

    protected void refreshHpMpCp() {
        int n;
        int n2 = this.getMaxHp();
        int n3 = this.getMaxMp();
        int n4 = n = this.isPlayer() ? this.getMaxCp() : 0;
        if (this._currentHp > (double)n2) {
            this.setCurrentHp(n2, false);
        }
        if (this._currentMp > (double)n3) {
            this.setCurrentMp(n3, false);
        }
        if (this._currentCp > (double)n) {
            this.setCurrentCp(n, false);
        }
        if (this._currentHp < (double)n2 || this._currentMp < (double)n3 || this._currentCp < (double)n) {
            this.startRegeneration();
        }
    }

    public void updateStats() {
        this.refreshHpMpCp();
        this.sendChanges();
    }

    public void setOverhitAttacker(Creature creature) {
    }

    public void setOverhitDamage(double d) {
    }

    public boolean isCursedWeaponEquipped() {
        return false;
    }

    public boolean isHero() {
        return false;
    }

    public int getAccessLevel() {
        return 0;
    }

    public Clan getClan() {
        return null;
    }

    public double getRateAdena() {
        return 1.0;
    }

    public double getRateItems() {
        return 1.0;
    }

    public double getRateExp() {
        return 1.0;
    }

    public double getRateSp() {
        return 1.0;
    }

    public double getRateSpoil() {
        return 1.0;
    }

    public double getRateSealStones() {
        return 1.0;
    }

    public int getFormId() {
        return 0;
    }

    public boolean isNameAbove() {
        return true;
    }

    public boolean isTargetable() {
        return true;
    }

    @Override
    public void setLoc(Location location) {
        this.setXYZ(location.x, location.y, location.z);
    }

    public void setLoc(Location location, boolean bl) {
        this.setXYZ(location.x, location.y, location.z, bl);
    }

    @Override
    public void setXYZ(int n, int n2, int n3) {
        this.setXYZ(n, n2, n3, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setXYZ(int n, int n2, int n3, boolean bl) {
        if (!bl) {
            this.stopMove();
        }
        this.p.lock();
        try {
            super.setXYZ(n, n2, n3);
        }
        finally {
            this.p.unlock();
        }
        this.updateZones();
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        this.updateStats();
        this.updateZones();
    }

    @Override
    public void spawnMe(Location location) {
        if (location.h > 0) {
            this.setHeading(location.h);
        }
        try {
            super.spawnMe(location);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onDespawn() {
        if (!this.isLockedTarget()) {
            this.setTarget(null);
        }
        this.stopMove();
        this.stopAttackStanceTask();
        this.stopRegeneration();
        this.updateZones();
        this.clearStatusListeners();
        super.onDespawn();
    }

    public final void doDecay() {
        if (!this.isDead()) {
            return;
        }
        this.onDecay();
    }

    protected void onDecay() {
        this.decayMe();
    }

    public void validateLocation(int n) {
        ValidateLocation validateLocation = new ValidateLocation(this);
        if (n == 0) {
            this.sendPacket((IStaticPacket)validateLocation);
        } else if (n == 1) {
            this.broadcastPacket(validateLocation);
        } else {
            this.broadcastPacketToOthers(validateLocation);
        }
    }

    public void addUnActiveSkill(Skill skill) {
        if (skill == null || this.isUnActiveSkill(skill.getId())) {
            return;
        }
        this.removeStatsOwner(skill);
        this.removeTriggers(skill);
        this.removeAbnormals(skill);
        this.a.add(skill.getId());
    }

    public void removeUnActiveSkill(Skill skill) {
        if (skill == null || !this.isUnActiveSkill(skill.getId())) {
            return;
        }
        this.addStatFuncs(skill.getStatFuncs());
        this.addTriggers(skill);
        this.a.remove(skill.getId());
    }

    public boolean isUnActiveSkill(int n) {
        return this.a.contains(n);
    }

    public abstract int getLevel();

    public abstract ItemInstance getActiveWeaponInstance();

    public abstract WeaponTemplate getActiveWeaponItem();

    public abstract ItemInstance getSecondaryWeaponInstance();

    public abstract WeaponTemplate getSecondaryWeaponItem();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CharListenerList getListeners() {
        if (this.listeners == null) {
            Creature creature = this;
            synchronized (creature) {
                if (this.listeners == null) {
                    this.listeners = new CharListenerList(this);
                }
            }
        }
        return this.listeners;
    }

    public <T extends Listener<Creature>> boolean addListener(T t) {
        return this.getListeners().add(t);
    }

    public <T extends Listener<Creature>> boolean removeListener(T t) {
        return this.getListeners().remove(t);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public CharStatsChangeRecorder<? extends Creature> getStatsRecorder() {
        if (this._statsRecorder == null) {
            Creature creature = this;
            synchronized (creature) {
                if (this._statsRecorder == null) {
                    this._statsRecorder = new CharStatsChangeRecorder<Creature>(this);
                }
            }
        }
        return this._statsRecorder;
    }

    @Override
    public boolean isCreature() {
        return true;
    }

    public void displayGiveDamageMessage(Creature creature, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        if (bl2 && creature.isPlayer() && !creature.isDamageBlocked()) {
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_AVOIDED_C1S_ATTACK).addName(this));
        }
    }

    public void displayReceiveDamageMessage(Creature creature, int n) {
    }

    protected IntObjectMap<TimeStamp> getSkillReuses0() {
        return this._skillReuses;
    }

    public Collection<TimeStamp> getSkillReuses() {
        return this.getSkillReuses0().values();
    }

    public TimeStamp getSkillReuse(Skill skill) {
        return (TimeStamp)this.getSkillReuses0().get(skill.hashCode());
    }

    public AutoFarmContext getFarmSystem() {
        return null;
    }

    protected static abstract class MoveActionBase {
        private final HardReference<? extends Creature> u;
        private final boolean bs;
        private long aZ;
        private int gz;
        private double k;
        protected volatile boolean isFinished = false;

        public MoveActionBase(Creature creature) {
            this.u = creature.getRef();
            this.bs = creature.isPlayable();
            this.aZ = 0L;
            this.gz = 0;
            this.k = 0.0;
            this.isFinished = false;
        }

        protected boolean isForPlayable() {
            return this.bs;
        }

        protected Creature getActor() {
            return this.u.get();
        }

        protected void setIsFinished(boolean bl) {
            this.isFinished = bl;
        }

        public boolean isFinished() {
            return this.isFinished;
        }

        protected long getPrevTick() {
            return this.aZ;
        }

        protected void setPrevTick(long l) {
            this.aZ = l;
        }

        protected int getPrevSpeed() {
            return this.gz;
        }

        protected void setPrevSpeed(int n) {
            this.gz = n;
        }

        protected double getPassDist() {
            return this.k;
        }

        protected void setPassDist(double d) {
            this.k = d;
        }

        public boolean start() {
            Creature creature = this.getActor();
            if (creature == null) {
                return false;
            }
            this.setPrevTick(System.currentTimeMillis());
            this.setPrevSpeed(creature.getMoveSpeed());
            this.setPassDist(0.0);
            this.setIsFinished(false);
            return this.weightCheck(creature);
        }

        public abstract Location moveFrom();

        public abstract Location moveTo();

        protected double getMoveLen() {
            return PositionUtils.calculateDistance(this.moveFrom(), this.moveTo(), this.includeMoveZ());
        }

        protected boolean includeMoveZ() {
            Creature creature = this.getActor();
            return creature == null || creature.isInWater() || creature.isFlying() || creature.isBoat() || creature.isInBoat();
        }

        public int getNextTickInterval() {
            if (!this.isForPlayable()) {
                return Math.min(Config.MOVE_TASK_QUANTUM_NPC, (int)(1000.0 * (this.getMoveLen() - this.getPassDist()) / (double)Math.max(this.getPrevSpeed(), 1)));
            }
            return Math.min(Config.MOVE_TASK_QUANTUM_PC, (int)(1000.0 * (this.getMoveLen() - this.getPassDist()) / (double)Math.max(this.getPrevSpeed(), 1)));
        }

        protected boolean onEnd() {
            return true;
        }

        protected void onFinish(boolean bl, boolean bl2) {
            this.setIsFinished(true);
        }

        public void interrupt() {
            this.tick();
            this.onFinish(false, true);
        }

        protected boolean onTick(double d) {
            Creature creature = this.getActor();
            if (creature == null) {
                this.onFinish(false, true);
                return false;
            }
            return true;
        }

        public boolean scheduleNextTick() {
            Creature creature = this.getActor();
            if (creature == null) {
                return false;
            }
            Runnable runnable = creature.d;
            creature.d = runnable = new CreatureMoveActionTask(creature);
            creature.g = ThreadPoolManager.getInstance().schedule(runnable, this.getNextTickInterval());
            return true;
        }

        public boolean tick() {
            Creature creature = this.getActor();
            if (creature == null) {
                return false;
            }
            creature.p.lock();
            try {
                boolean bl = this.b(creature);
                return bl;
            }
            finally {
                creature.p.unlock();
            }
        }

        private boolean b(Creature creature) {
            if (this.isFinished()) {
                return false;
            }
            if (creature.moveAction != this) {
                this.setIsFinished(true);
                return false;
            }
            if (creature.isMovementDisabled()) {
                this.onFinish(false, false);
                return false;
            }
            int n = creature.getMoveSpeed();
            if (n <= 0) {
                this.onFinish(false, false);
                return false;
            }
            long l = System.currentTimeMillis();
            float f = (float)(l - this.getPrevTick()) / 1000.0f;
            boolean bl = this.includeMoveZ();
            double d = this.getPassDist();
            this.setPrevTick(l);
            this.setPrevSpeed(n);
            this.setPassDist(d += (double)f * ((double)Math.max(this.getPrevSpeed() + n, 2) / 2.0));
            double d2 = this.getMoveLen();
            double d3 = Math.max(0.0, Math.min(d / Math.max(d2, 1.0), 1.0));
            Location location = creature.getLoc();
            Location location2 = location.clone();
            if (!this.calcMidDest(creature, location2, bl, d3, d, d2)) {
                this.onFinish(false, false);
                return false;
            }
            if (!bl) {
                // empty if block
            }
            creature.setLoc(location2, true);
            if (d3 == 1.0) {
                return !this.onEnd();
            }
            if (!this.onTick(d3)) {
                this.setIsFinished(true);
                return false;
            }
            return true;
        }

        protected boolean weightCheck(Creature creature) {
            if (!creature.isPlayer()) {
                return true;
            }
            if (creature.getPlayer().getCurrentLoad() >= 2 * creature.getPlayer().getMaxLoad()) {
                creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_CANNOT_MOVE_YOUR_ITEM_WEIGHT_IS_TOO_GREAT));
                return false;
            }
            return true;
        }

        protected boolean calcMidDest(Creature creature, Location location, boolean bl, double d, double d2, double d3) {
            location.set(this.moveTo().clone().indent(this.moveFrom(), (int)Math.round(d3 - d2), creature.isFlying() || creature.isInWater())).correctGeoZ();
            return true;
        }

        public abstract L2GameServerPacket movePacket();

        public MoveToLocationAction getAsMoveToLocation() {
            return null;
        }

        public MoveToRelativeAction getAsMoveToRelative() {
            return null;
        }
    }

    public static class MoveToLocationAction
    extends MoveToAction {
        private final Location u;
        private final Location v;

        public MoveToLocationAction(Creature creature, Location location, Location location2, boolean bl, int n, boolean bl2) {
            super(creature, bl, n, bl2);
            this.v = location.clone();
            this.u = location2.clone();
        }

        public MoveToLocationAction(Creature creature, Location location, int n, boolean bl) {
            this(creature, creature.getLoc(), location, creature.isBoat() || creature.isInBoat(), n, bl);
        }

        public boolean isSameDest(Location location) {
            return this.u.equalsGeo(location);
        }

        public Location getFinalDest() {
            return this.u;
        }

        @Override
        public boolean start() {
            if (!super.start()) {
                return false;
            }
            if (!this.buildPathLines(this.v, this.u)) {
                return false;
            }
            return !this.onEnd();
        }

        @Override
        protected boolean onEnd() {
            Creature creature = this.getActor();
            if (creature == null) {
                return true;
            }
            if (!this.pollPathLine()) {
                this.onFinish(true, false);
                return true;
            }
            creature.aT();
            return false;
        }

        @Override
        protected void onFinish(boolean bl, boolean bl2) {
            Creature creature = this.getActor();
            if (this.isFinished() || creature == null) {
                return;
            }
            if (bl2) {
                this.setIsFinished(true);
                return;
            }
            if (bl) {
                if (creature.isPlayer() && !this.pathFind) {
                    creature.stopMove(true, false, false);
                }
                ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ARRIVED));
            } else {
                creature.stopMove(true, true, false);
                ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ARRIVED_BLOCKED, creature.getLoc()));
            }
            super.onFinish(bl, bl2);
        }

        @Override
        public L2GameServerPacket movePacket() {
            Creature creature = this.getActor();
            return creature != null ? new CharMoveToLocation(creature, creature.getLoc(), this.moveTo().clone()) : null;
        }

        @Override
        protected boolean isRelativeMove() {
            return false;
        }

        @Override
        protected boolean pollPathLine() {
            if (this.currentGeoPathLine(this.getGeoPathLines().poll()) != null) {
                Creature creature = this.getActor();
                Location location = this.currentGeoPathLine().get(0).clone().geo2world();
                Location location2 = this.currentGeoPathLine().get(this.currentGeoPathLine().size() - 1).clone().geo2world();
                this.setMoveFrom(location);
                this.setMoveTo(this.isForPlayable() && this.remainingLinesCount() == 0 && this.getFinalDest().equalsGeo(location2) ? this.getFinalDest() : location2);
                this.setPrevIncZ(this.includeMoveZ());
                this.setPrevMoveLen(PositionUtils.calculateDistance(location, location2, this.isPrevIncZ()));
                this.setPassDist(0.0);
                this.setPrevTick(System.currentTimeMillis());
                if (this.getPrevMoveLen() > 16.0) {
                    creature.setHeading(PositionUtils.calculateHeadingFrom(location.getX(), location.getY(), location2.getX(), location2.getY()));
                }
                return true;
            }
            return false;
        }

        @Override
        public MoveToLocationAction getAsMoveToLocation() {
            return this;
        }
    }

    public static class MoveToRelativeAction
    extends MoveToAction {
        private final HardReference<? extends GameObject> v;
        private Location w;
        private boolean bt;
        private final int gA;

        protected MoveToRelativeAction(Creature creature, GameObject gameObject, boolean bl, int n, int n2, boolean bl2) {
            super(creature, bl, n, bl2);
            this.v = gameObject.getRef();
            this.w = gameObject.getLoc().clone();
            this.gA = Math.max(n2, n + 16);
            this.bt = false;
        }

        private GameObject getTarget() {
            return this.v.get();
        }

        public boolean isSameTarget(GameObject gameObject) {
            return this.getTarget() == gameObject;
        }

        @Override
        public boolean start() {
            Location location;
            if (!super.start()) {
                return false;
            }
            Creature creature = this.getActor();
            GameObject gameObject = this.getTarget();
            if (creature == null || gameObject == null) {
                return false;
            }
            Location location2 = creature.getLoc();
            if (!this.buildPathLines(location2, location = gameObject.getLoc().clone())) {
                return false;
            }
            this.w = location.clone();
            return !this.onEnd();
        }

        protected boolean isPathRebuildRequired() {
            Creature creature = this.getActor();
            GameObject gameObject = this.getTarget();
            if (creature == null || gameObject == null) {
                return true;
            }
            Location location = gameObject.getLoc();
            if (!this.bt) {
                return false;
            }
            return !this.w.equalsGeo(location);
        }

        @Override
        protected boolean onEnd() {
            Creature creature = this.getActor();
            GameObject gameObject = this.getTarget();
            if (creature == null || gameObject == null) {
                return true;
            }
            int n = this.remainingLinesCount();
            if (n > 1) {
                if (!this.pollPathLine()) {
                    this.onFinish(false, false);
                    return true;
                }
            } else if (n == 1) {
                this.bt = true;
                if (this.isPathRebuildRequired()) {
                    Location location;
                    if (this.isArrived()) {
                        this.onFinish(true, false);
                        return true;
                    }
                    Location location2 = creature.getLoc();
                    if (!this.buildPathLines(location2, location = this.e())) {
                        this.onFinish(false, false);
                        return true;
                    }
                    if (!this.pollPathLine()) {
                        this.onFinish(false, false);
                        return true;
                    }
                    this.w = location.clone();
                } else if (!this.pollPathLine()) {
                    this.onFinish(false, false);
                    return true;
                }
            } else {
                this.onFinish(true, false);
                return true;
            }
            creature.aT();
            return false;
        }

        protected boolean isArrived() {
            Creature creature = this.getActor();
            GameObject gameObject = this.getTarget();
            if (creature == null || gameObject == null) {
                return false;
            }
            if (gameObject.isCreature() && ((Creature)gameObject).isMoving()) {
                int n = this.indent + 16;
                if (this.includeMoveZ()) {
                    return gameObject.isInRangeZ(creature, (long)n);
                }
                return gameObject.isInRange(creature, (long)n);
            }
            if (this.includeMoveZ()) {
                return gameObject.isInRangeZ(creature, (long)(this.indent + 16));
            }
            return gameObject.isInRange(creature, (long)(this.indent + 16));
        }

        private Location e() {
            Creature creature = this.getActor();
            GameObject gameObject = this.getTarget();
            if (creature == null || gameObject == null) {
                return null;
            }
            if (!gameObject.isCreature()) {
                return gameObject.getLoc();
            }
            Creature creature2 = (Creature)gameObject;
            Location location = gameObject.getLoc();
            if (!creature2.isMoving()) {
                return location;
            }
            return GeoMove.getIntersectPoint(creature.getLoc(), location, creature2.getMoveSpeed(), Math.max(128, Config.MOVE_TASK_QUANTUM_PC / 2));
        }

        @Override
        protected boolean onTick(double d) {
            if (!super.onTick(d)) {
                return false;
            }
            Creature creature = this.getActor();
            GameObject gameObject = this.getTarget();
            if (creature == null || gameObject == null) {
                return false;
            }
            if (d < 1.0) {
                if (this.isPathRebuildRequired()) {
                    Location location = creature.getLoc();
                    Location location2 = this.e();
                    if (creature.isPlayer() && creature.getPlayer().getNetConnection() != null) {
                        int n = creature.getPlayer().getNetConnection().getPawnClippingRange();
                        if (location.distance3D(location2) > (double)n) {
                            this.onFinish(false, false);
                            return false;
                        }
                    }
                    if (!this.buildPathLines(location, location2)) {
                        this.onFinish(false, false);
                        return false;
                    }
                    if (!this.pollPathLine()) {
                        this.onFinish(false, false);
                        return false;
                    }
                    this.w = location2.clone();
                } else if (this.bt && this.isArrived()) {
                    this.onFinish(true, false);
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onFinish(boolean bl, boolean bl2) {
            Creature creature = this.getActor();
            GameObject gameObject = this.getTarget();
            if (this.isFinished() || creature == null || gameObject == null) {
                return;
            }
            if (bl2) {
                this.setIsFinished(true);
                return;
            }
            creature.stopMove(!(gameObject instanceof StaticObjectInstance) && !gameObject.isDoor(), false, false);
            boolean bl3 = false;
            if (bl) {
                bl3 = (this.includeMoveZ() ? creature.getRealDistance3D(gameObject) : creature.getRealDistance(gameObject)) <= (double)(this.gA + 16);
            }
            this.setIsFinished(true);
            if (bl3) {
                ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ARRIVED_TARGET));
            } else {
                ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ARRIVED_BLOCKED, creature.getLoc()));
            }
        }

        @Override
        protected boolean isRelativeMove() {
            return this.bt;
        }

        @Override
        public L2GameServerPacket movePacket() {
            Creature creature = this.getActor();
            if (creature == null) {
                return null;
            }
            GameObject gameObject = this.getTarget();
            if (this.isRelativeMove()) {
                if (gameObject == null) {
                    return null;
                }
                return new MoveToPawn(creature, gameObject, this.indent);
            }
            return new CharMoveToLocation(creature, creature.getLoc(), this.moveTo().clone());
        }

        @Override
        public MoveToRelativeAction getAsMoveToRelative() {
            return this;
        }
    }

    private class AttackStanceTask
    extends RunnableImpl {
        private AttackStanceTask() {
        }

        @Override
        public void runImpl() throws Exception {
            if (!Creature.this.isInCombat()) {
                Creature.this.stopAttackStanceTask();
            }
        }
    }

    private class RegenTask
    implements Runnable {
        private RegenTask() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            if (Creature.this.isAlikeDead() || Creature.this.getRegenTick() == 0L) {
                return;
            }
            double d = Creature.this._currentHp;
            int n = Creature.this.getMaxHp();
            int n2 = Creature.this.getMaxMp();
            int n3 = Creature.this.isPlayer() ? Creature.this.getMaxCp() : 0;
            double d2 = 0.0;
            double d3 = 0.0;
            Creature.this.q.lock();
            try {
                if (Creature.this._currentHp < (double)n) {
                    d2 += Formulas.calcHpRegen(Creature.this);
                }
                if (Creature.this._currentMp < (double)n2) {
                    d3 += Formulas.calcMpRegen(Creature.this);
                }
                if (Creature.this.isPlayer() && Config.REGEN_SIT_WAIT) {
                    Player player = (Player)Creature.this;
                    if (player.isSitting()) {
                        player.updateWaitSitTime();
                        if (player.getWaitSitTime() > 5) {
                            d2 += (double)player.getWaitSitTime();
                            d3 += (double)player.getWaitSitTime();
                        }
                    }
                } else if (Creature.this.isRaid() && Creature.this.getLevel() >= Config.RATE_MOD_MIN_LEVEL_LIMIT && Creature.this.getLevel() <= Config.RATE_MOD_MAX_LEVEL_LIMIT) {
                    d2 *= Config.RATE_RAID_REGEN;
                    d3 *= Config.RATE_RAID_REGEN;
                }
                Creature.this._currentHp += Math.max(0.0, Math.min(d2, Creature.this.calcStat(Stats.HP_LIMIT, null, null) * (double)n / 100.0 - Creature.this._currentHp));
                Creature.this._currentMp += Math.max(0.0, Math.min(d3, Creature.this.calcStat(Stats.MP_LIMIT, null, null) * (double)n2 / 100.0 - Creature.this._currentMp));
                Creature.this._currentHp = Math.min((double)n, Creature.this._currentHp);
                Creature.this._currentMp = Math.min((double)n2, Creature.this._currentMp);
                if (Creature.this.isPlayer()) {
                    Creature.this._currentCp += Math.max(0.0, Math.min(Formulas.calcCpRegen(Creature.this), Creature.this.calcStat(Stats.CP_LIMIT, null, null) * (double)n3 / 100.0 - Creature.this._currentCp));
                    Creature.this._currentCp = Math.min((double)n3, Creature.this._currentCp);
                }
                if (Creature.this._currentHp == (double)n && Creature.this._currentMp == (double)n2 && Creature.this._currentCp == (double)n3) {
                    Creature.this.stopRegeneration();
                }
            }
            finally {
                Creature.this.q.unlock();
            }
            Creature.this.broadcastStatusUpdate();
            Creature.this.sendChanges();
            Creature.this.checkHpMessages(d, Creature.this._currentHp);
        }
    }

    public static abstract class MoveToAction
    extends MoveActionBase {
        protected final int indent;
        protected final boolean pathFind;
        protected final boolean ignoreGeo;
        protected Queue<List<Location>> geoPathLines;
        protected List<Location> currentGeoPathLine;
        protected Location moveFrom;
        protected Location moveTo;
        protected double prevMoveLen;
        protected boolean prevIncZ;

        protected MoveToAction(Creature creature, boolean bl, int n, boolean bl2) {
            super(creature);
            this.indent = n;
            this.pathFind = bl2;
            this.ignoreGeo = bl;
            this.geoPathLines = new LinkedList<List<Location>>();
            this.currentGeoPathLine = Collections.emptyList();
            this.moveFrom = creature.getLoc();
            this.moveTo = creature.getLoc();
            this.prevMoveLen = 0.0;
            this.prevIncZ = false;
        }

        protected boolean buildPathLines(Location location, Location location2) {
            Creature creature = this.getActor();
            if (creature == null) {
                return false;
            }
            LinkedList<List<Location>> linkedList = new LinkedList<List<Location>>();
            if (!GeoMove.buildGeoPath(linkedList, location.clone().world2geo(), location2.clone().world2geo(), creature.getGeoIndex(), (int)creature.getColRadius(), (int)creature.getColHeight(), this.indent, this.pathFind && !this.ignoreGeo && !this.isRelativeMove(), this.isForPlayable(), creature.isFlying(), creature.isInWater(), creature.getWaterZ(), this.ignoreGeo)) {
                return false;
            }
            this.geoPathLines.clear();
            this.geoPathLines.addAll(linkedList);
            return true;
        }

        protected Queue<List<Location>> getGeoPathLines() {
            return this.geoPathLines;
        }

        public List<Location> currentGeoPathLine(List<Location> list) {
            this.currentGeoPathLine = list;
            return this.currentGeoPathLine;
        }

        public List<Location> currentGeoPathLine() {
            return this.currentGeoPathLine;
        }

        public boolean isPrevIncZ() {
            return this.prevIncZ;
        }

        public void setPrevIncZ(boolean bl) {
            this.prevIncZ = bl;
        }

        public double getPrevMoveLen() {
            return this.prevMoveLen;
        }

        public void setPrevMoveLen(double d) {
            this.prevMoveLen = d;
        }

        protected boolean pollPathLine() {
            if (this.currentGeoPathLine(this.getGeoPathLines().poll()) != null) {
                Creature creature = this.getActor();
                Location location = this.currentGeoPathLine().get(0).clone().geo2world();
                Location location2 = this.currentGeoPathLine().get(this.currentGeoPathLine().size() - 1).clone().geo2world();
                this.setMoveFrom(location);
                this.setMoveTo(location2);
                this.setPrevIncZ(this.includeMoveZ());
                this.setPrevMoveLen(PositionUtils.calculateDistance(location, location2, this.isPrevIncZ()));
                this.setPassDist(0.0);
                this.setPrevTick(System.currentTimeMillis());
                if (this.getPrevMoveLen() > 16.0) {
                    creature.setHeading(PositionUtils.calculateHeadingFrom(location.getX(), location.getY(), location2.getX(), location2.getY()));
                }
                return true;
            }
            return false;
        }

        protected int remainingLinesCount() {
            return this.geoPathLines.size();
        }

        protected abstract boolean isRelativeMove();

        @Override
        protected boolean calcMidDest(Creature creature, Location location, boolean bl, double d, double d2, double d3) {
            if (this.currentGeoPathLine == null) {
                return false;
            }
            Location location2 = creature.getLoc();
            if (d3 < 16.0 || d == 0.0 || d2 == 0.0 || this.currentGeoPathLine.isEmpty()) {
                location.set(location2);
                return true;
            }
            int n = this.currentGeoPathLine.size() - 1;
            location.set(this.moveFrom).indent(this.moveTo, (int)(d2 + 0.5), bl).setZ(this.currentGeoPathLine.get(Math.min(n, (int)((double)n * d + 0.5))).getZ());
            if (location.equalsGeo(location2) || this.ignoreGeo || !Config.ALLOW_GEODATA) {
                return true;
            }
            if (bl) {
                return true;
            }
            return GeoEngine.canMoveToCoord(location2.getX(), location2.getY(), location2.getZ(), location.getX(), location.getY(), location.getZ(), creature.getGeoIndex());
        }

        @Override
        public Location moveFrom() {
            return this.moveFrom;
        }

        @Override
        public Location moveTo() {
            return this.moveTo;
        }

        protected void setMoveFrom(Location location) {
            this.moveFrom = location;
        }

        protected void setMoveTo(Location location) {
            this.moveTo = location;
        }

        @Override
        protected double getMoveLen() {
            boolean bl = this.includeMoveZ();
            if (bl != this.prevIncZ) {
                this.prevMoveLen = PositionUtils.calculateDistance(this.moveFrom, this.moveTo, bl);
                this.prevIncZ = bl;
            }
            return this.prevMoveLen;
        }
    }

    protected static class CreatureMoveActionTask
    extends RunnableImpl {
        private final HardReference<? extends Creature> t;

        public CreatureMoveActionTask(Creature creature) {
            this.t = creature.getRef();
        }

        @Override
        public void runImpl() throws Exception {
            Creature creature = this.t.get();
            if (creature == null) {
                return;
            }
            creature.p.lock();
            try {
                MoveActionBase moveActionBase = creature.moveAction;
                if (creature.d == this && moveActionBase != null && !moveActionBase.isFinished() && moveActionBase.b(creature) && creature.d == this) {
                    moveActionBase.scheduleNextTick();
                }
            }
            finally {
                creature.p.unlock();
            }
        }
    }
}
