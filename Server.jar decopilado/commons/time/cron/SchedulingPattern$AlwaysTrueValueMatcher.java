/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.AlwaysTrueValueMatcher
implements SchedulingPattern.ValueMatcher {
    private SchedulingPattern.AlwaysTrueValueMatcher() {
    }

    @Override
    public boolean match(int n) {
        return true;
    }
}
