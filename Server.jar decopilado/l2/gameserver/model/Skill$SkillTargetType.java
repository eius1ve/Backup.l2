/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public static final class Skill.SkillTargetType
extends Enum<Skill.SkillTargetType> {
    public static final /* enum */ Skill.SkillTargetType TARGET_ALLY = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_AREA = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_AREA_AIM_CORPSE = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_AREA_CLAN = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_AURA = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_PET_AURA = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_CHAIN = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_CHEST = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_FEEDABLE_BEAST = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_CLAN = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_CLAN_ONLY = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_CORPSE = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_CORPSE_PLAYER = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_ENEMY_PET = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_ENEMY_SUMMON = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_ENEMY_SERVITOR = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_FLAGPOLE = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_COMMCHANNEL = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_HOLY = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_ITEM = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_MULTIFACE = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_MULTIFACE_AURA = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_TUNNEL = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_NONE = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_ONE = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_OTHER = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_OWNER = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_PARTY = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_PET = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_SELF = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_SIEGE = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_UNLOCKABLE = new Skill.SkillTargetType();
    public static final /* enum */ Skill.SkillTargetType TARGET_ALLY_AND_PARTY = new Skill.SkillTargetType();
    private static final /* synthetic */ Skill.SkillTargetType[] a;

    public static Skill.SkillTargetType[] values() {
        return (Skill.SkillTargetType[])a.clone();
    }

    public static Skill.SkillTargetType valueOf(String string) {
        return Enum.valueOf(Skill.SkillTargetType.class, string);
    }

    private static /* synthetic */ Skill.SkillTargetType[] a() {
        return new Skill.SkillTargetType[]{TARGET_ALLY, TARGET_AREA, TARGET_AREA_AIM_CORPSE, TARGET_AREA_CLAN, TARGET_AURA, TARGET_PET_AURA, TARGET_CHAIN, TARGET_CHEST, TARGET_FEEDABLE_BEAST, TARGET_CLAN, TARGET_CLAN_ONLY, TARGET_CORPSE, TARGET_CORPSE_PLAYER, TARGET_ENEMY_PET, TARGET_ENEMY_SUMMON, TARGET_ENEMY_SERVITOR, TARGET_FLAGPOLE, TARGET_COMMCHANNEL, TARGET_HOLY, TARGET_ITEM, TARGET_MULTIFACE, TARGET_MULTIFACE_AURA, TARGET_TUNNEL, TARGET_NONE, TARGET_ONE, TARGET_OTHER, TARGET_OWNER, TARGET_PARTY, TARGET_PET, TARGET_SELF, TARGET_SIEGE, TARGET_UNLOCKABLE, TARGET_ALLY_AND_PARTY};
    }

    static {
        a = Skill.SkillTargetType.a();
    }
}
