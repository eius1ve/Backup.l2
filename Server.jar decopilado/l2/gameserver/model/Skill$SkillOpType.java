/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Skill.SkillOpType
extends Enum<Skill.SkillOpType> {
    public static final /* enum */ Skill.SkillOpType OP_ACTIVE = new Skill.SkillOpType();
    public static final /* enum */ Skill.SkillOpType OP_PASSIVE = new Skill.SkillOpType();
    public static final /* enum */ Skill.SkillOpType OP_TOGGLE = new Skill.SkillOpType();
    private static final /* synthetic */ Skill.SkillOpType[] a;

    public static Skill.SkillOpType[] values() {
        return (Skill.SkillOpType[])a.clone();
    }

    public static Skill.SkillOpType valueOf(String string) {
        return Enum.valueOf(Skill.SkillOpType.class, string);
    }

    private static /* synthetic */ Skill.SkillOpType[] a() {
        return new Skill.SkillOpType[]{OP_ACTIVE, OP_PASSIVE, OP_TOGGLE};
    }

    static {
        a = Skill.SkillOpType.a();
    }
}
