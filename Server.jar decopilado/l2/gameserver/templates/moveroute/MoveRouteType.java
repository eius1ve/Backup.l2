/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.moveroute;

public final class MoveRouteType
extends Enum<MoveRouteType> {
    public static final /* enum */ MoveRouteType LOOP = new MoveRouteType();
    public static final /* enum */ MoveRouteType CIRCLE = new MoveRouteType();
    public static final /* enum */ MoveRouteType ONCE = new MoveRouteType();
    private static final /* synthetic */ MoveRouteType[] a;

    public static MoveRouteType[] values() {
        return (MoveRouteType[])a.clone();
    }

    public static MoveRouteType valueOf(String string) {
        return Enum.valueOf(MoveRouteType.class, string);
    }

    private static /* synthetic */ MoveRouteType[] a() {
        return new MoveRouteType[]{LOOP, CIRCLE, ONCE};
    }

    static {
        a = MoveRouteType.a();
    }
}
