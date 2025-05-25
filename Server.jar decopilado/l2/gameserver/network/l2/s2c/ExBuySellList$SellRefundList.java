/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.s2c.ExBuySellList;

public static class ExBuySellList.SellRefundList
extends ExBuySellList {
    private final List<TradeItem> cc;
    private final List<TradeItem> cd;
    private int uL;

    public ExBuySellList.SellRefundList(Player player, boolean bl) {
        super(1, player);
        int n = this.uL = bl ? 1 : 0;
        if (bl) {
            this.cd = Collections.emptyList();
            this.cc = Collections.emptyList();
        } else {
            ItemInstance[] itemInstanceArray = player.getRefund().getItems();
            this.cd = new ArrayList<TradeItem>(itemInstanceArray.length);
            for (ItemInstance itemInstance : itemInstanceArray) {
                this.cd.add(new TradeItem(itemInstance));
            }
            itemInstanceArray = player.getInventory().getItems();
            this.cc = new ArrayList<TradeItem>(itemInstanceArray.length);
            for (ItemInstance itemInstance : itemInstanceArray) {
                if (!itemInstance.canBeSold(player)) continue;
                this.cc.add(new TradeItem(itemInstance));
            }
        }
    }

    @Override
    protected void writeImpl() {
        super.writeImpl();
        this.writeD(this._slots);
        this.writeH(this.cc.size());
        for (TradeItem tradeItem : this.cc) {
            this.writeItemInfo(tradeItem);
            this.writeQ(Math.max(1L, tradeItem.getReferencePrice() / Config.ALT_SHOP_REFUND_SELL_DIVISOR));
        }
        this.writeH(this.cd.size());
        for (TradeItem tradeItem : this.cd) {
            this.writeItemInfo(tradeItem);
            this.writeD(tradeItem.getObjectId());
            this.writeQ(tradeItem.getCount() * tradeItem.getReferencePrice() / 2L);
        }
        this.writeC(this.uL);
    }
}
