/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExRotation
extends L2GameServerPacket {
    private int pM;
    private int qc;

    public ExRotation(int n, int n2) {
        this.pM = n;
        this.qc = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(194);
        this.writeD(this.pM);
        this.writeD(this.qc);
    }
}
