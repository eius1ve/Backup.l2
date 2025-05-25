/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExDominionChannelSet
extends L2GameServerPacket {
    public static final L2GameServerPacket ACTIVE = new ExDominionChannelSet(1);
    public static final L2GameServerPacket DEACTIVE = new ExDominionChannelSet(0);
    private int uT;

    public ExDominionChannelSet(int n) {
        this.uT = n;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(150);
        this.writeD(this.uT);
    }
}
