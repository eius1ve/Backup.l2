/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

static class ExShowFortressInfo.FortressInfo {
    public int _id;
    public int _siege;
    public String _owner;
    public boolean _status;

    public ExShowFortressInfo.FortressInfo(String string, int n, boolean bl, int n2) {
        this._owner = string;
        this._id = n;
        this._status = bl;
        this._siege = n2;
    }
}
