/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.NpcInstance;

protected class DefaultAI.RunningTask
extends RunnableImpl {
    protected DefaultAI.RunningTask() {
    }

    @Override
    public void runImpl() throws Exception {
        NpcInstance npcInstance = DefaultAI.this.getActor();
        if (npcInstance != null) {
            npcInstance.setRunning();
        }
        DefaultAI.this._runningTask = null;
    }
}
