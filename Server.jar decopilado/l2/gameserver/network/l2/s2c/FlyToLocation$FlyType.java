/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class FlyToLocation.FlyType
extends Enum<FlyToLocation.FlyType> {
    public static final /* enum */ FlyToLocation.FlyType THROW_UP = new FlyToLocation.FlyType();
    public static final /* enum */ FlyToLocation.FlyType THROW_HORIZONTAL = new FlyToLocation.FlyType();
    public static final /* enum */ FlyToLocation.FlyType DUMMY = new FlyToLocation.FlyType();
    public static final /* enum */ FlyToLocation.FlyType CHARGE = new FlyToLocation.FlyType();
    public static final /* enum */ FlyToLocation.FlyType NONE = new FlyToLocation.FlyType();
    private static final /* synthetic */ FlyToLocation.FlyType[] a;

    public static FlyToLocation.FlyType[] values() {
        return (FlyToLocation.FlyType[])a.clone();
    }

    public static FlyToLocation.FlyType valueOf(String string) {
        return Enum.valueOf(FlyToLocation.FlyType.class, string);
    }

    private static /* synthetic */ FlyToLocation.FlyType[] a() {
        return new FlyToLocation.FlyType[]{THROW_UP, THROW_HORIZONTAL, DUMMY, CHARGE, NONE};
    }

    static {
        a = FlyToLocation.FlyType.a();
    }
}
