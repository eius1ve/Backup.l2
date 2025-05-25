/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.MonthValueParser
extends SchedulingPattern.SimpleValueParser {
    private static String[] F = new String[]{"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"};

    public SchedulingPattern.MonthValueParser() {
        super(1, 12);
    }

    @Override
    public int parse(String string) throws Exception {
        try {
            return super.parse(string);
        }
        catch (Exception exception) {
            return SchedulingPattern.a(string, F, 1);
        }
    }
}
