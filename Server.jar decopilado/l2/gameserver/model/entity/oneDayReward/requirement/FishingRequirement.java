/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;

public class FishingRequirement
implements OneDayRewardRequirement {
    private final int lW;

    public FishingRequirement(int n) {
        this.lW = n;
    }

    @Override
    public int getRequiredProgress() {
        return this.lW;
    }
}
