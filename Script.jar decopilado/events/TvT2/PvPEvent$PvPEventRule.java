/*
 * Decompiled with CFR 0.152.
 */
package events.TvT2;

import events.TvT2.PvPEvent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public static final class PvPEvent.PvPEventRule
extends Enum<PvPEvent.PvPEventRule> {
    public static final /* enum */ PvPEvent.PvPEventRule TVT = new PvPEvent.PvPEventRule(new PvPEvent.TvTParticipantController());
    public static final /* enum */ PvPEvent.PvPEventRule CTF = new PvPEvent.PvPEventRule(new PvPEvent.CTFParticipantController());
    public static final /* enum */ PvPEvent.PvPEventRule DM = new PvPEvent.PvPEventRule(new PvPEvent.DMParticipantController());
    public static PvPEvent.PvPEventRule[] VALUES;
    private final PvPEvent.IParticipantController a;
    private static final /* synthetic */ PvPEvent.PvPEventRule[] a;

    public static PvPEvent.PvPEventRule[] values() {
        return (PvPEvent.PvPEventRule[])a.clone();
    }

    public static PvPEvent.PvPEventRule valueOf(String string) {
        return Enum.valueOf(PvPEvent.PvPEventRule.class, string);
    }

    private PvPEvent.PvPEventRule(PvPEvent.IParticipantController iParticipantController) {
        this.a = iParticipantController;
    }

    public PvPEvent.IParticipantController getParticipantController() {
        return this.a;
    }

    private static /* synthetic */ PvPEvent.PvPEventRule[] a() {
        return new PvPEvent.PvPEventRule[]{TVT, CTF, DM};
    }

    static {
        a = PvPEvent.PvPEventRule.a();
        VALUES = PvPEvent.PvPEventRule.values();
    }
}
