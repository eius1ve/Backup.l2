/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;

public static class GameObjectTasks.CastEndTimeTask
extends RunnableImpl {
    private final HardReference<? extends Creature> z;

    public GameObjectTasks.CastEndTimeTask(Creature creature) {
        this.z = creature.getRef();
    }

    @Override
    public void runImpl() {
        Creature creature = this.z.get();
        if (creature == null) {
            return;
        }
        creature.onCastEndTime();
    }
}
