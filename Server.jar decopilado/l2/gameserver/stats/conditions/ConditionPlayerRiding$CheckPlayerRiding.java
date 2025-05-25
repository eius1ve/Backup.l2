/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

public static final class ConditionPlayerRiding.CheckPlayerRiding
extends Enum<ConditionPlayerRiding.CheckPlayerRiding> {
    public static final /* enum */ ConditionPlayerRiding.CheckPlayerRiding NONE = new ConditionPlayerRiding.CheckPlayerRiding();
    public static final /* enum */ ConditionPlayerRiding.CheckPlayerRiding STRIDER = new ConditionPlayerRiding.CheckPlayerRiding();
    public static final /* enum */ ConditionPlayerRiding.CheckPlayerRiding WYVERN = new ConditionPlayerRiding.CheckPlayerRiding();
    private static final /* synthetic */ ConditionPlayerRiding.CheckPlayerRiding[] a;

    public static ConditionPlayerRiding.CheckPlayerRiding[] values() {
        return (ConditionPlayerRiding.CheckPlayerRiding[])a.clone();
    }

    public static ConditionPlayerRiding.CheckPlayerRiding valueOf(String string) {
        return Enum.valueOf(ConditionPlayerRiding.CheckPlayerRiding.class, string);
    }

    private static /* synthetic */ ConditionPlayerRiding.CheckPlayerRiding[] a() {
        return new ConditionPlayerRiding.CheckPlayerRiding[]{NONE, STRIDER, WYVERN};
    }

    static {
        a = ConditionPlayerRiding.CheckPlayerRiding.a();
    }
}
