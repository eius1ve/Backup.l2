/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager.actionrunner.tasks;

import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.taskmanager.actionrunner.tasks.OneDayRewardDailyResetTask;
import l2.gameserver.taskmanager.actionrunner.tasks.SchedulingPatternTask;

public class OneDayRewardWeeklyResetTask
extends SchedulingPatternTask {
    private static final OneDayRewardWeeklyResetTask a = new OneDayRewardWeeklyResetTask();

    private OneDayRewardWeeklyResetTask() {
        super("30 6 * * Mon");
    }

    public static OneDayRewardWeeklyResetTask getInstance() {
        return a;
    }

    @Override
    protected void runTask() {
        OneDayRewardDailyResetTask.resetOneDayRewardStatuses(OneDayReward.ResetTime.WEEKLY);
    }
}
