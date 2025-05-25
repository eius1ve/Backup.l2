/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.threading.RunnableImpl;

class AutoSaveManager.1
extends RunnableImpl {
    AutoSaveManager.1() {
    }

    @Override
    public void runImpl() throws Exception {
        AutoSaveManager.this.purge();
    }
}
