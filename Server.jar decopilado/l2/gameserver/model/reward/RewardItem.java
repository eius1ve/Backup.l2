/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.reward;

public class RewardItem {
    public final int itemId;
    public long count;
    public int enchantMin;
    public int enchantMax;
    public boolean isAdena;
    public boolean isSealStone;

    public RewardItem(int n) {
        this.itemId = n;
        this.count = 1L;
    }
}
