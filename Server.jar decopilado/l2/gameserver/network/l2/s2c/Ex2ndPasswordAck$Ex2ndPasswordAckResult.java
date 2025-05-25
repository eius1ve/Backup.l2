/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class Ex2ndPasswordAck.Ex2ndPasswordAckResult
extends Enum<Ex2ndPasswordAck.Ex2ndPasswordAckResult> {
    public static final /* enum */ Ex2ndPasswordAck.Ex2ndPasswordAckResult SUCCESS_CREATE = new Ex2ndPasswordAck.Ex2ndPasswordAckResult(0, 0);
    public static final /* enum */ Ex2ndPasswordAck.Ex2ndPasswordAckResult SUCCESS_VERIFY = new Ex2ndPasswordAck.Ex2ndPasswordAckResult(2, 0);
    public static final /* enum */ Ex2ndPasswordAck.Ex2ndPasswordAckResult FAIL_CREATE = new Ex2ndPasswordAck.Ex2ndPasswordAckResult(0, 1);
    public static final /* enum */ Ex2ndPasswordAck.Ex2ndPasswordAckResult FAIL_VERIFY = new Ex2ndPasswordAck.Ex2ndPasswordAckResult(2, 1);
    public static final /* enum */ Ex2ndPasswordAck.Ex2ndPasswordAckResult BLOCK_HOMEPAGE = new Ex2ndPasswordAck.Ex2ndPasswordAckResult(0, 2);
    public static final /* enum */ Ex2ndPasswordAck.Ex2ndPasswordAckResult ERROR = new Ex2ndPasswordAck.Ex2ndPasswordAckResult(3, 0);
    private int uh;
    private int ui;
    private static final /* synthetic */ Ex2ndPasswordAck.Ex2ndPasswordAckResult[] a;

    public static Ex2ndPasswordAck.Ex2ndPasswordAckResult[] values() {
        return (Ex2ndPasswordAck.Ex2ndPasswordAckResult[])a.clone();
    }

    public static Ex2ndPasswordAck.Ex2ndPasswordAckResult valueOf(String string) {
        return Enum.valueOf(Ex2ndPasswordAck.Ex2ndPasswordAckResult.class, string);
    }

    private Ex2ndPasswordAck.Ex2ndPasswordAckResult(int n2, int n3) {
        this.uh = n2;
        this.ui = n3;
    }

    public int getArg0() {
        return this.uh;
    }

    public int getArg1() {
        return this.ui;
    }

    private static /* synthetic */ Ex2ndPasswordAck.Ex2ndPasswordAckResult[] a() {
        return new Ex2ndPasswordAck.Ex2ndPasswordAckResult[]{SUCCESS_CREATE, SUCCESS_VERIFY, FAIL_CREATE, FAIL_VERIFY, BLOCK_HOMEPAGE, ERROR};
    }

    static {
        a = Ex2ndPasswordAck.Ex2ndPasswordAckResult.a();
    }
}
