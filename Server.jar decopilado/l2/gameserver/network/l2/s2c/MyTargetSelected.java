/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class MyTargetSelected
extends L2GameServerPacket {
    private int fW;
    private int xk;

    public MyTargetSelected(int n, int n2) {
        this.fW = n;
        this.xk = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(185);
        this.writeD(1);
        this.writeD(this.fW);
        this.writeH(this.xk);
        this.writeD(0);
    }
}
