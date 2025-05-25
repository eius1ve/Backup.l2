/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public class VariationGroupData {
    private final long dX;
    private final int GT;
    private final int GU;
    private final long dY;

    public VariationGroupData(int n, int n2, long l, long l2) {
        this.GT = n;
        this.GU = n2;
        this.dY = l;
        this.dX = l2;
    }

    public long getCancelPrice() {
        return this.dX;
    }

    public int getMineralItemId() {
        return this.GT;
    }

    public int getGemstoneItemId() {
        return this.GU;
    }

    public long getGemstoneItemCnt() {
        return this.dY;
    }
}
