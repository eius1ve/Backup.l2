/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

private static class SetPrivateStoreBuyList.SetPrivateStoreBuyListEntry {
    public static final int IN_PACKET_SIZE = 54;
    private final int si;
    private final int sj;
    private final int sk;
    private final long cY;
    private final long cZ;

    private SetPrivateStoreBuyList.SetPrivateStoreBuyListEntry(int n, int n2, int n3, long l, long l2) {
        this.si = n;
        this.sj = n2;
        this.sk = n3;
        this.cY = l;
        this.cZ = l2;
    }

    public int getItemId() {
        return this.si;
    }

    public int getEnchant() {
        return this.sj;
    }

    public int getDamage() {
        return this.sk;
    }

    public long getCount() {
        return this.cY;
    }

    public long getPrice() {
        return this.cZ;
    }
}
