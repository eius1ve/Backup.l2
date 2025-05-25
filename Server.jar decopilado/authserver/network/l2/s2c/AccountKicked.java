/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

import l2.authserver.network.l2.s2c.L2LoginServerPacket;

public final class AccountKicked
extends L2LoginServerPacket {
    private int dy;

    public AccountKicked(AccountKickedReason accountKickedReason) {
        this.dy = accountKickedReason.getCode();
    }

    @Override
    protected void writeImpl() {
        this.writeC(2);
        this.writeD(this.dy);
    }

    public static final class AccountKickedReason
    extends Enum<AccountKickedReason> {
        public static final /* enum */ AccountKickedReason REASON_FALSE_DATA_STEALER_REPORT = new AccountKickedReason(0);
        public static final /* enum */ AccountKickedReason REASON_DATA_STEALER = new AccountKickedReason(1);
        public static final /* enum */ AccountKickedReason REASON_SOUSPICION_DATA_STEALER = new AccountKickedReason(3);
        public static final /* enum */ AccountKickedReason REASON_NON_PAYEMENT_CELL_PHONE = new AccountKickedReason(4);
        public static final /* enum */ AccountKickedReason REASON_30_DAYS_SUSPENDED_CASH = new AccountKickedReason(8);
        public static final /* enum */ AccountKickedReason REASON_PERMANENTLY_SUSPENDED_CASH = new AccountKickedReason(16);
        public static final /* enum */ AccountKickedReason REASON_PERMANENTLY_BANNED = new AccountKickedReason(32);
        public static final /* enum */ AccountKickedReason REASON_ACCOUNT_MUST_BE_VERIFIED = new AccountKickedReason(64);
        private final int dX;
        private static final /* synthetic */ AccountKickedReason[] a;

        public static AccountKickedReason[] values() {
            return (AccountKickedReason[])a.clone();
        }

        public static AccountKickedReason valueOf(String string) {
            return Enum.valueOf(AccountKickedReason.class, string);
        }

        private AccountKickedReason(int n2) {
            this.dX = n2;
        }

        public final int getCode() {
            return this.dX;
        }

        private static /* synthetic */ AccountKickedReason[] a() {
            return new AccountKickedReason[]{REASON_FALSE_DATA_STEALER_REPORT, REASON_DATA_STEALER, REASON_SOUSPICION_DATA_STEALER, REASON_NON_PAYEMENT_CELL_PHONE, REASON_30_DAYS_SUSPENDED_CASH, REASON_PERMANENTLY_SUSPENDED_CASH, REASON_PERMANENTLY_BANNED, REASON_ACCOUNT_MUST_BE_VERIFIED};
        }

        static {
            a = AccountKickedReason.a();
        }
    }
}
