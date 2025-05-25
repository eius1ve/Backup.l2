/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeReceiveUpdatePower
extends L2GameServerPacket {
    private int pd;

    public PledgeReceiveUpdatePower(int n) {
        this.pd = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(67);
        this.writeD(this.pd);
    }
}
