/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

public static final class LoginFail.LoginFailReason
extends Enum<LoginFail.LoginFailReason> {
    public static final /* enum */ LoginFail.LoginFailReason REASON_NO_MESSAGE = new LoginFail.LoginFailReason(0);
    public static final /* enum */ LoginFail.LoginFailReason REASON_SYSTEM_ERROR_LOGIN_LATER = new LoginFail.LoginFailReason(1);
    public static final /* enum */ LoginFail.LoginFailReason REASON_USER_OR_PASS_WRONG = new LoginFail.LoginFailReason(2);
    public static final /* enum */ LoginFail.LoginFailReason REASON_UNK3 = new LoginFail.LoginFailReason(3);
    public static final /* enum */ LoginFail.LoginFailReason REASON_ACCESS_FAILED_TRY_AGAIN_LATER = new LoginFail.LoginFailReason(4);
    public static final /* enum */ LoginFail.LoginFailReason REASON_ACCOUNT_INFO_INCORRECT_CONTACT_SUPPORT = new LoginFail.LoginFailReason(5);
    public static final /* enum */ LoginFail.LoginFailReason REASON_UNK6 = new LoginFail.LoginFailReason(6);
    public static final /* enum */ LoginFail.LoginFailReason REASON_ACCOUNT_IN_USE = new LoginFail.LoginFailReason(7);
    public static final /* enum */ LoginFail.LoginFailReason REASON_UNK8 = new LoginFail.LoginFailReason(8);
    public static final /* enum */ LoginFail.LoginFailReason REASON_UNK9 = new LoginFail.LoginFailReason(9);
    public static final /* enum */ LoginFail.LoginFailReason REASON_UNKA = new LoginFail.LoginFailReason(10);
    public static final /* enum */ LoginFail.LoginFailReason REASON_UNKB = new LoginFail.LoginFailReason(11);
    public static final /* enum */ LoginFail.LoginFailReason REASON_UNDER_18_YEARS_KR = new LoginFail.LoginFailReason(12);
    public static final /* enum */ LoginFail.LoginFailReason REASON_UNKD = new LoginFail.LoginFailReason(13);
    public static final /* enum */ LoginFail.LoginFailReason REASON_SERVER_OVERLOADED = new LoginFail.LoginFailReason(15);
    public static final /* enum */ LoginFail.LoginFailReason REASON_SERVER_MAINTENANCE = new LoginFail.LoginFailReason(16);
    public static final /* enum */ LoginFail.LoginFailReason REASON_TEMP_PASS_EXPIRED = new LoginFail.LoginFailReason(17);
    public static final /* enum */ LoginFail.LoginFailReason REASON_GAME_TIME_EXPIRED = new LoginFail.LoginFailReason(18);
    public static final /* enum */ LoginFail.LoginFailReason REASON_NO_TIME_LEFT = new LoginFail.LoginFailReason(19);
    public static final /* enum */ LoginFail.LoginFailReason REASON_SYSTEM_ERROR = new LoginFail.LoginFailReason(20);
    public static final /* enum */ LoginFail.LoginFailReason REASON_ACCESS_FAILED = new LoginFail.LoginFailReason(21);
    public static final /* enum */ LoginFail.LoginFailReason REASON_RESTRICTED_IP = new LoginFail.LoginFailReason(22);
    public static final /* enum */ LoginFail.LoginFailReason REASON_WEEK_USAGE_FINISHED = new LoginFail.LoginFailReason(30);
    public static final /* enum */ LoginFail.LoginFailReason REASON_SECURITY_CARD_NUMBER_INVALID = new LoginFail.LoginFailReason(31);
    public static final /* enum */ LoginFail.LoginFailReason REASON_AGE_NOT_VERIFIED_CANT_LOG_BEETWEEN_10PM_6AM = new LoginFail.LoginFailReason(32);
    public static final /* enum */ LoginFail.LoginFailReason REASON_SERVER_CANNOT_BE_ACCESSED_BY_YOUR_COUPON = new LoginFail.LoginFailReason(33);
    public static final /* enum */ LoginFail.LoginFailReason REASON_DUAL_BOX = new LoginFail.LoginFailReason(35);
    public static final /* enum */ LoginFail.LoginFailReason REASON_INACTIVE = new LoginFail.LoginFailReason(36);
    public static final /* enum */ LoginFail.LoginFailReason REASON_USER_AGREEMENT_REJECTED_ON_WEBSITE = new LoginFail.LoginFailReason(37);
    public static final /* enum */ LoginFail.LoginFailReason REASON_GUARDIAN_CONSENT_REQUIRED = new LoginFail.LoginFailReason(38);
    public static final /* enum */ LoginFail.LoginFailReason REASON_USER_AGREEMENT_DECLINED_OR_WITHDRAWL_REQUEST = new LoginFail.LoginFailReason(39);
    public static final /* enum */ LoginFail.LoginFailReason REASON_ACCOUNT_SUSPENDED_CALL = new LoginFail.LoginFailReason(40);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CHANGE_PASSWORD_AND_QUIZ_ON_WEBSITE = new LoginFail.LoginFailReason(41);
    public static final /* enum */ LoginFail.LoginFailReason REASON_ALREADY_LOGGED_INTO_10_ACCOUNTS = new LoginFail.LoginFailReason(42);
    public static final /* enum */ LoginFail.LoginFailReason REASON_MASTER_ACCOUNT_RESTRICTED = new LoginFail.LoginFailReason(43);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_FAILED = new LoginFail.LoginFailReason(46);
    public static final /* enum */ LoginFail.LoginFailReason REASON_TELEPHONE_CERTIFICATION_UNAVAILABLE = new LoginFail.LoginFailReason(47);
    public static final /* enum */ LoginFail.LoginFailReason REASON_TELEPHONE_SIGNALS_DELAYED = new LoginFail.LoginFailReason(48);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_FAILED_LINE_BUSY = new LoginFail.LoginFailReason(49);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_SERVICE_NUMBER_EXPIRED_OR_INCORRECT = new LoginFail.LoginFailReason(50);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_SERVICE_CURRENTLY_BEING_CHECKED = new LoginFail.LoginFailReason(51);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_SERVICE_CANT_BE_USED_HEAVY_VOLUME = new LoginFail.LoginFailReason(52);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_SERVICE_EXPIRED_GAMEPLAY_BLOCKED = new LoginFail.LoginFailReason(53);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_FAILED_3_TIMES_GAMEPLAY_BLOCKED_30_MIN = new LoginFail.LoginFailReason(54);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_DAILY_USE_EXCEEDED = new LoginFail.LoginFailReason(55);
    public static final /* enum */ LoginFail.LoginFailReason REASON_CERTIFICATION_UNDERWAY_TRY_AGAIN_LATER = new LoginFail.LoginFailReason(56);
    public static final /* enum */ LoginFail.LoginFailReason UNK_DD = new LoginFail.LoginFailReason(221);
    private final int ea;
    private static final /* synthetic */ LoginFail.LoginFailReason[] a;

    public static LoginFail.LoginFailReason[] values() {
        return (LoginFail.LoginFailReason[])a.clone();
    }

    public static LoginFail.LoginFailReason valueOf(String string) {
        return Enum.valueOf(LoginFail.LoginFailReason.class, string);
    }

    private LoginFail.LoginFailReason(int n2) {
        this.ea = n2;
    }

    public final int getCode() {
        return this.ea;
    }

    private static /* synthetic */ LoginFail.LoginFailReason[] a() {
        return new LoginFail.LoginFailReason[]{REASON_NO_MESSAGE, REASON_SYSTEM_ERROR_LOGIN_LATER, REASON_USER_OR_PASS_WRONG, REASON_UNK3, REASON_ACCESS_FAILED_TRY_AGAIN_LATER, REASON_ACCOUNT_INFO_INCORRECT_CONTACT_SUPPORT, REASON_UNK6, REASON_ACCOUNT_IN_USE, REASON_UNK8, REASON_UNK9, REASON_UNKA, REASON_UNKB, REASON_UNDER_18_YEARS_KR, REASON_UNKD, REASON_SERVER_OVERLOADED, REASON_SERVER_MAINTENANCE, REASON_TEMP_PASS_EXPIRED, REASON_GAME_TIME_EXPIRED, REASON_NO_TIME_LEFT, REASON_SYSTEM_ERROR, REASON_ACCESS_FAILED, REASON_RESTRICTED_IP, REASON_WEEK_USAGE_FINISHED, REASON_SECURITY_CARD_NUMBER_INVALID, REASON_AGE_NOT_VERIFIED_CANT_LOG_BEETWEEN_10PM_6AM, REASON_SERVER_CANNOT_BE_ACCESSED_BY_YOUR_COUPON, REASON_DUAL_BOX, REASON_INACTIVE, REASON_USER_AGREEMENT_REJECTED_ON_WEBSITE, REASON_GUARDIAN_CONSENT_REQUIRED, REASON_USER_AGREEMENT_DECLINED_OR_WITHDRAWL_REQUEST, REASON_ACCOUNT_SUSPENDED_CALL, REASON_CHANGE_PASSWORD_AND_QUIZ_ON_WEBSITE, REASON_ALREADY_LOGGED_INTO_10_ACCOUNTS, REASON_MASTER_ACCOUNT_RESTRICTED, REASON_CERTIFICATION_FAILED, REASON_TELEPHONE_CERTIFICATION_UNAVAILABLE, REASON_TELEPHONE_SIGNALS_DELAYED, REASON_CERTIFICATION_FAILED_LINE_BUSY, REASON_CERTIFICATION_SERVICE_NUMBER_EXPIRED_OR_INCORRECT, REASON_CERTIFICATION_SERVICE_CURRENTLY_BEING_CHECKED, REASON_CERTIFICATION_SERVICE_CANT_BE_USED_HEAVY_VOLUME, REASON_CERTIFICATION_SERVICE_EXPIRED_GAMEPLAY_BLOCKED, REASON_CERTIFICATION_FAILED_3_TIMES_GAMEPLAY_BLOCKED_30_MIN, REASON_CERTIFICATION_DAILY_USE_EXCEEDED, REASON_CERTIFICATION_UNDERWAY_TRY_AGAIN_LATER, UNK_DD};
    }

    static {
        a = LoginFail.LoginFailReason.a();
    }
}
