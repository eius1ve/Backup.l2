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

@AchievementCondition.AchievementConditionName(value="self_is_class_id_in")
public static class AchievementCondition.AchSelfIsActiveClass
extends AchievementCondition {
    private final Set<Integer> a = new HashSet<Integer>();

    public AchievementCondition.AchSelfIsActiveClass(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";,");
        while (stringTokenizer.hasMoreTokens()) {
            this.a.add(Integer.parseInt(stringTokenizer.nextToken()));
        }
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        return this.a.contains(player.getClassId());
    }
}
