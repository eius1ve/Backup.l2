/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.oly.Competition
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.Competition;

@AchievementCondition.AchievementConditionName(value="is_oly_winner")
public static class AchievementCondition.AchIsOlyWinner
extends AchievementCondition {
    private final boolean a;

    public AchievementCondition.AchIsOlyWinner(String string) {
        this.a = Boolean.parseBoolean(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        boolean bl = false;
        for (Object object : objectArray) {
            if (!(object instanceof Competition)) continue;
            bl = true;
        }
        if (bl) {
            for (Object object : objectArray) {
                if (!(object instanceof Boolean)) continue;
                return this.a == (Boolean)object;
            }
        }
        return false;
    }
}
