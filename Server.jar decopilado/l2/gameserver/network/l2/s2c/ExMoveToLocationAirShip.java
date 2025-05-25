/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExMoveToLocationAirShip
extends L2GameServerPacket {
    private int fW;
    private Location T;
    private Location _destination;

    public ExMoveToLocationAirShip(Boat boat) {
        this.fW = boat.getObjectId();
        this.T = boat.getLoc();
        this._destination = boat.getDestination();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(101);
        this.writeD(this.fW);
        this.writeD(this._destination.x);
        this.writeD(this._destination.y);
        this.writeD(this._destination.z);
        this.writeD(this.T.x);
        this.writeD(this.T.y);
        this.writeD(this.T.z);
    }
}
