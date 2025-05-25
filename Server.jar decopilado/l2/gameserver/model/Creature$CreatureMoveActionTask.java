/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;

protected static class Creature.CreatureMoveActionTask
extends RunnableImpl {
    private final HardReference<? extends Creature> t;

    public Creature.CreatureMoveActionTask(Creature creature) {
        this.t = creature.getRef();
    }

    @Override
    public void runImpl() throws Exception {
        Creature creature = this.t.get();
        if (creature == null) {
            return;
        }
        creature.p.lock();
        try {
            Creature.MoveActionBase moveActionBase = creature.moveAction;
            if (creature.d == this && moveActionBase != null && !moveActionBase.isFinished() && moveActionBase.b(creature) && creature.d == this) {
                moveActionBase.scheduleNextTick();
            }
        }
        finally {
            creature.p.unlock();
        }
    }
}
