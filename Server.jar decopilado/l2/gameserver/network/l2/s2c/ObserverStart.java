/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ObserverStart
extends L2GameServerPacket {
    private Location _loc;

    public ObserverStart(Location location) {
        this._loc = location;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(235);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeC(0);
        this.writeC(192);
        this.writeC(0);
    }
}
