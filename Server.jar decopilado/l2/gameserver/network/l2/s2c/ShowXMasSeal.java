/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ShowXMasSeal
extends L2GameServerPacket {
    private int BA;

    public ShowXMasSeal(int n) {
        this.BA = n;
    }

    @Override
    protected void writeImpl() {
        this.writeC(248);
        this.writeD(this.BA);
    }
}
