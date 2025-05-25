/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

public final class SpecialEffectState
extends Enum<SpecialEffectState> {
    public static final /* enum */ SpecialEffectState FALSE = new SpecialEffectState();
    public static final /* enum */ SpecialEffectState TRUE = new SpecialEffectState();
    public static final /* enum */ SpecialEffectState GM = new SpecialEffectState();
    private static final /* synthetic */ SpecialEffectState[] a;

    public static SpecialEffectState[] values() {
        return (SpecialEffectState[])a.clone();
    }

    public static SpecialEffectState valueOf(String string) {
        return Enum.valueOf(SpecialEffectState.class, string);
    }

    private static /* synthetic */ SpecialEffectState[] a() {
        return new SpecialEffectState[]{FALSE, TRUE, GM};
    }

    static {
        a = SpecialEffectState.a();
    }
}
