/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.utils.Location;

public class CannotMoveAnymoreInVehicle
extends L2GameClientPacket {
    private Location _loc = new Location();
    private int pY;

    @Override
    protected void readImpl() {
        this.pY = this.readD();
        this._loc.x = this.readD();
        this._loc.y = this.readD();
        this._loc.z = this.readD();
        this._loc.h = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Boat boat = player.getBoat();
        if (boat != null && boat.getObjectId() == this.pY) {
            player.setInBoatPosition(this._loc);
            player.setHeading(this._loc.h);
            player.broadcastPacket(boat.inStopMovePacket(player));
        }
    }
}
