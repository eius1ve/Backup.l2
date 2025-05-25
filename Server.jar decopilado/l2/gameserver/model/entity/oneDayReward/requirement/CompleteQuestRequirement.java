/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;

public class CompleteQuestRequirement
implements OneDayRewardRequirement {
    private final int lU;

    public CompleteQuestRequirement(int n) {
        this.lU = n;
    }

    @Override
    public int getRequiredProgress() {
        return this.lU;
    }
}
