/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPrivateStoreSellingResult
extends L2GameServerPacket {
    private int wR;
    private long count;
    private String fk;

    public ExPrivateStoreSellingResult(int n, long l, String string) {
        this.wR = n;
        this.count = l;
        this.fk = string;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(454);
        this.writeD(this.wR);
        this.writeQ(this.count);
        this.writeS(this.fk);
    }
}
