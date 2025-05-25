/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.items.ItemInstance;

private class RequestMultiSellChoose.ItemData {
    private final int rv;
    private final long cS;
    private final ItemInstance f;

    public RequestMultiSellChoose.ItemData(int n, long l, ItemInstance itemInstance) {
        this.rv = n;
        this.cS = l;
        this.f = itemInstance;
    }

    public int getId() {
        return this.rv;
    }

    public long getCount() {
        return this.cS;
    }

    public ItemInstance getItem() {
        return this.f;
    }

    public boolean equals(Object object) {
        if (!(object instanceof RequestMultiSellChoose.ItemData)) {
            return false;
        }
        RequestMultiSellChoose.ItemData itemData = (RequestMultiSellChoose.ItemData)object;
        return this.rv == itemData.rv && this.cS == itemData.cS && this.f == itemData.f;
    }
}
