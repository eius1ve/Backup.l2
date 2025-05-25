/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnQuestStateChangeListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.quest.QuestState
 */
package achievements;

import achievements.AchievementMetricListeners;
import achievements.AchievementMetricType;
import l2.gameserver.listener.actor.player.OnQuestStateChangeListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.quest.QuestState;

public static class AchievementMetricListeners.AchievementOnQuestStateChange
implements OnQuestStateChangeListener {
    public void onQuestStateChange(Player player, QuestState questState) {
        AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.QUEST_STATE, questState);
    }
}
