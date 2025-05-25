/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class PrivateStoreListBuy
extends AbstractItemListPacket {
    private int rR;
    private long dg;
    private List<TradeItem> aV;

    public PrivateStoreListBuy(Player player, Player player2) {
        this.dg = player.getAdena();
        this.rR = player2.getObjectId();
        this.aV = new ArrayList<TradeItem>();
        List<TradeItem> list = player2.getBuyList();
        ItemInstance[] itemInstanceArray = player.getInventory().getItems();
        for (TradeItem tradeItem : list) {
            TradeItem tradeItem2 = null;
            for (ItemInstance itemInstance : itemInstanceArray) {
                if (itemInstance.getItemId() != tradeItem.getItemId() || !itemInstance.canBeTraded(player)) continue;
                tradeItem2 = new TradeItem(itemInstance);
                this.aV.add(tradeItem2);
                tradeItem2.setOwnersPrice(tradeItem.getOwnersPrice());
                tradeItem2.setCount(tradeItem.getCount());
                tradeItem2.setCurrentValue(Math.min(tradeItem.getCount(), itemInstance.getCount()));
            }
            if (tradeItem2 != null) continue;
            tradeItem2 = new TradeItem();
            tradeItem2.setItemId(tradeItem.getItemId());
            tradeItem2.setOwnersPrice(tradeItem.getOwnersPrice());
            tradeItem2.setCount(tradeItem.getCount());
            tradeItem2.setCurrentValue(0L);
            this.aV.add(tradeItem2);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(190);
        this.writeD(this.rR);
        this.writeQ(this.dg);
        this.writeD(0);
        this.writeD(this.aV.size());
        for (TradeItem tradeItem : this.aV) {
            this.writeItemInfo(tradeItem, tradeItem.getCurrentValue());
            this.writeD(tradeItem.getObjectId());
            this.writeQ(tradeItem.getOwnersPrice());
            this.writeQ(tradeItem.getStorePrice());
            this.writeQ(tradeItem.getCount());
        }
    }
}
