/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ShowCalc
extends L2GameServerPacket {
    private int By;

    public ShowCalc(int n) {
        this.By = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(226);
        this.writeD(this.By);
    }
}
