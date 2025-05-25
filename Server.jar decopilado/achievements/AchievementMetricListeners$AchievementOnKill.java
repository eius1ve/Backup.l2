/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.OnKillListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.RaidBossInstance
 *  l2.gameserver.utils.Location
 */
package achievements;

import achievements.AchievementMetricListeners;
import achievements.AchievementMetricType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import l2.gameserver.listener.actor.OnKillListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.utils.Location;

public static class AchievementMetricListeners.AchievementOnKill
implements OnKillListener {
    public void onKill(Creature creature, Creature creature2) {
        if (creature == null || !creature.isPlayer() || creature2 == null) {
            return;
        }
        if (creature2.isNpc()) {
            AchievementMetricListeners.getInstance().metricEvent(creature.getPlayer(), AchievementMetricType.NPC_KILL, creature2);
            if (creature2 instanceof RaidBossInstance) {
                RaidBossInstance raidBossInstance = (RaidBossInstance)creature2;
                Location location = raidBossInstance.getLoc();
                ArrayList arrayList = new ArrayList(raidBossInstance.getAggroList().getCharMap().keySet());
                LinkedHashSet<Player> linkedHashSet = new LinkedHashSet<Player>();
                for (Creature creature3 : arrayList) {
                    if (creature3 == null || location.distance3D(creature3.getLoc()) > 1500.0 || !(creature3 instanceof Player)) continue;
                    linkedHashSet.add((Player)creature3);
                }
                for (Player player : linkedHashSet) {
                    AchievementMetricListeners.getInstance().metricEvent(player, AchievementMetricType.RAID_PARTICIPATION, creature2);
                }
            }
        }
    }

    public boolean ignorePetOrSummon() {
        return true;
    }
}
