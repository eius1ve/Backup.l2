/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collections;
import java.util.List;
import l2.gameserver.data.xml.holder.BuyListHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.s2c.ExBuySellList;

public static class ExBuySellList.BuyList
extends ExBuySellList {
    private final int uK;
    private final List<TradeItem> cb;
    private final long dg;
    private final double Z;

    public ExBuySellList.BuyList(BuyListHolder.NpcTradeList npcTradeList, Player player, double d) {
        super(0, player);
        this.dg = player.getAdena();
        this.Z = d;
        if (npcTradeList != null) {
            this.uK = npcTradeList.getListId();
            this.cb = npcTradeList.getItems();
            player.setBuyListId(this.uK);
        } else {
            this.uK = 0;
            this.cb = Collections.emptyList();
            player.setBuyListId(0);
        }
    }

    @Override
    protected void writeImpl() {
        super.writeImpl();
        this.writeQ(this.dg);
        this.writeD(this.uK);
        this.writeD(this._slots);
        this.writeH(this.cb.size());
        for (TradeItem tradeItem : this.cb) {
            this.writeItemInfo(tradeItem, tradeItem.getCurrentValue());
            this.writeQ((long)((double)tradeItem.getOwnersPrice() * (1.0 + this.Z)));
        }
    }
}
