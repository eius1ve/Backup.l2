/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExStopMoveAirShip
extends L2GameServerPacket {
    private int vI;
    private Location _loc;

    public ExStopMoveAirShip(Boat boat) {
        this.vI = boat.getObjectId();
        this._loc = boat.getLoc();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(102);
        this.writeD(this.vI);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this._loc.h);
    }
}
