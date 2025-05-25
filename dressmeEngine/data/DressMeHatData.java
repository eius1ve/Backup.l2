/*
 * Decompiled with CFR 0.152.
 */
package dressmeEngine.data;

import dressmeEngine.data.A;

public class DressMeHatData
implements A {
    private final int C;
    private final int B;
    private final String A;
    private final int D;
    private final int G;
    private final long F;
    private final dressmeEngine.util.A E;

    public DressMeHatData(int i, int i2, String str, int i3, int i4, long j, dressmeEngine.util.A a) {
        this.C = i;
        this.B = i2;
        this.A = str;
        this.D = i3;
        this.G = i4;
        this.F = j;
        this.E = a;
    }

    @Override
    public int C() {
        return this.C;
    }

    public int F() {
        return this.B;
    }

    @Override
    public String B() {
        return this.A;
    }

    public int E() {
        return this.D;
    }

    @Override
    public int A() {
        return this.G;
    }

    @Override
    public long D() {
        return this.F;
    }

    public dressmeEngine.util.A G() {
        return this.E;
    }
}
