/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class TradeOwnAdd
extends AbstractItemListPacket {
    private final boolean fo;
    private ItemInfo a;
    private long cP;

    public TradeOwnAdd(boolean bl, ItemInfo itemInfo, long l) {
        this.fo = bl;
        this.a = itemInfo;
        this.cP = l;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(26);
        this.writeC(this.fo ? 1 : 2);
        if (this.fo) {
            this.writeD(1);
        } else {
            this.writeD(1);
            this.writeD(1);
            this.writeItemInfo(this.a, this.cP);
        }
    }
}
