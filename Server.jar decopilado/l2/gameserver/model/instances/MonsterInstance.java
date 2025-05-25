/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 */
package l2.gameserver.model.instances;

import gnu.trove.TIntHashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.model.AggroList;
import l2.gameserver.model.CommandChannel;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Manor;
import l2.gameserver.model.MinionList;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.PlayerGroup;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.base.SpecialEffectState;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.oneDayReward.requirement.KillMobRequirement;
import l2.gameserver.model.instances.ChestInstance;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.model.instances.ReflectionBossInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestEventType;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.model.reward.RewardItem;
import l2.gameserver.model.reward.RewardList;
import l2.gameserver.model.reward.RewardType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExMagicAttackInfo;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.Stats;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.npc.Faction;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MonsterInstance
extends NpcInstance {
    private static final int mp = 1000;
    private ScheduledFuture<?> aa;
    private MinionList a;
    private boolean dw;
    private int mq;
    private boolean dx;
    private RewardItem a;
    private final Lock y = new ReentrantLock();
    private int mr;
    private double G;
    private TIntHashSet c;
    private final Lock z = new ReentrantLock();
    private boolean dy;
    private int ms;
    private List<RewardItem> bI;
    private final Lock A = new ReentrantLock();
    private int mt;
    private final double H = 200.0;
    private final double I = 50.0;
    private final double J = 30.0;

    public MonsterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this.setUndying(SpecialEffectState.FALSE);
        this.a = new MinionList(this);
    }

    @Override
    public boolean isMovementDisabled() {
        return this.getNpcId() == 18344 || this.getNpcId() == 18345 || super.isMovementDisabled();
    }

    @Override
    public boolean isLethalImmune() {
        return this.getMaxHp() >= 50000 || this.mt > 0 || this.getNpcId() == 22215 || this.getNpcId() == 22216 || this.getNpcId() == 22217 || super.isLethalImmune();
    }

    @Override
    public boolean isFearImmune() {
        return this.mt > 0 || super.isFearImmune();
    }

    @Override
    public boolean isParalyzeImmune() {
        return this.mt > 0 || super.isParalyzeImmune();
    }

    @Override
    public boolean isAutoAttackable(Creature creature) {
        return !creature.isMonster();
    }

    public int getChampion() {
        return this.mt;
    }

    public void setChampion() {
        if (this.getReflection().canChampions() && this.canChampion()) {
            double d = Rnd.nextDouble();
            if (Config.ALT_CHAMPION_CHANCE2 / 100.0 >= d) {
                this.setChampion(2);
            } else if ((Config.ALT_CHAMPION_CHANCE1 + Config.ALT_CHAMPION_CHANCE2) / 100.0 >= d) {
                this.setChampion(1);
            } else {
                this.setChampion(0);
            }
        } else {
            this.setChampion(0);
        }
    }

    public void setChampion(int n) {
        if (n == 0) {
            this.removeSkillById(4407);
            this.mt = 0;
        } else {
            this.addSkill(SkillTable.getInstance().getInfo(4407, n));
            this.mt = n;
        }
    }

    public boolean canChampion() {
        return !(!Config.ALT_CHAMPION_CAN_BE_SEVEN_SIGN_MONSTERS && this.isSevenSignsMonster() || this.isMinion() || Config.ALT_IGNORE_CHAMPIONS_LIST.contains(this.getNpcId()) || !Config.ALT_CHAMPIONS_LIST.isEmpty() && !Config.ALT_CHAMPIONS_LIST.contains(this.getNpcId()) || this.getTemplate().rewardExp <= 0L || this.getTemplate().level < Config.ALT_CHAMPION_MIN_LEVEL || this.getTemplate().level > Config.ALT_CHAMPION_TOP_LEVEL);
    }

    @Override
    public TeamType getTeam() {
        return this.getChampion() == 2 ? TeamType.RED : (this.getChampion() == 1 ? TeamType.BLUE : TeamType.NONE);
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
        if (this.getMinionList().hasMinions()) {
            if (this.aa != null) {
                this.aa.cancel(false);
                this.aa = null;
            }
            this.aa = ThreadPoolManager.getInstance().schedule(new MinionMaintainTask(), 1000L);
        }
    }

    @Override
    protected void onDespawn() {
        this.setOverhitDamage(0.0);
        this.setOverhitAttacker(null);
        this.clearSweep();
        this.clearHarvest();
        this.clearAbsorbers();
        super.onDespawn();
    }

    @Override
    public MinionList getMinionList() {
        return this.a;
    }

    public Location getMinionPosition() {
        return Location.findPointToStay(this, 100, 150);
    }

    public void notifyMinionDied(MinionInstance minionInstance) {
    }

    public void spawnMinion(MonsterInstance monsterInstance) {
        monsterInstance.setReflection(this.getReflection());
        if (this.getChampion() == 2) {
            monsterInstance.setChampion(1);
        } else {
            monsterInstance.setChampion(0);
        }
        monsterInstance.setHeading(this.getHeading());
        monsterInstance.setCurrentHpMp(monsterInstance.getMaxHp(), monsterInstance.getMaxMp(), true);
        monsterInstance.spawnMe(this.getMinionPosition());
    }

    @Override
    public boolean hasMinions() {
        return this.getMinionList().hasMinions();
    }

    @Override
    public void setReflection(Reflection reflection) {
        super.setReflection(reflection);
        if (this.hasMinions()) {
            for (MinionInstance minionInstance : this.getMinionList().getAliveMinions()) {
                minionInstance.setReflection(reflection);
            }
        }
    }

    @Override
    protected void onDelete() {
        if (this.aa != null) {
            this.aa.cancel(false);
            this.aa = null;
        }
        this.getMinionList().deleteMinions();
        super.onDelete();
    }

    @Override
    protected void onDeath(Creature creature) {
        if (this.aa != null) {
            this.aa.cancel(false);
            this.aa = null;
        }
        this.calculateRewards(creature);
        super.onDeath(creature);
    }

    @Override
    protected void onReduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3) {
        if (skill != null && skill.isOverhit()) {
            double d2 = (this.getCurrentHp() - d) * -1.0;
            if (d2 <= 0.0) {
                this.setOverhitDamage(0.0);
                this.setOverhitAttacker(null);
            } else {
                this.setOverhitDamage(d2);
                this.setOverhitAttacker(creature);
            }
        }
        super.onReduceCurrentHp(d, creature, skill, bl, bl2, bl3);
    }

    private int a(Iterable<Player> iterable, Map<Playable, AggroList.HateInfo> map) {
        int n = 0;
        for (Player player : iterable) {
            AggroList.HateInfo hateInfo;
            AggroList.HateInfo hateInfo2 = map.get(player);
            if (hateInfo2 == null) continue;
            n += hateInfo2.damage;
            Summon summon = player.getPet();
            if (summon == null || (hateInfo = map.get(summon)) == null) continue;
            n += hateInfo.damage;
        }
        return n;
    }

    private Creature a(Iterable<? extends PlayerGroup> iterable, Map<Playable, AggroList.HateInfo> map) {
        Object object;
        if (map == null || map.isEmpty()) {
            return null;
        }
        int n = Integer.MIN_VALUE;
        PlayerGroup playerGroup = null;
        for (PlayerGroup playerGroup2 : iterable) {
            int n2;
            if (playerGroup2 == playerGroup || (n2 = this.a(playerGroup2, map)) <= n) continue;
            n = n2;
            playerGroup = playerGroup2;
        }
        if (playerGroup == null) {
            return null;
        }
        if (playerGroup instanceof Player) {
            return (Player)playerGroup;
        }
        if (playerGroup instanceof Party) {
            object = (Party)playerGroup;
            return this.a((Iterable<? extends PlayerGroup>)((Party)object).getPartyMembers(), map);
        }
        if (playerGroup instanceof CommandChannel) {
            object = (CommandChannel)playerGroup;
            return this.a((Iterable<? extends PlayerGroup>)((CommandChannel)object).getParties(), map);
        }
        return null;
    }

    private Creature a(Map<Playable, AggroList.HateInfo> map) {
        HashSet<PlayerGroup> hashSet = new HashSet<PlayerGroup>();
        for (Playable playable : map.keySet()) {
            if (!(playable instanceof Player)) continue;
            hashSet.add(((Player)playable).getPlayerGroup());
        }
        return this.a((Iterable<? extends PlayerGroup>)hashSet, map);
    }

    protected Creature getTopDamager() {
        return this.a(this.getAggroList().getPlayableMap());
    }

    /*
     * WARNING - void declaration
     */
    public void calculateRewards(Creature creature) {
        Object object;
        Object object2;
        Object object3;
        Cloneable cloneable;
        Creature creature2 = this.getTopDamager();
        if (creature == null || !creature.isPlayable()) {
            creature = creature2;
        }
        if (creature == null || !creature.isPlayable()) {
            return;
        }
        Player player = creature.getPlayer();
        if (player == null) {
            return;
        }
        if (Config.ALT_DROP_LASTHIT) {
            creature2 = player;
        }
        Map<Playable, AggroList.HateInfo> map = this.getAggroList().getPlayableMap();
        Quest[] questArray = this.getTemplate().getEventQuests(QuestEventType.MOB_KILLED_WITH_QUEST);
        if (questArray != null && questArray.length > 0) {
            cloneable = null;
            if (this.isRaid() && Config.ALT_NO_LASTHIT) {
                cloneable = new ArrayList();
                for (Playable playable : map.keySet()) {
                    if (playable.isDead() || !this.isInRangeZ(playable, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) && !player.isInRangeZ(playable, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) || cloneable.contains(playable.getPlayer())) continue;
                    cloneable.add(playable.getPlayer());
                }
            } else if (player.getParty() != null) {
                cloneable = new ArrayList(player.getParty().getMemberCount());
                for (Player player2 : player.getParty().getPartyMembers()) {
                    if (player2.isDead() || !this.isInRangeZ(player2, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE) && !player.isInRangeZ(player2, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE)) continue;
                    cloneable.add(player2);
                }
            }
            for (Quest quest : questArray) {
                QuestState questState;
                object3 = player;
                if (quest.getParty() != 0 && cloneable != null) {
                    if (this.isRaid() || quest.getParty() == 2) {
                        Iterator iterator = cloneable.iterator();
                        while (iterator.hasNext()) {
                            object2 = (Player)iterator.next();
                            object = ((Player)object2).getQuestState(quest.getName());
                            if (object == null || ((QuestState)object).isCompleted()) continue;
                            quest.notifyKill(this, (QuestState)object);
                        }
                        object3 = null;
                    } else {
                        ArrayList<Object> arrayList = new ArrayList<Object>(cloneable.size());
                        object2 = cloneable.iterator();
                        while (object2.hasNext()) {
                            object = (Player)object2.next();
                            QuestState questState2 = ((Player)object).getQuestState(quest.getName());
                            if (questState2 == null || questState2.isCompleted()) continue;
                            arrayList.add(object);
                        }
                        if (arrayList.isEmpty()) continue;
                        object3 = (Player)arrayList.get(Rnd.get(arrayList.size()));
                        if (object3 == null) {
                            object3 = player;
                        }
                    }
                }
                if (object3 == null || (questState = ((Player)object3).getQuestState(quest.getName())) == null || questState.isCompleted()) continue;
                quest.notifyKill(this, questState);
            }
        }
        cloneable = new HashMap();
        for (AggroList.HateInfo hateInfo : map.values()) {
            if (hateInfo.damage <= 1) continue;
            Playable playable = (Playable)hateInfo.attacker;
            Player player3 = playable.getPlayer();
            object3 = (RewardInfo)cloneable.get(player3);
            if (object3 == null) {
                cloneable.put(player3, new RewardInfo(player3, hateInfo.damage));
                continue;
            }
            ((RewardInfo)object3).addDamage(hateInfo.damage);
        }
        Player[] playerArray = cloneable.keySet().toArray(new Player[cloneable.size()]);
        double[] dArray = new double[2];
        for (Object object4 : playerArray) {
            int n;
            int n2;
            if (((Player)object4).isDead() || (object2 = (RewardInfo)cloneable.get(object4)) == null) continue;
            object = ((Player)object4).getParty();
            int n3 = this.getMaxHp();
            var8_14[0] = 0.0;
            var8_14[1] = 0.0;
            if (object == null) {
                n2 = Math.min(((RewardInfo)object2)._dmg, n3);
                if (n2 > 0) {
                    void var8_14;
                    if (this.isInRangeZ((GameObject)object4, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE)) {
                        double[] dArray2 = this.a(((Player)object4).getLevel(), n2);
                    }
                    var8_14[0] = this.a(player, (double)var8_14[0]);
                    if (!((Player)object4).isCursedWeaponEquipped() && var8_14[0] > 0.0 && ((Player)object4).getKarma() > 0) {
                        n = Formulas.calculateKarmaLost((Player)object4, (long)var8_14[0]);
                        ((Player)object4).decreaseKarma(Math.max(n, 0));
                    }
                    ((Player)object4).addExpAndCheckBonus(this, (long)var8_14[0], (long)var8_14[1], 1.0);
                }
                cloneable.remove(object4);
                continue;
            }
            n2 = 0;
            n = 1;
            ArrayList<Player> arrayList = new ArrayList<Player>();
            for (Player player4 : ((Party)object).getPartyMembers()) {
                RewardInfo rewardInfo = (RewardInfo)cloneable.remove(player4);
                if (player4.isDead() || !this.isInRangeZ(player4, (long)Config.ALT_PARTY_DISTRIBUTION_RANGE)) continue;
                if (rewardInfo != null) {
                    n2 += rewardInfo._dmg;
                }
                arrayList.add(player4);
                if (player4.getLevel() <= n) continue;
                n = player4.getLevel();
            }
            if ((n2 = Math.min(n2, n3)) <= 0) continue;
            double[] dArray3 = this.a(n, n2);
            double d = (double)n2 / (double)n3;
            dArray3[0] = dArray3[0] * d;
            dArray3[1] = dArray3[1] * d;
            dArray3[0] = this.a(player, dArray3[0]);
            ((Party)object).distributeXpAndSp(dArray3[0], dArray3[1], arrayList, creature, this);
        }
        CursedWeaponsManager.getInstance().dropAttackable(this, player);
        if (creature2 == null || !creature2.isPlayable()) {
            return;
        }
        int n = creature2.getLevel() - this.getLevel();
        for (Map.Entry<RewardType, RewardList> entry : this.getTemplate().getRewards().entrySet()) {
            this.rollRewards(entry, creature, creature2);
        }
        if (this.getChampion() > 0 && Config.ALT_CHAMPION_DROP_ITEM_IDS != null && Config.ALT_CHAMPION_DROP_ITEM_IDS.get(this.getChampion()).length > 0 && Math.abs(this.getLevel() - creature2.getLevel()) < Config.ALT_CHAMPION_DROP_LEVEL_DIFF) {
            void var10_27;
            boolean bl = false;
            while (var10_27 < Config.ALT_CHAMPION_DROP_ITEM_IDS.get(this.getChampion()).length) {
                if (Config.ALT_CHAMPION_DROP_ITEM_IDS.get(this.getChampion())[var10_27] > 0 && Rnd.chance(Config.ALT_CHAMPION_DROP_CHANCES.get(this.getChampion())[var10_27])) {
                    this.dropItem(creature2.getPlayer(), Config.ALT_CHAMPION_DROP_ITEM_IDS.get(this.getChampion())[var10_27], Config.ALT_CHAMPION_DROP_COUNTS.get(this.getChampion())[var10_27]);
                }
                ++var10_27;
            }
        }
        if (n <= 9) {
            OneDayRewardHolder.getInstance().fireRequirementsWithDistribution(player, this, KillMobRequirement.class);
        }
    }

    @Override
    public void onRandomAnimation() {
        if (System.currentTimeMillis() - this._lastSocialAction > 10000L) {
            this.broadcastPacket(new SocialAction(this.getObjectId(), 1));
            this._lastSocialAction = System.currentTimeMillis();
        }
    }

    @Override
    public void startRandomAnimation() {
    }

    @Override
    public int getKarma() {
        return 0;
    }

    public void addAbsorber(Player player) {
        if (player == null) {
            return;
        }
        if (this.getCurrentHpPercents() > 50.0) {
            return;
        }
        this.z.lock();
        try {
            if (this.c == null) {
                this.c = new TIntHashSet();
            }
            this.c.add(player.getObjectId());
        }
        finally {
            this.z.unlock();
        }
    }

    public boolean isAbsorbed(Player player) {
        this.z.lock();
        try {
            if (this.c == null) {
                boolean bl = false;
                return bl;
            }
            if (!this.c.contains(player.getObjectId())) {
                boolean bl = false;
                return bl;
            }
        }
        finally {
            this.z.unlock();
        }
        return true;
    }

    public void clearAbsorbers() {
        this.z.lock();
        try {
            if (this.c != null) {
                this.c.clear();
            }
        }
        finally {
            this.z.unlock();
        }
    }

    public RewardItem takeHarvest() {
        this.y.lock();
        try {
            RewardItem rewardItem = this.a;
            this.clearHarvest();
            RewardItem rewardItem2 = rewardItem;
            return rewardItem2;
        }
        finally {
            this.y.unlock();
        }
    }

    public void clearHarvest() {
        this.y.lock();
        try {
            this.a = null;
            this.dx = false;
            this.mq = 0;
            this.dw = false;
        }
        finally {
            this.y.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean setSeeded(Player player, int n, boolean bl) {
        this.y.lock();
        try {
            if (this.isSeeded()) {
                boolean bl2 = false;
                return bl2;
            }
            this.dw = true;
            this.dx = bl;
            this.mq = player.getObjectId();
            this.a = new RewardItem(Manor.getInstance().getCropType(n));
            if (this.getTemplate().rateHp > 1.0) {
                this.a.count = Rnd.get(Math.round(this.getTemplate().rateHp), Math.round(1.5 * this.getTemplate().rateHp));
            }
        }
        finally {
            this.y.unlock();
        }
        return true;
    }

    public boolean isSeeded(Player player) {
        return this.isSeeded() && this.mq == player.getObjectId() && this.getDeadTime() < 20000L;
    }

    public boolean isSeeded() {
        return this.dw;
    }

    public boolean isSpoiled() {
        return this.dy;
    }

    public boolean isSpoiled(Player player) {
        if (!this.isSpoiled()) {
            return false;
        }
        if (player.getObjectId() == this.ms && this.getDeadTime() < 20000L) {
            return true;
        }
        if (player.isInParty()) {
            for (Player player2 : player.getParty().getPartyMembers()) {
                if (player2.getObjectId() != this.ms || !(this.getDistance(player2) < (double)Config.ALT_PARTY_DISTRIBUTION_RANGE)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean setSpoiled(Player player) {
        this.A.lock();
        try {
            if (this.isSpoiled()) {
                boolean bl = false;
                return bl;
            }
            this.dy = true;
            this.ms = player.getObjectId();
        }
        finally {
            this.A.unlock();
        }
        return true;
    }

    public boolean isSweepActive() {
        this.A.lock();
        try {
            boolean bl = this.bI != null && this.bI.size() > 0;
            return bl;
        }
        finally {
            this.A.unlock();
        }
    }

    public List<RewardItem> takeSweep() {
        this.A.lock();
        try {
            List<RewardItem> list = this.bI;
            this.clearSweep();
            List<RewardItem> list2 = list;
            return list2;
        }
        finally {
            this.A.unlock();
        }
    }

    public void clearSweep() {
        this.A.lock();
        try {
            this.dy = false;
            this.ms = 0;
            this.bI = null;
        }
        finally {
            this.A.unlock();
        }
    }

    public void rollRewards(Map.Entry<RewardType, RewardList> entry, Creature creature, Creature creature2) {
        RewardType rewardType = entry.getKey();
        RewardList rewardList = entry.getValue();
        if (rewardType == RewardType.SWEEP && !this.isSpoiled()) {
            return;
        }
        Creature creature3 = rewardType == RewardType.SWEEP ? creature : creature2;
        Player player = creature3.getPlayer();
        if (player == null) {
            return;
        }
        int n = this.calculateLevelDiffForDrop(creature2.getLevel());
        double d = this.calcStat(Stats.ITEM_REWARD_MULTIPLIER, 1.0, creature3, null);
        List<RewardItem> list = rewardList.roll(player, d *= Experience.penaltyModifier(n, 9.0), this instanceof RaidBossInstance);
        switch (rewardType) {
            case SWEEP: {
                this.bI = list;
                break;
            }
            default: {
                for (RewardItem rewardItem : list) {
                    if (this.isSeeded() && !this.dx && !rewardItem.isAdena && !rewardItem.isSealStone) continue;
                    if (rewardItem.enchantMin > 0 && rewardItem.enchantMin < rewardItem.enchantMax) {
                        this.dropItem(player, rewardItem.itemId, rewardItem.count, Rnd.get(rewardItem.enchantMin, rewardItem.enchantMax));
                        continue;
                    }
                    this.dropItem(player, rewardItem.itemId, rewardItem.count, rewardItem.enchantMin);
                }
            }
        }
    }

    private double[] a(int n, long l) {
        int n2 = n - this.getLevel();
        double d = this.getExpReward() * l / (long)this.getMaxHp();
        double d2 = this.getSpReward() * l / (long)this.getMaxHp();
        if (Config.EXP_SP_DIFF_LIMIT != 0 && Math.abs(n2) > Config.EXP_SP_DIFF_LIMIT) {
            d = 0.0;
            d2 = 0.0;
        }
        if (n2 > Config.THRESHOLD_LEVEL_DIFF) {
            double d3 = Math.pow(0.83, n2 - 5);
            d *= d3;
            d2 *= d3;
        }
        d = Math.max(0.0, d);
        d2 = Math.max(0.0, d2);
        return new double[]{d, d2};
    }

    private double a(Player player, double d) {
        if (d > 0.0 && player.getObjectId() == this.mr) {
            int n = this.calculateOverhitExp(d * Config.RATE_OVERHIT);
            player.sendPacket((IStaticPacket)SystemMsg.OVERHIT);
            player.sendPacket((IStaticPacket)new ExMagicAttackInfo(player.getObjectId(), this.getObjectId(), 3));
            d += (double)n;
        }
        return d;
    }

    @Override
    public void setOverhitAttacker(Creature creature) {
        this.mr = creature == null ? 0 : creature.getObjectId();
    }

    public double getOverhitDamage() {
        return this.G;
    }

    @Override
    public void setOverhitDamage(double d) {
        this.G = d;
    }

    public int calculateOverhitExp(double d) {
        double d2 = this.getOverhitDamage() * 100.0 / (double)this.getMaxHp();
        if (d2 > 25.0) {
            d2 = 25.0;
        }
        double d3 = d2 / 100.0 * d;
        this.setOverhitAttacker(null);
        this.setOverhitDamage(0.0);
        return (int)Math.round(d3);
    }

    @Override
    public boolean isAggressive() {
        return (Config.ALT_CHAMPION_CAN_BE_AGGRO || this.getChampion() == 0) && super.isAggressive();
    }

    @Override
    public Faction getFaction() {
        return Config.ALT_CHAMPION_CAN_BE_SOCIAL || this.getChampion() == 0 ? super.getFaction() : Faction.NONE;
    }

    @Override
    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        this.a(creature, d);
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    private void a(Creature creature, double d) {
        Skill skill;
        double d2;
        if ((double)this.getTemplate().baseAtkRange > 200.0 || this.getLevel() < 20 || this.getLevel() > 78 || creature.getLevel() - this.getLevel() > 9 || this.getLevel() - creature.getLevel() > 9) {
            return;
        }
        if (this.isMinion() || this.getMinionList() != null || this.isRaid() || this instanceof ReflectionBossInstance || this instanceof ChestInstance || this.getChampion() > 0) {
            return;
        }
        int n = 5044;
        int n2 = 1;
        if (this.getLevel() >= 41 || this.getLevel() <= 60) {
            n2 = 2;
        } else if (this.getLevel() > 60) {
            n2 = 3;
        }
        double d3 = this.getDistance(creature);
        if (d3 <= 50.0) {
            if (this.getEffectList() != null && this.getEffectList().getEffectsBySkillId(n) != null) {
                for (Effect effect : this.getEffectList().getEffectsBySkillId(n)) {
                    effect.exit();
                }
            }
        } else if (d3 >= 200.0 && Rnd.chance(d2 = 30.0 / ((double)this.getMaxHp() / d)) && (skill = SkillTable.getInstance().getInfo(n, n2)) != null) {
            skill.getEffects(this, this, false, false);
        }
    }

    @Override
    public boolean isMonster() {
        return true;
    }

    @Override
    public Clan getClan() {
        return null;
    }

    @Override
    public boolean isInvul() {
        return this._isInvul;
    }

    public class MinionMaintainTask
    extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            if (MonsterInstance.this.isDead()) {
                return;
            }
            MonsterInstance.this.getMinionList().spawnMinions();
        }
    }

    protected static final class RewardInfo {
        protected Creature _attacker;
        protected int _dmg = 0;

        public RewardInfo(Creature creature, int n) {
            this._attacker = creature;
            this._dmg = n;
        }

        public void addDamage(int n) {
            if (n < 0) {
                n = 0;
            }
            this._dmg += n;
        }

        public int hashCode() {
            return this._attacker.getObjectId();
        }
    }
}
