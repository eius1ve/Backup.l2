/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.s2c;

import l2.authserver.network.l2.SessionKey;
import l2.authserver.network.l2.s2c.L2LoginServerPacket;

public final class PlayOk
extends L2LoginServerPacket {
    private int eg;
    private int eh;

    public PlayOk(SessionKey sessionKey) {
        this.eg = sessionKey.playOkID1;
        this.eh = sessionKey.playOkID2;
    }

    @Override
    protected void writeImpl() {
        this.writeC(7);
        this.writeD(this.eg);
        this.writeD(this.eh);
    }
}
