/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExMoveToTargetInAirShip
extends L2GameServerPacket {
    private int char_id;
    private int vI;
    private int vJ;
    private int vK;
    private Location _loc;

    public ExMoveToTargetInAirShip(Player player, Boat boat, int n, int n2, Location location) {
        this.char_id = player.getObjectId();
        this.vI = boat.getObjectId();
        this.vJ = n;
        this.vK = n2;
        this._loc = location;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(113);
        this.writeD(this.char_id);
        this.writeD(this.vJ);
        this.writeD(this.vK);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this._loc.h);
        this.writeD(this.vI);
    }
}
