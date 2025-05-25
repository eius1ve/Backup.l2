/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward.requirement;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;
import l2.gameserver.model.pledge.Clan;

public class JoinClanRequirement
implements OneDayRewardRequirement {
    @Override
    public OneDayRewardStatus getInitialState(OneDayReward oneDayReward, Player player) {
        Clan clan = player.getClan();
        if (clan != null) {
            return new OneDayRewardStatus(oneDayReward, player.getObjectId(), 1);
        }
        return null;
    }

    @Override
    public int getRequiredProgress() {
        return 1;
    }
}
