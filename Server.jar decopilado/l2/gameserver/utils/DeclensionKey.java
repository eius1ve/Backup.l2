/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

public final class DeclensionKey
extends Enum<DeclensionKey> {
    public static final /* enum */ DeclensionKey DAYS = new DeclensionKey();
    public static final /* enum */ DeclensionKey HOUR = new DeclensionKey();
    public static final /* enum */ DeclensionKey MINUTES = new DeclensionKey();
    public static final /* enum */ DeclensionKey PIECE = new DeclensionKey();
    public static final /* enum */ DeclensionKey POINT = new DeclensionKey();
    private static final /* synthetic */ DeclensionKey[] a;

    public static DeclensionKey[] values() {
        return (DeclensionKey[])a.clone();
    }

    public static DeclensionKey valueOf(String string) {
        return Enum.valueOf(DeclensionKey.class, string);
    }

    private static /* synthetic */ DeclensionKey[] a() {
        return new DeclensionKey[]{DAYS, HOUR, MINUTES, PIECE, POINT};
    }

    static {
        a = DeclensionKey.a();
    }
}
