/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="self_is_hero")
public static class AchievementCondition.AchSelfIsHero
extends AchievementCondition {
    private final boolean c;

    public AchievementCondition.AchSelfIsHero(String string) {
        this.c = Boolean.parseBoolean(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        return player.isHero() == this.c;
    }
}
