/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.npc;

import l2.gameserver.model.Skill;
import l2.gameserver.skills.EffectType;

static class NpcTemplate.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$skills$EffectType;
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$Skill$SkillType;

    static {
        $SwitchMap$l2$gameserver$model$Skill$SkillType = new int[Skill.SkillType.values().length];
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.PDAM.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.MANADAM.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.MDAM.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DRAIN.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DRAIN_SOUL.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DOT.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.MDOT.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.POISON.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.BLEED.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DEBUFF.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.SLEEP.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.ROOT.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.PARALYZE.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.MUTE.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.TELEPORT_NPC.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.AGGRESSION.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.BUFF.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.STUN.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.HEAL.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.HEAL_PERCENT.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.HOT.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$skills$EffectType = new int[EffectType.values().length];
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$skills$EffectType[EffectType.Stun.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$skills$EffectType[EffectType.DamOverTime.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$skills$EffectType[EffectType.DamOverTimeLethal.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$skills$EffectType[EffectType.ManaDamOverTime.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            NpcTemplate.1.$SwitchMap$l2$gameserver$skills$EffectType[EffectType.LDManaDamOverTime.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
