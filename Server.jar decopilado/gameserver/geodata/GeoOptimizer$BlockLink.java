/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

public static class GeoOptimizer.BlockLink {
    public final int blockIndex;
    public final int linkBlockIndex;
    public final byte linkMapX;
    public final byte linkMapY;

    public GeoOptimizer.BlockLink(short s, byte by, byte by2, short s2) {
        this.blockIndex = s & 0xFFFF;
        this.linkMapX = by;
        this.linkMapY = by2;
        this.linkBlockIndex = s2 & 0xFFFF;
    }

    public GeoOptimizer.BlockLink(int n, byte by, byte by2, int n2) {
        this.blockIndex = n & 0xFFFF;
        this.linkMapX = by;
        this.linkMapY = by2;
        this.linkBlockIndex = n2 & 0xFFFF;
    }
}
