/*
 * Decompiled with CFR 0.152.
 */
package dressmeEngine.data;

import dressmeEngine.data.A;

public class DressMeCloakData
implements A {
    private final int Y;
    private final int Z;
    private final String X;
    private final int b;
    private final long a;
    private final dressmeEngine.util.A _ab;

    public DressMeCloakData(int i, int i2, String str, int i3, long j, dressmeEngine.util.A a) {
        this.Y = i;
        this.Z = i2;
        this.X = str;
        this.b = i3;
        this.a = j;
        this._ab = a;
    }

    @Override
    public int C() {
        return this.Y;
    }

    public int P() {
        return this.Z;
    }

    @Override
    public String B() {
        return this.X;
    }

    @Override
    public int A() {
        return this.b;
    }

    @Override
    public long D() {
        return this.a;
    }

    public dressmeEngine.util.A Q() {
        return this._ab;
    }
}
