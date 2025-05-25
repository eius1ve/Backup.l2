/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.Henna;

public class HennaItemInfo
extends L2GameServerPacket {
    private int yo;
    private int yp;
    private int yq;
    private int yr;
    private int ys;
    private int yt;
    private long dg;
    private Henna a;

    public HennaItemInfo(Henna henna, Player player) {
        this.a = henna;
        this.dg = player.getAdena();
        this.yo = player.getSTR();
        this.yq = player.getDEX();
        this.yp = player.getCON();
        this.yr = player.getINT();
        this.ys = player.getWIT();
        this.yt = player.getMEN();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(228);
        this.writeD(this.a.getSymbolId());
        this.writeD(this.a.getDyeId());
        this.writeQ(this.a.getDrawCount());
        this.writeQ(this.a.getPrice());
        this.writeD(1);
        this.writeQ(this.dg);
        this.writeD(this.yr);
        this.writeH(this.yr + this.a.getStatINT());
        this.writeD(this.yo);
        this.writeH(this.yo + this.a.getStatSTR());
        this.writeD(this.yp);
        this.writeH(this.yp + this.a.getStatCON());
        this.writeD(this.yt);
        this.writeH(this.yt + this.a.getStatMEN());
        this.writeD(this.yq);
        this.writeH(this.yq + this.a.getStatDEX());
        this.writeD(this.ys);
        this.writeH(this.ys + this.a.getStatWIT());
    }
}
