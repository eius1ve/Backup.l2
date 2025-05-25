/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public final class EnsoulFeeSlotType
extends Enum<EnsoulFeeSlotType> {
    public static final /* enum */ EnsoulFeeSlotType Normal = new EnsoulFeeSlotType();
    public static final /* enum */ EnsoulFeeSlotType Bm = new EnsoulFeeSlotType();
    private static final /* synthetic */ EnsoulFeeSlotType[] a;

    public static EnsoulFeeSlotType[] values() {
        return (EnsoulFeeSlotType[])a.clone();
    }

    public static EnsoulFeeSlotType valueOf(String string) {
        return Enum.valueOf(EnsoulFeeSlotType.class, string);
    }

    public static EnsoulFeeSlotType getSlotType(int n) {
        switch (n) {
            case 1: {
                return Normal;
            }
            case 2: {
                return Bm;
            }
        }
        return null;
    }

    private static /* synthetic */ EnsoulFeeSlotType[] a() {
        return new EnsoulFeeSlotType[]{Normal, Bm};
    }

    static {
        a = EnsoulFeeSlotType.a();
    }
}
