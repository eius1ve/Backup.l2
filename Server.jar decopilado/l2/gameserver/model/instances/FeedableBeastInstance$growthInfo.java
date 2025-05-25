/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

private static class FeedableBeastInstance.growthInfo {
    public int growth_level;
    public int growth_chance;
    public int[][] spice;

    public FeedableBeastInstance.growthInfo(int n, int[][] nArray, int n2) {
        this.growth_level = n;
        this.spice = nArray;
        this.growth_chance = n2;
    }
}
