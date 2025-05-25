/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

public static final class PetitionManager.PetitionState
extends Enum<PetitionManager.PetitionState> {
    public static final /* enum */ PetitionManager.PetitionState Pending = new PetitionManager.PetitionState();
    public static final /* enum */ PetitionManager.PetitionState Responder_Cancel = new PetitionManager.PetitionState();
    public static final /* enum */ PetitionManager.PetitionState Responder_Missing = new PetitionManager.PetitionState();
    public static final /* enum */ PetitionManager.PetitionState Responder_Reject = new PetitionManager.PetitionState();
    public static final /* enum */ PetitionManager.PetitionState Responder_Complete = new PetitionManager.PetitionState();
    public static final /* enum */ PetitionManager.PetitionState Petitioner_Cancel = new PetitionManager.PetitionState();
    public static final /* enum */ PetitionManager.PetitionState Petitioner_Missing = new PetitionManager.PetitionState();
    public static final /* enum */ PetitionManager.PetitionState In_Process = new PetitionManager.PetitionState();
    public static final /* enum */ PetitionManager.PetitionState Completed = new PetitionManager.PetitionState();
    private static final /* synthetic */ PetitionManager.PetitionState[] a;

    public static PetitionManager.PetitionState[] values() {
        return (PetitionManager.PetitionState[])a.clone();
    }

    public static PetitionManager.PetitionState valueOf(String string) {
        return Enum.valueOf(PetitionManager.PetitionState.class, string);
    }

    private static /* synthetic */ PetitionManager.PetitionState[] a() {
        return new PetitionManager.PetitionState[]{Pending, Responder_Cancel, Responder_Missing, Responder_Reject, Responder_Complete, Petitioner_Cancel, Petitioner_Missing, In_Process, Completed};
    }

    static {
        a = PetitionManager.PetitionState.a();
    }
}
