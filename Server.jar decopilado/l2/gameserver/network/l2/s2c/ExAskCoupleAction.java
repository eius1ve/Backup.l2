/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExAskCoupleAction
extends L2GameServerPacket {
    private int fW;
    private int uu;

    public ExAskCoupleAction(int n, int n2) {
        this.fW = n;
        this.uu = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(188);
        this.writeD(this.uu);
        this.writeD(this.fW);
    }
}
