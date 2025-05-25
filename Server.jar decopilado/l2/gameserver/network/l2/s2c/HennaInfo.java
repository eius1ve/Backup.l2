/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class HennaInfo
extends L2GameServerPacket {
    private final Henna[] a = new Henna[3];
    private final int ze;
    private final int zf;
    private final int zg;
    private final int zh;
    private final int zi;
    private final int zj;
    private int gT = 0;
    private int zk;

    public HennaInfo(Player player) {
        for (int i = 0; i < 3; ++i) {
            l2.gameserver.templates.Henna henna = player.getHenna(i + 1);
            if (henna == null) continue;
            this.a[this.gT++] = new Henna(henna.getSymbolId(), henna.isForThisClass(player));
        }
        this.ze = player.getHennaStatSTR();
        this.zf = player.getHennaStatCON();
        this.zg = player.getHennaStatDEX();
        this.zh = player.getHennaStatINT();
        this.zi = player.getHennaStatWIT();
        this.zj = player.getHennaStatMEN();
        this.zk = player.getLevel() < 40 ? 2 : 3;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(229);
        this.writeH(this.zh);
        this.writeH(this.ze);
        this.writeH(this.zf);
        this.writeH(this.zj);
        this.writeH(this.zg);
        this.writeH(this.zi);
        this.writeH(0);
        this.writeH(0);
        this.writeD(this.zk);
        this.writeD(this.gT);
        for (int i = 0; i < this.gT; ++i) {
            this.writeD(this.a[i]._symbolId);
            this.writeD(this.a[i].eX ? this.a[i]._symbolId : 0);
        }
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
    }

    private static class Henna {
        private int _symbolId;
        private boolean eX;

        public Henna(int n, boolean bl) {
            this._symbolId = n;
            this.eX = bl;
        }
    }
}
