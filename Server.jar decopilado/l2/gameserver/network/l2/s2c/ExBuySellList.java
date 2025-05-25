/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.BuyListHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.s2c.AbstractItemListPacket;

public abstract class ExBuySellList
extends AbstractItemListPacket {
    protected int _type;
    protected int _slots;

    public ExBuySellList(int n, Player player) {
        this._type = n;
        this._slots = player.getInventory().getSize();
    }

    @Override
    protected void writeImpl() {
        this.writeEx(184);
        this.writeD(this._type);
    }

    public static class SellRefundList
    extends ExBuySellList {
        private final List<TradeItem> cc;
        private final List<TradeItem> cd;
        private int uL;

        public SellRefundList(Player player, boolean bl) {
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

    public static class BuyList
    extends ExBuySellList {
        private final int uK;
        private final List<TradeItem> cb;
        private final long dg;
        private final double Z;

        public BuyList(BuyListHolder.NpcTradeList npcTradeList, Player player, double d) {
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
}
