/*
 * Decompiled with CFR 0.152.
 */
package events.SavingSnowman;

public static final class SavingSnowman.SnowmanState
extends Enum<SavingSnowman.SnowmanState> {
    public static final /* enum */ SavingSnowman.SnowmanState CAPTURED = new SavingSnowman.SnowmanState();
    public static final /* enum */ SavingSnowman.SnowmanState KILLED = new SavingSnowman.SnowmanState();
    public static final /* enum */ SavingSnowman.SnowmanState SAVED = new SavingSnowman.SnowmanState();
    private static final /* synthetic */ SavingSnowman.SnowmanState[] a;

    public static SavingSnowman.SnowmanState[] values() {
        return (SavingSnowman.SnowmanState[])a.clone();
    }

    public static SavingSnowman.SnowmanState valueOf(String string) {
        return Enum.valueOf(SavingSnowman.SnowmanState.class, string);
    }

    private static /* synthetic */ SavingSnowman.SnowmanState[] a() {
        return new SavingSnowman.SnowmanState[]{CAPTURED, KILLED, SAVED};
    }

    static {
        a = SavingSnowman.SnowmanState.a();
    }
}
