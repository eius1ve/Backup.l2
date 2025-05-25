/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStatus;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.taskmanager.actionrunner.tasks.OneDayRewardDailyResetTask;
import l2.gameserver.taskmanager.actionrunner.tasks.OneDayRewardMonthlyResetTask;
import l2.gameserver.taskmanager.actionrunner.tasks.OneDayRewardWeeklyResetTask;

public class ExOneDayReceiveRewardList
extends L2GameServerPacket {
    private final int vO;
    private final DayOfWeek a;
    private final List<RewardInfo> cl = new ArrayList<RewardInfo>();
    private int vP;
    private int vQ;
    private int vR;

    public ExOneDayReceiveRewardList(Player player) {
        this.vO = player.getActiveClassId();
        this.a = LocalDate.now().getDayOfWeek();
        this.vP = (int)(OneDayRewardDailyResetTask.getInstance().getLeftTime() / 1000L);
        this.vQ = (int)(OneDayRewardWeeklyResetTask.getInstance().getLeftTime() / 1000L);
        this.vR = (int)(OneDayRewardMonthlyResetTask.getInstance().getLeftTime() / 1000L);
        Collection<OneDayReward> collection = OneDayRewardHolder.getInstance().getOneDayRewards(player);
        for (OneDayReward oneDayReward : collection) {
            int n = 2;
            OneDayRewardStatus oneDayRewardStatus = player.getOneDayRewardStore().getStatus(oneDayReward);
            OneDayRewardRequirement oneDayRewardRequirement = oneDayReward.getRequirement();
            if (oneDayRewardStatus.isReceived()) {
                n = 3;
            } else if (oneDayRewardRequirement != null && oneDayRewardRequirement.isDone(oneDayRewardStatus)) {
                n = 1;
            }
            RewardInfo rewardInfo = new RewardInfo(oneDayReward.getId(), n, oneDayRewardRequirement != null, oneDayRewardRequirement != null ? oneDayRewardRequirement.getRequiredProgress() : 0, oneDayRewardStatus.getCurrentProgress());
            this.cl.add(rewardInfo);
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(424);
        this.writeD(this.vP);
        this.writeD(this.vQ);
        this.writeD(this.vR);
        this.writeC(0);
        this.writeD(this.vO);
        this.writeD(this.a.ordinal());
        this.writeD(this.cl.size());
        for (RewardInfo rewardInfo : this.cl) {
            this.writeH(rewardInfo.id);
            this.writeC(rewardInfo.status);
            this.writeC(rewardInfo.hasProgress);
            this.writeD(rewardInfo.currentProgress);
            this.writeD(rewardInfo.requiredProgress);
        }
    }

    private static class RewardInfo {
        int id;
        int status;
        boolean hasProgress;
        int requiredProgress;
        int currentProgress;

        private RewardInfo(int n, int n2, boolean bl, int n3, int n4) {
            this.id = n;
            this.status = n2;
            this.hasProgress = bl;
            this.requiredProgress = n3;
            this.currentProgress = n4;
        }
    }
}
