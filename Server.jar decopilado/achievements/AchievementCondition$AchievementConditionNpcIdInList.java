/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 */
package achievements;

import achievements.AchievementCondition;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;

@AchievementCondition.AchievementConditionName(value="npc_id_in_list")
public static class AchievementCondition.AchievementConditionNpcIdInList
extends AchievementCondition {
    private Set<Integer> d = new HashSet<Integer>();

    public AchievementCondition.AchievementConditionNpcIdInList(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";,");
        while (stringTokenizer.hasMoreTokens()) {
            this.d.add(Integer.parseInt(stringTokenizer.nextToken()));
        }
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        for (Object object : objectArray) {
            NpcInstance npcInstance;
            if (object == null || !(object instanceof NpcInstance) || !this.d.contains((npcInstance = (NpcInstance)object).getNpcId())) continue;
            return true;
        }
        return false;
    }
}
