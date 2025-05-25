/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class TradeUpdate
extends AbstractItemListPacket {
    private final boolean fq;
    private ItemInfo a;
    private long cP;

    public TradeUpdate(boolean bl, ItemInfo itemInfo, long l) {
        this.fq = bl;
        this.a = itemInfo;
        this.cP = l;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(129);
        this.writeC(this.fq ? 1 : 2);
        if (this.fq) {
            this.writeD(1);
        } else {
            this.writeD(1);
            this.writeD(1);
            this.writeH(this.cP > 0L && this.a.getItem().isStackable() ? 3 : 2);
            this.writeItemInfo(this.a, this.cP);
        }
    }
}
