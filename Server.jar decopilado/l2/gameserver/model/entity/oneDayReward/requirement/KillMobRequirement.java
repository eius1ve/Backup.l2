/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;

public class KillMobRequirement
implements OneDayRewardRequirement {
    private final int lX;

    public KillMobRequirement(int n) {
        this.lX = n;
    }

    @Override
    public int getRequiredProgress() {
        return this.lX;
    }
}
