/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class InvisibleType
extends Enum<InvisibleType> {
    public static final /* enum */ InvisibleType NONE = new InvisibleType();
    public static final /* enum */ InvisibleType NORMAL = new InvisibleType();
    public static final /* enum */ InvisibleType EFFECT = new InvisibleType();
    private static final /* synthetic */ InvisibleType[] a;

    public static InvisibleType[] values() {
        return (InvisibleType[])a.clone();
    }

    public static InvisibleType valueOf(String string) {
        return Enum.valueOf(InvisibleType.class, string);
    }

    private static /* synthetic */ InvisibleType[] a() {
        return new InvisibleType[]{NONE, NORMAL, EFFECT};
    }

    static {
        a = InvisibleType.a();
    }
}
