/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.geodata.PathFindBuffers;
import l2.gameserver.utils.Location;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PathFind {
    private int fB = 0;
    private PathFindBuffers.PathFindBuffer a;
    private List<Location> ao;
    private final short[] a;
    private final Location m;
    private final Location n;
    private PathFindBuffers.GeoNode a = new short[2];
    private PathFindBuffers.GeoNode b;
    private PathFindBuffers.GeoNode c;

    public PathFind(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7) {
        this.fB = n7;
        this.m = Config.PATHFIND_BOOST == 0 ? new Location(n, n2, n3) : GeoEngine.moveCheckWithCollision(n, n2, n3, n4, n5, true, n7);
        this.n = Config.PATHFIND_BOOST != 2 || Math.abs(n6 - n3) > 200 ? new Location(n4, n5, n6) : GeoEngine.moveCheckBackwardWithCollision(n4, n5, n6, this.m.x, this.m.y, true, n7);
        this.m.world2geo();
        this.n.world2geo();
        this.m.z = GeoEngine.NgetHeight(this.m.x, this.m.y, this.m.z, n7);
        this.n.z = GeoEngine.NgetHeight(this.n.x, this.n.y, this.n.z, n7);
        int n8 = Math.abs(this.n.x - this.m.x);
        int n9 = Math.abs(this.n.y - this.m.y);
        if (n8 == 0 && n9 == 0) {
            if (Math.abs(this.n.z - this.m.z) < 32) {
                this.ao = new ArrayList<Location>();
                this.ao.add(0, this.m);
            }
            return;
        }
        int n10 = 2 * Math.max(n8, n9);
        this.a = PathFindBuffers.alloc(n10);
        if (this.a != null) {
            this.a.offsetX = this.m.x - this.a.mapSize / 2;
            this.a.offsetY = this.m.y - this.a.mapSize / 2;
            ++this.a.totalUses;
            if (bl) {
                ++this.a.playableUses;
            }
            this.k();
            this.a.free();
            PathFindBuffers.recycle(this.a);
        }
    }

    private List<Location> k() {
        this.a = this.a.nodes[this.m.x - this.a.offsetX][this.m.y - this.a.offsetY].set(this.m.x, this.m.y, (short)this.m.z);
        GeoEngine.NgetHeightAndNSWE(this.m.x, this.m.y, (short)this.m.z, this.a, this.fB);
        this.a.z = this.a[0];
        this.a.nswe = this.a[1];
        this.a.costFromStart = 0.0f;
        this.a.state = 1;
        this.a.parent = null;
        this.b = this.a.nodes[this.n.x - this.a.offsetX][this.n.y - this.a.offsetY].set(this.n.x, this.n.y, (short)this.n.z);
        this.a.costToEnd = this.a(this.a);
        this.a.totalCost = this.a.costFromStart + this.a.costToEnd;
        this.a.open.add(this.a);
        long l = System.nanoTime();
        long l2 = 0L;
        int n = 0;
        while ((l2 = System.nanoTime() - l) < Config.PATHFIND_MAX_TIME && (this.c = this.a.open.poll()) != null) {
            ++n;
            if (this.c.x == this.n.x && this.c.y == this.n.y && Math.abs(this.c.z - this.n.z) < 64) {
                this.ao = this.a(this.c);
                break;
            }
            this.a(this.c);
            this.c.state = -1;
        }
        this.a.totalTime += l2;
        this.a.totalItr += (long)n;
        if (this.ao != null) {
            ++this.a.successUses;
        } else if (l2 > Config.PATHFIND_MAX_TIME) {
            ++this.a.overtimeUses;
        }
        return this.ao;
    }

    private List<Location> a(PathFindBuffers.GeoNode geoNode) {
        ArrayList<Location> arrayList = new ArrayList<Location>();
        do {
            arrayList.add(0, geoNode.getLoc());
            geoNode = geoNode.parent;
        } while (geoNode.parent != null);
        return arrayList;
    }

    private void a(PathFindBuffers.GeoNode geoNode) {
        int n = geoNode.x;
        int n2 = geoNode.y;
        short s = geoNode.z;
        this.a(n, n2, s);
        short s2 = this.a[1];
        if (Config.PATHFIND_DIAGONAL) {
            if ((s2 & 4) == 4 && (s2 & 1) == 1) {
                this.a(n + 1, n2, s);
                if ((this.a[1] & 4) == 4) {
                    this.a(n, n2 + 1, s);
                    if ((this.a[1] & 1) == 1) {
                        this.a(n + 1, n2 + 1, geoNode, true);
                    }
                }
            }
            if ((s2 & 4) == 4 && (s2 & 2) == 2) {
                this.a(n - 1, n2, s);
                if ((this.a[1] & 4) == 4) {
                    this.a(n, n2 + 1, s);
                    if ((this.a[1] & 2) == 2) {
                        this.a(n - 1, n2 + 1, geoNode, true);
                    }
                }
            }
            if ((s2 & 8) == 8 && (s2 & 1) == 1) {
                this.a(n + 1, n2, s);
                if ((this.a[1] & 8) == 8) {
                    this.a(n, n2 - 1, s);
                    if ((this.a[1] & 1) == 1) {
                        this.a(n + 1, n2 - 1, geoNode, true);
                    }
                }
            }
            if ((s2 & 8) == 8 && (s2 & 2) == 2) {
                this.a(n - 1, n2, s);
                if ((this.a[1] & 8) == 8) {
                    this.a(n, n2 - 1, s);
                    if ((this.a[1] & 2) == 2) {
                        this.a(n - 1, n2 - 1, geoNode, true);
                    }
                }
            }
        }
        if ((s2 & 1) == 1) {
            this.a(n + 1, n2, geoNode, false);
        }
        if ((s2 & 2) == 2) {
            this.a(n - 1, n2, geoNode, false);
        }
        if ((s2 & 4) == 4) {
            this.a(n, n2 + 1, geoNode, false);
        }
        if ((s2 & 8) == 8) {
            this.a(n, n2 - 1, geoNode, false);
        }
    }

    private float a(PathFindBuffers.GeoNode geoNode) {
        int n = this.b.x - geoNode.x;
        int n2 = this.b.y - geoNode.y;
        int n3 = this.b.z - geoNode.z;
        return (float)Math.sqrt(n * n + n2 * n2 + n3 * n3 / 256);
    }

    private float a(PathFindBuffers.GeoNode geoNode, PathFindBuffers.GeoNode geoNode2, boolean bl) {
        if (geoNode2.nswe != 15 || Math.abs(geoNode2.z - geoNode.z) > 16) {
            return 3.0f;
        }
        this.a(geoNode2.x + 1, geoNode2.y, geoNode2.z);
        if (this.a[1] != 15 || Math.abs(geoNode2.z - this.a[0]) > 16) {
            return 2.0f;
        }
        this.a(geoNode2.x - 1, geoNode2.y, geoNode2.z);
        if (this.a[1] != 15 || Math.abs(geoNode2.z - this.a[0]) > 16) {
            return 2.0f;
        }
        this.a(geoNode2.x, geoNode2.y + 1, geoNode2.z);
        if (this.a[1] != 15 || Math.abs(geoNode2.z - this.a[0]) > 16) {
            return 2.0f;
        }
        this.a(geoNode2.x, geoNode2.y - 1, geoNode2.z);
        if (this.a[1] != 15 || Math.abs(geoNode2.z - this.a[0]) > 16) {
            return 2.0f;
        }
        return bl ? 1.414f : 1.0f;
    }

    private void a(int n, int n2, PathFindBuffers.GeoNode geoNode, boolean bl) {
        int n3;
        int n4 = n - this.a.offsetX;
        int n5 = n2 - this.a.offsetY;
        if (n4 >= this.a.mapSize || n4 < 0 || n5 >= this.a.mapSize || n5 < 0) {
            return;
        }
        PathFindBuffers.GeoNode geoNode2 = this.a.nodes[n4][n5];
        if (!geoNode2.isSet()) {
            geoNode2 = geoNode2.set(n, n2, geoNode.z);
            GeoEngine.NgetHeightAndNSWE(n, n2, geoNode.z, this.a, this.fB);
            geoNode2.z = this.a[0];
            geoNode2.nswe = this.a[1];
        }
        if ((n3 = Math.abs(geoNode2.z - geoNode.z)) > Config.PATHFIND_MAX_Z_DIFF || geoNode2.nswe == 0) {
            return;
        }
        float f = geoNode.costFromStart + this.a(geoNode, geoNode2, bl);
        if ((geoNode2.state == 1 || geoNode2.state == -1) && geoNode2.costFromStart <= f) {
            return;
        }
        if (geoNode2.state == 0) {
            geoNode2.costToEnd = this.a(geoNode2);
        }
        geoNode2.parent = geoNode;
        geoNode2.costFromStart = f;
        geoNode2.totalCost = geoNode2.costFromStart + geoNode2.costToEnd;
        if (geoNode2.state == 1) {
            return;
        }
        geoNode2.state = 1;
        this.a.open.add(geoNode2);
    }

    private void a(int n, int n2, short s) {
        int n3 = n - this.a.offsetX;
        int n4 = n2 - this.a.offsetY;
        if (n3 >= this.a.mapSize || n3 < 0 || n4 >= this.a.mapSize || n4 < 0) {
            this.a[1] = 0;
            return;
        }
        PathFindBuffers.GeoNode geoNode = this.a.nodes[n3][n4];
        if (!geoNode.isSet()) {
            geoNode = geoNode.set(n, n2, s);
            GeoEngine.NgetHeightAndNSWE(n, n2, s, this.a, this.fB);
            geoNode.z = this.a[0];
            geoNode.nswe = this.a[1];
        } else {
            this.a[0] = geoNode.z;
            this.a[1] = geoNode.nswe;
        }
    }

    public List<Location> getPath() {
        return this.ao;
    }
}
