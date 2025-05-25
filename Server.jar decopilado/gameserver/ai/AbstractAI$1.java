/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.CtrlIntention;

static class AbstractAI.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$ai$CtrlIntention;
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$ai$CtrlEvent;

    static {
        $SwitchMap$l2$gameserver$ai$CtrlEvent = new int[CtrlEvent.values().length];
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_THINK.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_ATTACKED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_CLAN_ATTACKED.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_AGGRESSION.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_READY_TO_ACT.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_ARRIVED.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_ARRIVED_TARGET.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_ARRIVED_BLOCKED.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_FORGET_OBJECT.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_DEAD.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_FAKE_DEATH.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_FINISH_CASTING.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_SEE_SPELL.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_SPAWN.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_DESPAWN.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlEvent[CtrlEvent.EVT_TIMER.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SwitchMap$l2$gameserver$ai$CtrlIntention = new int[CtrlIntention.values().length];
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_IDLE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_ACTIVE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_REST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_ATTACK.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_CAST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_PICK_UP.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_INTERACT.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AbstractAI.1.$SwitchMap$l2$gameserver$ai$CtrlIntention[CtrlIntention.AI_INTENTION_FOLLOW.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
