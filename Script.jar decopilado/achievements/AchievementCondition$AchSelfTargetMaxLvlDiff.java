/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="self_target_max_lvl_diff")
public static class AchievementCondition.AchSelfTargetMaxLvlDiff
extends AchievementCondition {
    private final int e;

    public AchievementCondition.AchSelfTargetMaxLvlDiff(String string) {
        this.e = Integer.parseInt(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null || !(object instanceof Creature)) continue;
            Creature creature = (Creature)object;
            return Math.abs(creature.getLevel() - player.getLevel()) <= this.e;
        }
        return false;
    }
}
