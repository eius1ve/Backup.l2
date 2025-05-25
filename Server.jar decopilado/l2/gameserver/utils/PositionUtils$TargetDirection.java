/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

public static final class PositionUtils.TargetDirection
extends Enum<PositionUtils.TargetDirection> {
    public static final /* enum */ PositionUtils.TargetDirection NONE = new PositionUtils.TargetDirection();
    public static final /* enum */ PositionUtils.TargetDirection FRONT = new PositionUtils.TargetDirection();
    public static final /* enum */ PositionUtils.TargetDirection SIDE = new PositionUtils.TargetDirection();
    public static final /* enum */ PositionUtils.TargetDirection BEHIND = new PositionUtils.TargetDirection();
    private static final /* synthetic */ PositionUtils.TargetDirection[] a;

    public static PositionUtils.TargetDirection[] values() {
        return (PositionUtils.TargetDirection[])a.clone();
    }

    public static PositionUtils.TargetDirection valueOf(String string) {
        return Enum.valueOf(PositionUtils.TargetDirection.class, string);
    }

    private static /* synthetic */ PositionUtils.TargetDirection[] a() {
        return new PositionUtils.TargetDirection[]{NONE, FRONT, SIDE, BEHIND};
    }

    static {
        a = PositionUtils.TargetDirection.a();
    }
}
