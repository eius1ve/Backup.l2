/*
 * Decompiled with CFR 0.152.
 */
package dressmeEngine.data;

import dressmeEngine.data.A;

public class DressMeEnchantData
implements A {
    private final int d;
    private final int e;
    private final String c;
    private final int g;
    private final long f;
    private final int l;
    private final int bp;

    public DressMeEnchantData(int i, int i2, int lv, int bp, String str, int i3, long j) {
        this.d = i;
        this.e = i2;
        this.c = str;
        this.g = i3;
        this.f = j;
        this.l = lv;
        this.bp = bp;
    }

    @Override
    public int C() {
        return this.d;
    }

    public int R() {
        return this.e;
    }

    @Override
    public String B() {
        return this.c;
    }

    @Override
    public int A() {
        return this.g;
    }

    @Override
    public long D() {
        return this.f;
    }

    public int LEVEL() {
        return this.l;
    }

    public int BP() {
        return this.bp;
    }
}
