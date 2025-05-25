/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class ExPVPMatchCCRecord.PVPMatchCCAction
extends Enum<ExPVPMatchCCRecord.PVPMatchCCAction> {
    public static final /* enum */ ExPVPMatchCCRecord.PVPMatchCCAction INIT = new ExPVPMatchCCRecord.PVPMatchCCAction(0);
    public static final /* enum */ ExPVPMatchCCRecord.PVPMatchCCAction UPDATE = new ExPVPMatchCCRecord.PVPMatchCCAction(1);
    public static final /* enum */ ExPVPMatchCCRecord.PVPMatchCCAction DONE = new ExPVPMatchCCRecord.PVPMatchCCAction(2);
    private final int vX;
    private static final /* synthetic */ ExPVPMatchCCRecord.PVPMatchCCAction[] a;

    public static ExPVPMatchCCRecord.PVPMatchCCAction[] values() {
        return (ExPVPMatchCCRecord.PVPMatchCCAction[])a.clone();
    }

    public static ExPVPMatchCCRecord.PVPMatchCCAction valueOf(String string) {
        return Enum.valueOf(ExPVPMatchCCRecord.PVPMatchCCAction.class, string);
    }

    private ExPVPMatchCCRecord.PVPMatchCCAction(int n2) {
        this.vX = n2;
    }

    public int getVal() {
        return this.vX;
    }

    private static /* synthetic */ ExPVPMatchCCRecord.PVPMatchCCAction[] a() {
        return new ExPVPMatchCCRecord.PVPMatchCCAction[]{INIT, UPDATE, DONE};
    }

    static {
        a = ExPVPMatchCCRecord.PVPMatchCCAction.a();
    }
}
