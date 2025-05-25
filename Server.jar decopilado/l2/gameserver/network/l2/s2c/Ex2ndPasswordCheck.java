/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class Ex2ndPasswordCheck
extends L2GameServerPacket {
    private final int uj;
    private final int uk;

    public Ex2ndPasswordCheck(Ex2ndPasswordCheckResult ex2ndPasswordCheckResult) {
        this.uj = ex2ndPasswordCheckResult.getArg0();
        this.uk = ex2ndPasswordCheckResult.getArg1();
    }

    public Ex2ndPasswordCheck(Ex2ndPasswordCheckResult ex2ndPasswordCheckResult, int n) {
        this.uj = ex2ndPasswordCheckResult.getArg0();
        this.uk = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(261);
        this.writeD(this.uj);
        this.writeD(this.uk);
    }

    public static final class Ex2ndPasswordCheckResult
    extends Enum<Ex2ndPasswordCheckResult> {
        public static final /* enum */ Ex2ndPasswordCheckResult CREATE = new Ex2ndPasswordCheckResult(0, 0);
        public static final /* enum */ Ex2ndPasswordCheckResult CHECK = new Ex2ndPasswordCheckResult(1, 0);
        public static final /* enum */ Ex2ndPasswordCheckResult BLOCK_TIME = new Ex2ndPasswordCheckResult(1);
        public static final /* enum */ Ex2ndPasswordCheckResult SUCCESS = new Ex2ndPasswordCheckResult(2, 0);
        public static final /* enum */ Ex2ndPasswordCheckResult ERROR = new Ex2ndPasswordCheckResult(3, 0);
        private final int ul;
        private final int um;
        private static final /* synthetic */ Ex2ndPasswordCheckResult[] a;

        public static Ex2ndPasswordCheckResult[] values() {
            return (Ex2ndPasswordCheckResult[])a.clone();
        }

        public static Ex2ndPasswordCheckResult valueOf(String string) {
            return Enum.valueOf(Ex2ndPasswordCheckResult.class, string);
        }

        private Ex2ndPasswordCheckResult(int n2, int n3) {
            this.ul = n2;
            this.um = n3;
        }

        private Ex2ndPasswordCheckResult(int n2) {
            this.ul = n2;
            this.um = -1;
        }

        public int getArg0() {
            return this.ul;
        }

        public int getArg1() {
            return this.um;
        }

        private static /* synthetic */ Ex2ndPasswordCheckResult[] a() {
            return new Ex2ndPasswordCheckResult[]{CREATE, CHECK, BLOCK_TIME, SUCCESS, ERROR};
        }

        static {
            a = Ex2ndPasswordCheckResult.a();
        }
    }
}
