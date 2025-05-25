/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;

public class WinInOlympiadRequirement
implements OneDayRewardRequirement {
    private final int lZ;

    public WinInOlympiadRequirement(int n) {
        this.lZ = n;
    }

    @Override
    public int getRequiredProgress() {
        return this.lZ;
    }
}
