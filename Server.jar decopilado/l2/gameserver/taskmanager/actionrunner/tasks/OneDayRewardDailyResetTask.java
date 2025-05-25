/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager.actionrunner.tasks;

import l2.gameserver.Config;
import l2.gameserver.dao.CharacterOneDayRewardDAO;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.taskmanager.actionrunner.tasks.SchedulingPatternTask;

public class OneDayRewardDailyResetTask
extends SchedulingPatternTask {
    private static final OneDayRewardDailyResetTask a = new OneDayRewardDailyResetTask();

    private OneDayRewardDailyResetTask() {
        super("30 6 * * *");
    }

    public static OneDayRewardDailyResetTask getInstance() {
        return a;
    }

    public static void resetOneDayRewardStatuses(OneDayReward.ResetTime resetTime) {
        if (!Config.EX_ONE_DAY_REWARD) {
            return;
        }
        for (Player player : GameObjectsStorage.getAllPlayers()) {
            player.getOneDayRewardStore().resetStatuses(resetTime);
        }
        CharacterOneDayRewardDAO.INSTANCE.deleteByResetTime(resetTime);
    }

    @Override
    protected void runTask() {
        OneDayRewardDailyResetTask.resetOneDayRewardStatuses(OneDayReward.ResetTime.DAILY);
    }
}
