/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Skill.Ternary
extends Enum<Skill.Ternary> {
    public static final /* enum */ Skill.Ternary TRUE = new Skill.Ternary();
    public static final /* enum */ Skill.Ternary FALSE = new Skill.Ternary();
    public static final /* enum */ Skill.Ternary DEFAULT = new Skill.Ternary();
    private static final /* synthetic */ Skill.Ternary[] a;

    public static Skill.Ternary[] values() {
        return (Skill.Ternary[])a.clone();
    }

    public static Skill.Ternary valueOf(String string) {
        return Enum.valueOf(Skill.Ternary.class, string);
    }

    private static /* synthetic */ Skill.Ternary[] a() {
        return new Skill.Ternary[]{TRUE, FALSE, DEFAULT};
    }

    static {
        a = Skill.Ternary.a();
    }
}
