/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.reward.RewardData
 */
package achievements;

import achievements.AchievementCondition;
import achievements.AchievementInfo;
import java.util.ArrayList;
import java.util.List;
import l2.gameserver.data.StringHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.reward.RewardData;

public static class AchievementInfo.AchievementInfoLevel
implements Comparable<AchievementInfo.AchievementInfoLevel> {
    private final AchievementInfo b;
    private final int _level;
    private final int _value;
    private final String g;
    private final boolean h;
    private List<RewardData> c = new ArrayList<RewardData>();
    private List<AchievementCondition> d = new ArrayList<AchievementCondition>();

    public AchievementInfo.AchievementInfoLevel(AchievementInfo achievementInfo, int n, int n2, String string, boolean bl) {
        this.b = achievementInfo;
        this._level = n;
        this._value = n2;
        this.g = string;
        this.h = bl;
    }

    public AchievementInfo getAchievementInfo() {
        return this.b;
    }

    public boolean isResetMetric() {
        return this.h;
    }

    public int getLevel() {
        return this._level;
    }

    public int getValue() {
        return this._value;
    }

    public String getDescAddr() {
        return this.g;
    }

    public List<RewardData> getRewardDataList() {
        return this.c;
    }

    public List<AchievementCondition> getCondList() {
        return this.d;
    }

    public void addCond(AchievementCondition achievementCondition) {
        this.d.add(achievementCondition);
    }

    public void addRewardData(RewardData rewardData) {
        this.c.add(rewardData);
    }

    public String getDesc(Player player) {
        return StringHolder.getInstance().getNotNull(player, this.getDescAddr());
    }

    public boolean testConds(Player player, Object ... objectArray) {
        for (AchievementCondition achievementCondition : this.getCondList()) {
            if (achievementCondition.test(player, objectArray)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(AchievementInfo.AchievementInfoLevel achievementInfoLevel) {
        if (this.getAchievementInfo().getId() != achievementInfoLevel.getAchievementInfo().getId()) {
            return Integer.compare(this.getAchievementInfo().getId(), achievementInfoLevel.getAchievementInfo().getId());
        }
        return Integer.compare(this.getLevel(), achievementInfoLevel.getLevel());
    }
}
