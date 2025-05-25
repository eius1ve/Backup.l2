/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;

public class ObtainLevelRequirement
implements OneDayRewardRequirement {
    private int level;

    public ObtainLevelRequirement(int n) {
        this.level = n;
    }

    @Override
    public int increaseProgress(Player player, int n) {
        int n2 = player.getLevel();
        if (n2 >= this.level) {
            return this.level;
        }
        return n2;
    }

    @Override
    public OneDayRewardStatus getInitialState(OneDayReward oneDayReward, Player player) {
        int n = player.getLevel();
        if (player.getLevel() >= this.level) {
            n = this.level;
        }
        return new OneDayRewardStatus(oneDayReward, player.getObjectId(), n);
    }

    @Override
    public int getRequiredProgress() {
        return this.level;
    }
}
