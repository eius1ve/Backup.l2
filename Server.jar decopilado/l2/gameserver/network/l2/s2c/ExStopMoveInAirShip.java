/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class ExStopMoveInAirShip
extends L2GameServerPacket {
    private int char_id;
    private int vI;
    private int xA;
    private Location _loc;

    public ExStopMoveInAirShip(Player player) {
        this.char_id = player.getObjectId();
        this.vI = player.getBoat().getObjectId();
        this._loc = player.getInBoatPosition();
        this.xA = player.getHeading();
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(110);
        this.writeD(this.char_id);
        this.writeD(this.vI);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this.xA);
    }
}
