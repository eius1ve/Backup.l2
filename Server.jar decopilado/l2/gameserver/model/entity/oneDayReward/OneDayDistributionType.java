/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward;

public final class OneDayDistributionType
extends Enum<OneDayDistributionType> {
    public static final /* enum */ OneDayDistributionType SOLO = new OneDayDistributionType();
    public static final /* enum */ OneDayDistributionType PARTY_RANDOM = new OneDayDistributionType();
    public static final /* enum */ OneDayDistributionType PARTY_ALL = new OneDayDistributionType();
    private static final /* synthetic */ OneDayDistributionType[] a;

    public static OneDayDistributionType[] values() {
        return (OneDayDistributionType[])a.clone();
    }

    public static OneDayDistributionType valueOf(String string) {
        return Enum.valueOf(OneDayDistributionType.class, string);
    }

    public static OneDayDistributionType fromString(String string) {
        if (string == null) {
            return SOLO;
        }
        for (OneDayDistributionType oneDayDistributionType : OneDayDistributionType.values()) {
            if (!oneDayDistributionType.name().equalsIgnoreCase(string)) continue;
            return oneDayDistributionType;
        }
        throw new IllegalArgumentException("Unknown reward type: " + string);
    }

    private static /* synthetic */ OneDayDistributionType[] a() {
        return new OneDayDistributionType[]{SOLO, PARTY_RANDOM, PARTY_ALL};
    }

    static {
        a = OneDayDistributionType.a();
    }
}
