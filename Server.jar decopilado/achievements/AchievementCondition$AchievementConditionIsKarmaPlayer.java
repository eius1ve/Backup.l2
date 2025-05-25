/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="is_karma_player")
public static class AchievementCondition.AchievementConditionIsKarmaPlayer
extends AchievementCondition {
    private final boolean g;

    public AchievementCondition.AchievementConditionIsKarmaPlayer(String string) {
        this.g = Boolean.parseBoolean(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null || !(this.g ? object instanceof Player && ((Player)object).getKarma() > 0 : object instanceof Player && ((Player)object).getKarma() == 0)) continue;
            return true;
        }
        return false;
    }
}
