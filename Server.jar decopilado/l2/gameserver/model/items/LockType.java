/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

public final class LockType
extends Enum<LockType> {
    public static final /* enum */ LockType INCLUDE = new LockType();
    public static final /* enum */ LockType EXCLUDE = new LockType();
    public static final /* enum */ LockType NONE = new LockType();
    private static final /* synthetic */ LockType[] a;

    public static LockType[] values() {
        return (LockType[])a.clone();
    }

    public static LockType valueOf(String string) {
        return Enum.valueOf(LockType.class, string);
    }

    private static /* synthetic */ LockType[] a() {
        return new LockType[]{INCLUDE, EXCLUDE, NONE};
    }

    static {
        a = LockType.a();
    }
}
