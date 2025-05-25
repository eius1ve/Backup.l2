/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="self_is_clan_leader")
public static class AchievementCondition.AchSelfIsClanLeader
extends AchievementCondition {
    private final boolean b;

    public AchievementCondition.AchSelfIsClanLeader(String string) {
        this.b = Boolean.parseBoolean(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        return player.isClanLeader() == this.b;
    }
}
