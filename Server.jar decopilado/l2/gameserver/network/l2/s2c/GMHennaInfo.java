/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.Henna;

public class GMHennaInfo
extends L2GameServerPacket {
    private int gT;
    private int yo;
    private int yp;
    private int yq;
    private int yr;
    private int ys;
    private int yt;
    private final Henna[] b = new Henna[3];

    public GMHennaInfo(Player player) {
        this.yo = player.getHennaStatSTR();
        this.yp = player.getHennaStatCON();
        this.yq = player.getHennaStatDEX();
        this.yr = player.getHennaStatINT();
        this.ys = player.getHennaStatWIT();
        this.yt = player.getHennaStatMEN();
        int n = 0;
        for (int i = 0; i < 3; ++i) {
            Henna henna = player.getHenna(i + 1);
            if (henna == null) continue;
            this.b[n++] = henna;
        }
        this.gT = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(240);
        this.writeH(this.yr);
        this.writeH(this.yo);
        this.writeH(this.yp);
        this.writeH(this.yt);
        this.writeH(this.yq);
        this.writeH(this.ys);
        this.writeH(0);
        this.writeH(0);
        this.writeD(3);
        this.writeD(this.gT);
        for (int i = 0; i < this.gT; ++i) {
            this.writeD(this.b[i].getSymbolId());
            this.writeD(this.b[i].getSymbolId());
        }
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
    }
}
