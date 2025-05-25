/*
 * Decompiled with CFR 0.152.
 */
package services;

static final class ACP.ACPHelper.ACPHelperState
extends Enum<ACP.ACPHelper.ACPHelperState> {
    public static final /* enum */ ACP.ACPHelper.ACPHelperState IDLE = new ACP.ACPHelper.ACPHelperState();
    public static final /* enum */ ACP.ACPHelper.ACPHelperState APPLY = new ACP.ACPHelper.ACPHelperState();
    public static final /* enum */ ACP.ACPHelper.ACPHelperState USE = new ACP.ACPHelper.ACPHelperState();
    private static final /* synthetic */ ACP.ACPHelper.ACPHelperState[] a;

    public static ACP.ACPHelper.ACPHelperState[] values() {
        return (ACP.ACPHelper.ACPHelperState[])a.clone();
    }

    public static ACP.ACPHelper.ACPHelperState valueOf(String string) {
        return Enum.valueOf(ACP.ACPHelper.ACPHelperState.class, string);
    }

    private static /* synthetic */ ACP.ACPHelper.ACPHelperState[] a() {
        return new ACP.ACPHelper.ACPHelperState[]{IDLE, APPLY, USE};
    }

    static {
        a = ACP.ACPHelper.ACPHelperState.a();
    }
}
