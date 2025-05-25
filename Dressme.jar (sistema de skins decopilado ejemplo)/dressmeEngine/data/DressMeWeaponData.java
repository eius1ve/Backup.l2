/*
 * Decompiled with CFR 0.152.
 */
package dressmeEngine.data;

import dressmeEngine.data.A;

public class DressMeWeaponData
implements A {
    private final int k;
    private final String i;
    private final String j;
    private final boolean h;
    private final boolean l;
    private final int o;
    private final long n;
    private final dressmeEngine.util.A m;

    public DressMeWeaponData(int i, String str, String str2, boolean z, boolean z2, int i2, long j, dressmeEngine.util.A a) {
        this.k = i;
        this.i = str;
        this.j = str2;
        this.h = z;
        this.l = z2;
        this.o = i2;
        this.n = j;
        this.m = a;
    }

    @Override
    public int C() {
        return this.k;
    }

    @Override
    public String B() {
        return this.i;
    }

    public String S() {
        return this.j;
    }

    public boolean V() {
        return this.h;
    }

    @Override
    public int A() {
        return this.o;
    }

    @Override
    public long D() {
        return this.n;
    }

    public boolean T() {
        return this.l;
    }

    public dressmeEngine.util.A U() {
        return this.m;
    }
}
