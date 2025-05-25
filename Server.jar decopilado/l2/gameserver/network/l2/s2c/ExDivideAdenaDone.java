/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExDivideAdenaDone
extends L2GameServerPacket {
    private final int uS;
    private final long dh;
    private final long di;
    private final String eU;

    public ExDivideAdenaDone(int n, long l, long l2, String string) {
        this.uS = n;
        this.dh = l;
        this.di = l2;
        this.eU = string;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(349);
        this.writeC(1);
        this.writeC(0);
        this.writeD(this.uS);
        this.writeQ(this.di);
        this.writeQ(this.dh);
        this.writeS(this.eU);
    }
}
