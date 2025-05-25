/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExShowQuestMark
extends L2GameServerPacket {
    private int _questId;
    private int xv;

    public ExShowQuestMark(int n, int n2) {
        this._questId = n;
        this.xv = n2;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(33);
        this.writeD(this._questId);
        this.writeD(this.xv);
    }
}
