/*
 * Decompiled with CFR 0.152.
 */
package dressmeEngine.data;

import dressmeEngine.data.A;

public class DressMeShieldData
implements A {
    private final int r;
    private final int v;
    private final String p;
    private final int u;
    private final long t;
    private final boolean q;
    private final dressmeEngine.util.A s;

    public DressMeShieldData(int i, int i2, String str, int i3, long j, boolean z, dressmeEngine.util.A a) {
        this.r = i;
        this.v = i2;
        this.p = str;
        this.u = i3;
        this.t = j;
        this.q = z;
        this.s = a;
    }

    @Override
    public int C() {
        return this.r;
    }

    public int W() {
        return this.v;
    }

    @Override
    public String B() {
        return this.p;
    }

    @Override
    public int A() {
        return this.u;
    }

    @Override
    public long D() {
        return this.t;
    }

    public boolean X() {
        return this.q;
    }

    public dressmeEngine.util.A Y() {
        return this.s;
    }
}
