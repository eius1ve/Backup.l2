/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

private static interface SchedulingPattern.ValueParser {
    public int parse(String var1) throws Exception;

    public int getMinValue();

    public int getMaxValue();
}
