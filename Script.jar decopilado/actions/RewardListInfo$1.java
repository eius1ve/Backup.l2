/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.reward.RewardType
 */
package actions;

import l2.gameserver.model.reward.RewardType;

static class RewardListInfo.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$reward$RewardType;

    static {
        $SwitchMap$l2$gameserver$model$reward$RewardType = new int[RewardType.values().length];
        try {
            RewardListInfo.1.$SwitchMap$l2$gameserver$model$reward$RewardType[RewardType.RATED_GROUPED.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RewardListInfo.1.$SwitchMap$l2$gameserver$model$reward$RewardType[RewardType.NOT_RATED_GROUPED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RewardListInfo.1.$SwitchMap$l2$gameserver$model$reward$RewardType[RewardType.NOT_RATED_NOT_GROUPED.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RewardListInfo.1.$SwitchMap$l2$gameserver$model$reward$RewardType[RewardType.RATED_NOT_GROUPED.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RewardListInfo.1.$SwitchMap$l2$gameserver$model$reward$RewardType[RewardType.SWEEP.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
