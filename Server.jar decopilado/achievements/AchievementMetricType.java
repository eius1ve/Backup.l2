/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package achievements;

import achievements.AchievementCounter;
import achievements.AchievementInfo;
import l2.gameserver.model.Player;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public class AchievementMetricType
extends Enum<AchievementMetricType> {
    public static final /* enum */ AchievementMetricType LOGIN = new AchievementMetricType();
    public static final /* enum */ AchievementMetricType NPC_KILL = new AchievementMetricType();
    public static final /* enum */ AchievementMetricType PVP_KILL = new AchievementMetricType();
    public static final /* enum */ AchievementMetricType DEATH = new AchievementMetricType();
    public static final /* enum */ AchievementMetricType LEVEL = new AchievementMetricType(){

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
    };
    public static final /* enum */ AchievementMetricType OLYMPIAD = new AchievementMetricType();
    public static final /* enum */ AchievementMetricType RAID_PARTICIPATION = new AchievementMetricType();
    public static final /* enum */ AchievementMetricType QUEST_STATE = new AchievementMetricType();
    private static final /* synthetic */ AchievementMetricType[] a;

    public static AchievementMetricType[] values() {
        return (AchievementMetricType[])a.clone();
    }

    public static AchievementMetricType valueOf(String string) {
        return Enum.valueOf(AchievementMetricType.class, string);
    }

    public AchievementCounter getCounter(Player player, AchievementInfo achievementInfo) {
        return AchievementCounter.makeDBStorableCounter(player.getObjectId(), achievementInfo.getId());
    }

    private static /* synthetic */ AchievementMetricType[] a() {
        return new AchievementMetricType[]{LOGIN, NPC_KILL, PVP_KILL, DEATH, LEVEL, OLYMPIAD, RAID_PARTICIPATION, QUEST_STATE};
    }

    static {
        a = AchievementMetricType.a();
    }
}
