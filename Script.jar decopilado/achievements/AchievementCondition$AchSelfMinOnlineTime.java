/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="self_min_online_time")
public static class AchievementCondition.AchSelfMinOnlineTime
extends AchievementCondition {
    private final long b;

    public AchievementCondition.AchSelfMinOnlineTime(String string) {
        this.b = Long.parseLong(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        long l = player.getOnlineBeginTime();
        long l2 = System.currentTimeMillis();
        if (l > 0L && l <= l2) {
            return (l2 - l) / 1000L >= this.b;
        }
        return false;
    }
}
