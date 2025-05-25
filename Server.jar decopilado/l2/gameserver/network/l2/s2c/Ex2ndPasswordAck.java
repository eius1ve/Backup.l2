/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class Ex2ndPasswordAck
extends L2GameServerPacket {
    private final Ex2ndPasswordAckResult a;
    private final int ug;

    public Ex2ndPasswordAck(Ex2ndPasswordAckResult ex2ndPasswordAckResult) {
        this.a = ex2ndPasswordAckResult;
        this.ug = 0;
    }

    public Ex2ndPasswordAck(Ex2ndPasswordAckResult ex2ndPasswordAckResult, int n) {
        this.a = ex2ndPasswordAckResult;
        this.ug = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(263);
        this.writeC(this.a.getArg0());
        this.writeD(this.a.getArg1());
        this.writeD(this.ug);
    }

    public static final class Ex2ndPasswordAckResult
    extends Enum<Ex2ndPasswordAckResult> {
        public static final /* enum */ Ex2ndPasswordAckResult SUCCESS_CREATE = new Ex2ndPasswordAckResult(0, 0);
        public static final /* enum */ Ex2ndPasswordAckResult SUCCESS_VERIFY = new Ex2ndPasswordAckResult(2, 0);
        public static final /* enum */ Ex2ndPasswordAckResult FAIL_CREATE = new Ex2ndPasswordAckResult(0, 1);
        public static final /* enum */ Ex2ndPasswordAckResult FAIL_VERIFY = new Ex2ndPasswordAckResult(2, 1);
        public static final /* enum */ Ex2ndPasswordAckResult BLOCK_HOMEPAGE = new Ex2ndPasswordAckResult(0, 2);
        public static final /* enum */ Ex2ndPasswordAckResult ERROR = new Ex2ndPasswordAckResult(3, 0);
        private int uh;
        private int ui;
        private static final /* synthetic */ Ex2ndPasswordAckResult[] a;

        public static Ex2ndPasswordAckResult[] values() {
            return (Ex2ndPasswordAckResult[])a.clone();
        }

        public static Ex2ndPasswordAckResult valueOf(String string) {
            return Enum.valueOf(Ex2ndPasswordAckResult.class, string);
        }

        private Ex2ndPasswordAckResult(int n2, int n3) {
            this.uh = n2;
            this.ui = n3;
        }

        public int getArg0() {
            return this.uh;
        }

        public int getArg1() {
            return this.ui;
        }

        private static /* synthetic */ Ex2ndPasswordAckResult[] a() {
            return new Ex2ndPasswordAckResult[]{SUCCESS_CREATE, SUCCESS_VERIFY, FAIL_CREATE, FAIL_VERIFY, BLOCK_HOMEPAGE, ERROR};
        }

        static {
            a = Ex2ndPasswordAckResult.a();
        }
    }
}
