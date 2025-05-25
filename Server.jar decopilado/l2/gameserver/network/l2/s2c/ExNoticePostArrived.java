/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExNoticePostArrived
extends L2GameServerPacket {
    public static final L2GameServerPacket STATIC_TRUE = new ExNoticePostArrived(1);
    public static final L2GameServerPacket STATIC_FALSE = new ExNoticePostArrived(0);
    private int gW;

    public ExNoticePostArrived(int n) {
        this.gW = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(170);
        this.writeD(this.gW);
    }
}
