/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExChangeNpcState
extends L2GameServerPacket {
    private int sR;
    private int _state;

    public ExChangeNpcState(int n, int n2) {
        this.sR = n;
        this._state = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(190);
        this.writeD(this.sR);
        this.writeD(this._state);
    }
}
