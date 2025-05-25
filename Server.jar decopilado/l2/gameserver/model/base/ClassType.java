/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class ClassType
extends Enum<ClassType> {
    public static final /* enum */ ClassType Fighter = new ClassType();
    public static final /* enum */ ClassType Mystic = new ClassType();
    public static final /* enum */ ClassType Priest = new ClassType();
    private static final /* synthetic */ ClassType[] a;

    public static ClassType[] values() {
        return (ClassType[])a.clone();
    }

    public static ClassType valueOf(String string) {
        return Enum.valueOf(ClassType.class, string);
    }

    private static /* synthetic */ ClassType[] a() {
        return new ClassType[]{Fighter, Mystic, Priest};
    }

    static {
        a = ClassType.a();
    }
}
