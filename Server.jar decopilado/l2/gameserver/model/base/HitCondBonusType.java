/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class HitCondBonusType
extends Enum<HitCondBonusType> {
    public static final /* enum */ HitCondBonusType ahead = new HitCondBonusType();
    public static final /* enum */ HitCondBonusType side = new HitCondBonusType();
    public static final /* enum */ HitCondBonusType back = new HitCondBonusType();
    public static final /* enum */ HitCondBonusType high = new HitCondBonusType();
    public static final /* enum */ HitCondBonusType low = new HitCondBonusType();
    public static final /* enum */ HitCondBonusType dark = new HitCondBonusType();
    public static final /* enum */ HitCondBonusType rain = new HitCondBonusType();
    public static final HitCondBonusType[] VALUES;
    private static final /* synthetic */ HitCondBonusType[] a;

    public static HitCondBonusType[] values() {
        return (HitCondBonusType[])a.clone();
    }

    public static HitCondBonusType valueOf(String string) {
        return Enum.valueOf(HitCondBonusType.class, string);
    }

    private static /* synthetic */ HitCondBonusType[] a() {
        return new HitCondBonusType[]{ahead, side, back, high, low, dark, rain};
    }

    static {
        a = HitCondBonusType.a();
        VALUES = HitCondBonusType.values();
    }
}
