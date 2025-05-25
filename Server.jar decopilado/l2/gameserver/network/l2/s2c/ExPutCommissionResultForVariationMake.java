/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPutCommissionResultForVariationMake
extends L2GameServerPacket {
    public static final ExPutCommissionResultForVariationMake FAIL_PACKET = new ExPutCommissionResultForVariationMake();
    private int wT;
    private int dW;
    private int dA;
    private long cR;
    private long dj;

    private ExPutCommissionResultForVariationMake() {
        this.wT = 0;
        this.dW = 1;
        this.cR = 0L;
        this.dj = 0L;
        this.dA = 0;
    }

    public ExPutCommissionResultForVariationMake(int n, long l) {
        this.wT = n;
        this.dW = 1;
        this.cR = l;
        this.dj = l;
        this.dA = 1;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(86);
        this.writeD(this.wT);
        this.writeD(this.dW);
        this.writeQ(this.cR);
        this.writeQ(this.dj);
        this.writeD(this.dA);
    }
}
