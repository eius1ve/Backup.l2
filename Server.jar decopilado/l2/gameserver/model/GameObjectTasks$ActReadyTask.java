/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Creature;

public static class GameObjectTasks.ActReadyTask
extends RunnableImpl {
    private final HardReference<? extends Creature> w;

    public GameObjectTasks.ActReadyTask(Creature creature) {
        this.w = creature.getRef();
    }

    @Override
    public void runImpl() throws Exception {
        Creature creature = this.w.get();
        if (creature == null) {
            return;
        }
        creature.getAI().notifyEvent(CtrlEvent.EVT_READY_TO_ACT);
    }
}
