/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;

class DecayTaskManager.2
extends RunnableImpl {
    final /* synthetic */ Creature val$actor;

    DecayTaskManager.2(Creature creature) {
        this.val$actor = creature;
    }

    @Override
    public void runImpl() throws Exception {
        this.val$actor.doDecay();
    }
}
