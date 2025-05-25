/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExPutItemResultForVariationCancel
extends L2GameServerPacket {
    private int qH;
    private int _itemId;
    private int wX;
    private int wY;
    private long _price;
    private boolean eI;

    public ExPutItemResultForVariationCancel(ItemInstance itemInstance, long l, boolean bl) {
        this.qH = itemInstance.getObjectId();
        this._itemId = itemInstance.getItemId();
        this.wX = itemInstance.getVariationStat1();
        this.wY = itemInstance.getVariationStat2();
        this._price = l;
        this.eI = bl;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(88);
        this.writeD(this.qH);
        this.writeD(this._itemId);
        this.writeD(this.wX);
        this.writeD(this.wY);
        this.writeQ(this._price);
        this.writeD(this.eI ? 1 : 0);
    }
}
