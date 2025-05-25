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

public class RequestGetOffVehicle
extends L2GameClientPacket {
    private int fW;
    private Location s = new Location();

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.s.x = this.readD();
        this.s.y = this.readD();
        this.s.z = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Boat boat = BoatHolder.getInstance().getBoat(this.fW);
        if (boat == null || boat.isMoving()) {
            player.sendActionFailed();
            return;
        }
        boat.oustPlayer(player, this.s, false);
    }
}
