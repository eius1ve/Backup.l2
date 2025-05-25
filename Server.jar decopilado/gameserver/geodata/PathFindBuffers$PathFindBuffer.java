/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.util.PriorityQueue;
import java.util.Queue;
import l2.gameserver.geodata.PathFindBuffers;

public static class PathFindBuffers.PathFindBuffer {
    final int mapSize;
    final PathFindBuffers.GeoNode[][] nodes;
    final Queue<PathFindBuffers.GeoNode> open;
    int offsetX;
    int offsetY;
    boolean inUse;
    long totalUses;
    long successUses;
    long overtimeUses;
    long playableUses;
    long totalTime;
    long totalItr;

    public PathFindBuffers.PathFindBuffer(int n) {
        this.open = new PriorityQueue<PathFindBuffers.GeoNode>(n);
        this.mapSize = n;
        this.nodes = new PathFindBuffers.GeoNode[n][n];
        for (int i = 0; i < this.nodes.length; ++i) {
            for (int j = 0; j < this.nodes[i].length; ++j) {
                this.nodes[i][j] = new PathFindBuffers.GeoNode();
            }
        }
    }

    public void free() {
        this.open.clear();
        for (int i = 0; i < this.nodes.length; ++i) {
            for (int j = 0; j < this.nodes[i].length; ++j) {
                this.nodes[i][j].free();
            }
        }
    }
}
