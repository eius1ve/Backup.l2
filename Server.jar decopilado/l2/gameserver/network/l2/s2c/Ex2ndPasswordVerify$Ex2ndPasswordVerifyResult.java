/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult
extends Enum<Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult> {
    public static final /* enum */ Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult SUCCESS = new Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult(0);
    public static final /* enum */ Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult FAILED = new Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult(1);
    public static final /* enum */ Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult BLOCK_HOMEPAGE = new Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult(2);
    public static final /* enum */ Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult ERROR = new Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult(3);
    private int uo;
    private static final /* synthetic */ Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult[] a;

    public static Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult[] values() {
        return (Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult[])a.clone();
    }

    public static Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult valueOf(String string) {
        return Enum.valueOf(Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult.class, string);
    }

    private Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult(int n2) {
        this.uo = n2;
    }

    public int getVal() {
        return this.uo;
    }

    private static /* synthetic */ Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult[] a() {
        return new Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult[]{SUCCESS, FAILED, BLOCK_HOMEPAGE, ERROR};
    }

    static {
        a = Ex2ndPasswordVerify.Ex2ndPasswordVerifyResult.a();
    }
}
