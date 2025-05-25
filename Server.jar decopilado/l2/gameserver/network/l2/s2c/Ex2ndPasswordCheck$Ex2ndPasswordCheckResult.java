/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class Ex2ndPasswordCheck.Ex2ndPasswordCheckResult
extends Enum<Ex2ndPasswordCheck.Ex2ndPasswordCheckResult> {
    public static final /* enum */ Ex2ndPasswordCheck.Ex2ndPasswordCheckResult CREATE = new Ex2ndPasswordCheck.Ex2ndPasswordCheckResult(0, 0);
    public static final /* enum */ Ex2ndPasswordCheck.Ex2ndPasswordCheckResult CHECK = new Ex2ndPasswordCheck.Ex2ndPasswordCheckResult(1, 0);
    public static final /* enum */ Ex2ndPasswordCheck.Ex2ndPasswordCheckResult BLOCK_TIME = new Ex2ndPasswordCheck.Ex2ndPasswordCheckResult(1);
    public static final /* enum */ Ex2ndPasswordCheck.Ex2ndPasswordCheckResult SUCCESS = new Ex2ndPasswordCheck.Ex2ndPasswordCheckResult(2, 0);
    public static final /* enum */ Ex2ndPasswordCheck.Ex2ndPasswordCheckResult ERROR = new Ex2ndPasswordCheck.Ex2ndPasswordCheckResult(3, 0);
    private final int ul;
    private final int um;
    private static final /* synthetic */ Ex2ndPasswordCheck.Ex2ndPasswordCheckResult[] a;

    public static Ex2ndPasswordCheck.Ex2ndPasswordCheckResult[] values() {
        return (Ex2ndPasswordCheck.Ex2ndPasswordCheckResult[])a.clone();
    }

    public static Ex2ndPasswordCheck.Ex2ndPasswordCheckResult valueOf(String string) {
        return Enum.valueOf(Ex2ndPasswordCheck.Ex2ndPasswordCheckResult.class, string);
    }

    private Ex2ndPasswordCheck.Ex2ndPasswordCheckResult(int n2, int n3) {
        this.ul = n2;
        this.um = n3;
    }

    private Ex2ndPasswordCheck.Ex2ndPasswordCheckResult(int n2) {
        this.ul = n2;
        this.um = -1;
    }

    public int getArg0() {
        return this.ul;
    }

    public int getArg1() {
        return this.um;
    }

    private static /* synthetic */ Ex2ndPasswordCheck.Ex2ndPasswordCheckResult[] a() {
        return new Ex2ndPasswordCheck.Ex2ndPasswordCheckResult[]{CREATE, CHECK, BLOCK_TIME, SUCCESS, ERROR};
    }

    static {
        a = Ex2ndPasswordCheck.Ex2ndPasswordCheckResult.a();
    }
}
