/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;

class LazyPrecisionTaskManager.1
extends RunnableImpl {
    LazyPrecisionTaskManager.1() {
    }

    @Override
    public void runImpl() throws Exception {
        LazyPrecisionTaskManager.this.purge();
    }
}
