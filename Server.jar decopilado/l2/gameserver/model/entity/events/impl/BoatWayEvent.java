/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.impl;

import java.util.ArrayList;
import java.util.List;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.Config;
import l2.gameserver.data.BoatHolder;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.objects.BoatPoint;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.MapRegionUtils;

public class BoatWayEvent
extends GlobalEvent {
    public static final String BOAT_POINTS = "boat_points";
    private final int lu;
    private final Location I;
    private final Boat b;

    public BoatWayEvent(MultiValueSet<String> multiValueSet) {
        super(multiValueSet);
        this.lu = multiValueSet.getInteger("ticketId", 0);
        this.I = Location.parseLoc(multiValueSet.getString("return_point"));
        String string = multiValueSet.getString("class", null);
        if (string != null) {
            this.b = BoatHolder.getInstance().initBoat(this.getName(), string);
            Location location = Location.parseLoc(multiValueSet.getString("spawn_point"));
            this.b.setLoc(location, true);
            this.b.setHeading(location.h);
        } else {
            this.b = BoatHolder.getInstance().getBoat(this.getName());
        }
        this.b.setWay(string != null ? 1 : 0, this);
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void startEvent() {
        L2GameServerPacket l2GameServerPacket = this.b.startPacket();
        for (Player player : this.b.getPlayers()) {
            if (this.lu > 0) {
                if (player.consumeItem(this.lu, 1L)) {
                    if (l2GameServerPacket == null) continue;
                    player.sendPacket((IStaticPacket)l2GameServerPacket);
                    continue;
                }
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_POSSESS_THE_CORRECT_TICKET_TO_BOARD_THE_BOAT);
                this.b.oustPlayer(player, this.I, true);
                continue;
            }
            if (l2GameServerPacket == null) continue;
            player.sendPacket((IStaticPacket)l2GameServerPacket);
        }
        this.moveNext();
    }

    public void moveNext() {
        List list = this.getObjects(BOAT_POINTS);
        if (this.b.getRunState() >= list.size()) {
            this.b.trajetEnded(true);
            return;
        }
        BoatPoint boatPoint = (BoatPoint)list.get(this.b.getRunState());
        if (boatPoint.getSpeed1() >= 0) {
            this.b.setMoveSpeed(boatPoint.getSpeed1());
        }
        if (boatPoint.getSpeed2() >= 0) {
            this.b.setRotationSpeed(boatPoint.getSpeed2());
        }
        if (this.b.getRunState() == 0) {
            this.b.broadcastCharInfo();
        }
        this.b.setRunState(this.b.getRunState() + 1);
        if (boatPoint.isTeleport()) {
            this.b.teleportShip(boatPoint.getX(), boatPoint.getY(), boatPoint.getZ());
        } else {
            this.b.moveToLocation(boatPoint.getX(), boatPoint.getY(), boatPoint.getZ(), 0, false);
        }
    }

    @Override
    public void reCalcNextTime(boolean bl) {
        this.registerActions();
    }

    @Override
    protected long startTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public List<Player> broadcastPlayers(int n) {
        if (n <= 0) {
            ArrayList<Player> arrayList = new ArrayList<Player>();
            int n2 = MapRegionUtils.regionX(this.b.getX());
            int n3 = MapRegionUtils.regionY(this.b.getY());
            int n4 = Config.SHOUT_OFFSET;
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                if (player.getReflection() != this.b.getReflection()) continue;
                int n5 = MapRegionUtils.regionX(player);
                int n6 = MapRegionUtils.regionY(player);
                if (n5 < n2 - n4 || n5 > n2 + n4 || n6 < n3 - n4 || n6 > n3 + n4) continue;
                arrayList.add(player);
            }
            return arrayList;
        }
        return World.getAroundPlayers(this.b, n, Math.max(n / 2, 200));
    }

    @Override
    protected void printInfo() {
    }

    public Location getReturnLoc() {
        return this.I;
    }
}
