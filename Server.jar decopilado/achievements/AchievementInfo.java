/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.time.cron.NextTime
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.reward.RewardData
 */
package achievements;

import achievements.AchievementCondition;
import achievements.AchievementMetricType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import l2.commons.time.cron.NextTime;
import l2.gameserver.data.StringHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.reward.RewardData;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AchievementInfo {
    private final int _id;
    private final AchievementMetricType a;
    private final long c;
    private final String e;
    private final NextTime a;
    private AchievementInfoCategory a;
    private int b;
    private int c;
    private List<AchievementCondition> a;
    private Map<Integer, AchievementInfoLevel> a;
    private List<AchievementInfoLevel> b = Integer.MAX_VALUE;
    private String _icon = "icon.etc_plan_i00";

    public AchievementInfo(int n, AchievementMetricType achievementMetricType, long l, String string, NextTime nextTime) {
        this.c = Integer.MIN_VALUE;
        this.a = new ArrayList();
        this.a = new TreeMap();
        this.b = (int)new ArrayList();
        this._id = n;
        this.a = achievementMetricType;
        this.c = l;
        this.e = string;
        this.a = nextTime;
        this.c = 0;
    }

    public String getIcon() {
        return this._icon;
    }

    public void setIcon(String string) {
        this._icon = string;
    }

    public AchievementInfoCategory getCategory() {
        return this.a;
    }

    public void setCategory(AchievementInfoCategory achievementInfoCategory) {
        this.a = achievementInfoCategory;
    }

    public long nextResetTimeMills() {
        if (this.a == null) {
            return -1L;
        }
        return this.a.next(System.currentTimeMillis());
    }

    public int getId() {
        return this._id;
    }

    public void addCond(AchievementCondition achievementCondition) {
        this.a.add(achievementCondition);
    }

    public void addLevel(AchievementInfoLevel achievementInfoLevel) {
        if (achievementInfoLevel.getLevel() > this.c) {
            this.c = achievementInfoLevel.getLevel();
        }
        if (achievementInfoLevel.getLevel() < this.b) {
            this.b = achievementInfoLevel.getLevel();
        }
        this.a.put(achievementInfoLevel.getLevel(), achievementInfoLevel);
        this.b.add(achievementInfoLevel);
        Collections.sort(this.b);
    }

    public AchievementInfoLevel addLevel(int n, int n2, String string, boolean bl) {
        AchievementInfoLevel achievementInfoLevel = new AchievementInfoLevel(this, n, n2, string, bl);
        this.addLevel(achievementInfoLevel);
        return achievementInfoLevel;
    }

    public int getMaxLevel() {
        return this.c;
    }

    public List<AchievementCondition> getCondList() {
        return this.a;
    }

    public void setCondList(List<AchievementCondition> list) {
        this.a = list;
    }

    public AchievementMetricType getMetricType() {
        return this.a;
    }

    public long getMetricNotifyDelay() {
        return this.c;
    }

    public List<AchievementInfoLevel> getLevels() {
        return this.b;
    }

    public AchievementInfoLevel getLevel(int n) {
        return (AchievementInfoLevel)this.a.get(n);
    }

    public String getNameAddr() {
        return this.e;
    }

    public String getName(Player player) {
        return StringHolder.getInstance().getNotNull(player, this.getNameAddr());
    }

    public boolean testConds(Player player, Object ... objectArray) {
        for (AchievementCondition achievementCondition : this.getCondList()) {
            if (achievementCondition.test(player, objectArray)) continue;
            return false;
        }
        return true;
    }

    public static class AchievementInfoCategory {
        private final String _name;
        private final String f;

        public AchievementInfoCategory(String string, String string2) {
            this._name = string;
            this.f = string2;
        }

        public String getName() {
            return this._name;
        }

        public String getTitle(Player player) {
            return StringHolder.getInstance().getNotNull(player, this.f);
        }
    }

    public static class AchievementInfoLevel
    implements Comparable<AchievementInfoLevel> {
        private final AchievementInfo b;
        private final int _level;
        private final int _value;
        private final String g;
        private final boolean h;
        private List<RewardData> c = new ArrayList<RewardData>();
        private List<AchievementCondition> d = new ArrayList<AchievementCondition>();

        public AchievementInfoLevel(AchievementInfo achievementInfo, int n, int n2, String string, boolean bl) {
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
        public int compareTo(AchievementInfoLevel achievementInfoLevel) {
            if (this.getAchievementInfo().getId() != achievementInfoLevel.getAchievementInfo().getId()) {
                return Integer.compare(this.getAchievementInfo().getId(), achievementInfoLevel.getAchievementInfo().getId());
            }
            return Integer.compare(this.getLevel(), achievementInfoLevel.getLevel());
        }
    }
}
