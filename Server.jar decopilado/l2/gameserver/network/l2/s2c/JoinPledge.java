/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class JoinPledge
extends L2GameServerPacket {
    private int rK;

    public JoinPledge(int n) {
        this.rK = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(45);
        this.writeD(this.rK);
    }
}
