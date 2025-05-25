/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.ShortCutPacket;

protected static class ShortCutPacket.ItemShortcutInfo
extends ShortCutPacket.ShortcutInfo {
    private int Br;
    private int Bs;
    private int Bt;
    private int Bu;
    private int Bv;

    public ShortCutPacket.ItemShortcutInfo(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        super(n, n2, n3, n9);
        this.Br = n4;
        this.Bs = n5;
        this.Bt = n6;
        this.Bu = n7;
        this.Bv = n8;
    }

    @Override
    protected void write0(ShortCutPacket shortCutPacket) {
        shortCutPacket.writeD(this._id);
        shortCutPacket.writeD(this._characterType);
        shortCutPacket.writeD(this.Br);
        shortCutPacket.writeD(this.Bs);
        shortCutPacket.writeD(this.Bt);
        shortCutPacket.writeD(this.Bu);
        shortCutPacket.writeD(this.Bv);
        shortCutPacket.writeD(0);
    }
}
