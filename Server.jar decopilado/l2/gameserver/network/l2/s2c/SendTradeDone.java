/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SendTradeDone
extends L2GameServerPacket {
    public static final L2GameServerPacket SUCCESS = new SendTradeDone(1);
    public static final L2GameServerPacket FAIL = new SendTradeDone(0);
    private final int Bg;

    private SendTradeDone(int n) {
        this.Bg = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(28);
        this.writeD(this.Bg);
    }
}
