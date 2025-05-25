/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.player.OnPvpPkKillListener
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementMetricListeners;
import achievements.AchievementMetricType;
import l2.gameserver.listener.actor.player.OnPvpPkKillListener;
import l2.gameserver.model.Player;

public static class AchievementMetricListeners.AchievementOnPvPPkKill
implements OnPvpPkKillListener {
    public void onPvpPkKill(Player player, Player player2, boolean bl) {
        if (!bl) {
            AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.PVP_KILL, player.getPvpKills(), player2);
        }
    }
}
