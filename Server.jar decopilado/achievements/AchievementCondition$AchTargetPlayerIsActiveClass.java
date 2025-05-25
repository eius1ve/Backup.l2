/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCondition;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import l2.gameserver.model.Player;

@AchievementCondition.AchievementConditionName(value="is_target_player_class_id_in")
public static class AchievementCondition.AchTargetPlayerIsActiveClass
extends AchievementCondition {
    private final Set<Integer> c = new HashSet<Integer>();

    public AchievementCondition.AchTargetPlayerIsActiveClass(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";,");
        while (stringTokenizer.hasMoreTokens()) {
            this.c.add(Integer.parseInt(stringTokenizer.nextToken()));
        }
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null || !(object instanceof Player)) continue;
            Player player2 = (Player)object;
            return this.c.contains(player2.getClassId());
        }
        return false;
    }
}
