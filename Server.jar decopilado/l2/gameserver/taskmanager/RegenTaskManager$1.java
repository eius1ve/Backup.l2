/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;

class RegenTaskManager.1
extends RunnableImpl {
    RegenTaskManager.1() {
    }

    @Override
    public void runImpl() throws Exception {
        RegenTaskManager.this.purge();
    }
}
