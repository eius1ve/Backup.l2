/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.threading;

private class RunnableStatsManager.ClassStat {
    private final Class<?> a;
    private long au = 0L;
    private long av = 0L;
    private long aw = Long.MAX_VALUE;
    private long ax = Long.MIN_VALUE;

    private RunnableStatsManager.ClassStat(Class<?> clazz) {
        this.a = clazz;
        RunnableStatsManager.this.y.put(clazz, this);
    }
}
