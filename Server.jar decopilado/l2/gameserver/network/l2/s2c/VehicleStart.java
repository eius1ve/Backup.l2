/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class VehicleStart
extends L2GameServerPacket {
    private int fW;
    private int _state;

    public VehicleStart(Boat boat) {
        this.fW = boat.getObjectId();
        this._state = boat.getRunState();
    }

    @Override
    protected void writeImpl() {
        this.writeC(192);
        this.writeD(this.fW);
        this.writeD(this._state);
    }
}
