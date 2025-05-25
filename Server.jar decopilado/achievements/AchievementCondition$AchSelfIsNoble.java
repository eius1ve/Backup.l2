/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="self_is_noble")
public static class AchievementCondition.AchSelfIsNoble
extends AchievementCondition {
    private final boolean d;

    public AchievementCondition.AchSelfIsNoble(String string) {
        this.d = Boolean.parseBoolean(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        return player.isNoble() == this.d;
    }
}
