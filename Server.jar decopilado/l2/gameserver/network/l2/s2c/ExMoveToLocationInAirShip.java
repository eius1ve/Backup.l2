/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExMoveToLocationInAirShip
extends L2GameServerPacket {
    private int char_id;
    private int vI;
    private Location T;
    private Location _destination;

    public ExMoveToLocationInAirShip(Player player, Boat boat, Location location, Location location2) {
        this.char_id = player.getObjectId();
        this.vI = boat.getObjectId();
        this.T = location;
        this._destination = location2;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(109);
        this.writeD(this.char_id);
        this.writeD(this.vI);
        this.writeD(this._destination.x);
        this.writeD(this._destination.y);
        this.writeD(this._destination.z);
        this.writeD(this.T.x);
        this.writeD(this.T.y);
        this.writeD(this.T.z);
    }
}
