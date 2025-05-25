/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager.actionrunner.tasks;

import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.taskmanager.actionrunner.tasks.OneDayRewardDailyResetTask;
import l2.gameserver.taskmanager.actionrunner.tasks.SchedulingPatternTask;

public class OneDayRewardMonthlyResetTask
extends SchedulingPatternTask {
    private static final OneDayRewardMonthlyResetTask a = new OneDayRewardMonthlyResetTask();

    private OneDayRewardMonthlyResetTask() {
        super("30 6 1 * *");
    }

    public static OneDayRewardMonthlyResetTask getInstance() {
        return a;
    }

    @Override
    protected void runTask() {
        OneDayRewardDailyResetTask.resetOneDayRewardStatuses(OneDayReward.ResetTime.MONTHLY);
    }
}
