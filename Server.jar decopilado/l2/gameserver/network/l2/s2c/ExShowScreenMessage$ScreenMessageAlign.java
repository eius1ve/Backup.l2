/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class ExShowScreenMessage.ScreenMessageAlign
extends Enum<ExShowScreenMessage.ScreenMessageAlign> {
    public static final /* enum */ ExShowScreenMessage.ScreenMessageAlign TOP_LEFT = new ExShowScreenMessage.ScreenMessageAlign();
    public static final /* enum */ ExShowScreenMessage.ScreenMessageAlign TOP_CENTER = new ExShowScreenMessage.ScreenMessageAlign();
    public static final /* enum */ ExShowScreenMessage.ScreenMessageAlign TOP_RIGHT = new ExShowScreenMessage.ScreenMessageAlign();
    public static final /* enum */ ExShowScreenMessage.ScreenMessageAlign MIDDLE_LEFT = new ExShowScreenMessage.ScreenMessageAlign();
    public static final /* enum */ ExShowScreenMessage.ScreenMessageAlign MIDDLE_CENTER = new ExShowScreenMessage.ScreenMessageAlign();
    public static final /* enum */ ExShowScreenMessage.ScreenMessageAlign MIDDLE_RIGHT = new ExShowScreenMessage.ScreenMessageAlign();
    public static final /* enum */ ExShowScreenMessage.ScreenMessageAlign BOTTOM_CENTER = new ExShowScreenMessage.ScreenMessageAlign();
    public static final /* enum */ ExShowScreenMessage.ScreenMessageAlign BOTTOM_RIGHT = new ExShowScreenMessage.ScreenMessageAlign();
    private static final /* synthetic */ ExShowScreenMessage.ScreenMessageAlign[] a;

    public static ExShowScreenMessage.ScreenMessageAlign[] values() {
        return (ExShowScreenMessage.ScreenMessageAlign[])a.clone();
    }

    public static ExShowScreenMessage.ScreenMessageAlign valueOf(String string) {
        return Enum.valueOf(ExShowScreenMessage.ScreenMessageAlign.class, string);
    }

    private static /* synthetic */ ExShowScreenMessage.ScreenMessageAlign[] a() {
        return new ExShowScreenMessage.ScreenMessageAlign[]{TOP_LEFT, TOP_CENTER, TOP_RIGHT, MIDDLE_LEFT, MIDDLE_CENTER, MIDDLE_RIGHT, BOTTOM_CENTER, BOTTOM_RIGHT};
    }

    static {
        a = ExShowScreenMessage.ScreenMessageAlign.a();
    }
}
