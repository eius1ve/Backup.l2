/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectIterator
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.instances;

import gnu.trove.TIntObjectIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.collections.MultiValueSet;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.handler.items.RefineryHandler;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.DimensionalRiftManager;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.NpcListener;
import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObjectTasks;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.MinionList;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.TeleportLocation;
import l2.gameserver.model.Territory;
import l2.gameserver.model.World;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.listener.NpcListenerList;
import l2.gameserver.model.actor.recorder.NpcStatsChangeRecorder;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.base.BaseStats;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.NpcInfoSpeed;
import l2.gameserver.model.base.SpecialEffectState;
import l2.gameserver.model.entity.DimensionalRift;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.model.instances.ReflectionBossInstance;
import l2.gameserver.model.instances.TrainerInstance;
import l2.gameserver.model.instances.WarehouseInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestEventType;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.AcquireSkillDone;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.AutoAttackStart;
import l2.gameserver.network.l2.s2c.ExAbnormalStatusUpdateFromTarget;
import l2.gameserver.network.l2.s2c.ExAcquirableSkillListByClass;
import l2.gameserver.network.l2.s2c.ExChangeNpcState;
import l2.gameserver.network.l2.s2c.ExEnSoulExtractionShow;
import l2.gameserver.network.l2.s2c.ExEnchantSkillList;
import l2.gameserver.network.l2.s2c.ExNpcInfoSpeed;
import l2.gameserver.network.l2.s2c.ExShowBaseAttributeCancelWindow;
import l2.gameserver.network.l2.s2c.ExShowEnsoulWindow;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.NpcInfo;
import l2.gameserver.network.l2.s2c.RadarControl;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.SysMsgContainer;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.ValidateLocation;
import l2.gameserver.scripts.Events;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.FuncMul;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.taskmanager.DecayTaskManager;
import l2.gameserver.taskmanager.LazyPrecisionTaskManager;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.templates.npc.Faction;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.templates.spawn.SpawnRange;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.ReflectionUtils;
import l2.gameserver.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NpcInstance
extends Creature {
    public static final String NO_CHAT_WINDOW = "noChatWindow";
    public static final String NO_RANDOM_WALK = "noRandomWalk";
    public static final String IGNORE_DROP_DIFF = "ignoreDropLevelDiff";
    public static final String NO_RANDOM_ANIMATION = "noRandomAnimation";
    public static final String TARGETABLE = "TargetEnabled";
    public static final String SHOW_NAME = "showName";
    private static final Logger cj = LoggerFactory.getLogger(NpcInstance.class);
    private int mE = -1;
    private int _level = 0;
    private long cs = 0L;
    protected int _spawnAnimation = 2;
    private int mF;
    private int mG;
    private double K;
    private double L;
    private int mH = 0;
    protected boolean _hasRandomAnimation;
    protected boolean _hasRandomWalk;
    protected boolean _hasChatWindow;
    protected boolean _ignoreDropDiffPenalty;
    private Future<?> z;
    private Future<?> K;
    private AggroList a;
    private boolean dz;
    private boolean _showName;
    private Castle a;
    private ClanHall a;
    private Spawner a;
    private Location M = new Location();
    private SpawnRange a;
    private MultiValueSet<String> c = StatsSet.EMPTY;
    public AtomicInteger av_quest0 = new AtomicInteger();
    protected boolean _unAggred = false;
    private int _displayId = 0;
    private ScheduledFuture<?> R;
    protected long _lastSocialAction;
    private boolean dA;
    private String dD = "";
    private boolean dB = false;

    public NpcInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        if (npcTemplate == null) {
            throw new NullPointerException("No template for Npc. Please check your datapack is setup correctly.");
        }
        this.setUndying(SpecialEffectState.TRUE);
        this.setParameters(npcTemplate.getAIParams());
        this._hasRandomAnimation = !this.getParameter(NO_RANDOM_ANIMATION, false) && Config.MAX_NPC_ANIMATION > 0;
        this._hasRandomWalk = !this.getParameter(NO_RANDOM_WALK, false);
        this._ignoreDropDiffPenalty = this.getParameter(IGNORE_DROP_DIFF, false);
        this.setHasChatWindow(!this.getParameter(NO_CHAT_WINDOW, false));
        this.setTargetable(this.getParameter(TARGETABLE, true));
        this.setShowName(this.getParameter(SHOW_NAME, true));
        if (npcTemplate.getSkills().size() > 0) {
            TIntObjectIterator tIntObjectIterator = npcTemplate.getSkills().iterator();
            while (tIntObjectIterator.hasNext()) {
                tIntObjectIterator.advance();
                this.addSkill((Skill)tIntObjectIterator.value());
            }
        }
        this.setName(npcTemplate.name);
        this.setTitle(npcTemplate.title);
        this.setLHandId(this.getTemplate().lhand);
        this.setRHandId(this.getTemplate().rhand);
        this.setCollisionHeight(this.getTemplate().collisionHeight);
        this.setCollisionRadius(this.getTemplate().collisionRadius);
        this.a = new AggroList(this);
        this.setFlying(this.getParameter("isFlying", false));
        this.bH();
    }

    private final void bH() {
        if (this.isBoss()) {
            if (this.getLevel() >= Config.ALT_EPIC_BOSS_MIN_LEVEL_MODIFIER && this.getLevel() < Config.ALT_EPIC_BOSS_MAX_LEVEL_MODIFIER) {
                this.addStatFunc(new FuncMul(Stats.MAGIC_DEFENCE, 80, this, Config.ALT_EPIC_BOSS_MDEF_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.MAGIC_ATTACK, 80, this, Config.ALT_EPIC_BOSS_MATK_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.POWER_ATTACK, 80, this, Config.ALT_EPIC_BOSS_PATK_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.POWER_DEFENCE, 80, this, Config.ALT_EPIC_BOSS_PDEF_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.MAX_HP, 80, this, Config.ALT_EPIC_BOSS_MAXHP_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.MAX_MP, 80, this, Config.ALT_EPIC_BOSS_MAXMP_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.RUN_SPEED, 80, this, Config.ALT_EPIC_BOSS_SPD_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.MAGIC_ATTACK_SPEED, 80, this, Config.ALT_EPIC_BOSS_CAST_SPD_MODIFIER));
            }
        } else if (this.isRaid()) {
            if (this.getLevel() >= Config.ALT_RAID_BOSS_MIN_LEVEL_MODIFIER && this.getLevel() < Config.ALT_RAID_BOSS_MAX_LEVEL_MODIFIER) {
                this.addStatFunc(new FuncMul(Stats.MAGIC_DEFENCE, 80, this, Config.ALT_RAID_BOSS_MDEF_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.MAGIC_ATTACK, 80, this, Config.ALT_RAID_BOSS_MATK_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.POWER_ATTACK, 80, this, Config.ALT_RAID_BOSS_PATK_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.POWER_DEFENCE, 80, this, Config.ALT_RAID_BOSS_PDEF_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.MAX_HP, 80, this, Config.ALT_RAID_BOSS_MAXHP_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.MAX_MP, 80, this, Config.ALT_RAID_BOSS_MAXMP_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.RUN_SPEED, 80, this, Config.ALT_RAID_BOSS_SPD_MODIFIER));
                this.addStatFunc(new FuncMul(Stats.MAGIC_ATTACK_SPEED, 80, this, Config.ALT_RAID_BOSS_CAST_SPD_MODIFIER));
            }
        } else if (this.isMonster() && this.getLevel() >= Config.ALT_NPC_MIN_LEVEL_MODIFIER && this.getLevel() < Config.ALT_NPC_MAX_LEVEL_MODIFIER) {
            this.addStatFunc(new FuncMul(Stats.MAGIC_DEFENCE, 80, this, Config.ALT_NPC_MDEF_MODIFIER));
            this.addStatFunc(new FuncMul(Stats.MAGIC_ATTACK, 80, this, Config.ALT_NPC_MATK_MODIFIER));
            this.addStatFunc(new FuncMul(Stats.POWER_ATTACK, 80, this, Config.ALT_NPC_PATK_MODIFIER));
            this.addStatFunc(new FuncMul(Stats.POWER_DEFENCE, 80, this, Config.ALT_NPC_PDEF_MODIFIER));
            this.addStatFunc(new FuncMul(Stats.MAX_HP, 80, this, Config.ALT_NPC_MAXHP_MODIFIER));
            this.addStatFunc(new FuncMul(Stats.MAX_MP, 80, this, Config.ALT_NPC_MAXMP_MODIFIER));
            this.addStatFunc(new FuncMul(Stats.RUN_SPEED, 80, this, Config.ALT_NPC_SPD_MODIFIER));
            this.addStatFunc(new FuncMul(Stats.MAGIC_ATTACK_SPEED, 80, this, Config.ALT_NPC_CAST_SPD_MODIFIER));
        }
    }

    public HardReference<NpcInstance> getRef() {
        return super.getRef();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CharacterAI getAI() {
        if (this._ai == null) {
            NpcInstance npcInstance = this;
            synchronized (npcInstance) {
                if (this._ai == null) {
                    this._ai = this.getTemplate().getNewAI(this);
                }
            }
        }
        return this._ai;
    }

    public Location getSpawnedLoc() {
        return this.M;
    }

    public void setSpawnedLoc(Location location) {
        this.M = location;
    }

    public int getRightHandItem() {
        return this.mG;
    }

    public int getLeftHandItem() {
        return this.mF;
    }

    public void setLHandId(int n) {
        this.mF = n;
    }

    public void setRHandId(int n) {
        this.mG = n;
    }

    public double getCollisionHeight() {
        return this.L;
    }

    public void setCollisionHeight(double d) {
        this.L = d;
    }

    public double getCollisionRadius() {
        return this.K;
    }

    public void setCollisionRadius(double d) {
        this.K = d;
    }

    @Override
    protected void onReduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3) {
        if (creature.isPlayable()) {
            this.getAggroList().addDamageHate(creature, (int)d, 0);
        }
        if (this.isMonster() && this.hasAI() && creature.isPlayer() && !this.getAI().getTargetList().contains(creature)) {
            this.getAI().addToTargetList(creature.getPlayer());
        }
        super.onReduceCurrentHp(d, creature, skill, bl, bl2, bl3);
    }

    @Override
    protected void onDeath(Creature creature) {
        this.cs = System.currentTimeMillis();
        if (this.isMonster() && (((MonsterInstance)this).isSeeded() || ((MonsterInstance)this).isSpoiled())) {
            this.startDecay(20000L);
        } else if (this.isBoss()) {
            this.startDecay(20000L);
        } else if (this.isFlying()) {
            this.startDecay(4500L);
        } else {
            this.startDecay(8500L);
        }
        if (this.hasAI()) {
            this.getAI().getTargetList().clear();
        }
        this.setLHandId(this.getTemplate().lhand);
        this.setRHandId(this.getTemplate().rhand);
        this.setCollisionHeight(this.getTemplate().collisionHeight);
        this.setCollisionRadius(this.getTemplate().collisionRadius);
        this.getAI().stopAITask();
        this.stopRandomAnimation();
        super.onDeath(creature);
    }

    public long getDeadTime() {
        if (this.cs <= 0L) {
            return 0L;
        }
        return System.currentTimeMillis() - this.cs;
    }

    public AggroList getAggroList() {
        return this.a;
    }

    public MinionList getMinionList() {
        return null;
    }

    public Location getRndMinionPosition() {
        return Location.findPointToStay(this, (int)this.getColRadius() + 30, (int)this.getColRadius() + 50);
    }

    public boolean hasMinions() {
        return false;
    }

    public void dropItem(Player player, int n, long l) {
        this.dropItem(player, n, l, 0);
    }

    public void dropItem(Player player, int n, long l, int n2) {
        if (l == 0L || player == null) {
            return;
        }
        for (long i = 0L; i < l; ++i) {
            ItemInstance itemInstance = ItemFunctions.createItem(n);
            for (GlobalEvent globalEvent : this.getEvents()) {
                itemInstance.addEvent(globalEvent);
            }
            if (itemInstance.isStackable()) {
                i = l;
                itemInstance.setCount(l);
            } else if (n2 > 0 && itemInstance.getTemplate().isEnchantable()) {
                itemInstance.setEnchantLevel(n2);
            }
            if (this.isRaid() || this instanceof ReflectionBossInstance) {
                Object object;
                if (n == 57) {
                    object = new SystemMessage(SystemMsg.C1_HAS_DIED_AND_DROPPED_S2_ADENA);
                    ((SysMsgContainer)object).addName(this);
                    ((SysMsgContainer)object).addNumber(itemInstance.getCount());
                } else {
                    object = new SystemMessage(SystemMsg.C1_DIED_AND_DROPPED_S3_S2);
                    ((SysMsgContainer)object).addName(this);
                    ((SysMsgContainer)object).addItemName(n);
                    ((SysMsgContainer)object).addNumber(itemInstance.getCount());
                }
                this.broadcastPacket(new L2GameServerPacket[]{object});
            }
            player.doAutoLootOrDrop(itemInstance, this);
        }
    }

    public void dropItem(Player player, ItemInstance itemInstance) {
        if (itemInstance.getCount() == 0L) {
            return;
        }
        if (this.isRaid() || this instanceof ReflectionBossInstance) {
            SystemMessage systemMessage;
            if (itemInstance.getItemId() == 57) {
                systemMessage = new SystemMessage(SystemMsg.C1_HAS_DIED_AND_DROPPED_S2_ADENA);
                systemMessage.addName(this);
                systemMessage.addNumber(itemInstance.getCount());
            } else {
                systemMessage = new SystemMessage(SystemMsg.C1_DIED_AND_DROPPED_S3_S2);
                systemMessage.addName(this);
                systemMessage.addItemName(itemInstance.getItemId());
                systemMessage.addNumber(itemInstance.getCount());
            }
            this.broadcastPacket(systemMessage);
        }
        player.doAutoLootOrDrop(itemInstance, this);
    }

    @Override
    public boolean isAttackable(Creature creature) {
        return true;
    }

    @Override
    public boolean isAutoAttackable(Creature creature) {
        return false;
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        this.cs = 0L;
        this._spawnAnimation = 0;
        if (this.getAI().isGlobalAI() || this.getCurrentRegion() != null && this.getCurrentRegion().isActive()) {
            this.getAI().startAITask();
            this.startRandomAnimation();
        }
        ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(this, CtrlEvent.EVT_SPAWN));
        this.getListeners().onSpawn();
    }

    @Override
    protected void onDespawn() {
        this.getAggroList().clear();
        if (this.hasAI()) {
            this.getAI().getTargetList().clear();
        }
        this.getAI().onEvtDeSpawn();
        this.getAI().stopAITask();
        this.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE);
        this.stopRandomAnimation();
        super.onDespawn();
    }

    @Override
    public NpcTemplate getTemplate() {
        return (NpcTemplate)this._template;
    }

    @Override
    public int getNpcId() {
        return this.getTemplate().npcId;
    }

    public void setUnAggred(boolean bl) {
        this._unAggred = bl;
    }

    public boolean isAggressive() {
        return this.getAggroRange() > 0;
    }

    public int getAggroRange() {
        if (this._unAggred) {
            return 0;
        }
        if (this.mE >= 0) {
            return this.mE;
        }
        return this.getTemplate().aggroRange;
    }

    public void setAggroRange(int n) {
        this.mE = n;
    }

    public Faction getFaction() {
        return this.getTemplate().getFaction();
    }

    public boolean isInFaction(NpcInstance npcInstance) {
        return this.getFaction().equals(npcInstance.getFaction()) && !this.getFaction().isIgnoreNpcId(npcInstance.getNpcId());
    }

    public long getExpReward() {
        return (long)this.calcStat(Stats.EXP, this.getTemplate().rewardExp, null, null);
    }

    public long getSpReward() {
        return (long)this.calcStat(Stats.SP, this.getTemplate().rewardSp, null, null);
    }

    @Override
    protected void onDelete() {
        this.stopDecay();
        if (this.a != null) {
            this.a.stopRespawn();
        }
        this.setSpawn(null);
        super.onDelete();
    }

    public Spawner getSpawn() {
        return this.a;
    }

    public void setSpawn(Spawner spawner) {
        this.a = spawner;
    }

    @Override
    protected void onDecay() {
        super.onDecay();
        this._spawnAnimation = 2;
        if (this.a != null) {
            this.a.decreaseCount(this);
        } else if (!this.isMinion()) {
            this.deleteMe();
        }
    }

    public final void decayOrDelete() {
        this.onDecay();
    }

    protected void startDecay(long l) {
        this.stopDecay();
        this.z = DecayTaskManager.getInstance().addDecayTask(this, l);
    }

    public void stopDecay() {
        if (this.z != null) {
            this.z.cancel(false);
            this.z = null;
        }
    }

    public void endDecayTask() {
        if (this.z != null) {
            this.z.cancel(false);
            this.z = null;
        }
        this.doDecay();
    }

    @Override
    public boolean isUndead() {
        return this.getTemplate().isUndead();
    }

    public void setLevel(int n) {
        this._level = n;
    }

    @Override
    public int getLevel() {
        return this._level == 0 ? this.getTemplate().level : this._level;
    }

    public void setDisplayId(int n) {
        this._displayId = n;
    }

    public int getDisplayId() {
        return this._displayId > 0 ? this._displayId : this.getTemplate().displayId;
    }

    @Override
    public ItemInstance getActiveWeaponInstance() {
        return null;
    }

    @Override
    public int getPhysicalAttackRange() {
        return (int)this.calcStat(Stats.POWER_ATTACK_RANGE, this.getTemplate().baseAtkRange, null, null);
    }

    @Override
    public WeaponTemplate getActiveWeaponItem() {
        int n = this.getTemplate().rhand;
        if (n < 1) {
            return null;
        }
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(this.getTemplate().rhand);
        if (!(itemTemplate instanceof WeaponTemplate)) {
            return null;
        }
        return (WeaponTemplate)itemTemplate;
    }

    @Override
    public ItemInstance getSecondaryWeaponInstance() {
        return null;
    }

    @Override
    public WeaponTemplate getSecondaryWeaponItem() {
        int n = this.getTemplate().lhand;
        if (n < 1) {
            return null;
        }
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(this.getTemplate().lhand);
        if (!(itemTemplate instanceof WeaponTemplate)) {
            return null;
        }
        return (WeaponTemplate)itemTemplate;
    }

    @Override
    public void sendChanges() {
        if (this.isFlying()) {
            return;
        }
        super.sendChanges();
    }

    @Override
    public void broadcastCharInfo() {
        if (!this.isVisible()) {
            return;
        }
        if (this.R != null) {
            return;
        }
        this.R = ThreadPoolManager.getInstance().schedule(new BroadcastCharInfoTask(), Config.BROADCAST_CHAR_INFO_INTERVAL);
    }

    public void broadcastCharInfoImpl() {
        for (Player player : World.getAroundPlayers(this)) {
            player.sendPacket(new NpcInfo(this, player).update(), new ExNpcInfoSpeed(this, NpcInfoSpeed.MOVE_SPEED_MUL, NpcInfoSpeed.ATTACK_SPEED_MUL));
        }
    }

    public void onRandomAnimation() {
        if (System.currentTimeMillis() - this._lastSocialAction > 10000L) {
            this.broadcastPacket(new SocialAction(this.getObjectId(), 2));
            this._lastSocialAction = System.currentTimeMillis();
        }
    }

    public void startRandomAnimation() {
        if (!this.hasRandomAnimation()) {
            return;
        }
        this.K = (double)LazyPrecisionTaskManager.getInstance().addNpcAnimationTask(this);
    }

    public void stopRandomAnimation() {
        if (this.K != null) {
            this.K.cancel(false);
            this.K = (double)null;
        }
    }

    public boolean hasRandomAnimation() {
        return this._hasRandomAnimation;
    }

    public boolean hasRandomWalk() {
        return this._hasRandomWalk;
    }

    public Castle getCastle() {
        if (this.getReflection() == ReflectionManager.GIRAN_HARBOR && Config.SERVICES_GIRAN_HARBOR_NOTAX) {
            return null;
        }
        if (Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && this.getReflection() == ReflectionManager.GIRAN_HARBOR) {
            return null;
        }
        if (Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && this.isInZone(Zone.ZoneType.offshore)) {
            return null;
        }
        if (this.a == null) {
            this.a = (Castle)ResidenceHolder.getInstance().getResidence(this.getTemplate().getCastleId());
        }
        return this.a;
    }

    public Castle getCastle(Player player) {
        return this.getCastle();
    }

    public ClanHall getClanHall() {
        if (this.a == null) {
            this.a = ResidenceHolder.getInstance().findNearestResidence(ClanHall.class, this.getX(), this.getY(), this.getZ(), this.getReflection(), 32768);
        }
        return this.a;
    }

    @Override
    public void onAction(Player player, boolean bl) {
        if (!this.isTargetable()) {
            player.sendActionFailed();
            return;
        }
        if (player.getTarget() != this) {
            player.setTarget(this);
            if (player.getTarget() == this) {
                player.sendPacket(new MyTargetSelected(this.getObjectId(), player.getLevel() - this.getLevel()), new ExAbnormalStatusUpdateFromTarget(this, true), this.makeStatusUpdate(9, 10));
            }
            player.sendPacket(new ValidateLocation(this), ActionFail.STATIC);
            return;
        }
        if (Events.onAction(player, this, bl)) {
            player.sendActionFailed();
            return;
        }
        if (this.isAutoAttackable(player)) {
            player.getAI().Attack(this, false, bl);
            return;
        }
        if (!this.isInActingRange(player)) {
            if (!player.getAI().isIntendingInteract(this)) {
                player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
            }
            return;
        }
        if (player.getKarma() > 0 && !this.canInteractWithKarmaPlayer() && !player.isGM()) {
            player.sendActionFailed();
            return;
        }
        if (player.isCursedWeaponEquipped() && !this.canInteractWithCursedWeaponPlayer() && !player.isGM()) {
            player.sendActionFailed();
            return;
        }
        if (player.isFlying()) {
            player.sendActionFailed();
            return;
        }
        if (!Config.ALLOW_TALK_WHILE_SITTING && player.isSitting() || player.isAlikeDead()) {
            return;
        }
        if (this.hasRandomAnimation()) {
            this.onRandomAnimation();
        }
        player.sendActionFailed();
        if (player.isMoving()) {
            player.stopMove();
        }
        player.setLastNpcInteractionTime();
        if (this.dA) {
            this.showBusyWindow(player);
        } else if (this.isHasChatWindow()) {
            boolean bl2 = false;
            Quest[] questArray = this.getTemplate().getEventQuests(QuestEventType.NPC_FIRST_TALK);
            if (questArray != null && questArray.length > 0) {
                for (Quest quest : questArray) {
                    QuestState questState = player.getQuestState(quest.getName());
                    if (questState != null && questState.isCompleted() || !quest.notifyFirstTalk(this, player)) continue;
                    bl2 = true;
                }
            }
            if (!bl2) {
                this.showChatWindow(player, 0, new Object[0]);
            }
        }
    }

    protected boolean canInteractWithKarmaPlayer() {
        return Config.ALT_GAME_KARMA_PLAYER_CAN_SHOP;
    }

    protected boolean canInteractWithCursedWeaponPlayer() {
        return Config.ALT_GAME_CURSED_WEAPON_PLAYER_CAN_SHOP;
    }

    public void showQuestWindow(Player player, String string) {
        if (!player.isQuestContinuationPossible(true)) {
            return;
        }
        int n = 0;
        for (QuestState questArray : player.getAllQuestsStates()) {
            if (questArray == null || !questArray.getQuest().isVisible() || !questArray.isStarted() || questArray.getCond() <= 0) continue;
            ++n;
        }
        if (n > 40) {
            this.showChatWindow(player, "quest-limit.htm", new Object[0]);
            return;
        }
        try {
            Object object = player.getQuestState(string);
            if (object != null) {
                if (((QuestState)object).isCompleted()) {
                    this.showChatWindow(player, "completed-quest.htm", new Object[0]);
                    return;
                }
                if (((QuestState)object).getQuest().notifyTalk(this, (QuestState)object)) {
                    return;
                }
            } else {
                Quest[] questArray;
                Quest quest = QuestManager.getQuest(string);
                if (quest != null && (questArray = this.getTemplate().getEventQuests(QuestEventType.QUEST_START)) != null && questArray.length > 0) {
                    for (Quest quest2 : questArray) {
                        if (quest2 != quest) continue;
                        object = quest.newQuestState(player, 1);
                        if (!((QuestState)object).getQuest().notifyTalk(this, (QuestState)object)) break;
                        return;
                    }
                }
            }
            this.showChatWindow(player, "no-quest.htm", new Object[0]);
        }
        catch (Exception exception) {
            cj.warn("problem with npc text(questId: " + string + ") " + exception);
            cj.error("", (Throwable)exception);
        }
        player.sendActionFailed();
    }

    public static boolean canBypassCheck(Player player, NpcInstance npcInstance) {
        if (npcInstance == null || player.isActionsDisabled() || !Config.ALLOW_TALK_WHILE_SITTING && player.isSitting() || !npcInstance.isInActingRange(player)) {
            player.sendActionFailed();
            return false;
        }
        return true;
    }

    public void onBypassFeedback(Player player, String string) {
        block76: {
            if (!NpcInstance.canBypassCheck(player, this)) {
                return;
            }
            try {
                if (string.equalsIgnoreCase("TerritoryStatus")) {
                    NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
                    Castle castle = this.getCastle();
                    if (castle != null && castle.getId() > 0 && castle.getOwnerId() > 0) {
                        npcHtmlMessage.setFile("merchant/territorystatus.htm");
                        npcHtmlMessage.replace("%castlename%", HtmlUtils.htmlResidenceName(castle.getId()));
                        npcHtmlMessage.replace("%taxpercent%", String.valueOf(castle.getTaxPercent()));
                        npcHtmlMessage.replace("%castlename%", HtmlUtils.htmlResidenceName(castle.getId()));
                        npcHtmlMessage.replace("%kingdom_name%", new CustomMessage(this.getTerritoryName(castle.getId()), player, new Object[0]).toString());
                        Clan clan = ClanTable.getInstance().getClan(castle.getOwnerId());
                        if (clan != null) {
                            npcHtmlMessage.replace("%clanname%", clan.getName());
                            npcHtmlMessage.replace("%clanleadername%", clan.getLeaderName());
                        } else {
                            npcHtmlMessage.replace("%clanname%", "Nonexistent clan");
                            npcHtmlMessage.replace("%clanleadername%", "None");
                        }
                    } else {
                        npcHtmlMessage.setFile("merchant/nofeudinfo.htm");
                        npcHtmlMessage.replace("%castlename%", castle == null ? "" : HtmlUtils.htmlResidenceName(castle.getId()));
                        npcHtmlMessage.replace("%kingdom_name%", castle == null ? "" : new CustomMessage(this.getTerritoryName(castle.getId()), player, new Object[0]).toString());
                    }
                    player.sendPacket((IStaticPacket)npcHtmlMessage);
                    break block76;
                }
                if (string.startsWith("Quest")) {
                    String string2 = string.substring(5).trim();
                    if (string2.length() == 0) {
                        this.showQuestWindow(player);
                    } else {
                        this.showQuestWindow(player, string2);
                    }
                    break block76;
                }
                if (string.startsWith("Chat")) {
                    try {
                        int n = Integer.parseInt(string.substring(5));
                        this.showChatWindow(player, n, new Object[0]);
                    }
                    catch (NumberFormatException numberFormatException) {
                        String string3 = string.substring(5).trim();
                        if (string3.length() == 0) {
                            this.showChatWindow(player, "npcdefault.htm", new Object[0]);
                            break block76;
                        }
                        this.showChatWindow(player, string3, new Object[0]);
                    }
                    break block76;
                }
                if (string.startsWith("AttributeCancel")) {
                    player.sendPacket((IStaticPacket)new ExShowBaseAttributeCancelWindow(player));
                } else if (string.startsWith("NpcLocationInfo")) {
                    int n = Integer.parseInt(string.substring(16));
                    NpcInstance npcInstance = GameObjectsStorage.getByNpcId(n);
                    if (npcInstance != null) {
                        player.sendPacket((IStaticPacket)new RadarControl(2, 2, npcInstance.getLoc()));
                        player.sendPacket((IStaticPacket)new RadarControl(0, 1, npcInstance.getLoc()));
                    }
                } else if (string.startsWith("Multisell") || string.startsWith("multisell")) {
                    String string4 = string.substring(9).trim();
                    Castle castle = this.getCastle(player);
                    MultiSellHolder.getInstance().SeparateAndSend(Integer.parseInt(string4), player, castle != null ? castle.getTaxRate() : 0.0);
                } else if (string.startsWith("EnterRift")) {
                    StringTokenizer stringTokenizer = new StringTokenizer(string);
                    stringTokenizer.nextToken();
                    Integer n = Integer.parseInt(stringTokenizer.nextToken());
                    DimensionalRiftManager.getInstance().start(player, n, this);
                } else if (string.startsWith("ChangeRiftRoom")) {
                    if (player.isInParty() && player.getParty().isInReflection() && player.getParty().getReflection() instanceof DimensionalRift) {
                        ((DimensionalRift)player.getParty().getReflection()).manualTeleport(player, this);
                    } else {
                        DimensionalRiftManager.getInstance().teleportToWaitingRoom(player);
                    }
                } else if (string.startsWith("ExitRift")) {
                    if (player.isInParty() && player.getParty().isInReflection() && player.getParty().getReflection() instanceof DimensionalRift) {
                        ((DimensionalRift)player.getParty().getReflection()).manualExitRift(player, this);
                    } else {
                        DimensionalRiftManager.getInstance().teleportToWaitingRoom(player);
                    }
                } else if (string.equalsIgnoreCase("SkillList")) {
                    this.showSkillList(player);
                } else if (string.equalsIgnoreCase("CustomSkillList")) {
                    NpcInstance.showCustomSkillList(player);
                } else if (string.startsWith("AltSkillList")) {
                    int n = Integer.parseInt(string.substring(13).trim());
                    this.showSkillList(player, ClassId.VALUES[n]);
                } else if (string.equalsIgnoreCase("SkillEnchantList")) {
                    this.showSkillEnchantList(player);
                } else if (string.equalsIgnoreCase("ClanSkillList")) {
                    NpcInstance.showClanSkillList(player);
                } else if (string.startsWith("Augment")) {
                    int n = Integer.parseInt(string.substring(8, 9).trim());
                    if (n == 1) {
                        player.setRefineryHandler(RefineryHandler.getInstance());
                        RefineryHandler.getInstance().onInitRefinery(player);
                    } else if (n == 2) {
                        player.setRefineryHandler(RefineryHandler.getInstance());
                        RefineryHandler.getInstance().onInitRefineryCancel(player);
                    }
                } else if (string.startsWith("show_ensoul_window")) {
                    player.sendPacket((IStaticPacket)ExShowEnsoulWindow.STATIC);
                } else if (string.startsWith("show_extract_ensoul_window")) {
                    player.sendPacket((IStaticPacket)ExEnSoulExtractionShow.STATIC);
                } else if (string.startsWith("Link")) {
                    this.showChatWindow(player, string.substring(5), new Object[0]);
                } else if (string.startsWith("Teleport")) {
                    int n = Integer.parseInt(string.substring(9).trim());
                    TeleportLocation[] teleportLocationArray = this.getTemplate().getTeleportList(n);
                    if (teleportLocationArray != null) {
                        this.showTeleportList(player, teleportLocationArray);
                    } else {
                        player.sendMessage(new CustomMessage("Common.BrokenLink", player, new Object[0]));
                    }
                } else if (string.startsWith("Tele20Lvl")) {
                    int n = Integer.parseInt(string.substring(10, 11).trim());
                    TeleportLocation[] teleportLocationArray = this.getTemplate().getTeleportList(n);
                    if (player.getLevel() > 20) {
                        this.showChatWindow(player, "teleporter/" + this.getNpcId() + "-no.htm", new Object[0]);
                    } else if (teleportLocationArray != null) {
                        this.showTeleportList(player, teleportLocationArray);
                    } else {
                        player.sendMessage(new CustomMessage("Common.BrokenLink", player, new Object[0]));
                    }
                } else if (string.startsWith("open_gate")) {
                    int n = Integer.parseInt(string.substring(10));
                    ReflectionUtils.getDoor(n).openMe();
                    player.sendActionFailed();
                } else if (string.startsWith("lang")) {
                    String string5 = string.substring(4).trim();
                    if (string5.startsWith("ru")) {
                        player.setVar("lang@", "ru", -1L);
                        player.sendMessage("Lang RU enable");
                        this.showChatWindow(player, 0, new Object[0]);
                    } else if (string5.startsWith("en")) {
                        player.setVar("lang@", "en", -1L);
                        player.sendMessage("Lang EN enable");
                        this.showChatWindow(player, 0, new Object[0]);
                    }
                } else if (string.startsWith("ExitFromQuestInstance")) {
                    Reflection reflection = player.getReflection();
                    reflection.startCollapseTimer(60000L);
                    player.teleToLocation(reflection.getReturnLoc(), 0);
                    if (string.length() > 22) {
                        try {
                            int n = Integer.parseInt(string.substring(22));
                            this.showChatWindow(player, n, new Object[0]);
                        }
                        catch (NumberFormatException numberFormatException) {
                            String string6 = string.substring(22).trim();
                            if (string6.length() > 0) {
                                this.showChatWindow(player, string6, new Object[0]);
                            }
                        }
                    }
                }
            }
            catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                cj.info("Incorrect htm bypass! npcId=" + this.getTemplate().npcId + " command=[" + string + "]");
            }
            catch (NumberFormatException numberFormatException) {
                cj.info("Invalid bypass to Server command parameter! npcId=" + this.getTemplate().npcId + " command=[" + string + "]");
            }
        }
    }

    public void showTeleportList(Player player, TeleportLocation[] teleportLocationArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("&$556;").append("<br><br>");
        if (teleportLocationArray != null && player.getPlayerAccess().UseTeleport) {
            for (TeleportLocation teleportLocation : teleportLocationArray) {
                Object object;
                if (teleportLocation.getItem().getItemId() == 57) {
                    Object object2;
                    object = Float.valueOf(Config.GATEKEEPER_MODIFIER.containsKey(player.getLevel()) ? Config.GATEKEEPER_MODIFIER.get(player.getLevel()).floatValue() : 0.0f);
                    if (teleportLocation.getPrice() > 0L && ((Float)object).floatValue() > 0.0f) {
                        object2 = Calendar.getInstance();
                        int n = ((Calendar)object2).get(7);
                        int n2 = Calendar.getInstance().get(11);
                        if ((n == 1 || n == 7) && n2 >= 20 && n2 <= 12) {
                            object = Float.valueOf(((Float)object).floatValue() / 2.0f);
                        }
                    }
                    stringBuilder.append("<button ALIGN=LEFT ICON=\"TELEPORT\" action=\"bypass -h scripts_Util:Gatekeeper ").append(teleportLocation.getX()).append(" ").append(teleportLocation.getY()).append(" ").append(teleportLocation.getZ());
                    if (teleportLocation.getCastleId() != 0) {
                        stringBuilder.append(" ").append(teleportLocation.getCastleId());
                    }
                    object2 = new CustomMessage(teleportLocation.getName(), player, new Object[0]).toString();
                    stringBuilder.append(" ").append((long)((float)teleportLocation.getPrice() * ((Float)object).floatValue())).append("\"");
                    if (teleportLocation.getFString() > 0) {
                        stringBuilder.append(" msg=\"811;F;").append(teleportLocation.getFString()).append("\"");
                    }
                    stringBuilder.append(">").append((String)object2);
                    if ((float)teleportLocation.getPrice() * ((Float)object).floatValue() > 0.0f) {
                        stringBuilder.append(" - ").append((long)((float)teleportLocation.getPrice() * ((Float)object).floatValue())).append(" ").append(HtmlUtils.htmlItemName(57));
                    }
                    if (teleportLocation.getMinLevel() > 0) {
                        stringBuilder.append(" - ").append(new CustomMessage("l2.gameserver.model.instances.NpcInstance.TeleportListMinLevel", player, teleportLocation.getMinLevel()).toString());
                    }
                    if (teleportLocation.getMaxLevel() > 0) {
                        stringBuilder.append(" - ").append(new CustomMessage("l2.gameserver.model.instances.NpcInstance.TeleportListMaxLevel", player, teleportLocation.getMaxLevel()).toString());
                    }
                    if (teleportLocation.getKeyItemId() > 0) {
                        stringBuilder.append(" - ").append(new CustomMessage("l2.gameserver.model.instances.NpcInstance.TeleportListKeyItem", player, teleportLocation.getKeyItemId()).toString());
                    }
                    stringBuilder.append("</button><br1>\n");
                    continue;
                }
                object = new CustomMessage(teleportLocation.getName(), player, new Object[0]).toString();
                stringBuilder.append("<button ALIGN=LEFT ICON=\"TELEPORT\" action=\"bypass -h scripts_Util:QuestGatekeeper ").append(teleportLocation.getX()).append(" ").append(teleportLocation.getY()).append(" ").append(teleportLocation.getZ()).append(" ").append(teleportLocation.getPrice()).append(" ").append(teleportLocation.getItem().getItemId()).append("\" msg=\"811;F;").append(teleportLocation.getFString()).append("\">").append((String)object).append(" - ").append(teleportLocation.getPrice()).append(" ").append(HtmlUtils.htmlItemName(teleportLocation.getItem().getItemId()));
                if (teleportLocation.getMinLevel() > 0) {
                    stringBuilder.append(" - ").append(new CustomMessage("l2.gameserver.model.instances.NpcInstance.TeleportListMinLevel", player, teleportLocation.getMinLevel()).toString());
                }
                if (teleportLocation.getMaxLevel() > 0) {
                    stringBuilder.append(" - ").append(new CustomMessage("l2.gameserver.model.instances.NpcInstance.TeleportListMaxLevel", player, teleportLocation.getMaxLevel()).toString());
                }
                if (teleportLocation.getKeyItemId() > 0) {
                    stringBuilder.append(" - ").append(new CustomMessage("l2.gameserver.model.instances.NpcInstance.TeleportListKeyItem", player, teleportLocation.getKeyItemId()).toString());
                }
                stringBuilder.append("</button><br1>\n");
            }
        } else {
            stringBuilder.append("No teleports available for you.");
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
        npcHtmlMessage.setHtml(Strings.bbParse(stringBuilder.toString()));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void showQuestWindow(Player player) {
        ArrayList<Quest> arrayList = new ArrayList<Quest>();
        List<QuestState> list = player.getQuestsForEvent(this, QuestEventType.QUEST_TALK);
        Quest[] questArray = this.getTemplate().getEventQuests(QuestEventType.QUEST_START);
        if (list != null) {
            for (QuestState questState : list) {
                if (arrayList.contains(questState.getQuest()) || questState.getQuest().getQuestIntId() <= 0) continue;
                arrayList.add(questState.getQuest());
            }
        }
        if (questArray != null) {
            for (Quest quest : questArray) {
                if (arrayList.contains(quest) || quest.getQuestIntId() <= 0) continue;
                arrayList.add(quest);
            }
        }
        if (arrayList.size() > 1) {
            this.showQuestChooseWindow(player, arrayList.toArray(new Quest[arrayList.size()]));
        } else if (arrayList.size() == 1) {
            this.showQuestWindow(player, ((Quest)arrayList.get(0)).getName());
        } else {
            this.showQuestWindow(player, "");
        }
    }

    public void showQuestChooseWindow(Player player, Quest[] questArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body><title>Talk about:</title><br>");
        for (Quest quest : questArray) {
            if (!quest.isVisible()) continue;
            stringBuilder.append("<button ALIGN=LEFT ICON=\"QUEST\" action=\"bypass -h npc_").append(this.getObjectId()).append("_Quest ").append(quest.getName()).append("\">[").append(quest.getDescr(player)).append("]</button><br>");
        }
        stringBuilder.append("</body></html>");
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        int n2;
        Object object = "seven_signs/";
        int n3 = this.getNpcId();
        switch (n3) {
            case 31111: {
                int n4 = SevenSigns.getInstance().getSealOwner(1);
                n2 = SevenSigns.getInstance().getPlayerCabal(player);
                int n5 = SevenSigns.getInstance().getCabalHighestScore();
                if (n2 == n4 && n2 == n5) {
                    switch (n4) {
                        case 2: {
                            object = (String)object + "spirit_dawn.htm";
                            break;
                        }
                        case 1: {
                            object = (String)object + "spirit_dusk.htm";
                            break;
                        }
                        case 0: {
                            object = (String)object + "spirit_null.htm";
                        }
                    }
                    break;
                }
                object = (String)object + "spirit_null.htm";
                break;
            }
            case 31112: {
                object = (String)object + "spirit_exit.htm";
                break;
            }
            case 30298: {
                if (player.getPledgeType() == -1) {
                    object = this.getHtmlPath(n3, 1, player);
                    break;
                }
                object = this.getHtmlPath(n3, 0, player);
                break;
            }
            default: {
                if (n3 >= 31093 && n3 <= 31094 || n3 >= 31172 && n3 <= 31201 || n3 >= 31239 && n3 <= 31254) {
                    return;
                }
                object = this.getHtmlPath(n3, n, player);
            }
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this, (String)object, n);
        if (objectArray.length % 2 == 0) {
            for (n2 = 0; n2 < objectArray.length; n2 += 2) {
                npcHtmlMessage.replace(String.valueOf(objectArray[n2]), String.valueOf(objectArray[n2 + 1]));
            }
        }
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void showChatWindow(Player player, String string, Object ... objectArray) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this, string, 0);
        if (objectArray.length % 2 == 0) {
            for (int i = 0; i < objectArray.length; i += 2) {
                npcHtmlMessage.replace(String.valueOf(objectArray[i]), String.valueOf(objectArray[i + 1]));
            }
        }
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public String getHtmlPath(int n, int n2, Player player) {
        String string = n2 == 0 ? "" + n : n + "-" + n2;
        if (this.getTemplate().getHtmRoot() != null) {
            return this.getTemplate().getHtmRoot() + string + ".htm";
        }
        String string2 = "default/" + string + ".htm";
        if (HtmCache.getInstance().getNullable(string2, player) != null) {
            return string2;
        }
        string2 = "trainer/" + string + ".htm";
        if (HtmCache.getInstance().getNullable(string2, player) != null) {
            return string2;
        }
        return "npcdefault.htm";
    }

    public final boolean isBusy() {
        return this.dA;
    }

    public void setBusy(boolean bl) {
        this.dA = bl;
    }

    public final String getBusyMessage() {
        return this.dD;
    }

    public void setBusyMessage(String string) {
        this.dD = string;
    }

    public void showBusyWindow(Player player) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
        npcHtmlMessage.setFile("npcbusy.htm");
        npcHtmlMessage.replace("%npcname%", this.getName());
        npcHtmlMessage.replace("%playername%", player.getName());
        npcHtmlMessage.replace("%busymessage%", this.dD);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public boolean canEnchantSkills() {
        return this instanceof TrainerInstance;
    }

    public void showSkillEnchantList(Player player) {
        ClassId classId = player.getClassId();
        if (player.getClassId().getLevel() < 4) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><head><body>");
            if (player.isLangRus()) {
                stringBuilder.append("\u041c\u0430\u0441\u0442\u0435\u0440:<br>");
                stringBuilder.append("\u0412\u044b \u0434\u043e\u043b\u0436\u043d\u044b \u0432\u044b\u043f\u043e\u043b\u043d\u0438\u0442\u044c \u043a\u0432\u0435\u0441\u0442 \u043d\u0430 \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u0438\u0435 \u0442\u0440\u0435\u0442\u044c\u0435\u0439 \u043f\u0440\u043e\u0444\u0435\u0441\u0441\u0438\u0438.");
            } else {
                stringBuilder.append("Trainer:<br>");
                stringBuilder.append("You must have 3rd class change quest completed.");
            }
            stringBuilder.append("</body></html>");
            npcHtmlMessage.setHtml(stringBuilder.toString());
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        if (player.getLevel() < 76) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
            return;
        }
        if (!(this.getTemplate().canTeach(classId) || this.getTemplate().canTeach(classId.getParent()) || Config.ALT_ALLOW_ALLCLASS_SKILLENCHANT)) {
            if (this instanceof WarehouseInstance) {
                this.showChatWindow(player, "warehouse/" + this.getNpcId() + "-noteach.htm", new Object[0]);
            } else if (this.canEnchantSkills()) {
                this.showChatWindow(player, "trainer/" + this.getNpcId() + "-noteach.htm", new Object[0]);
            } else {
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<html><head><body>");
                stringBuilder.append(new CustomMessage("l2p.gameserver.model.instances.L2NpcInstance.WrongTeacherClass", player, new Object[0]));
                stringBuilder.append("</body></html>");
                npcHtmlMessage.setHtml(stringBuilder.toString());
                player.sendPacket((IStaticPacket)npcHtmlMessage);
            }
            return;
        }
        player.sendPacket((IStaticPacket)ExEnchantSkillList.packetFor(player, this));
    }

    public void showSkillList(Player player) {
        ClassId classId = player.getClassId();
        this.showSkillList(player, player.getClassId());
    }

    public void showSkillList(Player player, ClassId classId) {
        if (classId == null) {
            return;
        }
        int n = this.getTemplate().npcId;
        if (this.getTemplate().getTeachInfo().isEmpty()) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<html><head><body>");
            if (player.getVar("lang@").equalsIgnoreCase("en")) {
                stringBuilder.append("I cannot teach you. My class list is empty.<br> Ask admin to fix it. <br>NpcId:" + n + ", Your classId:" + classId.name() + "<br>");
            } else {
                stringBuilder.append("\u042f \u043d\u0435 \u043c\u043e\u0433\u0443 \u043e\u0431\u0443\u0447\u0438\u0442\u044c \u0442\u0435\u0431\u044f. \u0414\u043b\u044f \u0442\u0432\u043e\u0435\u0433\u043e \u043a\u043b\u0430\u0441\u0441\u0430 \u043c\u043e\u0439 \u0441\u043f\u0438\u0441\u043e\u043a \u043f\u0443\u0441\u0442.<br> \u0421\u0432\u044f\u0436\u0438\u0441\u044c \u0441 \u0430\u0434\u043c\u0438\u043d\u043e\u043c \u0434\u043b\u044f \u0444\u0438\u043a\u0441\u0430 \u044d\u0442\u043e\u0433\u043e. <br>NpcId:" + n + ", \u0442\u0432\u043e\u0439 classId:" + classId.name() + "<br>");
            }
            stringBuilder.append("</body></html>");
            npcHtmlMessage.setHtml(stringBuilder.toString());
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        if (!(this.getTemplate().canTeach(classId) || this.getTemplate().canTeach(classId.getParent()) || Config.ALT_ALLOW_ALLCLASS_SKILL_LEARN)) {
            if (this instanceof WarehouseInstance) {
                this.showChatWindow(player, "warehouse/" + this.getNpcId() + "-noteach.htm", new Object[0]);
            } else if (this instanceof TrainerInstance) {
                this.showChatWindow(player, "trainer/" + this.getNpcId() + "-noteach.htm", new Object[0]);
            } else {
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, this);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("<html><head><body>");
                stringBuilder.append(new CustomMessage("l2p.gameserver.model.instances.L2NpcInstance.WrongTeacherClass", player, new Object[0]));
                stringBuilder.append("</body></html>");
                npcHtmlMessage.setHtml(stringBuilder.toString());
                player.sendPacket((IStaticPacket)npcHtmlMessage);
            }
            return;
        }
        Collection<SkillLearn> collection = SkillAcquireHolder.getInstance().getAvailableSkills(player, classId, AcquireType.NORMAL, null, 0);
        ExAcquirableSkillListByClass exAcquirableSkillListByClass = new ExAcquirableSkillListByClass(AcquireType.NORMAL, collection.size());
        int n2 = 0;
        for (SkillLearn object : collection) {
            Skill skill;
            if (object.isClicked() || (skill = SkillTable.getInstance().getInfo(object.getId(), object.getLevel())) == null || !Config.ALT_WEAK_SKILL_LEARN && (!skill.getCanLearn(player.getClassId()) || !skill.canTeachBy(n))) continue;
            ++n2;
            exAcquirableSkillListByClass.addSkill(object.getId(), object.getLevel(), object.getLevel(), object.getCost(), 0);
        }
        if (n2 == 0) {
            int n3 = SkillAcquireHolder.getInstance().getMinLevelForNewSkill(classId, player.getLevel(), AcquireType.NORMAL);
            if (n3 > 0) {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.YOU_DO_NOT_HAVE_ANY_FURTHER_SKILLS_TO_LEARN__COME_BACK_WHEN_YOU_HAVE_REACHED_LEVEL_S1);
                systemMessage.addNumber(n3);
                player.sendPacket((IStaticPacket)systemMessage);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_OTHER_SKILLS_TO_LEARN);
            }
            player.sendPacket((IStaticPacket)AcquireSkillDone.STATIC);
        } else {
            if (Config.ALT_WEAK_SKILL_LEARN) {
                player.setVar("AcquireSkillClassId", classId.getId(), -1L);
            }
            player.sendPacket((IStaticPacket)exAcquirableSkillListByClass);
        }
        player.sendActionFailed();
    }

    public static void showFishingSkillList(Player player) {
        NpcInstance.showAcquireList(AcquireType.FISHING, player);
    }

    public static void showCustomSkillList(Player player) {
        if (Config.ALT_ALLOW_CUSTOM_SKILL_LEARN) {
            NpcInstance.showAcquireList(AcquireType.CERTIFICATION, player);
        } else {
            player.sendPacket((IStaticPacket)AcquireSkillDone.STATIC);
            player.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_OTHER_SKILLS_TO_LEARN);
        }
    }

    public static void showClanSkillList(Player player) {
        if (player.getClan() == null || !player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CLAN_LEADER_IS_ENABLED);
            player.sendActionFailed();
            return;
        }
        NpcInstance.showAcquireList(AcquireType.CLAN, player);
    }

    public static void showAcquireList(AcquireType acquireType, Player player) {
        Collection<SkillLearn> collection = SkillAcquireHolder.getInstance().getAvailableSkills(player, acquireType);
        ExAcquirableSkillListByClass exAcquirableSkillListByClass = new ExAcquirableSkillListByClass(acquireType, collection.size());
        for (SkillLearn skillLearn : collection) {
            exAcquirableSkillListByClass.addSkill(skillLearn.getId(), skillLearn.getLevel(), skillLearn.getLevel(), skillLearn.getCost(), 0);
        }
        if (collection.size() == 0) {
            player.sendPacket((IStaticPacket)AcquireSkillDone.STATIC);
            player.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_OTHER_SKILLS_TO_LEARN);
        } else {
            if (Config.ALT_WEAK_SKILL_LEARN) {
                player.unsetVar("AcquireSkillClassId");
            }
            player.sendPacket((IStaticPacket)exAcquirableSkillListByClass);
        }
        player.sendActionFailed();
    }

    public int getSpawnAnimation() {
        return this._spawnAnimation;
    }

    @Override
    public double getColRadius() {
        return this.getCollisionRadius();
    }

    @Override
    public double getColHeight() {
        return this.getCollisionHeight();
    }

    public int calculateLevelDiffForDrop(int n) {
        if (!Config.DEEPBLUE_DROP_RULES || this._ignoreDropDiffPenalty) {
            return 0;
        }
        int n2 = this.getLevel();
        int n3 = this instanceof RaidBossInstance ? Config.DEEPBLUE_DROP_RAID_MAXDIFF : Config.DEEPBLUE_DROP_MAXDIFF;
        int n4 = this instanceof RaidBossInstance ? Config.DEEPRED_DROP_RAID_MAXDIFF : Config.DEEPRED_DROP_MAXDIFF;
        int n5 = n - n2;
        int n6 = 0;
        if (n5 > n3) {
            n6 = Math.max(n5 - n3, 0);
        } else if (-n5 > n4) {
            n6 = Math.max(-n5 - n4, 0);
        }
        return n6;
    }

    public boolean isSevenSignsMonster() {
        return this.getFaction().getName().equalsIgnoreCase("c_dungeon_clan");
    }

    @Override
    public String toString() {
        return this.getNpcId() + " " + this.getName();
    }

    public void refreshID() {
        this.objectId = IdFactory.getInstance().getNextId();
        this._storedId = GameObjectsStorage.refreshId(this);
    }

    public void setUnderground(boolean bl) {
        this.dB = bl;
    }

    public boolean isUnderground() {
        return this.dB;
    }

    @Override
    public boolean isTargetable() {
        return this.dz;
    }

    public void setTargetable(boolean bl) {
        this.dz = bl;
    }

    public boolean isShowName() {
        return this._showName;
    }

    public void setShowName(boolean bl) {
        this._showName = bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public NpcListenerList getListeners() {
        if (this.listeners == null) {
            NpcInstance npcInstance = this;
            synchronized (npcInstance) {
                if (this.listeners == null) {
                    this.listeners = new NpcListenerList(this);
                }
            }
        }
        return (NpcListenerList)this.listeners;
    }

    @Override
    public <T extends NpcListener> boolean addListener(T t) {
        return this.getListeners().add(t);
    }

    @Override
    public <T extends NpcListener> boolean removeListener(T t) {
        return this.getListeners().remove(t);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public NpcStatsChangeRecorder getStatsRecorder() {
        if (this._statsRecorder == null) {
            NpcInstance npcInstance = this;
            synchronized (npcInstance) {
                if (this._statsRecorder == null) {
                    this._statsRecorder = new NpcStatsChangeRecorder(this);
                }
            }
        }
        return (NpcStatsChangeRecorder)this._statsRecorder;
    }

    public void setNpcState(int n) {
        this.broadcastPacket(new ExChangeNpcState(this.getObjectId(), n));
        this.mH = n;
    }

    public int getNpcState() {
        return this.mH;
    }

    @Override
    public List<L2GameServerPacket> addPacketList(Player player, Creature creature) {
        ArrayList<L2GameServerPacket> arrayList = new ArrayList<L2GameServerPacket>(3);
        arrayList.add(new NpcInfo(this, player));
        if (this.isInCombat()) {
            arrayList.add(new AutoAttackStart(this.getObjectId()));
        }
        if (this.isMoving() || this.isFollowing()) {
            arrayList.add(this.movePacket());
        }
        return arrayList;
    }

    @Override
    public Clan getClan() {
        Castle castle = this.getCastle();
        return castle != null ? castle.getOwner() : null;
    }

    @Override
    public boolean isNpc() {
        return true;
    }

    @Override
    public int getGeoZ(Location location) {
        if (this.isFlying() || this.isInWater() || this.isInBoat() || this.isBoat() || this.isDoor()) {
            return location.z;
        }
        if (this.isNpc()) {
            if (this.a instanceof Territory) {
                return GeoEngine.getHeight(location, this.getGeoIndex());
            }
            return location.z;
        }
        return super.getGeoZ(location);
    }

    public void teleportParty(Party party, int n, int n2, int n3, int n4, int n5) {
        if (party == null) {
            return;
        }
        for (Player player : party.getPartyMembers()) {
            if (n5 > 0 && this.isInRange(player, (long)n4) && Math.abs(this.getZ() - player.getZ()) < n5) {
                player.teleToLocation(n, n2, n3);
                continue;
            }
            if (!this.isInRange(player, (long)n4)) continue;
            player.teleToLocation(n, n2, n3);
        }
    }

    public boolean isMerchantNpc() {
        return false;
    }

    public SpawnRange getSpawnRange() {
        return this.a;
    }

    public void setSpawnRange(SpawnRange spawnRange) {
        this.a = spawnRange;
    }

    public void setParameter(String string, Object object) {
        if (this.c == StatsSet.EMPTY) {
            this.c = new StatsSet();
        }
        this.c.set(string, object);
    }

    public void setParameters(MultiValueSet<String> multiValueSet) {
        if (multiValueSet.isEmpty()) {
            return;
        }
        if (this.c == StatsSet.EMPTY) {
            this.c = new MultiValueSet(multiValueSet.size());
        }
        this.c.putAll(multiValueSet);
    }

    public int getParameter(String string, int n) {
        return this.c.getInteger(string, n);
    }

    public long getParameter(String string, long l) {
        return this.c.getLong(string, l);
    }

    public boolean getParameter(String string, boolean bl) {
        return this.c.getBool(string, bl);
    }

    public String getParameter(String string, String string2) {
        return this.c.getString(string, string2);
    }

    public MultiValueSet<String> getParameters() {
        return this.c;
    }

    public boolean isHasChatWindow() {
        return this._hasChatWindow;
    }

    public void setHasChatWindow(boolean bl) {
        this._hasChatWindow = bl;
    }

    @Override
    public boolean isFearImmune() {
        return !this.isMonster() || super.isFearImmune();
    }

    @Override
    public boolean isParalyzeImmune() {
        return !this.isMonster() || super.isParalyzeImmune();
    }

    private void updateEffectIconsImpl() {
        if (Config.SEND_EFFECT_LIST_ON_TARGET_NPC && this.isVisible()) {
            ExAbnormalStatusUpdateFromTarget exAbnormalStatusUpdateFromTarget = new ExAbnormalStatusUpdateFromTarget(this);
            for (Effect object : this.getEffectList().getAllFirstEffects()) {
                if (!object.isInUse() || object.isStackTypeMatch("HpRecoverCast")) continue;
                object.addIcon(exAbnormalStatusUpdateFromTarget);
            }
            List<Player> list = World.getAroundPlayers(this);
            for (int i = 0; i < list.size(); ++i) {
                Player player = (Player)list.get(i);
                if (player.getTarget() != this) continue;
                player.sendPacket((IStaticPacket)exAbnormalStatusUpdateFromTarget);
            }
        }
    }

    @Override
    public void updateEffectIcons() {
        this.updateEffectIconsImpl();
    }

    @Override
    public int getPAtk(Creature creature) {
        double d = BaseStats.STR.calcBonus(this) * (double)(this.getTemplate().level + 89);
        return (int)this.calcStat(Stats.POWER_ATTACK, (double)this.getTemplate().basePAtk * d / 100.0, creature, null);
    }

    @Override
    public int getPDef(Creature creature) {
        double d = this.getTemplate().level + 89;
        return (int)this.calcStat(Stats.POWER_DEFENCE, (double)this.getTemplate().basePDef * d / 100.0, creature, null);
    }

    @Override
    public int getMAtk(Creature creature, Skill skill) {
        double d = BaseStats.INT.calcBonus(this);
        double d2 = this.getTemplate().level + 89;
        double d3 = d2 * d2 * d * d / 10000.0;
        return (int)this.calcStat(Stats.MAGIC_ATTACK, (double)this.getTemplate().baseMAtk * d3, creature, skill);
    }

    @Override
    public int getMDef(Creature creature, Skill skill) {
        double d = BaseStats.MEN.calcBonus(this) * (double)(this.getTemplate().level + 89);
        return (int)this.calcStat(Stats.MAGIC_DEFENCE, (double)this.getTemplate().baseMDef * d / 100.0, creature, skill);
    }

    public String getTerritoryName(int n) {
        switch (n) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                return "the_kingdom_of_aden";
            }
            case 7: 
            case 8: 
            case 9: {
                return "the_kingdom_of_elmore";
            }
        }
        return "Unknown";
    }

    public class BroadcastCharInfoTask
    extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            NpcInstance.this.broadcastCharInfoImpl();
            NpcInstance.this.R = null;
        }
    }
}
