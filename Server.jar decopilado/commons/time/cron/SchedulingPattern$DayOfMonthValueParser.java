/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.DayOfMonthValueParser
extends SchedulingPattern.SimpleValueParser {
    public SchedulingPattern.DayOfMonthValueParser() {
        super(1, 31);
    }

    @Override
    public int parse(String string) throws Exception {
        if (string.equalsIgnoreCase("L")) {
            return 32;
        }
        return super.parse(string);
    }
}
