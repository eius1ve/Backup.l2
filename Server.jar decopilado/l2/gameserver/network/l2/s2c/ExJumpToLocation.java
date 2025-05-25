/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExJumpToLocation
extends L2GameServerPacket {
    private int fW;
    private Location R;
    private Location _destination;

    public ExJumpToLocation(int n, Location location, Location location2) {
        this.fW = n;
        this.R = location;
        this._destination = location2;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(136);
        this.writeD(this.fW);
        this.writeD(this._destination.x);
        this.writeD(this._destination.y);
        this.writeD(this._destination.z);
        this.writeD(this.R.x);
        this.writeD(this.R.y);
        this.writeD(this.R.z);
    }
}
