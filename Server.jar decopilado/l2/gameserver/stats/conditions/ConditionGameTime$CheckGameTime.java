/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

public static final class ConditionGameTime.CheckGameTime
extends Enum<ConditionGameTime.CheckGameTime> {
    public static final /* enum */ ConditionGameTime.CheckGameTime NIGHT = new ConditionGameTime.CheckGameTime();
    private static final /* synthetic */ ConditionGameTime.CheckGameTime[] a;

    public static ConditionGameTime.CheckGameTime[] values() {
        return (ConditionGameTime.CheckGameTime[])a.clone();
    }

    public static ConditionGameTime.CheckGameTime valueOf(String string) {
        return Enum.valueOf(ConditionGameTime.CheckGameTime.class, string);
    }

    private static /* synthetic */ ConditionGameTime.CheckGameTime[] a() {
        return new ConditionGameTime.CheckGameTime[]{NIGHT};
    }

    static {
        a = ConditionGameTime.CheckGameTime.a();
    }
}
