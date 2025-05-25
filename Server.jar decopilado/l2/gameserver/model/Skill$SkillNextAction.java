/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Skill.SkillNextAction
extends Enum<Skill.SkillNextAction> {
    public static final /* enum */ Skill.SkillNextAction ATTACK = new Skill.SkillNextAction();
    public static final /* enum */ Skill.SkillNextAction CAST = new Skill.SkillNextAction();
    public static final /* enum */ Skill.SkillNextAction DEFAULT = new Skill.SkillNextAction();
    public static final /* enum */ Skill.SkillNextAction MOVE = new Skill.SkillNextAction();
    public static final /* enum */ Skill.SkillNextAction NONE = new Skill.SkillNextAction();
    private static final /* synthetic */ Skill.SkillNextAction[] a;

    public static Skill.SkillNextAction[] values() {
        return (Skill.SkillNextAction[])a.clone();
    }

    public static Skill.SkillNextAction valueOf(String string) {
        return Enum.valueOf(Skill.SkillNextAction.class, string);
    }

    private static /* synthetic */ Skill.SkillNextAction[] a() {
        return new Skill.SkillNextAction[]{ATTACK, CAST, DEFAULT, MOVE, NONE};
    }

    static {
        a = Skill.SkillNextAction.a();
    }
}
