/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExAirShipInfo
extends L2GameServerPacket {
    private int sR;
    private int lv;
    private int lw;
    private int lx;
    private int ur;
    private int us;
    private int ut;
    private Location _loc;

    @Override
    protected final void writeImpl() {
        this.writeEx(96);
        this.writeD(this.sR);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this._loc.h);
        this.writeD(this.us);
        this.writeD(this.lv);
        this.writeD(this.lw);
        this.writeD(this.ut);
        if (this.ut != 0) {
            this.writeD(366);
            this.writeD(0);
            this.writeD(107);
            this.writeD(348);
            this.writeD(0);
            this.writeD(105);
        } else {
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
        }
        this.writeD(this.lx);
        this.writeD(this.ur);
    }
}
