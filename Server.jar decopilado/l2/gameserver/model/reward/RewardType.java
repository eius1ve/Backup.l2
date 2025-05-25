/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.reward;

public final class RewardType
extends Enum<RewardType> {
    public static final /* enum */ RewardType RATED_GROUPED = new RewardType();
    public static final /* enum */ RewardType NOT_RATED_NOT_GROUPED = new RewardType();
    public static final /* enum */ RewardType NOT_RATED_GROUPED = new RewardType();
    public static final /* enum */ RewardType SWEEP = new RewardType();
    public static final /* enum */ RewardType DISABLED = new RewardType();
    public static final /* enum */ RewardType RATED_NOT_GROUPED = new RewardType();
    public static final RewardType[] VALUES;
    private static final /* synthetic */ RewardType[] b;

    public static RewardType[] values() {
        return (RewardType[])b.clone();
    }

    public static RewardType valueOf(String string) {
        return Enum.valueOf(RewardType.class, string);
    }

    private static /* synthetic */ RewardType[] a() {
        return new RewardType[]{RATED_GROUPED, NOT_RATED_NOT_GROUPED, NOT_RATED_GROUPED, SWEEP, DISABLED, RATED_NOT_GROUPED};
    }

    static {
        b = RewardType.a();
        VALUES = RewardType.values();
    }
}
