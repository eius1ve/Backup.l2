/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import l2.gameserver.utils.Location;

public static class PathFindBuffers.GeoNode
implements Comparable<PathFindBuffers.GeoNode> {
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
    public PathFindBuffers.GeoNode parent;

    public PathFindBuffers.GeoNode set(int n, int n2, short s) {
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
    public int compareTo(PathFindBuffers.GeoNode geoNode) {
        if (this.totalCost > geoNode.totalCost) {
            return 1;
        }
        if (this.totalCost < geoNode.totalCost) {
            return -1;
        }
        return 0;
    }
}
