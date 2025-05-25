/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

import l2.authserver.network.l2.SessionKey;
import l2.authserver.network.l2.s2c.L2LoginServerPacket;

public final class LoginOk
extends L2LoginServerPacket {
    private int eb;
    private int ec;

    public LoginOk(SessionKey sessionKey) {
        this.eb = sessionKey.loginOkID1;
        this.ec = sessionKey.loginOkID2;
    }

    @Override
    protected void writeImpl() {
        this.writeC(3);
        this.writeD(this.eb);
        this.writeD(this.ec);
        this.writeD(0);
        this.writeD(0);
        this.writeD(1002);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeB(new byte[16]);
    }
}
