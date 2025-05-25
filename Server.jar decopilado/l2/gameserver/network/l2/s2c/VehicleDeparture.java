/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class VehicleDeparture
extends L2GameServerPacket {
    private int lp;
    private int lq;
    private int CJ;
    private Location _loc;

    public VehicleDeparture(Boat boat) {
        this.CJ = boat.getObjectId();
        this.lp = boat.getMoveSpeed();
        this.lq = boat.getRotationSpeed();
        this._loc = boat.getDestination();
        if (this._loc == null) {
            this._loc = boat.getReturnLoc();
        }
    }

    public VehicleDeparture(Boat boat, Location location) {
        this.CJ = boat.getObjectId();
        this.lp = boat.getMoveSpeed();
        this.lq = boat.getRotationSpeed();
        this._loc = location;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(108);
        this.writeD(this.CJ);
        this.writeD(this.lp);
        this.writeD(this.lq);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
    }
}
