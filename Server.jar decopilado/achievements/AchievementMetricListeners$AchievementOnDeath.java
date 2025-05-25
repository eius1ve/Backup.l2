/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 */
package achievements;

import achievements.AchievementMetricListeners;
import achievements.AchievementMetricType;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;

public static class AchievementMetricListeners.AchievementOnDeath
implements OnDeathListener {
    public void onDeath(Creature creature, Creature creature2) {
        if (creature.isPlayer()) {
            AchievementMetricListeners.getInstance().metricEvent(creature.getPlayer(), AchievementMetricType.DEATH, creature2);
        }
    }
}
