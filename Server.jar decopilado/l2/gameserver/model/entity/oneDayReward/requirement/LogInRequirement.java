/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;

public class LogInRequirement
implements OneDayRewardRequirement {
    @Override
    public OneDayRewardStatus getInitialState(OneDayReward oneDayReward, Player player) {
        return new OneDayRewardStatus(oneDayReward, player.getObjectId(), 1);
    }

    @Override
    public int getRequiredProgress() {
        return 1;
    }
}
