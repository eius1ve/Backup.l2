/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

public static final class SummonSiegeFlag.FlagType
extends Enum<SummonSiegeFlag.FlagType> {
    public static final /* enum */ SummonSiegeFlag.FlagType DESTROY = new SummonSiegeFlag.FlagType();
    public static final /* enum */ SummonSiegeFlag.FlagType NORMAL = new SummonSiegeFlag.FlagType();
    public static final /* enum */ SummonSiegeFlag.FlagType ADVANCED = new SummonSiegeFlag.FlagType();
    public static final /* enum */ SummonSiegeFlag.FlagType OUTPOST = new SummonSiegeFlag.FlagType();
    private static final /* synthetic */ SummonSiegeFlag.FlagType[] a;

    public static SummonSiegeFlag.FlagType[] values() {
        return (SummonSiegeFlag.FlagType[])a.clone();
    }

    public static SummonSiegeFlag.FlagType valueOf(String string) {
        return Enum.valueOf(SummonSiegeFlag.FlagType.class, string);
    }

    private static /* synthetic */ SummonSiegeFlag.FlagType[] a() {
        return new SummonSiegeFlag.FlagType[]{DESTROY, NORMAL, ADVANCED, OUTPOST};
    }

    static {
        a = SummonSiegeFlag.FlagType.a();
    }
}
