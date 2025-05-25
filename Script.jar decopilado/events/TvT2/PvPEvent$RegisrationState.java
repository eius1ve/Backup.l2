/*
 * Decompiled with CFR 0.152.
 */
package events.TvT2;

private static final class PvPEvent.RegisrationState
extends Enum<PvPEvent.RegisrationState> {
    public static final /* enum */ PvPEvent.RegisrationState ANNOUNCE = new PvPEvent.RegisrationState();
    public static final /* enum */ PvPEvent.RegisrationState REQUEST = new PvPEvent.RegisrationState();
    public static final /* enum */ PvPEvent.RegisrationState MORPH = new PvPEvent.RegisrationState();
    public static final /* enum */ PvPEvent.RegisrationState CAPCHA = new PvPEvent.RegisrationState();
    private static final /* synthetic */ PvPEvent.RegisrationState[] a;

    public static PvPEvent.RegisrationState[] values() {
        return (PvPEvent.RegisrationState[])a.clone();
    }

    public static PvPEvent.RegisrationState valueOf(String string) {
        return Enum.valueOf(PvPEvent.RegisrationState.class, string);
    }

    private static /* synthetic */ PvPEvent.RegisrationState[] a() {
        return new PvPEvent.RegisrationState[]{ANNOUNCE, REQUEST, MORPH, CAPCHA};
    }

    static {
        a = PvPEvent.RegisrationState.a();
    }
}
