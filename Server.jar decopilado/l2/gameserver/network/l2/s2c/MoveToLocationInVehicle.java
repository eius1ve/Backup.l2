/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class MoveToLocationInVehicle
extends L2GameServerPacket {
    private int gP;
    private int qX;
    private Location T;
    private Location _destination;

    public MoveToLocationInVehicle(Player player, Boat boat, Location location, Location location2) {
        this.gP = player.getObjectId();
        this.qX = boat.getObjectId();
        this.T = location;
        this._destination = location2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(126);
        this.writeD(this.gP);
        this.writeD(this.qX);
        this.writeD(this._destination.x);
        this.writeD(this._destination.y);
        this.writeD(this._destination.z);
        this.writeD(this.T.x);
        this.writeD(this.T.y);
        this.writeD(this.T.z);
    }
}
