/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import l2.gameserver.model.entity.oneDayReward.OneDayDistributionType;

static class OneDayRewardHolder.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$model$entity$oneDayReward$OneDayDistributionType;

    static {
        $SwitchMap$l2$gameserver$model$entity$oneDayReward$OneDayDistributionType = new int[OneDayDistributionType.values().length];
        try {
            OneDayRewardHolder.1.$SwitchMap$l2$gameserver$model$entity$oneDayReward$OneDayDistributionType[OneDayDistributionType.SOLO.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OneDayRewardHolder.1.$SwitchMap$l2$gameserver$model$entity$oneDayReward$OneDayDistributionType[OneDayDistributionType.PARTY_RANDOM.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            OneDayRewardHolder.1.$SwitchMap$l2$gameserver$model$entity$oneDayReward$OneDayDistributionType[OneDayDistributionType.PARTY_ALL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
