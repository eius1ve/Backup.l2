/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.spawn;

public final class PeriodOfDay
extends Enum<PeriodOfDay> {
    public static final /* enum */ PeriodOfDay ALL = new PeriodOfDay();
    public static final /* enum */ PeriodOfDay DAY = new PeriodOfDay();
    public static final /* enum */ PeriodOfDay NIGHT = new PeriodOfDay();
    private static final /* synthetic */ PeriodOfDay[] a;

    public static PeriodOfDay[] values() {
        return (PeriodOfDay[])a.clone();
    }

    public static PeriodOfDay valueOf(String string) {
        return Enum.valueOf(PeriodOfDay.class, string);
    }

    private static /* synthetic */ PeriodOfDay[] a() {
        return new PeriodOfDay[]{ALL, DAY, NIGHT};
    }

    static {
        a = PeriodOfDay.a();
    }
}
