/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class ClassType2
extends Enum<ClassType2> {
    public static final /* enum */ ClassType2 None = new ClassType2(10280, 10612);
    public static final /* enum */ ClassType2 Warrior = new ClassType2(10281, 10289);
    public static final /* enum */ ClassType2 Knight = new ClassType2(10282, 10288);
    public static final /* enum */ ClassType2 Rogue = new ClassType2(10283, 10290);
    public static final /* enum */ ClassType2 Healer = new ClassType2(10285, 10291);
    public static final /* enum */ ClassType2 Enchanter = new ClassType2(10287, 10293);
    public static final /* enum */ ClassType2 Summoner = new ClassType2(10286, 10294);
    public static final /* enum */ ClassType2 Wizard = new ClassType2(10284, 10292);
    public static final ClassType2[] VALUES;
    private final int kE;
    private final int kF;
    private static final /* synthetic */ ClassType2[] a;

    public static ClassType2[] values() {
        return (ClassType2[])a.clone();
    }

    public static ClassType2 valueOf(String string) {
        return Enum.valueOf(ClassType2.class, string);
    }

    private ClassType2(int n2, int n3) {
        this.kE = n2;
        this.kF = n3;
    }

    public int getCertificateId() {
        return this.kE;
    }

    public int getTransformationId() {
        return this.kF;
    }

    private static /* synthetic */ ClassType2[] a() {
        return new ClassType2[]{None, Warrior, Knight, Rogue, Healer, Enchanter, Summoner, Wizard};
    }

    static {
        a = ClassType2.a();
        VALUES = ClassType2.values();
    }
}
