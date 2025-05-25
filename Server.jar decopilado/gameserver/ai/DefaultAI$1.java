/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.gameserver.ai.DefaultAI;

static class DefaultAI.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$ai$DefaultAI$TaskType;

    static {
        $SwitchMap$l2$gameserver$ai$DefaultAI$TaskType = new int[DefaultAI.TaskType.values().length];
        try {
            DefaultAI.1.$SwitchMap$l2$gameserver$ai$DefaultAI$TaskType[DefaultAI.TaskType.MOVE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            DefaultAI.1.$SwitchMap$l2$gameserver$ai$DefaultAI$TaskType[DefaultAI.TaskType.ATTACK.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            DefaultAI.1.$SwitchMap$l2$gameserver$ai$DefaultAI$TaskType[DefaultAI.TaskType.CAST.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            DefaultAI.1.$SwitchMap$l2$gameserver$ai$DefaultAI$TaskType[DefaultAI.TaskType.BUFF.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
