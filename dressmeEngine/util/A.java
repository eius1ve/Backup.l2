/*
 * Decompiled with CFR 0.152.
 */
package dressmeEngine.util;

public enum A {
    NONE("NONE"),
    COMMON("COMMON"),
    LESSCOMMON("LESSCOMMON"),
    MIDGRADE("MIDGRADE"),
    HIGHGRADE("HIGHGRADE"),
    EPIC("EPIC"),
    LEGENDARY("LEGENDARY");

    private String A;

    private A(String str) {
        this.A = str;
    }

    public String A() {
        return this.A;
    }
}
