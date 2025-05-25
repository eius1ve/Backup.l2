/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.model.items.Warehouse;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public class PrivateStoreManageListBuy
extends AbstractItemListPacket {
    private final boolean ff;
    private final int AE;
    private final long dq;
    private final List<TradeItem> cU;
    private final List<TradeItem> cV;

    public PrivateStoreManageListBuy(boolean bl, Player player) {
        this.ff = bl;
        this.AE = player.getObjectId();
        this.dq = player.getAdena();
        this.cU = player.getBuyList();
        this.cV = new ArrayList<TradeItem>();
        ItemInstance[] itemInstanceArray = player.getInventory().getItems();
        ArrayUtils.eqSort(itemInstanceArray, Warehouse.ItemClassComparator.getInstance());
        for (ItemInstance itemInstance : itemInstanceArray) {
            if (!itemInstance.canBeTraded(player) || itemInstance.getItemId() == 57) continue;
            TradeItem tradeItem = new TradeItem(itemInstance);
            this.cV.add(tradeItem);
            tradeItem.setObjectId(0);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(189);
        this.writeC(this.ff ? 1 : 2);
        if (this.ff) {
            this.writeD(this.AE);
            this.writeQ(this.dq);
            this.writeD(this.cU.size());
            for (TradeItem tradeItem : this.cU) {
                this.writeItemInfo(tradeItem);
                this.writeQ(tradeItem.getOwnersPrice());
                this.writeQ(tradeItem.getStorePrice());
                this.writeQ(tradeItem.getCount());
            }
            this.writeD(this.cV.size());
        } else {
            this.writeD(this.cV.size());
            this.writeD(this.cV.size());
            for (TradeItem tradeItem : this.cV) {
                this.writeItemInfo(tradeItem);
                this.writeQ(tradeItem.getStorePrice());
            }
        }
    }
}
