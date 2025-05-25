/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import java.util.List;
import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.DayOfMonthValueMatcher
extends SchedulingPattern.IntArrayValueMatcher {
    private static final int[] am = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public SchedulingPattern.DayOfMonthValueMatcher(List<Integer> list) {
        super(list);
    }

    public boolean match(int n, int n2, boolean bl) {
        return super.match(n) || n > 27 && this.match(32) && SchedulingPattern.DayOfMonthValueMatcher.isLastDayOfMonth(n, n2, bl);
    }

    public static int getLastDayOfMonth(int n, boolean bl) {
        if (bl && n == 2) {
            return 29;
        }
        return am[n - 1];
    }

    public static boolean isLastDayOfMonth(int n, int n2, boolean bl) {
        return n == SchedulingPattern.DayOfMonthValueMatcher.getLastDayOfMonth(n2, bl);
    }
}
