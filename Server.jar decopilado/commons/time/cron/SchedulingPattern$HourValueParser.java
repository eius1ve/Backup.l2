/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.HourValueParser
extends SchedulingPattern.SimpleValueParser {
    public SchedulingPattern.HourValueParser() {
        super(0, 23);
    }
}
