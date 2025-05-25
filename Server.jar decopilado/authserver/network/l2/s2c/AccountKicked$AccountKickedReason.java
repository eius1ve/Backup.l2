/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

public static final class AccountKicked.AccountKickedReason
extends Enum<AccountKicked.AccountKickedReason> {
    public static final /* enum */ AccountKicked.AccountKickedReason REASON_FALSE_DATA_STEALER_REPORT = new AccountKicked.AccountKickedReason(0);
    public static final /* enum */ AccountKicked.AccountKickedReason REASON_DATA_STEALER = new AccountKicked.AccountKickedReason(1);
    public static final /* enum */ AccountKicked.AccountKickedReason REASON_SOUSPICION_DATA_STEALER = new AccountKicked.AccountKickedReason(3);
    public static final /* enum */ AccountKicked.AccountKickedReason REASON_NON_PAYEMENT_CELL_PHONE = new AccountKicked.AccountKickedReason(4);
    public static final /* enum */ AccountKicked.AccountKickedReason REASON_30_DAYS_SUSPENDED_CASH = new AccountKicked.AccountKickedReason(8);
    public static final /* enum */ AccountKicked.AccountKickedReason REASON_PERMANENTLY_SUSPENDED_CASH = new AccountKicked.AccountKickedReason(16);
    public static final /* enum */ AccountKicked.AccountKickedReason REASON_PERMANENTLY_BANNED = new AccountKicked.AccountKickedReason(32);
    public static final /* enum */ AccountKicked.AccountKickedReason REASON_ACCOUNT_MUST_BE_VERIFIED = new AccountKicked.AccountKickedReason(64);
    private final int dX;
    private static final /* synthetic */ AccountKicked.AccountKickedReason[] a;

    public static AccountKicked.AccountKickedReason[] values() {
        return (AccountKicked.AccountKickedReason[])a.clone();
    }

    public static AccountKicked.AccountKickedReason valueOf(String string) {
        return Enum.valueOf(AccountKicked.AccountKickedReason.class, string);
    }

    private AccountKicked.AccountKickedReason(int n2) {
        this.dX = n2;
    }

    public final int getCode() {
        return this.dX;
    }

    private static /* synthetic */ AccountKicked.AccountKickedReason[] a() {
        return new AccountKicked.AccountKickedReason[]{REASON_FALSE_DATA_STEALER_REPORT, REASON_DATA_STEALER, REASON_SOUSPICION_DATA_STEALER, REASON_NON_PAYEMENT_CELL_PHONE, REASON_30_DAYS_SUSPENDED_CASH, REASON_PERMANENTLY_SUSPENDED_CASH, REASON_PERMANENTLY_BANNED, REASON_ACCOUNT_MUST_BE_VERIFIED};
    }

    static {
        a = AccountKicked.AccountKickedReason.a();
    }
}
