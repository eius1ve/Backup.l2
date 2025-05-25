/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPutItemResultForVariationMake
extends L2GameServerPacket {
    public static final ExPutItemResultForVariationMake FAIL_PACKET = new ExPutItemResultForVariationMake(0, false);
    private int _itemObjId;
    private int dW;
    private int dA;

    public ExPutItemResultForVariationMake(int n, boolean bl) {
        this._itemObjId = n;
        this.dW = 0;
        this.dA = bl ? 1 : 0;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(84);
        this.writeD(this._itemObjId);
        this.writeD(this.dW);
        this.writeD(this.dA);
    }
}
