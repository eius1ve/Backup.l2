/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowFortressSiegeInfo
extends L2GameServerPacket {
    private int rd;
    private int xs;
    private int xt;

    @Override
    protected void writeImpl() {
        this.writeEx(23);
        this.writeD(this.rd);
        this.writeD(this.xs);
        this.writeD(this.xt);
    }
}
