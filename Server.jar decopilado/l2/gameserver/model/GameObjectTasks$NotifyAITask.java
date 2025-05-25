/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.model.Creature;

public static class GameObjectTasks.NotifyAITask
extends RunnableImpl {
    private final CtrlEvent a;
    private final Object g;
    private final Object h;
    private final HardReference<? extends Creature> K;

    public GameObjectTasks.NotifyAITask(Creature creature, CtrlEvent ctrlEvent, Object object, Object object2) {
        this.K = creature.getRef();
        this.a = ctrlEvent;
        this.g = object;
        this.h = object2;
    }

    public GameObjectTasks.NotifyAITask(Creature creature, CtrlEvent ctrlEvent, Object object) {
        this(creature, ctrlEvent, object, null);
    }

    public GameObjectTasks.NotifyAITask(Creature creature, CtrlEvent ctrlEvent) {
        this(creature, ctrlEvent, null, null);
    }

    @Override
    public void runImpl() {
        Creature creature = this.K.get();
        if (creature == null || !creature.hasAI()) {
            return;
        }
        creature.getAI().notifyEvent(this.a, this.g, this.h);
    }
}
