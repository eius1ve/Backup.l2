/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnGainExpSpListener
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementMetricListeners;
import achievements.AchievementMetricType;
import l2.gameserver.listener.actor.player.OnGainExpSpListener;
import l2.gameserver.model.Player;

public static class AchievementMetricListeners.AchievementOnGainExpSp
implements OnGainExpSpListener {
    public void onGainExpSp(Player player, long l, long l2) {
        AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.LEVEL, player.getLevel());
    }
}
