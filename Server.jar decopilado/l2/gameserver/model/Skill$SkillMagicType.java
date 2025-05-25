/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Skill.SkillMagicType
extends Enum<Skill.SkillMagicType> {
    public static final /* enum */ Skill.SkillMagicType PHYSIC = new Skill.SkillMagicType();
    public static final /* enum */ Skill.SkillMagicType MAGIC = new Skill.SkillMagicType();
    public static final /* enum */ Skill.SkillMagicType SPECIAL = new Skill.SkillMagicType();
    public static final /* enum */ Skill.SkillMagicType MUSIC = new Skill.SkillMagicType();
    private static final /* synthetic */ Skill.SkillMagicType[] a;

    public static Skill.SkillMagicType[] values() {
        return (Skill.SkillMagicType[])a.clone();
    }

    public static Skill.SkillMagicType valueOf(String string) {
        return Enum.valueOf(Skill.SkillMagicType.class, string);
    }

    private static /* synthetic */ Skill.SkillMagicType[] a() {
        return new Skill.SkillMagicType[]{PHYSIC, MAGIC, SPECIAL, MUSIC};
    }

    static {
        a = Skill.SkillMagicType.a();
    }
}
