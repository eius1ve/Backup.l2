/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;

public static class GameObjectTasks.DeleteTask
extends RunnableImpl {
    private final HardReference<? extends Creature> A;

    public GameObjectTasks.DeleteTask(Creature creature) {
        this.A = creature.getRef();
    }

    @Override
    public void runImpl() {
        Creature creature = this.A.get();
        if (creature != null) {
            creature.deleteMe();
        }
    }
}
