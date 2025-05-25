/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementMetricListeners;
import achievements.AchievementMetricType;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;

public static class AchievementMetricListeners.AchievementOnPlayerEnter
implements OnPlayerEnterListener {
    public void onPlayerEnter(Player player) {
        AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.LOGIN, player);
    }
}
