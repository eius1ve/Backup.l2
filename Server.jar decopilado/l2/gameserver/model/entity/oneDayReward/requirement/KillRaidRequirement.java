/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;

public class KillRaidRequirement
implements OneDayRewardRequirement {
    private final int lY;

    public KillRaidRequirement(int n) {
        this.lY = n;
    }

    @Override
    public int getRequiredProgress() {
        return this.lY;
    }
}
