/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnOlyCompetitionListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.oly.Competition
 */
package achievements;

import achievements.AchievementMetricListeners;
import achievements.AchievementMetricType;
import l2.gameserver.listener.actor.player.OnOlyCompetitionListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.Competition;

public static class AchievementMetricListeners.AchievementOnOlyCompetitionCompleted
implements OnOlyCompetitionListener {
    public void onOlyCompetitionCompleted(Player player, Competition competition, boolean bl) {
        AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.OLYMPIAD, competition, bl);
    }
}
