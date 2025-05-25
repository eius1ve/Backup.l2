/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public final class LureType
extends Enum<LureType> {
    public static final /* enum */ LureType NORMAL = new LureType();
    public static final /* enum */ LureType NIGHT = new LureType();
    private static final /* synthetic */ LureType[] a;

    public static LureType[] values() {
        return (LureType[])a.clone();
    }

    public static LureType valueOf(String string) {
        return Enum.valueOf(LureType.class, string);
    }

    private static /* synthetic */ LureType[] a() {
        return new LureType[]{NORMAL, NIGHT};
    }

    static {
        a = LureType.a();
    }
}
