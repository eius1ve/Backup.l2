/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public final class FishGrade
extends Enum<FishGrade> {
    public static final /* enum */ FishGrade EASY = new FishGrade();
    public static final /* enum */ FishGrade NORMAL = new FishGrade();
    public static final /* enum */ FishGrade HARD = new FishGrade();
    private static final /* synthetic */ FishGrade[] a;

    public static FishGrade[] values() {
        return (FishGrade[])a.clone();
    }

    public static FishGrade valueOf(String string) {
        return Enum.valueOf(FishGrade.class, string);
    }

    private static /* synthetic */ FishGrade[] a() {
        return new FishGrade[]{EASY, NORMAL, HARD};
    }

    static {
        a = FishGrade.a();
    }
}
