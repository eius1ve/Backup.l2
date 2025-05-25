/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills;

public final class SkillEntryType
extends Enum<SkillEntryType> {
    public static final /* enum */ SkillEntryType NONE = new SkillEntryType();
    public static final /* enum */ SkillEntryType CERTIFICATION = new SkillEntryType();
    public static final /* enum */ SkillEntryType TRANSFER = new SkillEntryType();
    private static final /* synthetic */ SkillEntryType[] a;

    public static SkillEntryType[] values() {
        return (SkillEntryType[])a.clone();
    }

    public static SkillEntryType valueOf(String string) {
        return Enum.valueOf(SkillEntryType.class, string);
    }

    private static /* synthetic */ SkillEntryType[] a() {
        return new SkillEntryType[]{NONE, CERTIFICATION, TRANSFER};
    }

    static {
        a = SkillEntryType.a();
    }
}
