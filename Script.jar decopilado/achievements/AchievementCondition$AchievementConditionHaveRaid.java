/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.RaidBossInstance
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.RaidBossInstance;

@AchievementCondition.AchievementConditionName(value="is_raid_boss")
public static class AchievementCondition.AchievementConditionHaveRaid
extends AchievementCondition {
    private final boolean f;

    public AchievementCondition.AchievementConditionHaveRaid(String string) {
        this.f = Boolean.parseBoolean(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null || !(this.f ? object instanceof RaidBossInstance : !(object instanceof RaidBossInstance))) continue;
            return true;
        }
        return false;
    }
}
