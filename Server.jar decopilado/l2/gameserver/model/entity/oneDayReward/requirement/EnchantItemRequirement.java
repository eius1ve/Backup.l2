/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;

public class EnchantItemRequirement
implements OneDayRewardRequirement {
    private final int lV;

    public EnchantItemRequirement(int n) {
        this.lV = n;
    }

    @Override
    public int getRequiredProgress() {
        return this.lV;
    }
}
