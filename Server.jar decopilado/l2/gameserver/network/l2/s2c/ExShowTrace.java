/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.GameObject;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExShowTrace
extends L2GameServerPacket {
    private final List<Trace> cE = new ArrayList<Trace>();
    private int _time = Integer.MIN_VALUE;

    public void addTrace(int n, int n2, int n3, int n4) {
        this.cE.add(new Trace(n, n2, n3));
        this._time = Math.max(this._time, n4);
    }

    public void addLine(Location location, Location location2, int n, int n2) {
        this.addLine(location.x, location.y, location.z, location2.x, location2.y, location2.z, n, n2);
    }

    public void addLine(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        int n9 = n4 - n;
        int n10 = n5 - n2;
        int n11 = n6 - n3;
        double d = Math.sqrt(n9 * n9 + n10 * n10);
        double d2 = Math.sqrt(d * d + (double)(n11 * n11));
        int n12 = (int)(d2 / (double)n7);
        this.addTrace(n, n2, n3, n8);
        if (n12 > 1) {
            int n13 = n9 / n12;
            int n14 = n10 / n12;
            int n15 = n11 / n12;
            for (int i = 1; i < n12; ++i) {
                this.addTrace(n + n13 * i, n2 + n14 * i, n3 + n15 * i, n8);
            }
        }
        this.addTrace(n4, n5, n6, n8);
    }

    public void addTrace(GameObject gameObject, int n) {
        this.addTrace(gameObject.getX(), gameObject.getY(), gameObject.getZ(), n);
    }

    public void addCircle(int n, int n2, int n3, int n4, int n5) {
        double d = Math.min(Math.PI * 4 / (double)Math.max(1, n4), Math.PI * 2);
        for (double d2 = 0.0; d2 < Math.PI * 2; d2 += d) {
            this.addTrace((int)Math.round((double)n + Math.cos(d2) * (double)n4), (int)Math.round((double)n2 + Math.sin(d2) * (double)n4), n3, n5);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(104);
        this.writeH(0);
        this.writeD(this._time);
        this.writeH(this.cE.size());
        for (Trace trace : this.cE) {
            this.writeD(trace._x);
            this.writeD(trace._y);
            this.writeD(trace._z);
        }
    }

    static final class Trace {
        public final int _x;
        public final int _y;
        public final int _z;

        public Trace(int n, int n2, int n3) {
            this._x = n;
            this._y = n2;
            this._z = n3;
        }
    }
}
