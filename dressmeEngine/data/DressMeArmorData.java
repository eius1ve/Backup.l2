/*
 * Decompiled with CFR 0.152.
 */
package dressmeEngine.data;

import dressmeEngine.data.A;

public class DressMeArmorData
implements A {
    private final int N;
    private final String P;
    private final String J;
    private final int I;
    private final int M;
    private final int H;
    private final int R;
    private final int O;
    private final long L;
    private final boolean Q;
    private final dressmeEngine.util.A K;

    public DressMeArmorData(int i, String str, String str2, int i2, int i3, int i4, int i5, int i6, long j, boolean z, dressmeEngine.util.A a) {
        this.N = i;
        this.P = str;
        this.J = str2;
        this.I = i2;
        this.M = i3;
        this.H = i4;
        this.R = i5;
        this.O = i6;
        this.L = j;
        this.Q = z;
        this.K = a;
    }

    @Override
    public int C() {
        return this.N;
    }

    @Override
    public String B() {
        return this.P;
    }

    public String I() {
        return this.J;
    }

    public int H() {
        return this.I;
    }

    public int L() {
        return this.M;
    }

    public int K() {
        return this.H;
    }

    public int J() {
        return this.R;
    }

    @Override
    public int A() {
        return this.O;
    }

    @Override
    public long D() {
        return this.L;
    }

    public boolean M() {
        return this.Q;
    }

    public dressmeEngine.util.A N() {
        return this.K;
    }
}
