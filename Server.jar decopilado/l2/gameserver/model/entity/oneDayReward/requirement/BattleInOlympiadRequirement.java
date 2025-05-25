/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;

public class BattleInOlympiadRequirement
implements OneDayRewardRequirement {
    private final int lT;

    public BattleInOlympiadRequirement(int n) {
        this.lT = n;
    }

    @Override
    public int getRequiredProgress() {
        return this.lT;
    }
}
