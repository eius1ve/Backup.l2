/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class StopMoveToLocationInVehicle
extends L2GameServerPacket {
    private int qX;
    private int gP;
    private int gy;
    private Location _loc;

    public StopMoveToLocationInVehicle(Player player) {
        this.qX = player.getBoat().getObjectId();
        this.gP = player.getObjectId();
        this._loc = player.getInBoatPosition();
        this.gy = player.getHeading();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(127);
        this.writeD(this.gP);
        this.writeD(this.qX);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this.gy);
    }
}
