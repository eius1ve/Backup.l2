/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oneDayReward;

public static final class OneDayReward.ResetTime
extends Enum<OneDayReward.ResetTime> {
    public static final /* enum */ OneDayReward.ResetTime NULL = new OneDayReward.ResetTime();
    public static final /* enum */ OneDayReward.ResetTime DAILY = new OneDayReward.ResetTime();
    public static final /* enum */ OneDayReward.ResetTime WEEKLY = new OneDayReward.ResetTime();
    public static final /* enum */ OneDayReward.ResetTime MONTHLY = new OneDayReward.ResetTime();
    public static final /* enum */ OneDayReward.ResetTime SINGLE = new OneDayReward.ResetTime();
    private static final /* synthetic */ OneDayReward.ResetTime[] a;

    public static OneDayReward.ResetTime[] values() {
        return (OneDayReward.ResetTime[])a.clone();
    }

    public static OneDayReward.ResetTime valueOf(String string) {
        return Enum.valueOf(OneDayReward.ResetTime.class, string);
    }

    private static /* synthetic */ OneDayReward.ResetTime[] a() {
        return new OneDayReward.ResetTime[]{NULL, DAILY, WEEKLY, MONTHLY, SINGLE};
    }

    static {
        a = OneDayReward.ResetTime.a();
    }
}
