/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm.as2gs;

import l2.authserver.network.gamecomm.SendablePacket;

public class NotifyPwdCngResult
extends SendablePacket {
    public static final int RESULT_OK = 1;
    public static final int RESULT_WRONG_OLD_PASSWORD = 2;
    public static final int RESULT_WRONG_NEW_PASSWORD = 3;
    public static final int RESULT_WRONG_ACCOUNT = 4;
    private int dz;
    private int dA;

    public NotifyPwdCngResult(int n, int n2) {
        this.dz = n;
        this.dA = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeC(161);
        this.writeD(this.dz);
        this.writeD(this.dA);
    }
}
