/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.ShortCutPacket;

protected static class ShortCutPacket.SkillShortcutInfo
extends ShortCutPacket.ShortcutInfo {
    private final int Bw;
    private final int Bx;

    public ShortCutPacket.SkillShortcutInfo(int n, int n2, int n3, int n4, int n5, int n6) {
        super(n, n2, n3, n5);
        this.Bw = n4;
        this.Bx = n6;
    }

    @Override
    protected void write0(ShortCutPacket shortCutPacket) {
        shortCutPacket.writeD(this._id);
        shortCutPacket.writeD(this.Bw);
        shortCutPacket.writeD(this.Bx);
        shortCutPacket.writeC(0);
        shortCutPacket.writeD(this._characterType);
    }
}
