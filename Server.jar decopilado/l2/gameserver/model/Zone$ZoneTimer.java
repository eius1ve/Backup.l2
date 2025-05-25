/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.concurrent.Future;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.taskmanager.EffectTaskManager;

private abstract class Zone.ZoneTimer
extends RunnableImpl {
    protected Creature cha;
    protected Future<?> future;
    protected boolean active;

    public Zone.ZoneTimer(Creature creature) {
        this.cha = creature;
    }

    public void start() {
        this.active = true;
        this.future = EffectTaskManager.getInstance().schedule(this, (long)Zone.this.getTemplate().getInitialDelay() * 1000L);
    }

    public void stop() {
        this.active = false;
        if (this.future != null) {
            this.future.cancel(false);
            this.future = null;
        }
    }

    public void next() {
        if (!this.active) {
            return;
        }
        if (Zone.this.getTemplate().getUnitTick() == 0 && Zone.this.getTemplate().getRandomTick() == 0) {
            return;
        }
        this.future = EffectTaskManager.getInstance().schedule(this, (long)(Zone.this.getTemplate().getUnitTick() + Rnd.get(0, Zone.this.getTemplate().getRandomTick())) * 1000L);
    }

    @Override
    public abstract void runImpl() throws Exception;
}
