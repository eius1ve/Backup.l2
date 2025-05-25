/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="self_is_subclass_active")
public static class AchievementCondition.AchSelfIsSubclassActive
extends AchievementCondition {
    private final boolean e;

    public AchievementCondition.AchSelfIsSubclassActive(String string) {
        this.e = Boolean.parseBoolean(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        return player.isSubClassActive() == this.e;
    }
}
