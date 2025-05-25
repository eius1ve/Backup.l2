/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class AcquireType
extends Enum<AcquireType> {
    public static final /* enum */ AcquireType NORMAL = new AcquireType(0);
    public static final /* enum */ AcquireType FISHING = new AcquireType(1);
    public static final /* enum */ AcquireType CLAN = new AcquireType(2);
    public static final /* enum */ AcquireType SUB_UNIT = new AcquireType(3);
    public static final /* enum */ AcquireType GENERAL = new AcquireType(11);
    public static final /* enum */ AcquireType CERTIFICATION = new AcquireType(5);
    public static final /* enum */ AcquireType NOBLES = new AcquireType();
    public static final /* enum */ AcquireType HERO = new AcquireType();
    public static final /* enum */ AcquireType CLAN_LEADER = new AcquireType();
    public static final /* enum */ AcquireType PREMIUM_ACCOUNT = new AcquireType();
    public static final /* enum */ AcquireType GM = new AcquireType(14);
    public static final /* enum */ AcquireType MULTICLASS = new AcquireType(20);
    public static final AcquireType[] VALUES;
    private final int kA;
    private static final /* synthetic */ AcquireType[] a;

    public static AcquireType[] values() {
        return (AcquireType[])a.clone();
    }

    public static AcquireType valueOf(String string) {
        return Enum.valueOf(AcquireType.class, string);
    }

    private AcquireType(int n2) {
        this.kA = n2;
    }

    private AcquireType() {
        this.kA = this.ordinal();
    }

    public int getId() {
        return this.kA;
    }

    public static AcquireType getById(int n) {
        for (AcquireType acquireType : VALUES) {
            if (acquireType.getId() != n) continue;
            return acquireType;
        }
        return null;
    }

    private static /* synthetic */ AcquireType[] a() {
        return new AcquireType[]{NORMAL, FISHING, CLAN, SUB_UNIT, GENERAL, CERTIFICATION, NOBLES, HERO, CLAN_LEADER, PREMIUM_ACCOUNT, GM, MULTICLASS};
    }

    static {
        a = AcquireType.a();
        VALUES = AcquireType.values();
    }
}
