/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.ExServerPrimitive;

private static class ExServerPrimitive.Line
extends ExServerPrimitive.Point {
    private final int xh;
    private final int xi;
    private final int xj;

    public ExServerPrimitive.Line(String string, int n, boolean bl, int n2, int n3, int n4, int n5, int n6, int n7) {
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
