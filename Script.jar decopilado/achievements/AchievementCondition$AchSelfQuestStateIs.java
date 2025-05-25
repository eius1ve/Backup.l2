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
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;

@AchievementCondition.AchievementConditionName(value="self_quest_state_is")
public static class AchievementCondition.AchSelfQuestStateIs
extends AchievementCondition {
    private final int d;

    public AchievementCondition.AchSelfQuestStateIs(String string) {
        this.d = Quest.getStateId((String)string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null) continue;
            QuestState questState = null;
            if (object instanceof QuestState) {
                questState = (QuestState)object;
            }
            if (object instanceof Quest) {
                questState = player.getQuestState((Quest)object);
            }
            if (questState == null) continue;
            return questState.getState() == this.d;
        }
        return false;
    }
}
