/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class TradeOtherAdd
extends AbstractItemListPacket {
    private final boolean fn;
    private ItemInfo a;
    private long cP;

    public TradeOtherAdd(boolean bl, ItemInfo itemInfo, long l) {
        this.fn = bl;
        this.a = itemInfo;
        this.cP = l;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(27);
        this.writeC(this.fn ? 1 : 2);
        if (this.fn) {
            this.writeD(1);
        } else {
            this.writeD(1);
            this.writeD(1);
            this.writeItemInfo(this.a, this.cP);
        }
    }
}
