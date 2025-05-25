/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

private static class ExServerPrimitive.Point {
    private final String fp;
    private final int xk;
    private final boolean eQ;
    private final int xl;
    private final int xm;
    private final int xn;

    public ExServerPrimitive.Point(String string, int n, boolean bl, int n2, int n3, int n4) {
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
