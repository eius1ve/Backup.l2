/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

public static final class ConditionPlayerGender.Gender
extends Enum<ConditionPlayerGender.Gender> {
    public static final /* enum */ ConditionPlayerGender.Gender MALE = new ConditionPlayerGender.Gender(0);
    public static final /* enum */ ConditionPlayerGender.Gender FEMALE = new ConditionPlayerGender.Gender(1);
    private final int DI;
    private static final /* synthetic */ ConditionPlayerGender.Gender[] a;

    public static ConditionPlayerGender.Gender[] values() {
        return (ConditionPlayerGender.Gender[])a.clone();
    }

    public static ConditionPlayerGender.Gender valueOf(String string) {
        return Enum.valueOf(ConditionPlayerGender.Gender.class, string);
    }

    private ConditionPlayerGender.Gender(int n2) {
        this.DI = n2;
    }

    public int getPlayerGenderId() {
        return this.DI;
    }

    private static /* synthetic */ ConditionPlayerGender.Gender[] a() {
        return new ConditionPlayerGender.Gender[]{MALE, FEMALE};
    }

    static {
        a = ConditionPlayerGender.Gender.a();
    }
}
