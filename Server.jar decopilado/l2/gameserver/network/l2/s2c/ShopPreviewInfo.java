/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Map;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ShopPreviewInfo
extends L2GameServerPacket {
    private Map<Integer, Integer> bA;

    public ShopPreviewInfo(Map<Integer, Integer> map) {
        this.bA = map;
    }

    @Override
    protected void writeImpl() {
        this.writeC(246);
        this.writeD(59);
        this.writeD(this.m(0));
        this.writeD(this.m(8));
        this.writeD(this.m(9));
        this.writeD(this.m(4));
        this.writeD(this.m(13));
        this.writeD(this.m(14));
        this.writeD(this.m(1));
        this.writeD(this.m(5));
        this.writeD(this.m(7));
        this.writeD(this.m(10));
        this.writeD(this.m(6));
        this.writeD(this.m(11));
        this.writeD(this.m(12));
        this.writeD(this.m(28));
        this.writeD(this.m(5));
        this.writeD(this.m(2));
        this.writeD(this.m(3));
        this.writeD(this.m(16));
        this.writeD(this.m(15));
    }

    private int m(int n) {
        return this.bA.get(n) != null ? this.bA.get(n) : 0;
    }
}
