/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.time.cron.SchedulingPattern
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="now_match_cron")
public static class AchievementCondition.AchievementConditionIsNowMatchCron
extends AchievementCondition {
    private final SchedulingPattern a;

    public AchievementCondition.AchievementConditionIsNowMatchCron(String string) {
        this.a = new SchedulingPattern(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        return this.a.match(System.currentTimeMillis());
    }
}
