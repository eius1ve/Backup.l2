/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import java.util.List;
import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.IntArrayValueMatcher
implements SchedulingPattern.ValueMatcher {
    private int[] an;

    public SchedulingPattern.IntArrayValueMatcher(List<Integer> list) {
        int n = list.size();
        this.an = new int[n];
        for (int i = 0; i < n; ++i) {
            try {
                this.an[i] = list.get(i);
                continue;
            }
            catch (Exception exception) {
                throw new IllegalArgumentException(exception.getMessage());
            }
        }
    }

    @Override
    public boolean match(int n) {
        for (int i = 0; i < this.an.length; ++i) {
            if (this.an[i] != n) continue;
            return true;
        }
        return false;
    }
}
