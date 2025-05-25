/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.DayOfWeekValueParser
extends SchedulingPattern.SimpleValueParser {
    private static String[] F = new String[]{"sun", "mon", "tue", "wed", "thu", "fri", "sat"};

    public SchedulingPattern.DayOfWeekValueParser() {
        super(0, 7);
    }

    @Override
    public int parse(String string) throws Exception {
        try {
            return super.parse(string) % 7;
        }
        catch (Exception exception) {
            return SchedulingPattern.a(string, F, 0);
        }
    }
}
