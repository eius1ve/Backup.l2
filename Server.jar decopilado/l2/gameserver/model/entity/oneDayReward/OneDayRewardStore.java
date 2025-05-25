/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import l2.gameserver.dao.CharacterOneDayRewardDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExConnectedTimeAndGettableReward;

public class OneDayRewardStore {
    private final Player k;
    private final Map<Integer, OneDayRewardStatus> bd = new ConcurrentHashMap<Integer, OneDayRewardStatus>();

    public OneDayRewardStore(Player player) {
        this.k = player;
    }

    public OneDayRewardStatus getStatus(OneDayReward oneDayReward) {
        return this.bd.computeIfAbsent(oneDayReward.getId(), n -> {
            OneDayRewardStatus oneDayRewardStatus;
            OneDayRewardStatus oneDayRewardStatus2 = CharacterOneDayRewardDAO.INSTANCE.select(oneDayReward.getId(), oneDayReward.getResetTime(), this.k.getObjectId());
            if (oneDayRewardStatus2 != null) {
                return oneDayRewardStatus2;
            }
            OneDayRewardRequirement oneDayRewardRequirement = oneDayReward.getRequirement();
            if (oneDayRewardRequirement != null && (oneDayRewardStatus = oneDayRewardRequirement.getInitialState(oneDayReward, this.k)) != null) {
                return oneDayRewardStatus;
            }
            return new OneDayRewardStatus(oneDayReward, this.k.getObjectId(), 0);
        });
    }

    public OneDayRewardStatus updateStatus(OneDayReward oneDayReward, int n, boolean bl) {
        OneDayRewardStatus oneDayRewardStatus = this.getStatus(oneDayReward);
        OneDayRewardStatus oneDayRewardStatus2 = new OneDayRewardStatus(oneDayRewardStatus.getId(), oneDayRewardStatus.getPlayerObjectId(), n, oneDayRewardStatus.getResetTime(), bl);
        this.bd.put(oneDayRewardStatus2.getId(), oneDayRewardStatus2);
        CharacterOneDayRewardDAO.INSTANCE.updateProgressAndState(oneDayRewardStatus2);
        return oneDayRewardStatus2;
    }

    public void resetStatuses(OneDayReward.ResetTime resetTime) {
        for (OneDayRewardStatus oneDayRewardStatus : this.bd.values()) {
            if (oneDayRewardStatus.getResetTime() != resetTime.ordinal()) continue;
            this.bd.remove(oneDayRewardStatus.getId());
        }
        this.k.sendPacket((IStaticPacket)new ExConnectedTimeAndGettableReward(this.k));
    }
}
