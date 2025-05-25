/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.lang.reflect.Constructor;
import l2.gameserver.model.Skill;
import l2.gameserver.skills.skillclasses.AIeffects;
import l2.gameserver.skills.skillclasses.Aggression;
import l2.gameserver.skills.skillclasses.Balance;
import l2.gameserver.skills.skillclasses.BeastFeed;
import l2.gameserver.skills.skillclasses.BookMarkTeleport;
import l2.gameserver.skills.skillclasses.BuffCharger;
import l2.gameserver.skills.skillclasses.CPDam;
import l2.gameserver.skills.skillclasses.Call;
import l2.gameserver.skills.skillclasses.ChainHeal;
import l2.gameserver.skills.skillclasses.ClanGate;
import l2.gameserver.skills.skillclasses.CombatPointHeal;
import l2.gameserver.skills.skillclasses.Continuous;
import l2.gameserver.skills.skillclasses.Craft;
import l2.gameserver.skills.skillclasses.DeathPenalty;
import l2.gameserver.skills.skillclasses.Default;
import l2.gameserver.skills.skillclasses.DefuseTrap;
import l2.gameserver.skills.skillclasses.DeleteHate;
import l2.gameserver.skills.skillclasses.DeleteHateOfMe;
import l2.gameserver.skills.skillclasses.DestroySummon;
import l2.gameserver.skills.skillclasses.DetectTrap;
import l2.gameserver.skills.skillclasses.Disablers;
import l2.gameserver.skills.skillclasses.Drain;
import l2.gameserver.skills.skillclasses.DrainSoul;
import l2.gameserver.skills.skillclasses.EXPHeal;
import l2.gameserver.skills.skillclasses.Effect;
import l2.gameserver.skills.skillclasses.EffectsFromSkills;
import l2.gameserver.skills.skillclasses.FishingSkill;
import l2.gameserver.skills.skillclasses.Harvesting;
import l2.gameserver.skills.skillclasses.Heal;
import l2.gameserver.skills.skillclasses.HealPercent;
import l2.gameserver.skills.skillclasses.HideHairAccessories;
import l2.gameserver.skills.skillclasses.LethalShot;
import l2.gameserver.skills.skillclasses.MDam;
import l2.gameserver.skills.skillclasses.ManaDam;
import l2.gameserver.skills.skillclasses.ManaHeal;
import l2.gameserver.skills.skillclasses.ManaHealPercent;
import l2.gameserver.skills.skillclasses.NegateEffects;
import l2.gameserver.skills.skillclasses.NegateStats;
import l2.gameserver.skills.skillclasses.PDam;
import l2.gameserver.skills.skillclasses.PcBangPointsAdd;
import l2.gameserver.skills.skillclasses.PetSummon;
import l2.gameserver.skills.skillclasses.Recall;
import l2.gameserver.skills.skillclasses.ReelingPumping;
import l2.gameserver.skills.skillclasses.Resurrect;
import l2.gameserver.skills.skillclasses.Ride;
import l2.gameserver.skills.skillclasses.SPHeal;
import l2.gameserver.skills.skillclasses.ShiftAggression;
import l2.gameserver.skills.skillclasses.SkillSeed;
import l2.gameserver.skills.skillclasses.Sowing;
import l2.gameserver.skills.skillclasses.Spoil;
import l2.gameserver.skills.skillclasses.StealBuff;
import l2.gameserver.skills.skillclasses.Summon;
import l2.gameserver.skills.skillclasses.SummonItem;
import l2.gameserver.skills.skillclasses.SummonSiegeFlag;
import l2.gameserver.skills.skillclasses.Sweep;
import l2.gameserver.skills.skillclasses.TakeCastle;
import l2.gameserver.skills.skillclasses.TameControl;
import l2.gameserver.skills.skillclasses.TeleportNpc;
import l2.gameserver.skills.skillclasses.Toggle;
import l2.gameserver.skills.skillclasses.Transformation;
import l2.gameserver.skills.skillclasses.Unlock;
import l2.gameserver.skills.skillclasses.VitalityHeal;
import l2.gameserver.templates.StatsSet;

public static enum Skill.SkillType {
    AGGRESSION(Aggression.class),
    AIEFFECTS(AIeffects.class),
    BALANCE(Balance.class),
    BEAST_FEED(BeastFeed.class),
    BLEED(Continuous.class),
    BOOKMARK_TELEPORT(BookMarkTeleport.class),
    BUFF(Continuous.class),
    BUFF_CHARGER(BuffCharger.class),
    CALL(Call.class),
    CLAN_GATE(ClanGate.class),
    CHAIN_HEAL(ChainHeal.class),
    COMBATPOINTHEAL(CombatPointHeal.class),
    CONT(Toggle.class),
    CPDAM(CPDam.class),
    CPHOT(Continuous.class),
    CRAFT(Craft.class),
    DEATH_PENALTY(DeathPenalty.class),
    DEBUFF(Continuous.class),
    DELETE_HATE(DeleteHate.class),
    DELETE_HATE_OF_ME(DeleteHateOfMe.class),
    DESTROY_SUMMON(DestroySummon.class),
    DEFUSE_TRAP(DefuseTrap.class),
    DETECT_TRAP(DetectTrap.class),
    DISCORD(Continuous.class),
    DOT(Continuous.class),
    DRAIN(Drain.class),
    DRAIN_SOUL(DrainSoul.class),
    EFFECT(Effect.class),
    EFFECTS_FROM_SKILLS(EffectsFromSkills.class),
    ENCHANT_ARMOR,
    ENCHANT_WEAPON,
    FEED_PET,
    FISHING(FishingSkill.class),
    HARDCODED(Effect.class),
    HARVESTING(Harvesting.class),
    HEAL(Heal.class),
    HEAL_PERCENT(HealPercent.class),
    HIDE_HAIR_ACCESSORIES(HideHairAccessories.class),
    HOT(Continuous.class),
    LETHAL_SHOT(LethalShot.class),
    LUCK,
    MANADAM(ManaDam.class),
    MANAHEAL(ManaHeal.class),
    MANAHEAL_PERCENT(ManaHealPercent.class),
    MDAM(MDam.class),
    MDOT(Continuous.class),
    MPHOT(Continuous.class),
    MUTE(Disablers.class),
    NEGATE_EFFECTS(NegateEffects.class),
    NEGATE_STATS(NegateStats.class),
    ADD_PC_BANG(PcBangPointsAdd.class),
    NOTDONE,
    NOTUSED,
    PARALYZE(Disablers.class),
    PASSIVE,
    PDAM(PDam.class),
    PET_SUMMON(PetSummon.class),
    POISON(Continuous.class),
    PUMPING(ReelingPumping.class),
    RECALL(Recall.class),
    REELING(ReelingPumping.class),
    RESURRECT(Resurrect.class),
    RIDE(Ride.class),
    ROOT(Disablers.class),
    SHIFT_AGGRESSION(ShiftAggression.class),
    SSEED(SkillSeed.class),
    SLEEP(Disablers.class),
    SOULSHOT,
    SOWING(Sowing.class),
    SPHEAL(SPHeal.class),
    EXPHEAL(EXPHeal.class),
    SPIRITSHOT,
    SPOIL(Spoil.class),
    STEAL_BUFF(StealBuff.class),
    STUN(Disablers.class),
    SUMMON(Summon.class),
    SUMMON_FLAG(SummonSiegeFlag.class),
    SUMMON_ITEM(SummonItem.class),
    SWEEP(Sweep.class),
    TAKECASTLE(TakeCastle.class),
    TAMECONTROL(TameControl.class),
    TELEPORT_NPC(TeleportNpc.class),
    TRANSFORMATION(Transformation.class),
    UNLOCK(Unlock.class),
    VITALITY_HEAL(VitalityHeal.class),
    WATCHER_GAZE(Continuous.class);

    private final Class<? extends Skill> clazz;

    private Skill.SkillType() {
        this.clazz = Default.class;
    }

    private Skill.SkillType(Class<? extends Skill> clazz) {
        this.clazz = clazz;
    }

    public Skill makeSkill(StatsSet statsSet) {
        try {
            Constructor<? extends Skill> constructor = this.clazz.getConstructor(StatsSet.class);
            return constructor.newInstance(statsSet);
        }
        catch (Exception exception) {
            bI.error("", (Throwable)exception);
            throw new RuntimeException(exception);
        }
    }

    public final boolean isPvM() {
        switch (this) {
            case DISCORD: {
                return true;
            }
        }
        return false;
    }

    public boolean isAI() {
        switch (this) {
            case AGGRESSION: 
            case AIEFFECTS: 
            case SOWING: 
            case DELETE_HATE: 
            case DELETE_HATE_OF_ME: {
                return true;
            }
        }
        return false;
    }

    public final boolean isPvpSkill() {
        switch (this) {
            case AGGRESSION: 
            case DELETE_HATE: 
            case DELETE_HATE_OF_ME: 
            case BLEED: 
            case DEBUFF: 
            case DOT: 
            case MDOT: 
            case MUTE: 
            case PARALYZE: 
            case POISON: 
            case ROOT: 
            case SLEEP: 
            case MANADAM: 
            case DESTROY_SUMMON: 
            case NEGATE_STATS: 
            case NEGATE_EFFECTS: 
            case STEAL_BUFF: {
                return true;
            }
        }
        return false;
    }

    public boolean isOffensive() {
        switch (this) {
            case DISCORD: 
            case AGGRESSION: 
            case AIEFFECTS: 
            case SOWING: 
            case DELETE_HATE: 
            case DELETE_HATE_OF_ME: 
            case BLEED: 
            case DEBUFF: 
            case DOT: 
            case MDOT: 
            case MUTE: 
            case PARALYZE: 
            case POISON: 
            case ROOT: 
            case SLEEP: 
            case MANADAM: 
            case DESTROY_SUMMON: 
            case STEAL_BUFF: 
            case DRAIN: 
            case DRAIN_SOUL: 
            case LETHAL_SHOT: 
            case MDAM: 
            case PDAM: 
            case CPDAM: 
            case SOULSHOT: 
            case SPIRITSHOT: 
            case SPOIL: 
            case STUN: 
            case SWEEP: 
            case HARVESTING: 
            case TELEPORT_NPC: {
                return true;
            }
        }
        return false;
    }
}
