/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.authcomm.gs2as;

import l2.gameserver.network.authcomm.SendablePacket;

public class WhitelistedOk
extends SendablePacket {
    private final int py;

    public WhitelistedOk(int n) {
        this.py = n;
    }

    @Override
    protected void writeImpl() {
        this.writeC(168);
        this.writeD(this.py);
    }
}
