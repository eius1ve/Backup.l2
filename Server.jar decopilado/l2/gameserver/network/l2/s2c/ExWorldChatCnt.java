/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExWorldChatCnt
extends L2GameServerPacket {
    private final int yf;

    public ExWorldChatCnt(int n) {
        this.yf = Math.max(0, n);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(373);
        this.writeD(this.yf);
    }
}
