/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;

public final class TradeItem
extends ItemInfo {
    private long _price;
    private long cB;
    private long cC;
    private int oE;
    private int oF;

    public TradeItem() {
    }

    public TradeItem(ItemInstance itemInstance) {
        super(itemInstance);
        this.setReferencePrice(itemInstance.getReferencePrice());
    }

    public void setOwnersPrice(long l) {
        this._price = l;
    }

    public long getOwnersPrice() {
        return this._price;
    }

    public void setReferencePrice(long l) {
        this.cB = l;
    }

    public long getReferencePrice() {
        return this.cB;
    }

    public long getStorePrice() {
        return this.getReferencePrice() / 2L;
    }

    public void setCurrentValue(long l) {
        this.cC = l;
    }

    public long getCurrentValue() {
        return this.cC;
    }

    public void setRechargeTime(int n) {
        this.oF = n;
    }

    public int getRechargeTime() {
        return this.oF;
    }

    public boolean isCountLimited() {
        return this.getCount() > 0L;
    }

    public void setLastRechargeTime(int n) {
        this.oE = n;
    }

    public int getLastRechargeTime() {
        return this.oE;
    }
}
