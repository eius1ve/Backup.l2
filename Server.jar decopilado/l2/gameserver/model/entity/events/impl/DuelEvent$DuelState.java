/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

protected static final class DuelEvent.DuelState
extends Enum<DuelEvent.DuelState> {
    public static final /* enum */ DuelEvent.DuelState EPrepare = new DuelEvent.DuelState();
    public static final /* enum */ DuelEvent.DuelState EInProgress = new DuelEvent.DuelState();
    public static final /* enum */ DuelEvent.DuelState EEnd = new DuelEvent.DuelState();
    private static final /* synthetic */ DuelEvent.DuelState[] a;

    public static DuelEvent.DuelState[] values() {
        return (DuelEvent.DuelState[])a.clone();
    }

    public static DuelEvent.DuelState valueOf(String string) {
        return Enum.valueOf(DuelEvent.DuelState.class, string);
    }

    private static /* synthetic */ DuelEvent.DuelState[] a() {
        return new DuelEvent.DuelState[]{EPrepare, EInProgress, EEnd};
    }

    static {
        a = DuelEvent.DuelState.a();
    }
}
