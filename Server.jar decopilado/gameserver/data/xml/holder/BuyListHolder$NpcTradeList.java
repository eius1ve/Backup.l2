/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.items.TradeItem;

public static class BuyListHolder.NpcTradeList {
    private List<TradeItem> aa = new ArrayList<TradeItem>();
    private int _id;
    private int _npcId;

    public BuyListHolder.NpcTradeList(int n) {
        this._id = n;
    }

    public int getListId() {
        return this._id;
    }

    public void setNpcId(int n) {
        this._npcId = n;
    }

    public int getNpcId() {
        return this._npcId;
    }

    public void addItem(TradeItem tradeItem) {
        this.aa.add(tradeItem);
    }

    public synchronized List<TradeItem> getItems() {
        ArrayList<TradeItem> arrayList = new ArrayList<TradeItem>();
        long l = System.currentTimeMillis() / 60000L;
        for (TradeItem tradeItem : this.aa) {
            if (tradeItem.isCountLimited()) {
                if (tradeItem.getCurrentValue() < tradeItem.getCount() && (long)(tradeItem.getLastRechargeTime() + tradeItem.getRechargeTime()) <= l) {
                    tradeItem.setLastRechargeTime(tradeItem.getLastRechargeTime() + tradeItem.getRechargeTime());
                    tradeItem.setCurrentValue(tradeItem.getCount());
                }
                if (tradeItem.getCurrentValue() == 0L) continue;
            }
            arrayList.add(tradeItem);
        }
        return arrayList;
    }

    public TradeItem getItemByItemId(int n) {
        for (TradeItem tradeItem : this.aa) {
            if (tradeItem.getItemId() != n) continue;
            return tradeItem;
        }
        return null;
    }

    public synchronized void updateItems(List<TradeItem> list) {
        for (TradeItem tradeItem : list) {
            TradeItem tradeItem2 = this.getItemByItemId(tradeItem.getItemId());
            if (!tradeItem2.isCountLimited()) continue;
            tradeItem2.setCurrentValue(Math.max(tradeItem2.getCurrentValue() - tradeItem.getCount(), 0L));
        }
    }
}
