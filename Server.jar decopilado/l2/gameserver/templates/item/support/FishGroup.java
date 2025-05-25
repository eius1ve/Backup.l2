/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public final class FishGroup
extends Enum<FishGroup> {
    public static final /* enum */ FishGroup EASY_WIDE = new FishGroup();
    public static final /* enum */ FishGroup EASY_SWIFT = new FishGroup();
    public static final /* enum */ FishGroup EASY_UGLY = new FishGroup();
    public static final /* enum */ FishGroup HARD_WIDE = new FishGroup();
    public static final /* enum */ FishGroup HARD_SWIFT = new FishGroup();
    public static final /* enum */ FishGroup HARD_UGLY = new FishGroup();
    public static final /* enum */ FishGroup WIDE = new FishGroup();
    public static final /* enum */ FishGroup SWIFT = new FishGroup();
    public static final /* enum */ FishGroup UGLY = new FishGroup();
    public static final /* enum */ FishGroup BOX = new FishGroup();
    public static final /* enum */ FishGroup HS = new FishGroup();
    private static final /* synthetic */ FishGroup[] a;

    public static FishGroup[] values() {
        return (FishGroup[])a.clone();
    }

    public static FishGroup valueOf(String string) {
        return Enum.valueOf(FishGroup.class, string);
    }

    private static /* synthetic */ FishGroup[] a() {
        return new FishGroup[]{EASY_WIDE, EASY_SWIFT, EASY_UGLY, HARD_WIDE, HARD_SWIFT, HARD_UGLY, WIDE, SWIFT, UGLY, BOX, HS};
    }

    static {
        a = FishGroup.a();
    }
}
