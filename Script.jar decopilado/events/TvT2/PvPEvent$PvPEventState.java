/*
 * Decompiled with CFR 0.152.
 */
package events.TvT2;

protected static final class PvPEvent.PvPEventState
extends Enum<PvPEvent.PvPEventState> {
    public static final /* enum */ PvPEvent.PvPEventState STANDBY = new PvPEvent.PvPEventState();
    public static final /* enum */ PvPEvent.PvPEventState REGISTRATION = new PvPEvent.PvPEventState();
    public static final /* enum */ PvPEvent.PvPEventState PORTING_TO = new PvPEvent.PvPEventState();
    public static final /* enum */ PvPEvent.PvPEventState PREPARE_TO = new PvPEvent.PvPEventState();
    public static final /* enum */ PvPEvent.PvPEventState COMPETITION = new PvPEvent.PvPEventState();
    public static final /* enum */ PvPEvent.PvPEventState WINNER = new PvPEvent.PvPEventState();
    public static final /* enum */ PvPEvent.PvPEventState PREPARE_FROM = new PvPEvent.PvPEventState();
    public static final /* enum */ PvPEvent.PvPEventState PORTING_FROM = new PvPEvent.PvPEventState();
    private static final /* synthetic */ PvPEvent.PvPEventState[] a;

    public static PvPEvent.PvPEventState[] values() {
        return (PvPEvent.PvPEventState[])a.clone();
    }

    public static PvPEvent.PvPEventState valueOf(String string) {
        return Enum.valueOf(PvPEvent.PvPEventState.class, string);
    }

    private static /* synthetic */ PvPEvent.PvPEventState[] a() {
        return new PvPEvent.PvPEventState[]{STANDBY, REGISTRATION, PORTING_TO, PREPARE_TO, COMPETITION, WINNER, PREPARE_FROM, PORTING_FROM};
    }

    static {
        a = PvPEvent.PvPEventState.a();
    }
}
