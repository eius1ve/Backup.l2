/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.data.xml.holder.BuyListHolder;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public final class BuyListSeed
extends AbstractItemListPacket {
    private int hp;
    private List<TradeItem> bV = new ArrayList<TradeItem>();
    private long da;

    public BuyListSeed(BuyListHolder.NpcTradeList npcTradeList, int n, long l) {
        this.da = l;
        this.hp = n;
        this.bV = npcTradeList.getItems();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(233);
        this.writeQ(this.da);
        this.writeD(0);
        this.writeD(this.hp);
        this.writeH(this.bV.size());
        for (TradeItem tradeItem : this.bV) {
            this.writeItemInfo(tradeItem);
            this.writeQ(tradeItem.getOwnersPrice());
        }
    }
}
