/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class Earthquake
extends L2GameServerPacket {
    private Location _loc;
    private int tR;
    private int ss;

    public Earthquake(Location location, int n, int n2) {
        this._loc = location;
        this.tR = n;
        this.ss = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(211);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this.tR);
        this.writeD(this.ss);
        this.writeD(0);
    }
}
