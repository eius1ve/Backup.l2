/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class Ex2ndPasswordVerify
extends L2GameServerPacket {
    private final Ex2ndPasswordVerifyResult a;
    private final int un;

    public Ex2ndPasswordVerify(Ex2ndPasswordVerifyResult ex2ndPasswordVerifyResult) {
        this.a = ex2ndPasswordVerifyResult;
        this.un = 0;
    }

    public Ex2ndPasswordVerify(Ex2ndPasswordVerifyResult ex2ndPasswordVerifyResult, int n) {
        this.a = ex2ndPasswordVerifyResult;
        this.un = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(262);
        this.writeD(this.a.getVal());
        this.writeD(this.un);
    }

    public static final class Ex2ndPasswordVerifyResult
    extends Enum<Ex2ndPasswordVerifyResult> {
        public static final /* enum */ Ex2ndPasswordVerifyResult SUCCESS = new Ex2ndPasswordVerifyResult(0);
        public static final /* enum */ Ex2ndPasswordVerifyResult FAILED = new Ex2ndPasswordVerifyResult(1);
        public static final /* enum */ Ex2ndPasswordVerifyResult BLOCK_HOMEPAGE = new Ex2ndPasswordVerifyResult(2);
        public static final /* enum */ Ex2ndPasswordVerifyResult ERROR = new Ex2ndPasswordVerifyResult(3);
        private int uo;
        private static final /* synthetic */ Ex2ndPasswordVerifyResult[] a;

        public static Ex2ndPasswordVerifyResult[] values() {
            return (Ex2ndPasswordVerifyResult[])a.clone();
        }

        public static Ex2ndPasswordVerifyResult valueOf(String string) {
            return Enum.valueOf(Ex2ndPasswordVerifyResult.class, string);
        }

        private Ex2ndPasswordVerifyResult(int n2) {
            this.uo = n2;
        }

        public int getVal() {
            return this.uo;
        }

        private static /* synthetic */ Ex2ndPasswordVerifyResult[] a() {
            return new Ex2ndPasswordVerifyResult[]{SUCCESS, FAILED, BLOCK_HOMEPAGE, ERROR};
        }

        static {
            a = Ex2ndPasswordVerifyResult.a();
        }
    }
}
