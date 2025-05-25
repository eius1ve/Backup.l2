/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.NextAction;
import l2.gameserver.model.Skill;

static class PlayableAI.5 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$ai$NextAction;
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$ai$CtrlIntention;
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$Skill$SkillType;

    static {
        $SwitchMap$l2$gameserver$model$Skill$SkillType = new int[Skill.SkillType.values().length];
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$model$Skill$SkillType[Skill.SkillType.TAKECASTLE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$ai$CtrlIntention = new int[CtrlIntention.values().length];
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_ATTACK.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_CAST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_FOLLOW.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_ACTIVE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_PICK_UP.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_INTERACT.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$ai$NextAction = new int[NextAction.values().length];
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$NextAction[NextAction.ATTACK.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$NextAction[NextAction.CAST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$NextAction[NextAction.MOVE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$NextAction[NextAction.REST.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$NextAction[NextAction.INTERACT.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$NextAction[NextAction.EQUIP.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            PlayableAI.5.$SwitchMap$l2$gameserver$ai$NextAction[NextAction.PICKUP.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
