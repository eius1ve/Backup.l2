/*
 * Decompiled with CFR 0.152.
 */
package services;

static final class StatModifier.OlyMode
extends Enum<StatModifier.OlyMode> {
    public static final /* enum */ StatModifier.OlyMode OLY_ONLY = new StatModifier.OlyMode();
    public static final /* enum */ StatModifier.OlyMode NON_OLY_ONLY = new StatModifier.OlyMode();
    public static final /* enum */ StatModifier.OlyMode ANY = new StatModifier.OlyMode();
    private static final /* synthetic */ StatModifier.OlyMode[] a;

    public static StatModifier.OlyMode[] values() {
        return (StatModifier.OlyMode[])a.clone();
    }

    public static StatModifier.OlyMode valueOf(String string) {
        return Enum.valueOf(StatModifier.OlyMode.class, string);
    }

    private static /* synthetic */ StatModifier.OlyMode[] a() {
        return new StatModifier.OlyMode[]{OLY_ONLY, NON_OLY_ONLY, ANY};
    }

    static {
        a = StatModifier.OlyMode.a();
    }
}
