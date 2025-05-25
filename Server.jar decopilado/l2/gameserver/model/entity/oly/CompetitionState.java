/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

public final class CompetitionState
extends Enum<CompetitionState> {
    public static final /* enum */ CompetitionState INIT = new CompetitionState(0);
    public static final /* enum */ CompetitionState STAND_BY = new CompetitionState(1);
    public static final /* enum */ CompetitionState PLAYING = new CompetitionState(2);
    public static final /* enum */ CompetitionState FINISH = new CompetitionState(0);
    private int lC;
    private static final /* synthetic */ CompetitionState[] a;

    public static CompetitionState[] values() {
        return (CompetitionState[])a.clone();
    }

    public static CompetitionState valueOf(String string) {
        return Enum.valueOf(CompetitionState.class, string);
    }

    private CompetitionState(int n2) {
        this.lC = n2;
    }

    public int getStateId() {
        return this.lC;
    }

    private static /* synthetic */ CompetitionState[] a() {
        return new CompetitionState[]{INIT, STAND_BY, PLAYING, FINISH};
    }

    static {
        a = CompetitionState.a();
    }
}
