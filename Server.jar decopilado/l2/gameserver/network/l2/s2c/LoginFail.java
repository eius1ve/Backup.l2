/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class LoginFail
extends L2GameServerPacket {
    public static int NO_TEXT = 0;
    public static int SYSTEM_ERROR_LOGIN_LATER = 1;
    public static int PASSWORD_DOES_NOT_MATCH_THIS_ACCOUNT = 2;
    public static int PASSWORD_DOES_NOT_MATCH_THIS_ACCOUNT2 = 3;
    public static int ACCESS_FAILED_TRY_LATER = 4;
    public static int INCORRECT_ACCOUNT_INFO_CONTACT_CUSTOMER_SUPPORT = 5;
    public static int ACCESS_FAILED_TRY_LATER2 = 6;
    public static int ACOUNT_ALREADY_IN_USE = 7;
    public static int ACCESS_FAILED_TRY_LATER3 = 8;
    public static int ACCESS_FAILED_TRY_LATER4 = 9;
    public static int ACCESS_FAILED_TRY_LATER5 = 10;
    private int ef;

    public LoginFail(int n) {
        this.ef = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(10);
        this.writeD(this.ef);
        this.writeD(this.ef);
    }
}
