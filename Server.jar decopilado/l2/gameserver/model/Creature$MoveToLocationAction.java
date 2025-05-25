/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectTasks;
import l2.gameserver.network.l2.s2c.CharMoveToLocation;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;

public static class Creature.MoveToLocationAction
extends Creature.MoveToAction {
    private final Location u;
    private final Location v;

    public Creature.MoveToLocationAction(Creature creature, Location location, Location location2, boolean bl, int n, boolean bl2) {
        super(creature, bl, n, bl2);
        this.v = location.clone();
        this.u = location2.clone();
    }

    public Creature.MoveToLocationAction(Creature creature, Location location, int n, boolean bl) {
        this(creature, creature.getLoc(), location, creature.isBoat() || creature.isInBoat(), n, bl);
    }

    public boolean isSameDest(Location location) {
        return this.u.equalsGeo(location);
    }

    public Location getFinalDest() {
        return this.u;
    }

    @Override
    public boolean start() {
        if (!super.start()) {
            return false;
        }
        if (!this.buildPathLines(this.v, this.u)) {
            return false;
        }
        return !this.onEnd();
    }

    @Override
    protected boolean onEnd() {
        Creature creature = this.getActor();
        if (creature == null) {
            return true;
        }
        if (!this.pollPathLine()) {
            this.onFinish(true, false);
            return true;
        }
        creature.aT();
        return false;
    }

    @Override
    protected void onFinish(boolean bl, boolean bl2) {
        Creature creature = this.getActor();
        if (this.isFinished() || creature == null) {
            return;
        }
        if (bl2) {
            this.setIsFinished(true);
            return;
        }
        if (bl) {
            if (creature.isPlayer() && !this.pathFind) {
                creature.stopMove(true, false, false);
            }
            ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ARRIVED));
        } else {
            creature.stopMove(true, true, false);
            ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ARRIVED_BLOCKED, creature.getLoc()));
        }
        super.onFinish(bl, bl2);
    }

    @Override
    public L2GameServerPacket movePacket() {
        Creature creature = this.getActor();
        return creature != null ? new CharMoveToLocation(creature, creature.getLoc(), this.moveTo().clone()) : null;
    }

    @Override
    protected boolean isRelativeMove() {
        return false;
    }

    @Override
    protected boolean pollPathLine() {
        if (this.currentGeoPathLine(this.getGeoPathLines().poll()) != null) {
            Creature creature = this.getActor();
            Location location = this.currentGeoPathLine().get(0).clone().geo2world();
            Location location2 = this.currentGeoPathLine().get(this.currentGeoPathLine().size() - 1).clone().geo2world();
            this.setMoveFrom(location);
            this.setMoveTo(this.isForPlayable() && this.remainingLinesCount() == 0 && this.getFinalDest().equalsGeo(location2) ? this.getFinalDest() : location2);
            this.setPrevIncZ(this.includeMoveZ());
            this.setPrevMoveLen(PositionUtils.calculateDistance(location, location2, this.isPrevIncZ()));
            this.setPassDist(0.0);
            this.setPrevTick(System.currentTimeMillis());
            if (this.getPrevMoveLen() > 16.0) {
                creature.setHeading(PositionUtils.calculateHeadingFrom(location.getX(), location.getY(), location2.getX(), location2.getY()));
            }
            return true;
        }
        return false;
    }

    @Override
    public Creature.MoveToLocationAction getAsMoveToLocation() {
        return this;
    }
}
