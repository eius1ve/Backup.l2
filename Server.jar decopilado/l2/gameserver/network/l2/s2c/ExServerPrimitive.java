/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExServerPrimitive
extends L2GameServerPacket {
    private final String fo;
    private final int xe;
    private final int xf;
    private final int xg;
    private final List<Point> ct = new ArrayList<Point>();
    private final List<Line> cu = new ArrayList<Line>();

    public ExServerPrimitive(String string, int n, int n2, int n3) {
        this.fo = string;
        this.xe = n;
        this.xf = n2;
        this.xg = n3;
    }

    public ExServerPrimitive(String string, Location location) {
        this(string, location.getX(), location.getY(), location.getZ());
    }

    public void addPoint(String string, int n, boolean bl, int n2, int n3, int n4) {
        this.ct.add(new Point(string, n, bl, n2, n3, n4));
    }

    public void addGeoPoint(String string, int n, boolean bl, int n2, int n3, int n4) {
        this.addPoint(string, n, bl, new Location(n2, n3, (short)((short)(n4 & 0xFFF0) >> 1)).geo2world());
    }

    public void addPoint(String string, int n, boolean bl, Location location) {
        this.addPoint(string, n, bl, location.getX(), location.getY(), location.getZ());
    }

    public void addPoint(int n, int n2, int n3, int n4) {
        this.addPoint("", n, false, n2, n3, n4);
    }

    public void addPoint(int n, Location location) {
        this.addPoint("", n, false, location);
    }

    public void addPoint(String string, Color color, boolean bl, int n, int n2, int n3) {
        this.addPoint(string, color.getRGB(), bl, n, n2, n3);
    }

    public void addPoint(String string, Color color, boolean bl, Location location) {
        this.addPoint(string, color.getRGB(), bl, location);
    }

    public void addPoint(Color color, int n, int n2, int n3) {
        this.addPoint("", color, false, n, n2, n3);
    }

    public void addPoint(Color color, Location location) {
        this.addPoint("", color, false, location);
    }

    public void addLine(String string, int n, boolean bl, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.cu.add(new Line(string, n, bl, n2, n3, n4, n5, n6, n7));
    }

    public void addLine(String string, int n, boolean bl, Location location, int n2, int n3, int n4) {
        this.addLine(string, n, bl, location.getX(), location.getY(), location.getZ(), n2, n3, n4);
    }

    public void addLine(String string, int n, boolean bl, int n2, int n3, int n4, Location location) {
        this.addLine(string, n, bl, n2, n3, n4, location.getX(), location.getY(), location.getZ());
    }

    public void addLine(String string, int n, boolean bl, Location location, Location location2) {
        this.addLine(string, n, bl, location, location2.getX(), location2.getY(), location2.getZ());
    }

    public void addLine(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.addLine("", n, false, n2, n3, n4, n5, n6, n7);
    }

    public void addLine(int n, Location location, int n2, int n3, int n4) {
        this.addLine("", n, false, location, n2, n3, n4);
    }

    public void addLine(int n, int n2, int n3, int n4, Location location) {
        this.addLine("", n, false, n2, n3, n4, location);
    }

    public void addLine(int n, Location location, Location location2) {
        this.addLine("", n, false, location, location2);
    }

    public void addLine(String string, Color color, boolean bl, int n, int n2, int n3, int n4, int n5, int n6) {
        this.addLine(string, color.getRGB(), bl, n, n2, n3, n4, n5, n6);
    }

    public void addLine(String string, Color color, boolean bl, Location location, int n, int n2, int n3) {
        this.addLine(string, color.getRGB(), bl, location, n, n2, n3);
    }

    public void addLine(String string, Color color, boolean bl, int n, int n2, int n3, Location location) {
        this.addLine(string, color.getRGB(), bl, n, n2, n3, location);
    }

    public void addLine(String string, Color color, boolean bl, Location location, Location location2) {
        this.addLine(string, color.getRGB(), bl, location, location2);
    }

    public void addLine(Color color, int n, int n2, int n3, int n4, int n5, int n6) {
        this.addLine("", color, false, n, n2, n3, n4, n5, n6);
    }

    public void addLine(Color color, Location location, int n, int n2, int n3) {
        this.addLine("", color, false, location, n, n2, n3);
    }

    public void addLine(Color color, int n, int n2, int n3, Location location) {
        this.addLine("", color, false, n, n2, n3, location);
    }

    public void addLine(Color color, Location location, Location location2) {
        this.addLine("", color, false, location, location2);
    }

    @Override
    protected void writeImpl() {
        int n;
        this.writeEx(17);
        this.writeS(this.fo);
        this.writeD(this.xe);
        this.writeD(this.xf);
        this.writeD(this.xg);
        this.writeD(65535);
        this.writeD(65535);
        this.writeD(this.ct.size() + this.cu.size());
        for (Point point : this.ct) {
            this.writeC(1);
            this.writeS(point.getName());
            n = point.getColor();
            this.writeD(n >> 16 & 0xFF);
            this.writeD(n >> 8 & 0xFF);
            this.writeD(n & 0xFF);
            this.writeD(point.isNameColored() ? 1 : 0);
            this.writeD(point.getX());
            this.writeD(point.getY());
            this.writeD(point.getZ());
        }
        for (Line line : this.cu) {
            this.writeC(2);
            this.writeS(line.getName());
            n = line.getColor();
            this.writeD(n >> 16 & 0xFF);
            this.writeD(n >> 8 & 0xFF);
            this.writeD(n & 0xFF);
            this.writeD(line.isNameColored() ? 1 : 0);
            this.writeD(line.getX());
            this.writeD(line.getY());
            this.writeD(line.getZ());
            this.writeD(line.getX2());
            this.writeD(line.getY2());
            this.writeD(line.getZ2());
        }
    }

    private static class Point {
        private final String fp;
        private final int xk;
        private final boolean eQ;
        private final int xl;
        private final int xm;
        private final int xn;

        public Point(String string, int n, boolean bl, int n2, int n3, int n4) {
            this.fp = string;
            this.xk = n;
            this.eQ = bl;
            this.xl = n2;
            this.xm = n3;
            this.xn = n4;
        }

        public String getName() {
            return this.fp;
        }

        public int getColor() {
            return this.xk;
        }

        public boolean isNameColored() {
            return this.eQ;
        }

        public int getX() {
            return this.xl;
        }

        public int getY() {
            return this.xm;
        }

        public int getZ() {
            return this.xn;
        }
    }

    private static class Line
    extends Point {
        private final int xh;
        private final int xi;
        private final int xj;

        public Line(String string, int n, boolean bl, int n2, int n3, int n4, int n5, int n6, int n7) {
            super(string, n, bl, n2, n3, n4);
            this.xh = n5;
            this.xi = n6;
            this.xj = n7;
        }

        public int getX2() {
            return this.xh;
        }

        public int getY2() {
            return this.xi;
        }

        public int getZ2() {
            return this.xj;
        }
    }
}
