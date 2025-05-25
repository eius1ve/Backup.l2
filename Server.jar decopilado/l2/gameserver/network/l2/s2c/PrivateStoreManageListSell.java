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

public class PrivateStoreManageListSell
extends AbstractItemListPacket {
    private final boolean fg;
    private int rQ;
    private long dg;
    private boolean ec;
    private List<TradeItem> aV;
    private List<TradeItem> cW;

    public PrivateStoreManageListSell(boolean bl, Player player, boolean bl2) {
        this.fg = bl;
        this.rQ = player.getObjectId();
        this.dg = player.getAdena();
        this.ec = bl2;
        this.cW = player.getSellList(this.ec);
        this.aV = new ArrayList<TradeItem>();
        ItemInstance[] itemInstanceArray = this.cW.iterator();
        while (itemInstanceArray.hasNext()) {
            ItemInstance[] itemInstanceArray2 = itemInstanceArray.next();
            if (itemInstanceArray2.getCount() <= 0L) {
                this.cW.remove(itemInstanceArray2);
                continue;
            }
            ItemInstance itemInstance = player.getInventory().getItemByObjectId(itemInstanceArray2.getObjectId());
            if (itemInstance == null) {
                itemInstance = player.getInventory().getItemByItemId(itemInstanceArray2.getItemId());
            }
            if (itemInstance == null || !itemInstance.canBeTraded(player) || itemInstance.getItemId() == 57) {
                this.cW.remove(itemInstanceArray2);
                continue;
            }
            itemInstanceArray2.setCount(Math.min(itemInstance.getCount(), itemInstanceArray2.getCount()));
        }
        block1: for (ItemInstance itemInstance : itemInstanceArray = player.getInventory().getItems()) {
            if (!itemInstance.canBeTraded(player) || itemInstance.getItemId() == 57) continue;
            for (TradeItem tradeItem : this.cW) {
                if (tradeItem.getObjectId() != itemInstance.getObjectId()) continue;
                if (tradeItem.getCount() == itemInstance.getCount()) continue block1;
                TradeItem tradeItem2 = new TradeItem(itemInstance);
                tradeItem2.setCount(itemInstance.getCount() - tradeItem.getCount());
                this.aV.add(tradeItem2);
                continue block1;
            }
            this.aV.add(new TradeItem(itemInstance));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(160);
        this.writeC(this.fg ? 1 : 2);
        if (this.fg) {
            this.writeD(this.rQ);
            this.writeD(this.ec ? 1 : 0);
            this.writeQ(this.dg);
            this.writeD(this.cW.size());
            for (TradeItem tradeItem : this.cW) {
                this.writeItemInfo(tradeItem);
                this.writeQ(tradeItem.getOwnersPrice());
                this.writeQ(tradeItem.getStorePrice());
            }
            this.writeD(this.aV.size());
        } else {
            this.writeD(this.aV.size());
            this.writeD(this.aV.size());
            for (TradeItem tradeItem : this.aV) {
                this.writeItemInfo(tradeItem);
                this.writeQ(tradeItem.getStorePrice());
            }
        }
    }
}
