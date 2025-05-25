/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExVariationResult
extends L2GameServerPacket {
    public static final ExVariationResult FAIL_PACKET = new ExVariationResult(0, 0, 0);
    private int xW;
    private int xX;
    private int dA;

    public ExVariationResult(int n, int n2, int n3) {
        this.xW = n;
        this.xX = n2;
        this.dA = n3;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(87);
        this.writeD(this.xW);
        this.writeD(this.xX);
        this.writeD(this.dA);
    }
}
