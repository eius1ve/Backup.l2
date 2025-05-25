/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

public static final class CubicTemplate.ActionType
extends Enum<CubicTemplate.ActionType> {
    public static final /* enum */ CubicTemplate.ActionType ATTACK = new CubicTemplate.ActionType();
    public static final /* enum */ CubicTemplate.ActionType DEBUFF = new CubicTemplate.ActionType();
    public static final /* enum */ CubicTemplate.ActionType CANCEL = new CubicTemplate.ActionType();
    public static final /* enum */ CubicTemplate.ActionType HEAL = new CubicTemplate.ActionType();
    private static final /* synthetic */ CubicTemplate.ActionType[] a;

    public static CubicTemplate.ActionType[] values() {
        return (CubicTemplate.ActionType[])a.clone();
    }

    public static CubicTemplate.ActionType valueOf(String string) {
        return Enum.valueOf(CubicTemplate.ActionType.class, string);
    }

    private static /* synthetic */ CubicTemplate.ActionType[] a() {
        return new CubicTemplate.ActionType[]{ATTACK, DEBUFF, CANCEL, HEAL};
    }

    static {
        a = CubicTemplate.ActionType.a();
    }
}
