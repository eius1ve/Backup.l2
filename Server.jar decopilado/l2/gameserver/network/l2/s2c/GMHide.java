/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class GMHide
extends L2GameServerPacket {
    private final int yu;

    public GMHide(int n) {
        this.yu = n;
    }

    @Override
    protected void writeImpl() {
        this.writeC(147);
        this.writeD(this.yu);
    }
}
