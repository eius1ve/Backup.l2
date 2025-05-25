/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;

protected static abstract class Creature.MoveActionBase {
    private final HardReference<? extends Creature> u;
    private final boolean bs;
    private long aZ;
    private int gz;
    private double k;
    protected volatile boolean isFinished = false;

    public Creature.MoveActionBase(Creature creature) {
        this.u = creature.getRef();
        this.bs = creature.isPlayable();
        this.aZ = 0L;
        this.gz = 0;
        this.k = 0.0;
        this.isFinished = false;
    }

    protected boolean isForPlayable() {
        return this.bs;
    }

    protected Creature getActor() {
        return this.u.get();
    }

    protected void setIsFinished(boolean bl) {
        this.isFinished = bl;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    protected long getPrevTick() {
        return this.aZ;
    }

    protected void setPrevTick(long l) {
        this.aZ = l;
    }

    protected int getPrevSpeed() {
        return this.gz;
    }

    protected void setPrevSpeed(int n) {
        this.gz = n;
    }

    protected double getPassDist() {
        return this.k;
    }

    protected void setPassDist(double d) {
        this.k = d;
    }

    public boolean start() {
        Creature creature = this.getActor();
        if (creature == null) {
            return false;
        }
        this.setPrevTick(System.currentTimeMillis());
        this.setPrevSpeed(creature.getMoveSpeed());
        this.setPassDist(0.0);
        this.setIsFinished(false);
        return this.weightCheck(creature);
    }

    public abstract Location moveFrom();

    public abstract Location moveTo();

    protected double getMoveLen() {
        return PositionUtils.calculateDistance(this.moveFrom(), this.moveTo(), this.includeMoveZ());
    }

    protected boolean includeMoveZ() {
        Creature creature = this.getActor();
        return creature == null || creature.isInWater() || creature.isFlying() || creature.isBoat() || creature.isInBoat();
    }

    public int getNextTickInterval() {
        if (!this.isForPlayable()) {
            return Math.min(Config.MOVE_TASK_QUANTUM_NPC, (int)(1000.0 * (this.getMoveLen() - this.getPassDist()) / (double)Math.max(this.getPrevSpeed(), 1)));
        }
        return Math.min(Config.MOVE_TASK_QUANTUM_PC, (int)(1000.0 * (this.getMoveLen() - this.getPassDist()) / (double)Math.max(this.getPrevSpeed(), 1)));
    }

    protected boolean onEnd() {
        return true;
    }

    protected void onFinish(boolean bl, boolean bl2) {
        this.setIsFinished(true);
    }

    public void interrupt() {
        this.tick();
        this.onFinish(false, true);
    }

    protected boolean onTick(double d) {
        Creature creature = this.getActor();
        if (creature == null) {
            this.onFinish(false, true);
            return false;
        }
        return true;
    }

    public boolean scheduleNextTick() {
        Creature creature = this.getActor();
        if (creature == null) {
            return false;
        }
        Runnable runnable = creature.d;
        creature.d = runnable = new Creature.CreatureMoveActionTask(creature);
        creature.g = ThreadPoolManager.getInstance().schedule(runnable, this.getNextTickInterval());
        return true;
    }

    public boolean tick() {
        Creature creature = this.getActor();
        if (creature == null) {
            return false;
        }
        creature.p.lock();
        try {
            boolean bl = this.b(creature);
            return bl;
        }
        finally {
            creature.p.unlock();
        }
    }

    private boolean b(Creature creature) {
        if (this.isFinished()) {
            return false;
        }
        if (creature.moveAction != this) {
            this.setIsFinished(true);
            return false;
        }
        if (creature.isMovementDisabled()) {
            this.onFinish(false, false);
            return false;
        }
        int n = creature.getMoveSpeed();
        if (n <= 0) {
            this.onFinish(false, false);
            return false;
        }
        long l = System.currentTimeMillis();
        float f = (float)(l - this.getPrevTick()) / 1000.0f;
        boolean bl = this.includeMoveZ();
        double d = this.getPassDist();
        this.setPrevTick(l);
        this.setPrevSpeed(n);
        this.setPassDist(d += (double)f * ((double)Math.max(this.getPrevSpeed() + n, 2) / 2.0));
        double d2 = this.getMoveLen();
        double d3 = Math.max(0.0, Math.min(d / Math.max(d2, 1.0), 1.0));
        Location location = creature.getLoc();
        Location location2 = location.clone();
        if (!this.calcMidDest(creature, location2, bl, d3, d, d2)) {
            this.onFinish(false, false);
            return false;
        }
        if (!bl) {
            // empty if block
        }
        creature.setLoc(location2, true);
        if (d3 == 1.0) {
            return !this.onEnd();
        }
        if (!this.onTick(d3)) {
            this.setIsFinished(true);
            return false;
        }
        return true;
    }

    protected boolean weightCheck(Creature creature) {
        if (!creature.isPlayer()) {
            return true;
        }
        if (creature.getPlayer().getCurrentLoad() >= 2 * creature.getPlayer().getMaxLoad()) {
            creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_CANNOT_MOVE_YOUR_ITEM_WEIGHT_IS_TOO_GREAT));
            return false;
        }
        return true;
    }

    protected boolean calcMidDest(Creature creature, Location location, boolean bl, double d, double d2, double d3) {
        location.set(this.moveTo().clone().indent(this.moveFrom(), (int)Math.round(d3 - d2), creature.isFlying() || creature.isInWater())).correctGeoZ();
        return true;
    }

    public abstract L2GameServerPacket movePacket();

    public Creature.MoveToLocationAction getAsMoveToLocation() {
        return null;
    }

    public Creature.MoveToRelativeAction getAsMoveToRelative() {
        return null;
    }
}
