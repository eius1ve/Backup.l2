/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class RestartType
extends Enum<RestartType> {
    public static final /* enum */ RestartType TO_VILLAGE = new RestartType();
    public static final /* enum */ RestartType TO_CLANHALL = new RestartType();
    public static final /* enum */ RestartType TO_CASTLE = new RestartType();
    public static final /* enum */ RestartType TO_FORTRESS = new RestartType();
    public static final /* enum */ RestartType TO_FLAG = new RestartType();
    public static final /* enum */ RestartType FIXED = new RestartType();
    public static final /* enum */ RestartType AGATHION = new RestartType();
    public static final RestartType[] VALUES;
    private static final /* synthetic */ RestartType[] a;

    public static RestartType[] values() {
        return (RestartType[])a.clone();
    }

    public static RestartType valueOf(String string) {
        return Enum.valueOf(RestartType.class, string);
    }

    private static /* synthetic */ RestartType[] a() {
        return new RestartType[]{TO_VILLAGE, TO_CLANHALL, TO_CASTLE, TO_FORTRESS, TO_FLAG, FIXED, AGATHION};
    }

    static {
        a = RestartType.a();
        VALUES = RestartType.values();
    }
}
