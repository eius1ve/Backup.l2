/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExBR_BroadcastEventState
extends L2GameServerPacket {
    private final int ux;
    private final int uy;
    private int uz;
    private int qV;
    private int qW;
    private int uA;
    private int uB;
    private String eO;
    private String eP;
    public static final int APRIL_FOOLS = 20090401;
    public static final int EVAS_INFERNO = 20090801;
    public static final int HALLOWEEN_EVENT = 20091031;
    public static final int RAISING_RUDOLPH = 20091225;
    public static final int LOVERS_JUBILEE = 20100214;
    public static final int APRIL_FOOLS_10 = 20100401;

    public ExBR_BroadcastEventState(int n, int n2) {
        this.ux = n;
        this.uy = n2;
    }

    public ExBR_BroadcastEventState(int n, int n2, int n3, int n4, int n5, int n6, int n7, String string, String string2) {
        this.ux = n;
        this.uy = n2;
        this.uz = n3;
        this.qV = n4;
        this.qW = n5;
        this.uA = n6;
        this.uB = n7;
        this.eO = string;
        this.eP = string2;
    }

    @Override
    protected void writeImpl() {
        this.writeEx(189);
        this.writeD(this.ux);
        this.writeD(this.uy);
        this.writeD(this.uz);
        this.writeD(this.qV);
        this.writeD(this.qW);
        this.writeD(this.uA);
        this.writeD(this.uB);
        this.writeS(this.eO);
        this.writeS(this.eP);
    }
}
