/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;

public interface OneDayRewardRequirement {
    default public boolean isDone(OneDayRewardStatus oneDayRewardStatus) {
        return oneDayRewardStatus.getCurrentProgress() >= this.getRequiredProgress();
    }

    default public OneDayRewardStatus getInitialState(OneDayReward oneDayReward, Player player) {
        return null;
    }

    default public int increaseProgress(Player player, int n) {
        return n + 1;
    }

    public int getRequiredProgress();
}
