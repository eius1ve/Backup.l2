/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;

class AiTaskManager.1
extends RunnableImpl {
    AiTaskManager.1() {
    }

    @Override
    public void runImpl() throws Exception {
        AiTaskManager.this.purge();
    }
}
