/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ObserverEnd
extends L2GameServerPacket {
    private Location _loc;

    public ObserverEnd(Location location) {
        this._loc = location;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(236);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
    }
}
