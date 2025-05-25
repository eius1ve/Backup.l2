/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class SpecialCamera
extends L2GameServerPacket {
    private int _id;
    private int vK;
    private int BE;
    private int BF;
    private int _time;
    private int ss;
    private final int BG;
    private final int BH;
    private final int BI;
    private final int BJ;

    public SpecialCamera(int n, int n2, int n3, int n4, int n5, int n6) {
        this._id = n;
        this.vK = n2;
        this.BE = n3;
        this.BF = n4;
        this._time = n5;
        this.ss = n6;
        this.BG = 0;
        this.BH = 0;
        this.BI = 0;
        this.BJ = 0;
    }

    public SpecialCamera(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        this._id = n;
        this.vK = n2;
        this.BE = n3;
        this.BF = n4;
        this._time = n5;
        this.ss = n6;
        this.BG = n7;
        this.BH = n8;
        this.BI = n9;
        this.BJ = n10;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(214);
        this.writeD(this._id);
        this.writeD(this.vK);
        this.writeD(this.BE);
        this.writeD(this.BF);
        this.writeD(this._time);
        this.writeD(this.ss);
        this.writeD(this.BG);
        this.writeD(this.BH);
        this.writeD(this.BI);
        this.writeD(this.BJ);
    }
}
