/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 */
package achievements;

import achievements.AchievementCondition;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;

@AchievementCondition.AchievementConditionName(value="self_quest_id_in")
public static class AchievementCondition.AchSelfQuestId
extends AchievementCondition {
    private final Set<Integer> b = new HashSet<Integer>();

    public AchievementCondition.AchSelfQuestId(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";,");
        while (stringTokenizer.hasMoreTokens()) {
            this.b.add(Integer.parseInt(stringTokenizer.nextToken()));
        }
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null) continue;
            Quest quest = null;
            if (object instanceof Quest) {
                quest = (Quest)object;
            }
            if (object instanceof QuestState) {
                quest = ((QuestState)object).getQuest();
            }
            if (quest == null) continue;
            return this.b.contains(quest.getQuestIntId());
        }
        return false;
    }
}
