/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.data.BoatHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.utils.Location;

public class RequestGetOnVehicle
extends L2GameClientPacket {
    private int fW;
    private Location _loc = new Location();

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this._loc.x = this.readD();
        this._loc.y = this.readD();
        this._loc.z = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Boat boat = BoatHolder.getInstance().getBoat(this.fW);
        if (boat == null) {
            return;
        }
        player._stablePoint = boat.getCurrentWay().getReturnLoc();
        boat.addPlayer(player, this._loc);
    }
}
