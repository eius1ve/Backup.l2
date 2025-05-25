/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RequestNetPing
extends L2GameServerPacket {
    private final int AQ;

    public RequestNetPing(int n) {
        this.AQ = n;
    }

    @Override
    protected void writeImpl() {
        this.writeC(217);
        this.writeD(this.AQ);
    }
}
