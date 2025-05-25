/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBlockRemoveResult
extends L2GameServerPacket {
    private final boolean er;
    private final String eT;

    public ExBlockRemoveResult(boolean bl, String string) {
        this.er = bl;
        this.eT = string;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(238);
        this.writeD(this.er);
        this.writeS(this.eT);
    }
}
