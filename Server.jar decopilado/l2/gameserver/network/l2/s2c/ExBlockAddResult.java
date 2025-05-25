/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBlockAddResult
extends L2GameServerPacket {
    private final String eQ;

    public ExBlockAddResult(String string) {
        this.eQ = string;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(237);
        this.writeD(1);
        this.writeS(this.eQ);
    }
}
