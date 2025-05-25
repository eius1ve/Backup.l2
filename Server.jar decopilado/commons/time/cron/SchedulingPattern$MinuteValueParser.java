/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.time.cron;

import l2.commons.time.cron.SchedulingPattern;

private static class SchedulingPattern.MinuteValueParser
extends SchedulingPattern.SimpleValueParser {
    public SchedulingPattern.MinuteValueParser() {
        super(0, 59);
    }
}
