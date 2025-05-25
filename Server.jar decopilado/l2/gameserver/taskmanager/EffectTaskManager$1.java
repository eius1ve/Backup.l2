/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;

class EffectTaskManager.1
extends RunnableImpl {
    EffectTaskManager.1() {
    }

    @Override
    public void runImpl() throws Exception {
        EffectTaskManager.this.purge();
    }
}
