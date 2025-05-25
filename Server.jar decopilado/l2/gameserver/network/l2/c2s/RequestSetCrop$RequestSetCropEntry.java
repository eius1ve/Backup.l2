/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

private static class RequestSetCrop.RequestSetCropEntry {
    public static final RequestSetCrop.RequestSetCropEntry[] EMPTY_ARRAY = new RequestSetCrop.RequestSetCropEntry[0];
    private final int rZ;
    private final long cU;
    private final long cV;
    private final int sa;

    private RequestSetCrop.RequestSetCropEntry(int n, long l, long l2, int n2) {
        this.rZ = n;
        this.cU = l;
        this.cV = l2;
        this.sa = n2;
    }

    public int getId() {
        return this.rZ;
    }

    public long getSales() {
        return this.cU;
    }

    public long getPrice() {
        return this.cV;
    }

    public int getType() {
        return this.sa;
    }
}
