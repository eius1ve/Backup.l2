/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExSetPartyLooting
extends L2GameServerPacket {
    private int dA;
    private int _mode;

    public ExSetPartyLooting(int n, int n2) {
        this.dA = n;
        this._mode = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(193);
        this.writeD(this.dA);
        this.writeD(this._mode);
    }
}
