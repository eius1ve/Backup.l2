/*
 * Decompiled with CFR 0.152.
 */
package dressmeEngine.data;

import dressmeEngine.data.A;

public class DressMeAgathionData
implements A {
    private final int U;
    private final int T;
    private final String S;
    private final int W;
    private final long V;
    private int VISUAL;

    public DressMeAgathionData(int i, int i2, int visual, String str, int i3, long j) {
        this.U = i;
        this.T = i2;
        this.VISUAL = visual;
        this.S = str;
        this.W = i3;
        this.V = j;
    }

    @Override
    public int C() {
        return this.U;
    }

    public int O() {
        return this.T;
    }

    public int visual() {
        return this.VISUAL;
    }

    @Override
    public String B() {
        return this.S;
    }

    @Override
    public int A() {
        return this.W;
    }

    @Override
    public long D() {
        return this.V;
    }
}
