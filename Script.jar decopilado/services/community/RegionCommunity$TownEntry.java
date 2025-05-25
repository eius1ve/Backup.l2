/*
 * Decompiled with CFR 0.152.
 */
package services.community;

private static class RegionCommunity.TownEntry {
    private final String io;
    private final int bGr;
    private final int bGs;

    private RegionCommunity.TownEntry(String string, int n, int n2) {
        this.io = string;
        this.bGr = n;
        this.bGs = n2;
    }

    public String getTownNameAddr() {
        return this.io;
    }

    public int getX() {
        return this.bGr;
    }

    public int getY() {
        return this.bGs;
    }
}
