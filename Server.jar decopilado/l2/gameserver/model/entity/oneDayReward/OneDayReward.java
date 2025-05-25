/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.model.entity.oneDayReward;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.OneDayDistributionType;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.stats.conditions.Condition;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OneDayReward {
    private final int lQ;
    private final Set<Integer> w;
    private final OneDayRewardRequirement a;
    private final ResetTime a;
    private final Condition a;
    private final List<Pair<Integer, Long>> bE;
    private final OneDayDistributionType a;

    public OneDayReward(int n, Set<Integer> set, OneDayRewardRequirement oneDayRewardRequirement, ResetTime resetTime, List<Pair<Integer, Long>> list, Condition condition, OneDayDistributionType oneDayDistributionType) {
        this.lQ = n;
        this.w = set;
        this.a = oneDayRewardRequirement;
        this.a = resetTime;
        this.a = condition;
        this.bE = Collections.unmodifiableList(list);
        this.a = oneDayDistributionType != null ? oneDayDistributionType : OneDayDistributionType.SOLO;
    }

    public boolean accept(Player player) {
        if (this.w.isEmpty()) {
            return true;
        }
        return this.w.contains(player.getActiveClassId());
    }

    public Condition getCondition() {
        return this.a;
    }

    public List<Pair<Integer, Long>> getRewards() {
        return this.bE;
    }

    public ResetTime getResetTime() {
        return this.a;
    }

    public OneDayRewardRequirement getRequirement() {
        return this.a;
    }

    public int getId() {
        return this.lQ;
    }

    public OneDayDistributionType getDistributionType() {
        return this.a;
    }

    public static final class ResetTime
    extends Enum<ResetTime> {
        public static final /* enum */ ResetTime NULL = new ResetTime();
        public static final /* enum */ ResetTime DAILY = new ResetTime();
        public static final /* enum */ ResetTime WEEKLY = new ResetTime();
        public static final /* enum */ ResetTime MONTHLY = new ResetTime();
        public static final /* enum */ ResetTime SINGLE = new ResetTime();
        private static final /* synthetic */ ResetTime[] a;

        public static ResetTime[] values() {
            return (ResetTime[])a.clone();
        }

        public static ResetTime valueOf(String string) {
            return Enum.valueOf(ResetTime.class, string);
        }

        private static /* synthetic */ ResetTime[] a() {
            return new ResetTime[]{NULL, DAILY, WEEKLY, MONTHLY, SINGLE};
        }

        static {
            a = ResetTime.a();
        }
    }
}
