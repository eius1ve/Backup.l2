/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.data.BoatHolder;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.utils.Location;

public class ValidatePosition
extends L2GameClientPacket {
    private final Location Q = new Location();
    private int qX;
    private Location A;
    private Location B;

    @Override
    protected void readImpl() {
        this.Q.x = this.readD();
        this.Q.y = this.readD();
        this.Q.z = this.readD();
        this.Q.h = this.readD();
        this.qX = this.readD();
    }

    @Override
    protected void runImpl() {
        boolean bl;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isTeleporting() || player.isInObserverMode() || player.isOlyObserver()) {
            return;
        }
        this.A = player.getLastClientPosition();
        this.B = player.getLastServerPosition();
        if (this.A == null) {
            this.A = player.getLoc();
        }
        if (this.B == null) {
            this.B = player.getLoc();
        }
        if (player.getX() == 0 && player.getY() == 0 && player.getZ() == 0) {
            this.ag(player);
            return;
        }
        if (player.isInFlyingTransform()) {
            if (this.Q.x > -166168) {
                player.setTransformation(0);
                return;
            }
            if (this.Q.z <= 0 || this.Q.z >= 6000) {
                player.teleToLocation(player.getLoc().setZ(Math.min(5950, Math.max(50, this.Q.z))));
                return;
            }
        }
        double d = player.getDistance(this.Q.x, this.Q.y);
        double d2 = player.getDistance(this.Q.x, this.Q.y, this.Q.z);
        int n = Math.abs(this.Q.z - player.getZ());
        int n2 = this.B.z - player.getZ();
        if (this.qX > 0) {
            Boat boat = BoatHolder.getInstance().getBoat(this.qX);
            if (boat != null && player.getBoat() == boat) {
                player.setHeading(this.Q.h);
                boat.validateLocationPacket(player);
            }
            player.setLastClientPosition(this.Q.setH(player.getHeading()));
            player.setLastServerPosition(player.getLoc());
            return;
        }
        if (player.isFalling()) {
            d = 0.0;
            n = 0;
            n2 = 0;
        }
        int n3 = player.maxZDiff() + 64;
        boolean bl2 = bl = !player.isInWater() && !player.isFlying();
        if (bl && n2 >= 256) {
            player.falling(n2);
        }
        if (bl && n >= n3 / 2) {
            player.validateLocation(0);
        } else if (this.Q.z < -30000 || this.Q.z > 30000) {
            if (player.getIncorrectValidateCount() >= 3) {
                player.teleToClosestTown();
            } else {
                this.ag(player);
                player.setIncorrectValidateCount(player.getIncorrectValidateCount() + 1);
            }
        } else if (d2 > 500.0) {
            player.validateLocation(1);
        } else if (d > 1024.0) {
            if (player.getIncorrectValidateCount() >= Config.MOVE_TO_TOWN_ON_FAIL_TRY_COUNT && Config.MOVE_TO_TOWN_ON_FAIL_VALIDATE) {
                player.teleToClosestTown();
            } else if (player.getIncorrectValidateCount() > 3) {
                player.teleToLocation(Location.findPointToStay(this.B, player.getIncorrectValidateCount() * 32, player.getGeoIndex()));
                player.setIncorrectValidateCount(player.getIncorrectValidateCount() + 1);
            } else {
                player.teleToLocation(player.getLoc());
                player.setIncorrectValidateCount(player.getIncorrectValidateCount() + 1);
            }
        } else {
            player.setIncorrectValidateCount(0);
        }
        player.setLastClientPosition(this.Q.setH(player.getHeading()));
        player.setLastServerPosition(player.getLoc());
    }

    private void ag(Player player) {
        if (player.isGM()) {
            player.sendMessage("Server loc: " + player.getLoc());
            player.sendMessage("Correcting position...");
        }
        if (this.B.x != 0 && this.B.y != 0 && this.B.z != 0) {
            if (GeoEngine.getNSWE(this.B.x, this.B.y, this.B.z, player.getGeoIndex()) == 15) {
                player.teleToLocation(this.B);
            } else {
                player.teleToClosestTown();
            }
        } else if (this.A.x != 0 && this.A.y != 0 && this.A.z != 0) {
            if (GeoEngine.getNSWE(this.A.x, this.A.y, this.A.z, player.getGeoIndex()) == 15) {
                player.teleToLocation(this.A);
            } else {
                player.teleToClosestTown();
            }
        } else {
            player.teleToClosestTown();
        }
    }
}
