/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.geodata.GeoMove;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectTasks;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.network.l2.s2c.CharMoveToLocation;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MoveToPawn;
import l2.gameserver.utils.Location;

public static class Creature.MoveToRelativeAction
extends Creature.MoveToAction {
    private final HardReference<? extends GameObject> v;
    private Location w;
    private boolean bt;
    private final int gA;

    protected Creature.MoveToRelativeAction(Creature creature, GameObject gameObject, boolean bl, int n, int n2, boolean bl2) {
        super(creature, bl, n, bl2);
        this.v = gameObject.getRef();
        this.w = gameObject.getLoc().clone();
        this.gA = Math.max(n2, n + 16);
        this.bt = false;
    }

    private GameObject getTarget() {
        return this.v.get();
    }

    public boolean isSameTarget(GameObject gameObject) {
        return this.getTarget() == gameObject;
    }

    @Override
    public boolean start() {
        Location location;
        if (!super.start()) {
            return false;
        }
        Creature creature = this.getActor();
        GameObject gameObject = this.getTarget();
        if (creature == null || gameObject == null) {
            return false;
        }
        Location location2 = creature.getLoc();
        if (!this.buildPathLines(location2, location = gameObject.getLoc().clone())) {
            return false;
        }
        this.w = location.clone();
        return !this.onEnd();
    }

    protected boolean isPathRebuildRequired() {
        Creature creature = this.getActor();
        GameObject gameObject = this.getTarget();
        if (creature == null || gameObject == null) {
            return true;
        }
        Location location = gameObject.getLoc();
        if (!this.bt) {
            return false;
        }
        return !this.w.equalsGeo(location);
    }

    @Override
    protected boolean onEnd() {
        Creature creature = this.getActor();
        GameObject gameObject = this.getTarget();
        if (creature == null || gameObject == null) {
            return true;
        }
        int n = this.remainingLinesCount();
        if (n > 1) {
            if (!this.pollPathLine()) {
                this.onFinish(false, false);
                return true;
            }
        } else if (n == 1) {
            this.bt = true;
            if (this.isPathRebuildRequired()) {
                Location location;
                if (this.isArrived()) {
                    this.onFinish(true, false);
                    return true;
                }
                Location location2 = creature.getLoc();
                if (!this.buildPathLines(location2, location = this.e())) {
                    this.onFinish(false, false);
                    return true;
                }
                if (!this.pollPathLine()) {
                    this.onFinish(false, false);
                    return true;
                }
                this.w = location.clone();
            } else if (!this.pollPathLine()) {
                this.onFinish(false, false);
                return true;
            }
        } else {
            this.onFinish(true, false);
            return true;
        }
        creature.aT();
        return false;
    }

    protected boolean isArrived() {
        Creature creature = this.getActor();
        GameObject gameObject = this.getTarget();
        if (creature == null || gameObject == null) {
            return false;
        }
        if (gameObject.isCreature() && ((Creature)gameObject).isMoving()) {
            int n = this.indent + 16;
            if (this.includeMoveZ()) {
                return gameObject.isInRangeZ(creature, (long)n);
            }
            return gameObject.isInRange(creature, (long)n);
        }
        if (this.includeMoveZ()) {
            return gameObject.isInRangeZ(creature, (long)(this.indent + 16));
        }
        return gameObject.isInRange(creature, (long)(this.indent + 16));
    }

    private Location e() {
        Creature creature = this.getActor();
        GameObject gameObject = this.getTarget();
        if (creature == null || gameObject == null) {
            return null;
        }
        if (!gameObject.isCreature()) {
            return gameObject.getLoc();
        }
        Creature creature2 = (Creature)gameObject;
        Location location = gameObject.getLoc();
        if (!creature2.isMoving()) {
            return location;
        }
        return GeoMove.getIntersectPoint(creature.getLoc(), location, creature2.getMoveSpeed(), Math.max(128, Config.MOVE_TASK_QUANTUM_PC / 2));
    }

    @Override
    protected boolean onTick(double d) {
        if (!super.onTick(d)) {
            return false;
        }
        Creature creature = this.getActor();
        GameObject gameObject = this.getTarget();
        if (creature == null || gameObject == null) {
            return false;
        }
        if (d < 1.0) {
            if (this.isPathRebuildRequired()) {
                Location location = creature.getLoc();
                Location location2 = this.e();
                if (creature.isPlayer() && creature.getPlayer().getNetConnection() != null) {
                    int n = creature.getPlayer().getNetConnection().getPawnClippingRange();
                    if (location.distance3D(location2) > (double)n) {
                        this.onFinish(false, false);
                        return false;
                    }
                }
                if (!this.buildPathLines(location, location2)) {
                    this.onFinish(false, false);
                    return false;
                }
                if (!this.pollPathLine()) {
                    this.onFinish(false, false);
                    return false;
                }
                this.w = location2.clone();
            } else if (this.bt && this.isArrived()) {
                this.onFinish(true, false);
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onFinish(boolean bl, boolean bl2) {
        Creature creature = this.getActor();
        GameObject gameObject = this.getTarget();
        if (this.isFinished() || creature == null || gameObject == null) {
            return;
        }
        if (bl2) {
            this.setIsFinished(true);
            return;
        }
        creature.stopMove(!(gameObject instanceof StaticObjectInstance) && !gameObject.isDoor(), false, false);
        boolean bl3 = false;
        if (bl) {
            bl3 = (this.includeMoveZ() ? creature.getRealDistance3D(gameObject) : creature.getRealDistance(gameObject)) <= (double)(this.gA + 16);
        }
        this.setIsFinished(true);
        if (bl3) {
            ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ARRIVED_TARGET));
        } else {
            ThreadPoolManager.getInstance().execute(new GameObjectTasks.NotifyAITask(creature, CtrlEvent.EVT_ARRIVED_BLOCKED, creature.getLoc()));
        }
    }

    @Override
    protected boolean isRelativeMove() {
        return this.bt;
    }

    @Override
    public L2GameServerPacket movePacket() {
        Creature creature = this.getActor();
        if (creature == null) {
            return null;
        }
        GameObject gameObject = this.getTarget();
        if (this.isRelativeMove()) {
            if (gameObject == null) {
                return null;
            }
            return new MoveToPawn(creature, gameObject, this.indent);
        }
        return new CharMoveToLocation(creature, creature.getLoc(), this.moveTo().clone());
    }

    @Override
    public Creature.MoveToRelativeAction getAsMoveToRelative() {
        return this;
    }
}
