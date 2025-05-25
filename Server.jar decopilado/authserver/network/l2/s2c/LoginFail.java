/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

import l2.authserver.network.l2.s2c.L2LoginServerPacket;

public final class LoginFail
extends L2LoginServerPacket {
    private int dZ;

    public LoginFail(LoginFailReason loginFailReason) {
        this.dZ = loginFailReason.getCode();
    }

    @Override
    protected void writeImpl() {
        this.writeC(1);
        this.writeD(this.dZ);
    }

    public static final class LoginFailReason
    extends Enum<LoginFailReason> {
        public static final /* enum */ LoginFailReason REASON_NO_MESSAGE = new LoginFailReason(0);
        public static final /* enum */ LoginFailReason REASON_SYSTEM_ERROR_LOGIN_LATER = new LoginFailReason(1);
        public static final /* enum */ LoginFailReason REASON_USER_OR_PASS_WRONG = new LoginFailReason(2);
        public static final /* enum */ LoginFailReason REASON_UNK3 = new LoginFailReason(3);
        public static final /* enum */ LoginFailReason REASON_ACCESS_FAILED_TRY_AGAIN_LATER = new LoginFailReason(4);
        public static final /* enum */ LoginFailReason REASON_ACCOUNT_INFO_INCORRECT_CONTACT_SUPPORT = new LoginFailReason(5);
        public static final /* enum */ LoginFailReason REASON_UNK6 = new LoginFailReason(6);
        public static final /* enum */ LoginFailReason REASON_ACCOUNT_IN_USE = new LoginFailReason(7);
        public static final /* enum */ LoginFailReason REASON_UNK8 = new LoginFailReason(8);
        public static final /* enum */ LoginFailReason REASON_UNK9 = new LoginFailReason(9);
        public static final /* enum */ LoginFailReason REASON_UNKA = new LoginFailReason(10);
        public static final /* enum */ LoginFailReason REASON_UNKB = new LoginFailReason(11);
        public static final /* enum */ LoginFailReason REASON_UNDER_18_YEARS_KR = new LoginFailReason(12);
        public static final /* enum */ LoginFailReason REASON_UNKD = new LoginFailReason(13);
        public static final /* enum */ LoginFailReason REASON_SERVER_OVERLOADED = new LoginFailReason(15);
        public static final /* enum */ LoginFailReason REASON_SERVER_MAINTENANCE = new LoginFailReason(16);
        public static final /* enum */ LoginFailReason REASON_TEMP_PASS_EXPIRED = new LoginFailReason(17);
        public static final /* enum */ LoginFailReason REASON_GAME_TIME_EXPIRED = new LoginFailReason(18);
        public static final /* enum */ LoginFailReason REASON_NO_TIME_LEFT = new LoginFailReason(19);
        public static final /* enum */ LoginFailReason REASON_SYSTEM_ERROR = new LoginFailReason(20);
        public static final /* enum */ LoginFailReason REASON_ACCESS_FAILED = new LoginFailReason(21);
        public static final /* enum */ LoginFailReason REASON_RESTRICTED_IP = new LoginFailReason(22);
        public static final /* enum */ LoginFailReason REASON_WEEK_USAGE_FINISHED = new LoginFailReason(30);
        public static final /* enum */ LoginFailReason REASON_SECURITY_CARD_NUMBER_INVALID = new LoginFailReason(31);
        public static final /* enum */ LoginFailReason REASON_AGE_NOT_VERIFIED_CANT_LOG_BEETWEEN_10PM_6AM = new LoginFailReason(32);
        public static final /* enum */ LoginFailReason REASON_SERVER_CANNOT_BE_ACCESSED_BY_YOUR_COUPON = new LoginFailReason(33);
        public static final /* enum */ LoginFailReason REASON_DUAL_BOX = new LoginFailReason(35);
        public static final /* enum */ LoginFailReason REASON_INACTIVE = new LoginFailReason(36);
        public static final /* enum */ LoginFailReason REASON_USER_AGREEMENT_REJECTED_ON_WEBSITE = new LoginFailReason(37);
        public static final /* enum */ LoginFailReason REASON_GUARDIAN_CONSENT_REQUIRED = new LoginFailReason(38);
        public static final /* enum */ LoginFailReason REASON_USER_AGREEMENT_DECLINED_OR_WITHDRAWL_REQUEST = new LoginFailReason(39);
        public static final /* enum */ LoginFailReason REASON_ACCOUNT_SUSPENDED_CALL = new LoginFailReason(40);
        public static final /* enum */ LoginFailReason REASON_CHANGE_PASSWORD_AND_QUIZ_ON_WEBSITE = new LoginFailReason(41);
        public static final /* enum */ LoginFailReason REASON_ALREADY_LOGGED_INTO_10_ACCOUNTS = new LoginFailReason(42);
        public static final /* enum */ LoginFailReason REASON_MASTER_ACCOUNT_RESTRICTED = new LoginFailReason(43);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_FAILED = new LoginFailReason(46);
        public static final /* enum */ LoginFailReason REASON_TELEPHONE_CERTIFICATION_UNAVAILABLE = new LoginFailReason(47);
        public static final /* enum */ LoginFailReason REASON_TELEPHONE_SIGNALS_DELAYED = new LoginFailReason(48);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_FAILED_LINE_BUSY = new LoginFailReason(49);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_SERVICE_NUMBER_EXPIRED_OR_INCORRECT = new LoginFailReason(50);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_SERVICE_CURRENTLY_BEING_CHECKED = new LoginFailReason(51);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_SERVICE_CANT_BE_USED_HEAVY_VOLUME = new LoginFailReason(52);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_SERVICE_EXPIRED_GAMEPLAY_BLOCKED = new LoginFailReason(53);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_FAILED_3_TIMES_GAMEPLAY_BLOCKED_30_MIN = new LoginFailReason(54);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_DAILY_USE_EXCEEDED = new LoginFailReason(55);
        public static final /* enum */ LoginFailReason REASON_CERTIFICATION_UNDERWAY_TRY_AGAIN_LATER = new LoginFailReason(56);
        public static final /* enum */ LoginFailReason UNK_DD = new LoginFailReason(221);
        private final int ea;
        private static final /* synthetic */ LoginFailReason[] a;

        public static LoginFailReason[] values() {
            return (LoginFailReason[])a.clone();
        }

        public static LoginFailReason valueOf(String string) {
            return Enum.valueOf(LoginFailReason.class, string);
        }

        private LoginFailReason(int n2) {
            this.ea = n2;
        }

        public final int getCode() {
            return this.ea;
        }

        private static /* synthetic */ LoginFailReason[] a() {
            return new LoginFailReason[]{REASON_NO_MESSAGE, REASON_SYSTEM_ERROR_LOGIN_LATER, REASON_USER_OR_PASS_WRONG, REASON_UNK3, REASON_ACCESS_FAILED_TRY_AGAIN_LATER, REASON_ACCOUNT_INFO_INCORRECT_CONTACT_SUPPORT, REASON_UNK6, REASON_ACCOUNT_IN_USE, REASON_UNK8, REASON_UNK9, REASON_UNKA, REASON_UNKB, REASON_UNDER_18_YEARS_KR, REASON_UNKD, REASON_SERVER_OVERLOADED, REASON_SERVER_MAINTENANCE, REASON_TEMP_PASS_EXPIRED, REASON_GAME_TIME_EXPIRED, REASON_NO_TIME_LEFT, REASON_SYSTEM_ERROR, REASON_ACCESS_FAILED, REASON_RESTRICTED_IP, REASON_WEEK_USAGE_FINISHED, REASON_SECURITY_CARD_NUMBER_INVALID, REASON_AGE_NOT_VERIFIED_CANT_LOG_BEETWEEN_10PM_6AM, REASON_SERVER_CANNOT_BE_ACCESSED_BY_YOUR_COUPON, REASON_DUAL_BOX, REASON_INACTIVE, REASON_USER_AGREEMENT_REJECTED_ON_WEBSITE, REASON_GUARDIAN_CONSENT_REQUIRED, REASON_USER_AGREEMENT_DECLINED_OR_WITHDRAWL_REQUEST, REASON_ACCOUNT_SUSPENDED_CALL, REASON_CHANGE_PASSWORD_AND_QUIZ_ON_WEBSITE, REASON_ALREADY_LOGGED_INTO_10_ACCOUNTS, REASON_MASTER_ACCOUNT_RESTRICTED, REASON_CERTIFICATION_FAILED, REASON_TELEPHONE_CERTIFICATION_UNAVAILABLE, REASON_TELEPHONE_SIGNALS_DELAYED, REASON_CERTIFICATION_FAILED_LINE_BUSY, REASON_CERTIFICATION_SERVICE_NUMBER_EXPIRED_OR_INCORRECT, REASON_CERTIFICATION_SERVICE_CURRENTLY_BEING_CHECKED, REASON_CERTIFICATION_SERVICE_CANT_BE_USED_HEAVY_VOLUME, REASON_CERTIFICATION_SERVICE_EXPIRED_GAMEPLAY_BLOCKED, REASON_CERTIFICATION_FAILED_3_TIMES_GAMEPLAY_BLOCKED_30_MIN, REASON_CERTIFICATION_DAILY_USE_EXCEEDED, REASON_CERTIFICATION_UNDERWAY_TRY_AGAIN_LATER, UNK_DD};
        }

        static {
            a = LoginFailReason.a();
        }
    }
}
