/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class WareHouseDone
extends L2GameServerPacket {
    public static final L2GameServerPacket SUCCESS = new WareHouseDone(1);
    public static final L2GameServerPacket FAIL = new WareHouseDone(0);
    private final int CM;

    @Override
    protected void writeImpl() {
        this.writeC(67);
        this.writeD(this.CM);
    }

    public WareHouseDone(int n) {
        this.CM = n;
    }
}
