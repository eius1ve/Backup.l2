/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class ClassLevel
extends Enum<ClassLevel> {
    public static final /* enum */ ClassLevel First = new ClassLevel();
    public static final /* enum */ ClassLevel Second = new ClassLevel();
    public static final /* enum */ ClassLevel Third = new ClassLevel();
    public static final /* enum */ ClassLevel Fourth = new ClassLevel();
    private static final /* synthetic */ ClassLevel[] a;

    public static ClassLevel[] values() {
        return (ClassLevel[])a.clone();
    }

    public static ClassLevel valueOf(String string) {
        return Enum.valueOf(ClassLevel.class, string);
    }

    private static /* synthetic */ ClassLevel[] a() {
        return new ClassLevel[]{First, Second, Third, Fourth};
    }

    static {
        a = ClassLevel.a();
    }
}
