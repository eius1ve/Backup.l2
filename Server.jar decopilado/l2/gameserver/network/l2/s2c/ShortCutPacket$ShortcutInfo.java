/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.ShortCutPacket;

protected static class ShortCutPacket.ShortcutInfo {
    protected final int _type;
    protected final int _page;
    protected final int _id;
    protected final int _characterType;

    public ShortCutPacket.ShortcutInfo(int n, int n2, int n3, int n4) {
        this._type = n;
        this._page = n2;
        this._id = n3;
        this._characterType = n4;
    }

    protected void write(ShortCutPacket shortCutPacket) {
        shortCutPacket.writeD(this._type);
        shortCutPacket.writeD(this._page);
        this.write0(shortCutPacket);
    }

    protected void write0(ShortCutPacket shortCutPacket) {
        shortCutPacket.writeD(this._id);
        shortCutPacket.writeD(this._characterType);
    }
}
