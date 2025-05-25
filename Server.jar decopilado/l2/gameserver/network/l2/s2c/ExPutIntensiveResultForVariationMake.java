/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPutIntensiveResultForVariationMake
extends L2GameServerPacket {
    public static final ExPutIntensiveResultForVariationMake FAIL_PACKET = new ExPutIntensiveResultForVariationMake(0, 0, 0, 0L, false);
    private int qA;
    private int wV;
    private int wW;
    private int dA;
    private long cR;

    public ExPutIntensiveResultForVariationMake(int n, int n2, int n3, long l, boolean bl) {
        this.qA = n;
        this.wV = n2;
        this.wW = n3;
        this.cR = l;
        this.dA = bl ? 1 : 0;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(85);
        this.writeD(this.qA);
        this.writeD(this.wV);
        this.writeD(this.wW);
        this.writeQ(this.cR);
        this.writeD(this.dA);
    }
}
