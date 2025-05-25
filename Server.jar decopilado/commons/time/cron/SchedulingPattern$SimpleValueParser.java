/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.SimpleValueParser
implements SchedulingPattern.ValueParser {
    protected int minValue;
    protected int maxValue;

    public SchedulingPattern.SimpleValueParser(int n, int n2) {
        this.minValue = n;
        this.maxValue = n2;
    }

    @Override
    public int parse(String string) throws Exception {
        int n;
        try {
            n = Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            throw new Exception("invalid integer value");
        }
        if (n < this.minValue || n > this.maxValue) {
            throw new Exception("value out of range");
        }
        return n;
    }

    @Override
    public int getMinValue() {
        return this.minValue;
    }

    @Override
    public int getMaxValue() {
        return this.maxValue;
    }
}
