/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExValidateLocationInAirShip
extends L2GameServerPacket {
    private int gP;
    private int qX;
    private Location _loc;

    public ExValidateLocationInAirShip(Player player) {
        this.gP = player.getObjectId();
        this.qX = player.getBoat().getObjectId();
        this._loc = player.getInBoatPosition();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(111);
        this.writeD(this.gP);
        this.writeD(this.qX);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this._loc.h);
    }
}
