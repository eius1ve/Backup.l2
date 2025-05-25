/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="self_level_in_range")
public static class AchievementCondition.AchSelfLevelInRange
extends AchievementCondition {
    private final int b;
    private final int c;

    public AchievementCondition.AchSelfLevelInRange(String string) {
        int n = string.indexOf(45);
        this.b = Integer.parseInt(string.substring(0, n).trim());
        this.c = Integer.parseInt(string.substring(n + 1).trim());
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        return player.getLevel() >= this.b && player.getLevel() < this.c;
    }
}
