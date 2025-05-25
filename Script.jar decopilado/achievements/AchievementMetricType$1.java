/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCounter;
import achievements.AchievementInfo;
import achievements.AchievementMetricType;
import l2.gameserver.model.Player;

final class AchievementMetricType.1
extends AchievementMetricType {
    @Override
    public AchievementCounter getCounter(Player player, AchievementInfo achievementInfo) {
        final int n = player.getLevel();
        return new AchievementCounter(player.getObjectId(), achievementInfo.getId()){

            @Override
            public int getVal() {
                return n;
            }

            @Override
            public void setVal(int n2) {
            }

            @Override
            public int incrementAndGetValue() {
                return n;
            }

            @Override
            public void store() {
            }

            @Override
            public boolean isStorable() {
                return false;
            }
        };
    }
}
