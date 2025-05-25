/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm.as2gs;

import l2.authserver.network.gamecomm.SendablePacket;

public class LoginServerFail
extends SendablePacket {
    public static final int REASON_IP_BANNED = 1;
    public static final int REASON_IP_RESERVED = 2;
    public static final int REASON_WRONG_HEXID = 3;
    public static final int REASON_ID_RESERVED = 4;
    public static final int REASON_NO_FREE_ID = 5;
    public static final int NOT_AUTHED = 6;
    public static final int REASON_ALREADY_LOGGED_IN = 7;
    private int dy;

    public LoginServerFail(int n) {
        this.dy = n;
    }

    @Override
    protected void writeImpl() {
        this.writeC(1);
        this.writeC(this.dy);
    }
}
