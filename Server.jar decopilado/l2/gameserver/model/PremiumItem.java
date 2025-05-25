/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public class PremiumItem {
    private int _itemId;
    private long aT;
    private String cS;

    public PremiumItem(int n, long l, String string) {
        this._itemId = n;
        this.aT = l;
        this.cS = string;
    }

    public void updateCount(long l) {
        this.aT = l;
    }

    public int getItemId() {
        return this._itemId;
    }

    public long getCount() {
        return this.aT;
    }

    public String getSender() {
        return this.cS;
    }
}
