/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 *  gnu.trove.TIntIntIterator
 *  gnu.trove.TIntObjectHashMap
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.geodata;

import gnu.trove.TIntIntHashMap;
import gnu.trove.TIntIntIterator;
import gnu.trove.TIntObjectHashMap;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.commons.text.StrTable;
import l2.gameserver.Config;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;

public class PathFindBuffers {
    public static final int MIN_MAP_SIZE = 64;
    public static final int STEP_MAP_SIZE = 32;
    public static final int MAX_MAP_SIZE = 512;
    private static TIntObjectHashMap<PathFindBuffer[]> r = new TIntObjectHashMap();
    private static int[] aq = new int[0];
    private static Lock g = new ReentrantLock();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static PathFindBuffer a(int n) {
        g.lock();
        try {
            PathFindBuffer pathFindBuffer;
            PathFindBuffer[] pathFindBufferArray = (PathFindBuffer[])r.get(n);
            if (pathFindBufferArray != null) {
                pathFindBuffer = new PathFindBuffer(n);
                pathFindBufferArray = l2.commons.lang.ArrayUtils.add(pathFindBufferArray, pathFindBuffer);
            } else {
                pathFindBuffer = new PathFindBuffer(n);
                pathFindBufferArray = new PathFindBuffer[]{pathFindBuffer};
                aq = ArrayUtils.add((int[])aq, (int)n);
                Arrays.sort(aq);
            }
            r.put(n, (Object)pathFindBufferArray);
            pathFindBuffer.inUse = true;
            PathFindBuffer pathFindBuffer2 = pathFindBuffer;
            return pathFindBuffer2;
        }
        finally {
            g.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static PathFindBuffer b(int n) {
        g.lock();
        try {
            PathFindBuffer[] pathFindBufferArray;
            for (PathFindBuffer pathFindBuffer : pathFindBufferArray = (PathFindBuffer[])r.get(n)) {
                if (pathFindBuffer.inUse) continue;
                pathFindBuffer.inUse = true;
                PathFindBuffer pathFindBuffer2 = pathFindBuffer;
                return pathFindBuffer2;
            }
            PathFindBuffer[] pathFindBufferArray2 = null;
            return pathFindBufferArray2;
        }
        finally {
            g.unlock();
        }
    }

    public static PathFindBuffer alloc(int n) {
        int n2;
        if (n > 512) {
            return null;
        }
        if ((n += 32) < 64) {
            n = 64;
        }
        PathFindBuffer pathFindBuffer = null;
        for (n2 = 0; n2 < aq.length; ++n2) {
            if (aq[n2] < n) continue;
            n = aq[n2];
            pathFindBuffer = PathFindBuffers.b(n);
            break;
        }
        if (pathFindBuffer == null) {
            for (n2 = 64; n2 < 512; n2 += 32) {
                if (n2 < n) continue;
                n = n2;
                pathFindBuffer = PathFindBuffers.a(n);
                break;
            }
        }
        return pathFindBuffer;
    }

    public static void recycle(PathFindBuffer pathFindBuffer) {
        g.lock();
        try {
            pathFindBuffer.inUse = false;
        }
        finally {
            g.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static StrTable getStats() {
        StrTable strTable = new StrTable("PathFind Buffers Stats");
        g.lock();
        try {
            long l = 0L;
            long l2 = 0L;
            long l3 = 0L;
            int n = 0;
            for (int n2 : aq) {
                ++n;
                int n3 = 0;
                long l4 = 0L;
                long l5 = 0L;
                long l6 = 0L;
                long l7 = 0L;
                long l8 = 0L;
                long l9 = 0L;
                for (PathFindBuffer pathFindBuffer : (PathFindBuffer[])r.get(n2)) {
                    ++n3;
                    l4 += pathFindBuffer.totalUses;
                    l5 += pathFindBuffer.playableUses;
                    l7 += pathFindBuffer.successUses;
                    l8 += pathFindBuffer.overtimeUses;
                    l9 += pathFindBuffer.totalTime / 1000000L;
                    l6 += pathFindBuffer.totalItr;
                }
                l += l4;
                l2 += l5;
                l3 += l9;
                strTable.set(n, "Size", n2);
                strTable.set(n, "Count", n3);
                strTable.set(n, "Uses (success%)", l4 + "(" + String.format("%2.2f", l4 > 0L ? (double)l7 * 100.0 / (double)l4 : 0.0) + "%)");
                strTable.set(n, "Uses, playble", l5 + "(" + String.format("%2.2f", l4 > 0L ? (double)l5 * 100.0 / (double)l4 : 0.0) + "%)");
                strTable.set(n, "Uses, overtime", l8 + "(" + String.format("%2.2f", l4 > 0L ? (double)l8 * 100.0 / (double)l4 : 0.0) + "%)");
                strTable.set(n, "Iter., avg", l4 > 0L ? l6 / l4 : 0L);
                strTable.set(n, "Time, avg (ms)", String.format("%1.3f", l4 > 0L ? (double)l9 / (double)l4 : 0.0));
            }
            strTable.addTitle("Uses, total / playable  : " + l + " / " + l2);
            strTable.addTitle("Uses, total time / avg (ms) : " + l3 + " / " + String.format("%1.3f", l > 0L ? (double)l3 / (double)l : 0.0));
        }
        finally {
            g.unlock();
        }
        return strTable;
    }

    static {
        TIntIntHashMap tIntIntHashMap = new TIntIntHashMap();
        for (PathFindBuffer[] pathFindBufferArray : Config.PATHFIND_BUFFERS.split(";")) {
            String[] stringArray;
            if (pathFindBufferArray.isEmpty() || (stringArray = pathFindBufferArray.split("x")).length != 2) continue;
            tIntIntHashMap.put(Integer.valueOf(stringArray[1]).intValue(), Integer.valueOf(stringArray[0]).intValue());
        }
        TIntIntIterator tIntIntIterator = tIntIntHashMap.iterator();
        while (tIntIntIterator.hasNext()) {
            PathFindBuffer[] pathFindBufferArray;
            tIntIntIterator.advance();
            int n = tIntIntIterator.key();
            int n2 = tIntIntIterator.value();
            pathFindBufferArray = new PathFindBuffer[n2];
            for (int i = 0; i < n2; ++i) {
                pathFindBufferArray[i] = new PathFindBuffer(n);
            }
            r.put(n, (Object)pathFindBufferArray);
        }
        aq = tIntIntHashMap.keys();
        Arrays.sort(aq);
    }

    public static class PathFindBuffer {
        final int mapSize;
        final GeoNode[][] nodes;
        final Queue<GeoNode> open;
        int offsetX;
        int offsetY;
        boolean inUse;
        long totalUses;
        long successUses;
        long overtimeUses;
        long playableUses;
        long totalTime;
        long totalItr;

        public PathFindBuffer(int n) {
            this.open = new PriorityQueue<GeoNode>(n);
            this.mapSize = n;
            this.nodes = new GeoNode[n][n];
            for (int i = 0; i < this.nodes.length; ++i) {
                for (int j = 0; j < this.nodes[i].length; ++j) {
                    this.nodes[i][j] = new GeoNode();
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

    public static class GeoNode
    implements Comparable<GeoNode> {
        public static final int NONE = 0;
        public static final int OPENED = 1;
        public static final int CLOSED = -1;
        public int x;
        public int y;
        public short z;
        public short nswe = (short)-1;
        public float totalCost;
        public float costFromStart;
        public float costToEnd;
        public int state;
        public GeoNode parent;

        public GeoNode set(int n, int n2, short s) {
            this.x = n;
            this.y = n2;
            this.z = s;
            return this;
        }

        public boolean isSet() {
            return this.nswe != -1;
        }

        public void free() {
            this.nswe = (short)-1;
            this.costFromStart = 0.0f;
            this.totalCost = 0.0f;
            this.costToEnd = 0.0f;
            this.parent = null;
            this.state = 0;
        }

        public Location getLoc() {
            return new Location(this.x, this.y, this.z);
        }

        public String toString() {
            return "[" + this.x + "," + this.y + "," + this.z + "] f: " + this.totalCost;
        }

        @Override
        public int compareTo(GeoNode geoNode) {
            if (this.totalCost > geoNode.totalCost) {
                return 1;
            }
            if (this.totalCost < geoNode.totalCost) {
                return -1;
            }
            return 0;
        }
    }
}
