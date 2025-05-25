/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.NpcInstance;

class LazyPrecisionTaskManager.5
extends RunnableImpl {
    final /* synthetic */ NpcInstance val$npc;

    LazyPrecisionTaskManager.5(NpcInstance npcInstance) {
        this.val$npc = npcInstance;
    }

    @Override
    public void runImpl() throws Exception {
        if (this.val$npc.isVisible() && !this.val$npc.isActionsDisabled() && !this.val$npc.isMoving() && !this.val$npc.isInCombat()) {
            this.val$npc.onRandomAnimation();
        }
    }
}
