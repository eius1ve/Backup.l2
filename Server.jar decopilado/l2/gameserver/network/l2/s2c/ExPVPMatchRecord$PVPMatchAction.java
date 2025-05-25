/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class ExPVPMatchRecord.PVPMatchAction
extends Enum<ExPVPMatchRecord.PVPMatchAction> {
    public static final /* enum */ ExPVPMatchRecord.PVPMatchAction INIT = new ExPVPMatchRecord.PVPMatchAction(0);
    public static final /* enum */ ExPVPMatchRecord.PVPMatchAction UPDATE = new ExPVPMatchRecord.PVPMatchAction(1);
    public static final /* enum */ ExPVPMatchRecord.PVPMatchAction DONE = new ExPVPMatchRecord.PVPMatchAction(2);
    private final int wc;
    private static final /* synthetic */ ExPVPMatchRecord.PVPMatchAction[] a;

    public static ExPVPMatchRecord.PVPMatchAction[] values() {
        return (ExPVPMatchRecord.PVPMatchAction[])a.clone();
    }

    public static ExPVPMatchRecord.PVPMatchAction valueOf(String string) {
        return Enum.valueOf(ExPVPMatchRecord.PVPMatchAction.class, string);
    }

    private ExPVPMatchRecord.PVPMatchAction(int n2) {
        this.wc = n2;
    }

    public int getVal() {
        return this.wc;
    }

    private static /* synthetic */ ExPVPMatchRecord.PVPMatchAction[] a() {
        return new ExPVPMatchRecord.PVPMatchAction[]{INIT, UPDATE, DONE};
    }

    static {
        a = ExPVPMatchRecord.PVPMatchAction.a();
    }
}
