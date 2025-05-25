/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.model.Skill;

static class Skill.3 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$Skill$SkillType;
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$Skill$SkillTargetType;

    static {
        $SwitchMap$l2$gameserver$model$Skill$SkillTargetType = new int[Skill.SkillTargetType.values().length];
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_ALLY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_ALLY_AND_PARTY.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_CLAN.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_PARTY.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_CLAN_ONLY.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_SELF.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_AURA.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_COMMCHANNEL.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_MULTIFACE_AURA.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_HOLY.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_FLAGPOLE.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_UNLOCKABLE.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_CHEST.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_FEEDABLE_BEAST.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_PET.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_PET_AURA.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_OWNER.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_ENEMY_PET.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_ENEMY_SUMMON.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_ENEMY_SERVITOR.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_CHAIN.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_ONE.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_OTHER.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_AREA.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_AREA_CLAN.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_MULTIFACE.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_TUNNEL.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_AREA_AIM_CORPSE.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_CORPSE.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_CORPSE_PLAYER.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_SIEGE.ordinal()] = 31;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_ITEM.ordinal()] = 32;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillTargetType[Skill.SkillTargetType.TARGET_NONE.ordinal()] = 33;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$model$Skill$SkillType = new int[Skill.SkillType.values().length];
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DISCORD.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.AGGRESSION.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.AIEFFECTS.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.SOWING.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DELETE_HATE.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DELETE_HATE_OF_ME.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.BLEED.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DEBUFF.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DOT.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.MDOT.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.MUTE.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.PARALYZE.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.POISON.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.ROOT.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.SLEEP.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.MANADAM.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DESTROY_SUMMON.ordinal()] = 17;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.NEGATE_STATS.ordinal()] = 18;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.NEGATE_EFFECTS.ordinal()] = 19;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.STEAL_BUFF.ordinal()] = 20;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DRAIN.ordinal()] = 21;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.DRAIN_SOUL.ordinal()] = 22;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.LETHAL_SHOT.ordinal()] = 23;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.MDAM.ordinal()] = 24;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.PDAM.ordinal()] = 25;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.CPDAM.ordinal()] = 26;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.SOULSHOT.ordinal()] = 27;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.SPIRITSHOT.ordinal()] = 28;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.SPOIL.ordinal()] = 29;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.STUN.ordinal()] = 30;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.SWEEP.ordinal()] = 31;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.HARVESTING.ordinal()] = 32;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Skill.3.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.TELEPORT_NPC.ordinal()] = 33;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
