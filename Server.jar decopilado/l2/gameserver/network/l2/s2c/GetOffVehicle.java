/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class GetOffVehicle
extends L2GameServerPacket {
    private int gP;
    private int qX;
    private Location _loc;

    public GetOffVehicle(Player player, Boat boat, Location location) {
        this.gP = player.getObjectId();
        this.qX = boat.getObjectId();
        this._loc = location;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(111);
        this.writeD(this.gP);
        this.writeD(this.qX);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
    }
}
