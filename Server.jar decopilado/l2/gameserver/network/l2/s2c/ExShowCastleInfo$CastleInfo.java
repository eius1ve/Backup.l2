/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

private static class ExShowCastleInfo.CastleInfo {
    public String _ownerName;
    public int _id;
    public int _tax;
    public int _nextSiege;
    public boolean siegeInProgress;

    public ExShowCastleInfo.CastleInfo(String string, int n, int n2, int n3, boolean bl) {
        this._ownerName = string;
        this._id = n;
        this._tax = n2;
        this._nextSiege = n3;
        this.siegeInProgress = bl;
    }
}
