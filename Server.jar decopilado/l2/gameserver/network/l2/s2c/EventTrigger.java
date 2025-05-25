/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class EventTrigger
extends L2GameServerPacket {
    private int uf;
    private boolean T;

    public EventTrigger(int n, boolean bl) {
        this.uf = n;
        this.T = bl;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(207);
        this.writeD(this.uf);
        this.writeC(this.T ? 1 : 0);
    }
}
