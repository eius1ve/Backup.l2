/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.templates.npc;

import gnu.trove.TIntObjectHashMap;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.util.TroveUtils;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.Skill;
import l2.gameserver.model.TeleportLocation;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.model.instances.ReflectionBossInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestEventType;
import l2.gameserver.model.reward.RewardList;
import l2.gameserver.model.reward.RewardType;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.templates.CharTemplate;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.npc.AbsorbInfo;
import l2.gameserver.templates.npc.Faction;
import l2.gameserver.templates.npc.MinionData;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class NpcTemplate
extends CharTemplate {
    private static final Logger dD = LoggerFactory.getLogger(NpcTemplate.class);
    public static final Constructor<NpcInstance> DEFAULT_TYPE_CONSTRUCTOR = NpcInstance.class.getConstructors()[0];
    public static final Constructor<CharacterAI> DEFAULT_AI_CONSTRUCTOR = CharacterAI.class.getConstructors()[0];
    public final int npcId;
    public final String name;
    public final String title;
    public final int level;
    public final long rewardExp;
    public final int rewardSp;
    public final int rewardCRP;
    public final int rewardRp;
    public final int aggroRange;
    public final int rhand;
    public final int lhand;
    public final double rateHp;
    private Faction a;
    public final String jClass;
    public final int displayId;
    public final ShotsType shots;
    public boolean isRaid = false;
    private final StatsSet h;
    private int race = 0;
    private final int Hd;
    private Map<RewardType, RewardList> _rewards;
    private TIntObjectHashMap<TeleportLocation[]> A;
    private List<MinionData> dB;
    private List<AbsorbInfo> dC;
    private List<ClassId> dD;
    private Map<QuestEventType, Quest[]> bX;
    private TIntObjectHashMap<Skill> B;
    private Skill[] e;
    private Skill[] _dotSkills;
    private Skill[] _debuffSkills;
    private Skill[] _buffSkills;
    private Skill[] _stunSkills;
    private Skill[] _healSkills;
    private Class<NpcInstance> d = NpcInstance.class;
    private Constructor<NpcInstance> b;
    private Class<CharacterAI> c = CharacterAI.class;
    private Constructor<CharacterAI> a = Faction.NONE;
    private String gz;

    public NpcTemplate(StatsSet statsSet) {
        super(statsSet);
        this._rewards = Collections.emptyMap();
        this.A = TroveUtils.emptyIntObjectMap();
        this.dB = Collections.emptyList();
        this.dC = Collections.emptyList();
        this.dD = Collections.emptyList();
        this.bX = Collections.emptyMap();
        this.B = TroveUtils.emptyIntObjectMap();
        this.e = Skill.EMPTY_ARRAY;
        this._dotSkills = Skill.EMPTY_ARRAY;
        this._debuffSkills = Skill.EMPTY_ARRAY;
        this._buffSkills = Skill.EMPTY_ARRAY;
        this._stunSkills = Skill.EMPTY_ARRAY;
        this._healSkills = Skill.EMPTY_ARRAY;
        this.b = DEFAULT_TYPE_CONSTRUCTOR;
        this.a = DEFAULT_AI_CONSTRUCTOR;
        this.npcId = statsSet.getInteger("npcId");
        this.displayId = statsSet.getInteger("displayId");
        this.name = statsSet.getString("name");
        this.title = statsSet.getString("title");
        this.level = statsSet.getInteger("level");
        this.rewardExp = statsSet.getLong("rewardExp");
        this.rewardSp = statsSet.getInteger("rewardSp");
        this.rewardCRP = statsSet.getInteger("rewardCrp", 0);
        this.rewardRp = statsSet.getInteger("rewardRp");
        this.aggroRange = statsSet.getInteger("aggroRange");
        this.rhand = statsSet.getInteger("rhand", 0);
        this.lhand = statsSet.getInteger("lhand", 0);
        this.rateHp = statsSet.getDouble("baseHpRate");
        this.jClass = statsSet.getString("texture", null);
        this.gz = statsSet.getString("htm_root", null);
        this.shots = statsSet.getEnum("shots", ShotsType.class, ShotsType.NONE);
        this.Hd = statsSet.getInteger("castle_id", 0);
        this.h = (StatsSet)statsSet.getObject("aiParams", StatsSet.EMPTY);
        this.setType(statsSet.getString("type", null));
        this.g(statsSet.getString("ai_type", null));
    }

    public Class<? extends NpcInstance> getInstanceClass() {
        return this.d;
    }

    public Constructor<? extends NpcInstance> getInstanceConstructor() {
        return this.b;
    }

    public boolean isInstanceOf(Class<?> clazz) {
        return clazz.isAssignableFrom(this.d);
    }

    public NpcInstance getNewInstance() {
        try {
            return this.b.newInstance(IdFactory.getInstance().getNextId(), this);
        }
        catch (Exception exception) {
            dD.error("Unable to create instance of NPC " + this.npcId, (Throwable)exception);
            return null;
        }
    }

    public CharacterAI getNewAI(NpcInstance npcInstance) {
        try {
            return (CharacterAI)((Constructor)((Object)this.a)).newInstance(npcInstance);
        }
        catch (Exception exception) {
            dD.error("Unable to create ai of NPC " + this.npcId, (Throwable)exception);
            return new CharacterAI(npcInstance);
        }
    }

    private void setType(String string) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("l2.gameserver.model.instances." + string + "Instance");
        }
        catch (ClassNotFoundException classNotFoundException) {
            clazz = Scripts.getInstance().getClasses().get("npc.model." + string + "Instance");
        }
        if (clazz == null) {
            dD.error("Not found type class for type: " + string + ". NpcId: " + this.npcId);
        } else {
            this.d = clazz;
            this.b = this.d.getConstructors()[0];
        }
        if (this.d.isAnnotationPresent(Deprecated.class)) {
            dD.error("Npc type: " + string + ", is deprecated. NpcId: " + this.npcId);
        }
        this.isRaid = this.isInstanceOf(RaidBossInstance.class) && !this.isInstanceOf(ReflectionBossInstance.class);
    }

    private void g(String string) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("l2.gameserver.ai." + string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            clazz = Scripts.getInstance().getClasses().get("ai." + string);
        }
        if (clazz == null) {
            dD.error("Not found ai class for ai: " + string + ". NpcId: " + this.npcId);
        } else {
            this.c = clazz;
            this.a = this.c.getConstructors()[0];
        }
        if (this.c.isAnnotationPresent(Deprecated.class)) {
            dD.error("Ai type: " + string + ", is deprecated. NpcId: " + this.npcId);
        }
    }

    public void addTeachInfo(ClassId classId) {
        if (this.dD.isEmpty()) {
            this.dD = new ArrayList(1);
        }
        this.dD.add(classId);
    }

    public List<ClassId> getTeachInfo() {
        return this.dD;
    }

    public boolean canTeach(ClassId classId) {
        return this.dD.contains((Object)classId);
    }

    public void addTeleportList(int n, TeleportLocation[] teleportLocationArray) {
        if (this.A.isEmpty()) {
            this.A = new TIntObjectHashMap(1);
        }
        this.A.put(n, (Object)teleportLocationArray);
    }

    public TeleportLocation[] getTeleportList(int n) {
        return (TeleportLocation[])this.A.get(n);
    }

    public TIntObjectHashMap<TeleportLocation[]> getTeleportList() {
        return this.A;
    }

    public void putRewardList(RewardType rewardType, RewardList rewardList) {
        if (this._rewards.isEmpty()) {
            this._rewards = new HashMap<RewardType, RewardList>(RewardType.values().length);
        }
        this._rewards.put(rewardType, rewardList);
    }

    public RewardList getRewardList(RewardType rewardType) {
        return this._rewards.get((Object)rewardType);
    }

    public Map<RewardType, RewardList> getRewards() {
        return this._rewards;
    }

    public void addAbsorbInfo(AbsorbInfo absorbInfo) {
        if (this.dC.isEmpty()) {
            this.dC = new ArrayList<AbsorbInfo>(1);
        }
        this.dC.add(absorbInfo);
    }

    public void addMinion(MinionData minionData) {
        if (this.dB.isEmpty()) {
            this.dB = new ArrayList<MinionData>(1);
        }
        this.dB.add(minionData);
    }

    public void setFaction(Faction faction) {
        this.a = faction;
    }

    public Faction getFaction() {
        return this.a;
    }

    public void addSkill(Skill skill) {
        if (this.B.isEmpty()) {
            this.B = new TIntObjectHashMap();
        }
        this.B.put(skill.getId(), (Object)skill);
        if (skill.isNotUsedByAI() || skill.getTargetType() == Skill.SkillTargetType.TARGET_NONE || skill.getSkillType() == Skill.SkillType.NOTDONE || !skill.isActive()) {
            return;
        }
        switch (skill.getSkillType()) {
            case PDAM: 
            case MANADAM: 
            case MDAM: 
            case DRAIN: 
            case DRAIN_SOUL: {
                boolean bl = false;
                if (skill.hasEffects()) {
                    block12: for (EffectTemplate effectTemplate : skill.getEffectTemplates()) {
                        switch (effectTemplate.getEffectType()) {
                            case Stun: {
                                this._stunSkills = (Skill[])ArrayUtils.add((Object[])this._stunSkills, (Object)skill);
                                bl = true;
                                continue block12;
                            }
                            case DamOverTime: 
                            case DamOverTimeLethal: 
                            case ManaDamOverTime: 
                            case LDManaDamOverTime: {
                                this._dotSkills = (Skill[])ArrayUtils.add((Object[])this._dotSkills, (Object)skill);
                                bl = true;
                            }
                        }
                    }
                }
                if (bl) break;
                this.e = (Skill[])ArrayUtils.add((Object[])this.e, (Object)skill);
                break;
            }
            case DOT: 
            case MDOT: 
            case POISON: 
            case BLEED: {
                this._dotSkills = (Skill[])ArrayUtils.add((Object[])this._dotSkills, (Object)skill);
                break;
            }
            case DEBUFF: 
            case SLEEP: 
            case ROOT: 
            case PARALYZE: 
            case MUTE: 
            case TELEPORT_NPC: 
            case AGGRESSION: {
                this._debuffSkills = (Skill[])ArrayUtils.add((Object[])this._debuffSkills, (Object)skill);
                break;
            }
            case BUFF: {
                this._buffSkills = (Skill[])ArrayUtils.add((Object[])this._buffSkills, (Object)skill);
                break;
            }
            case STUN: {
                this._stunSkills = (Skill[])ArrayUtils.add((Object[])this._stunSkills, (Object)skill);
                break;
            }
            case HEAL: 
            case HEAL_PERCENT: 
            case HOT: {
                this._healSkills = (Skill[])ArrayUtils.add((Object[])this._healSkills, (Object)skill);
                break;
            }
        }
    }

    public Skill[] getDamageSkills() {
        return this.e;
    }

    public Skill[] getDotSkills() {
        return this._dotSkills;
    }

    public Skill[] getDebuffSkills() {
        return this._debuffSkills;
    }

    public Skill[] getBuffSkills() {
        return this._buffSkills;
    }

    public Skill[] getStunSkills() {
        return this._stunSkills;
    }

    public Skill[] getHealSkills() {
        return this._healSkills;
    }

    public List<MinionData> getMinionData() {
        return this.dB;
    }

    public TIntObjectHashMap<Skill> getSkills() {
        return this.B;
    }

    public void addQuestEvent(QuestEventType questEventType, Quest quest) {
        if (this.bX.isEmpty()) {
            this.bX = new HashMap<QuestEventType, Quest[]>();
        }
        if (this.bX.get((Object)questEventType) == null) {
            this.bX.put(questEventType, new Quest[]{quest});
        } else {
            Quest[] questArray = this.bX.get((Object)questEventType);
            int n = questArray.length;
            Quest[] questArray2 = new Quest[n + 1];
            for (int i = 0; i < n; ++i) {
                if (questArray[i].getName().equals(quest.getName())) {
                    questArray[i] = quest;
                    return;
                }
                questArray2[i] = questArray[i];
            }
            questArray2[n] = quest;
            this.bX.put(questEventType, questArray2);
        }
    }

    public Quest[] getEventQuests(QuestEventType questEventType) {
        return this.bX.get((Object)questEventType);
    }

    public int getRace() {
        return this.race;
    }

    public void setRace(int n) {
        this.race = n;
    }

    public boolean isUndead() {
        return this.race == 1;
    }

    public String toString() {
        return "Npc template " + this.name + "[" + this.npcId + "]";
    }

    @Override
    public int getNpcId() {
        return this.npcId;
    }

    public String getName() {
        return this.name;
    }

    public final String getJClass() {
        return this.jClass;
    }

    public final StatsSet getAIParams() {
        return this.h;
    }

    public List<AbsorbInfo> getAbsorbInfo() {
        return this.dC;
    }

    public int getCastleId() {
        return this.Hd;
    }

    public Map<QuestEventType, Quest[]> getQuestEvents() {
        return this.bX;
    }

    public String getHtmRoot() {
        return this.gz;
    }

    public static final class ShotsType
    extends Enum<ShotsType> {
        public static final /* enum */ ShotsType NONE = new ShotsType();
        public static final /* enum */ ShotsType SOUL = new ShotsType();
        public static final /* enum */ ShotsType SPIRIT = new ShotsType();
        public static final /* enum */ ShotsType BSPIRIT = new ShotsType();
        public static final /* enum */ ShotsType SOUL_SPIRIT = new ShotsType();
        public static final /* enum */ ShotsType SOUL_BSPIRIT = new ShotsType();
        private static final /* synthetic */ ShotsType[] a;

        public static ShotsType[] values() {
            return (ShotsType[])a.clone();
        }

        public static ShotsType valueOf(String string) {
            return Enum.valueOf(ShotsType.class, string);
        }

        private static /* synthetic */ ShotsType[] a() {
            return new ShotsType[]{NONE, SOUL, SPIRIT, BSPIRIT, SOUL_SPIRIT, SOUL_BSPIRIT};
        }

        static {
            a = ShotsType.a();
        }
    }
}
