/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import java.util.NoSuchElementException;
import l2.gameserver.Config;

public final class Stats
extends Enum<Stats> {
    public static final /* enum */ Stats MAX_HP = new Stats("maxHp", 0.0, Double.POSITIVE_INFINITY, 1.0);
    public static final /* enum */ Stats MAX_MP = new Stats("maxMp", 0.0, Double.POSITIVE_INFINITY, 1.0);
    public static final /* enum */ Stats MAX_CP = new Stats("maxCp", 0.0, Double.POSITIVE_INFINITY, 1.0);
    public static final /* enum */ Stats REGENERATE_HP_RATE = new Stats("regHp");
    public static final /* enum */ Stats REGENERATE_CP_RATE = new Stats("regCp");
    public static final /* enum */ Stats REGENERATE_MP_RATE = new Stats("regMp");
    public static final /* enum */ Stats HP_LIMIT = new Stats("hpLimit", 1.0, 100.0, 100.0);
    public static final /* enum */ Stats MP_LIMIT = new Stats("mpLimit", 1.0, 100.0, 100.0);
    public static final /* enum */ Stats CP_LIMIT = new Stats("cpLimit", 1.0, 100.0, 100.0);
    public static final /* enum */ Stats RUN_SPEED = new Stats("runSpd");
    public static final /* enum */ Stats POWER_DEFENCE = new Stats("pDef");
    public static final /* enum */ Stats MAGIC_DEFENCE = new Stats("mDef");
    public static final /* enum */ Stats POWER_ATTACK = new Stats("pAtk");
    public static final /* enum */ Stats MAGIC_ATTACK = new Stats("mAtk");
    public static final /* enum */ Stats POWER_ATTACK_SPEED = new Stats("pAtkSpd");
    public static final /* enum */ Stats MAGIC_ATTACK_SPEED = new Stats("mAtkSpd");
    public static final /* enum */ Stats MAGIC_REUSE_RATE = new Stats("mReuse");
    public static final /* enum */ Stats PHYSIC_REUSE_RATE = new Stats("pReuse");
    public static final /* enum */ Stats MUSIC_REUSE_RATE = new Stats("musicReuse");
    public static final /* enum */ Stats ATK_REUSE = new Stats("atkReuse");
    public static final /* enum */ Stats ATK_BASE = new Stats("atkBaseSpeed");
    public static final /* enum */ Stats CRITICAL_DAMAGE = new Stats("cAtk", 0.0, Double.POSITIVE_INFINITY, 100.0);
    public static final /* enum */ Stats CRITICAL_DAMAGE_STATIC = new Stats("cAtkStatic");
    public static final /* enum */ Stats EVASION_RATE = new Stats("rEvas");
    public static final /* enum */ Stats ACCURACY_COMBAT = new Stats("accCombat");
    public static final /* enum */ Stats CRITICAL_BASE = new Stats("baseCrit", 0.0, Double.POSITIVE_INFINITY, 100.0);
    public static final /* enum */ Stats CRITICAL_RATE = new Stats("rCrit", 0.0, Double.POSITIVE_INFINITY, 100.0);
    public static final /* enum */ Stats MCRITICAL_RATE = new Stats("mCritRate", 0.0, Double.POSITIVE_INFINITY, Config.MCRITICAL_BASE_STAT);
    public static final /* enum */ Stats MCRITICAL_DAMAGE = new Stats("mCritDamage", 0.0, 10.0, 2.5);
    public static final /* enum */ Stats MCRITICAL_DAMAGE_RESIST = new Stats("mCritDamageResist");
    public static final /* enum */ Stats PHYSICAL_DAMAGE = new Stats("physDamage");
    public static final /* enum */ Stats MAGIC_DAMAGE = new Stats("magicDamage");
    public static final /* enum */ Stats CAST_INTERRUPT = new Stats("concentration", 0.0, 100.0);
    public static final /* enum */ Stats SHIELD_DEFENCE = new Stats("sDef");
    public static final /* enum */ Stats SHIELD_RATE = new Stats("rShld", 0.0, 90.0);
    public static final /* enum */ Stats SHIELD_ANGLE = new Stats("shldAngle", 0.0, 360.0, 60.0);
    public static final /* enum */ Stats POWER_ATTACK_RANGE = new Stats("pAtkRange", 0.0, 1500.0);
    public static final /* enum */ Stats MAGIC_ATTACK_RANGE = new Stats("mAtkRange", 0.0, 1500.0);
    public static final /* enum */ Stats POLE_ATTACK_ANGLE = new Stats("poleAngle", 0.0, Config.POLE_ATTACK_ANGLE);
    public static final /* enum */ Stats POLE_TARGET_COUNT = new Stats("poleTargetCount");
    public static final /* enum */ Stats STAT_STR = new Stats("STR", 1.0, 99.0);
    public static final /* enum */ Stats STAT_CON = new Stats("CON", 1.0, 99.0);
    public static final /* enum */ Stats STAT_DEX = new Stats("DEX", 1.0, 99.0);
    public static final /* enum */ Stats STAT_INT = new Stats("INT", 1.0, 99.0);
    public static final /* enum */ Stats STAT_WIT = new Stats("WIT", 1.0, 99.0);
    public static final /* enum */ Stats STAT_MEN = new Stats("MEN", 1.0, 99.0);
    public static final /* enum */ Stats BREATH = new Stats("breath");
    public static final /* enum */ Stats FALL = new Stats("fall");
    public static final /* enum */ Stats EXP_LOST = new Stats("expLost");
    public static final /* enum */ Stats BLEED_RESIST = new Stats("bleedResist", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats POISON_RESIST = new Stats("poisonResist", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats STUN_RESIST = new Stats("stunResist", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats ROOT_RESIST = new Stats("rootResist", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats MENTAL_RESIST = new Stats("mentalResist", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats SLEEP_RESIST = new Stats("sleepResist", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats PARALYZE_RESIST = new Stats("paralyzeResist", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats CANCEL_RESIST = new Stats("cancelResist", -200.0, 300.0);
    public static final /* enum */ Stats DEBUFF_RESIST = new Stats("debuffResist", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats MAGIC_RESIST = new Stats("magicResist", -200.0, 300.0);
    public static final /* enum */ Stats BLEED_POWER = new Stats("bleedPower", -200.0, 200.0);
    public static final /* enum */ Stats POISON_POWER = new Stats("poisonPower", -200.0, 200.0);
    public static final /* enum */ Stats STUN_POWER = new Stats("stunPower", -200.0, 200.0);
    public static final /* enum */ Stats ROOT_POWER = new Stats("rootPower", -200.0, 200.0);
    public static final /* enum */ Stats MENTAL_POWER = new Stats("mentalPower", -200.0, 200.0);
    public static final /* enum */ Stats SLEEP_POWER = new Stats("sleepPower", -200.0, 200.0);
    public static final /* enum */ Stats PARALYZE_POWER = new Stats("paralyzePower", -200.0, 200.0);
    public static final /* enum */ Stats CANCEL_POWER = new Stats("cancelPower", -200.0, 200.0);
    public static final /* enum */ Stats DEBUFF_POWER = new Stats("debuffPower", -200.0, 200.0);
    public static final /* enum */ Stats MAGIC_POWER = new Stats("magicPower", -200.0, 200.0);
    public static final /* enum */ Stats FATALBLOW_RATE = new Stats("blowRate", 0.0, Config.BLOW_RATE_CHANCE_LIMIT, 1.0);
    public static final /* enum */ Stats SKILL_CRIT_CHANCE_MOD = new Stats("SkillCritChanceMod", 10.0, 190.0, 100.0);
    public static final /* enum */ Stats DEATH_VULNERABILITY = new Stats("deathVuln", 10.0, 190.0, 100.0);
    public static final /* enum */ Stats CRIT_DAMAGE_RECEPTIVE = new Stats("critDamRcpt", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats CRIT_CHANCE_RECEPTIVE = new Stats("critChanceRcpt", 10.0, 190.0, 100.0);
    public static final /* enum */ Stats DEFENCE_FIRE = new Stats("defenceFire", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats DEFENCE_WATER = new Stats("defenceWater", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats DEFENCE_WIND = new Stats("defenceWind", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats DEFENCE_EARTH = new Stats("defenceEarth", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats DEFENCE_HOLY = new Stats("defenceHoly", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats DEFENCE_UNHOLY = new Stats("defenceUnholy", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats ATTACK_FIRE = new Stats("attackFire", 0.0, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats ATTACK_WATER = new Stats("attackWater", 0.0, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats ATTACK_WIND = new Stats("attackWind", 0.0, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats ATTACK_EARTH = new Stats("attackEarth", 0.0, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats ATTACK_HOLY = new Stats("attackHoly", 0.0, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats ATTACK_UNHOLY = new Stats("attackUnholy", 0.0, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats SWORD_WPN_VULNERABILITY = new Stats("swordWpnVuln", 10.0, 200.0, 100.0);
    public static final /* enum */ Stats DUAL_WPN_VULNERABILITY = new Stats("dualWpnVuln", 10.0, 200.0, 100.0);
    public static final /* enum */ Stats BLUNT_WPN_VULNERABILITY = new Stats("bluntWpnVuln", 10.0, 200.0, 100.0);
    public static final /* enum */ Stats DAGGER_WPN_VULNERABILITY = new Stats("daggerWpnVuln", 10.0, 200.0, 100.0);
    public static final /* enum */ Stats BOW_WPN_VULNERABILITY = new Stats("bowWpnVuln", 10.0, 200.0, 100.0);
    public static final /* enum */ Stats CROSSBOW_WPN_VULNERABILITY = new Stats("crossbowWpnVuln", 10.0, 200.0, 100.0);
    public static final /* enum */ Stats POLE_WPN_VULNERABILITY = new Stats("poleWpnVuln", 10.0, 200.0, 100.0);
    public static final /* enum */ Stats FIST_WPN_VULNERABILITY = new Stats("fistWpnVuln", 10.0, 200.0, 100.0);
    public static final /* enum */ Stats ABSORB_DAMAGE_PERCENT = new Stats("absorbDam", 0.0, 100.0);
    public static final /* enum */ Stats ABSORB_MANA_DAMAGE_PERCENT = new Stats("absorbDamToMp");
    public static final /* enum */ Stats ABSORB_MANA_DAMAGE_CHANCE = new Stats("absorbDamToMpChance", 0.0, 100.0);
    public static final /* enum */ Stats TRANSFER_TO_SUMMON_DAMAGE_PERCENT = new Stats("transferPetDam", 0.0, 100.0);
    public static final /* enum */ Stats TRANSFER_TO_EFFECTOR_DAMAGE_PERCENT = new Stats("transferToEffectorDam", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_AND_BLOCK_DAMAGE_CHANCE = new Stats("reflectAndBlockDam", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_AND_BLOCK_PSKILL_DAMAGE_CHANCE = new Stats("reflectAndBlockPSkillDam", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_AND_BLOCK_MSKILL_DAMAGE_CHANCE = new Stats("reflectAndBlockMSkillDam", 0.0, 100.0);
    public static final /* enum */ Stats BLOCK_DAMAGE_VALUE = new Stats("absorbDamageValue", 0.0, Double.POSITIVE_INFINITY);
    public static final /* enum */ Stats REFLECT_DAMAGE_PERCENT = new Stats("reflectDam", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_PSKILL_DAMAGE_PERCENT = new Stats("reflectPSkillDam", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_MSKILL_DAMAGE_PERCENT = new Stats("reflectMSkillDam", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_PHYSIC_SKILL = new Stats("reflectPhysicSkill", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_MAGIC_SKILL = new Stats("reflectMagicSkill", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_PHYSIC_DEBUFF = new Stats("reflectPhysicDebuff", 0.0, 100.0);
    public static final /* enum */ Stats REFLECT_MAGIC_DEBUFF = new Stats("reflectMagicDebuff", 0.0, 100.0);
    public static final /* enum */ Stats PSKILL_EVASION = new Stats("pSkillEvasion", 0.0, 100.0);
    public static final /* enum */ Stats COUNTER_ATTACK = new Stats("counterAttack", 0.0, 100.0);
    public static final /* enum */ Stats SKILL_POWER = new Stats("skillPower");
    public static final /* enum */ Stats PVP_PHYS_DMG_BONUS = new Stats("pvpPhysDmgBonus");
    public static final /* enum */ Stats PVP_PHYS_SKILL_DMG_BONUS = new Stats("pvpPhysSkillDmgBonus");
    public static final /* enum */ Stats PVP_MAGIC_SKILL_DMG_BONUS = new Stats("pvpMagicSkillDmgBonus");
    public static final /* enum */ Stats PVP_PHYS_DEFENCE_BONUS = new Stats("pvpPhysDefenceBonus");
    public static final /* enum */ Stats PVP_PHYS_SKILL_DEFENCE_BONUS = new Stats("pvpPhysSkillDefenceBonus");
    public static final /* enum */ Stats PVP_MAGIC_SKILL_DEFENCE_BONUS = new Stats("pvpMagicSkillDefenceBonus");
    public static final /* enum */ Stats PVE_PHYS_DMG_BONUS = new Stats("pvePhysDmgBonus");
    public static final /* enum */ Stats PVE_PHYS_SKILL_DMG_BONUS = new Stats("pvePhysSkillDmgBonus");
    public static final /* enum */ Stats PVE_MAGIC_SKILL_DMG_BONUS = new Stats("pveMagicSkillDmgBonus");
    public static final /* enum */ Stats PVE_PHYS_DEFENCE_BONUS = new Stats("pvePhysDefenceBonus");
    public static final /* enum */ Stats PVE_PHYS_SKILL_DEFENCE_BONUS = new Stats("pvePhysSkillDefenceBonus");
    public static final /* enum */ Stats PVE_MAGIC_SKILL_DEFENCE_BONUS = new Stats("pveMagicSkillDefenceBonus");
    public static final /* enum */ Stats SOUL_SHOT_BONUS = new Stats("ssBonus");
    public static final /* enum */ Stats SPIRIT_SHOT_BONUS = new Stats("spsBonus");
    public static final /* enum */ Stats BLESSED_SPIRIT_SHOT_BONUS = new Stats("bspsBonus");
    public static final /* enum */ Stats HEAL_EFFECTIVNESS = new Stats("hpEff", 0.0, 1000.0);
    public static final /* enum */ Stats MANAHEAL_EFFECTIVNESS = new Stats("mpEff", 0.0, 1000.0);
    public static final /* enum */ Stats CPHEAL_EFFECTIVNESS = new Stats("cpEff", 0.0, 1000.0);
    public static final /* enum */ Stats HEAL_POWER = new Stats("healPower");
    public static final /* enum */ Stats MP_MAGIC_SKILL_CONSUME = new Stats("mpConsum");
    public static final /* enum */ Stats MP_PHYSICAL_SKILL_CONSUME = new Stats("mpConsumePhysical");
    public static final /* enum */ Stats MP_DANCE_SKILL_CONSUME = new Stats("mpDanceConsume");
    public static final /* enum */ Stats MP_USE_BOW = new Stats("cheapShot");
    public static final /* enum */ Stats MP_USE_BOW_CHANCE = new Stats("cheapShotChance");
    public static final /* enum */ Stats SS_USE_BOW = new Stats("miser");
    public static final /* enum */ Stats SS_USE_BOW_CHANCE = new Stats("miserChance");
    public static final /* enum */ Stats SKILL_MASTERY = new Stats("skillMastery");
    public static final /* enum */ Stats MAX_LOAD = new Stats("maxLoad");
    public static final /* enum */ Stats MAX_NO_PENALTY_LOAD = new Stats("maxNoPenaltyLoad");
    public static final /* enum */ Stats INVENTORY_LIMIT = new Stats("inventoryLimit");
    public static final /* enum */ Stats STORAGE_LIMIT = new Stats("storageLimit");
    public static final /* enum */ Stats TRADE_LIMIT = new Stats("tradeLimit");
    public static final /* enum */ Stats COMMON_RECIPE_LIMIT = new Stats("CommonRecipeLimit");
    public static final /* enum */ Stats DWARVEN_RECIPE_LIMIT = new Stats("DwarvenRecipeLimit");
    public static final /* enum */ Stats BUFF_LIMIT = new Stats("buffLimit");
    public static final /* enum */ Stats CUBICS_LIMIT = new Stats("cubicsLimit", 0.0, 3.0, 1.0);
    public static final /* enum */ Stats CLOAK_SLOT = new Stats("openCloakSlot", 0.0, 1.0);
    public static final /* enum */ Stats TALISMANS_LIMIT = new Stats("talismansLimit", 0.0, 6.0);
    public static final /* enum */ Stats BROOCH_LIMIT = new Stats("broochLimit", 0.0, 6.0);
    public static final /* enum */ Stats AGATHION_CHARM_LIMIT = new Stats("agathionCharnLimit", 0.0, 5.0);
    public static final /* enum */ Stats GRADE_EXPERTISE_LEVEL = new Stats("gradeExpertiseLevel");
    public static final /* enum */ Stats EXP = new Stats("ExpMultiplier");
    public static final /* enum */ Stats SP = new Stats("SpMultiplier");
    public static final /* enum */ Stats RAID_EXP = new Stats("RaidExpMultiplier");
    public static final /* enum */ Stats RAID_SP = new Stats("RaidSpMultiplier");
    public static final /* enum */ Stats ITEM_REWARD_MULTIPLIER = new Stats("ItemDropMultiplier");
    public static final /* enum */ Stats ADENA_REWARD_MULTIPLIER = new Stats("AdenaDropMultiplier");
    public static final /* enum */ Stats SPOIL_REWARD_MULTIPLIER = new Stats("SpoilDropMultiplier");
    public static final /* enum */ Stats SEAL_STONES_REWARD_MULTIPLIER = new Stats("SealStonesMultiplier");
    public static final /* enum */ Stats QUEST_DROP_MULTIPLIER = new Stats("QuestDropMultiplier");
    public static final /* enum */ Stats ENCHANT_BONUS_MULTIPLIER = new Stats("EnchantBonusMultiplier");
    public static final /* enum */ Stats ENCHANT_SKILL_BONUS_MULTIPLIER = new Stats("EnchantSkillBonusMultiplier");
    public static final /* enum */ Stats AUTO_LOOT = new Stats("autoLootAll", 0.0, 1.0);
    public static final /* enum */ Stats AUTO_LOOT_HERB = new Stats("autoLootHerb", 0.0, 1.0);
    public static final /* enum */ Stats AUTO_LOOT_ADENA = new Stats("autoLootAdena", 0.0, 1.0);
    public static final /* enum */ Stats WORLD_CHAT_BONUSES = new Stats("worldChatBonus");
    public static final /* enum */ Stats VIP_SILVER_DROP_MULTIPLIER = new Stats("vipBonusesSilverDropChance");
    public static final /* enum */ Stats VIP_GOLD_DROP_MULTIPLIER = new Stats("vipBonusesGoldDropChance");
    public static final Stats[] VALUES;
    public static final int NUM_STATS;
    private final String fX;
    private double ar;
    private double aa;
    private double as;
    private static final /* synthetic */ Stats[] a;

    public static Stats[] values() {
        return (Stats[])a.clone();
    }

    public static Stats valueOf(String string) {
        return Enum.valueOf(Stats.class, string);
    }

    public String getValue() {
        return this.fX;
    }

    public double getInit() {
        return this.as;
    }

    private Stats(String string2) {
        this(string2, 0.0, Double.POSITIVE_INFINITY, 0.0);
    }

    private Stats(String string2, double d, double d2) {
        this(string2, d, d2, 0.0);
    }

    private Stats(String string2, double d, double d2, double d3) {
        this.fX = string2;
        this.ar = d;
        this.aa = d2;
        this.as = d3;
    }

    public double validate(double d) {
        if (d < this.ar) {
            return this.ar;
        }
        if (d > this.aa) {
            return this.aa;
        }
        return d;
    }

    public static Stats valueOfXml(String string) {
        for (Stats stats : VALUES) {
            if (!stats.getValue().equals(string)) continue;
            return stats;
        }
        System.out.println("Unknown name '" + string + "' for enum BaseStats");
        throw new NoSuchElementException("Unknown name '" + string + "' for enum BaseStats");
    }

    public String toString() {
        return this.fX;
    }

    private static /* synthetic */ Stats[] a() {
        return new Stats[]{MAX_HP, MAX_MP, MAX_CP, REGENERATE_HP_RATE, REGENERATE_CP_RATE, REGENERATE_MP_RATE, HP_LIMIT, MP_LIMIT, CP_LIMIT, RUN_SPEED, POWER_DEFENCE, MAGIC_DEFENCE, POWER_ATTACK, MAGIC_ATTACK, POWER_ATTACK_SPEED, MAGIC_ATTACK_SPEED, MAGIC_REUSE_RATE, PHYSIC_REUSE_RATE, MUSIC_REUSE_RATE, ATK_REUSE, ATK_BASE, CRITICAL_DAMAGE, CRITICAL_DAMAGE_STATIC, EVASION_RATE, ACCURACY_COMBAT, CRITICAL_BASE, CRITICAL_RATE, MCRITICAL_RATE, MCRITICAL_DAMAGE, MCRITICAL_DAMAGE_RESIST, PHYSICAL_DAMAGE, MAGIC_DAMAGE, CAST_INTERRUPT, SHIELD_DEFENCE, SHIELD_RATE, SHIELD_ANGLE, POWER_ATTACK_RANGE, MAGIC_ATTACK_RANGE, POLE_ATTACK_ANGLE, POLE_TARGET_COUNT, STAT_STR, STAT_CON, STAT_DEX, STAT_INT, STAT_WIT, STAT_MEN, BREATH, FALL, EXP_LOST, BLEED_RESIST, POISON_RESIST, STUN_RESIST, ROOT_RESIST, MENTAL_RESIST, SLEEP_RESIST, PARALYZE_RESIST, CANCEL_RESIST, DEBUFF_RESIST, MAGIC_RESIST, BLEED_POWER, POISON_POWER, STUN_POWER, ROOT_POWER, MENTAL_POWER, SLEEP_POWER, PARALYZE_POWER, CANCEL_POWER, DEBUFF_POWER, MAGIC_POWER, FATALBLOW_RATE, SKILL_CRIT_CHANCE_MOD, DEATH_VULNERABILITY, CRIT_DAMAGE_RECEPTIVE, CRIT_CHANCE_RECEPTIVE, DEFENCE_FIRE, DEFENCE_WATER, DEFENCE_WIND, DEFENCE_EARTH, DEFENCE_HOLY, DEFENCE_UNHOLY, ATTACK_FIRE, ATTACK_WATER, ATTACK_WIND, ATTACK_EARTH, ATTACK_HOLY, ATTACK_UNHOLY, SWORD_WPN_VULNERABILITY, DUAL_WPN_VULNERABILITY, BLUNT_WPN_VULNERABILITY, DAGGER_WPN_VULNERABILITY, BOW_WPN_VULNERABILITY, CROSSBOW_WPN_VULNERABILITY, POLE_WPN_VULNERABILITY, FIST_WPN_VULNERABILITY, ABSORB_DAMAGE_PERCENT, ABSORB_MANA_DAMAGE_PERCENT, ABSORB_MANA_DAMAGE_CHANCE, TRANSFER_TO_SUMMON_DAMAGE_PERCENT, TRANSFER_TO_EFFECTOR_DAMAGE_PERCENT, REFLECT_AND_BLOCK_DAMAGE_CHANCE, REFLECT_AND_BLOCK_PSKILL_DAMAGE_CHANCE, REFLECT_AND_BLOCK_MSKILL_DAMAGE_CHANCE, BLOCK_DAMAGE_VALUE, REFLECT_DAMAGE_PERCENT, REFLECT_PSKILL_DAMAGE_PERCENT, REFLECT_MSKILL_DAMAGE_PERCENT, REFLECT_PHYSIC_SKILL, REFLECT_MAGIC_SKILL, REFLECT_PHYSIC_DEBUFF, REFLECT_MAGIC_DEBUFF, PSKILL_EVASION, COUNTER_ATTACK, SKILL_POWER, PVP_PHYS_DMG_BONUS, PVP_PHYS_SKILL_DMG_BONUS, PVP_MAGIC_SKILL_DMG_BONUS, PVP_PHYS_DEFENCE_BONUS, PVP_PHYS_SKILL_DEFENCE_BONUS, PVP_MAGIC_SKILL_DEFENCE_BONUS, PVE_PHYS_DMG_BONUS, PVE_PHYS_SKILL_DMG_BONUS, PVE_MAGIC_SKILL_DMG_BONUS, PVE_PHYS_DEFENCE_BONUS, PVE_PHYS_SKILL_DEFENCE_BONUS, PVE_MAGIC_SKILL_DEFENCE_BONUS, SOUL_SHOT_BONUS, SPIRIT_SHOT_BONUS, BLESSED_SPIRIT_SHOT_BONUS, HEAL_EFFECTIVNESS, MANAHEAL_EFFECTIVNESS, CPHEAL_EFFECTIVNESS, HEAL_POWER, MP_MAGIC_SKILL_CONSUME, MP_PHYSICAL_SKILL_CONSUME, MP_DANCE_SKILL_CONSUME, MP_USE_BOW, MP_USE_BOW_CHANCE, SS_USE_BOW, SS_USE_BOW_CHANCE, SKILL_MASTERY, MAX_LOAD, MAX_NO_PENALTY_LOAD, INVENTORY_LIMIT, STORAGE_LIMIT, TRADE_LIMIT, COMMON_RECIPE_LIMIT, DWARVEN_RECIPE_LIMIT, BUFF_LIMIT, CUBICS_LIMIT, CLOAK_SLOT, TALISMANS_LIMIT, BROOCH_LIMIT, AGATHION_CHARM_LIMIT, GRADE_EXPERTISE_LEVEL, EXP, SP, RAID_EXP, RAID_SP, ITEM_REWARD_MULTIPLIER, ADENA_REWARD_MULTIPLIER, SPOIL_REWARD_MULTIPLIER, SEAL_STONES_REWARD_MULTIPLIER, QUEST_DROP_MULTIPLIER, ENCHANT_BONUS_MULTIPLIER, ENCHANT_SKILL_BONUS_MULTIPLIER, AUTO_LOOT, AUTO_LOOT_HERB, AUTO_LOOT_ADENA, WORLD_CHAT_BONUSES, VIP_SILVER_DROP_MULTIPLIER, VIP_GOLD_DROP_MULTIPLIER};
    }

    static {
        a = Stats.a();
        VALUES = Stats.values();
        NUM_STATS = Stats.values().length;
    }
}
