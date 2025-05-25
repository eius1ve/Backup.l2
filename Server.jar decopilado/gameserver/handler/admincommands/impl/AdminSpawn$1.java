/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.SpawnManager;

class AdminSpawn.1
extends RunnableImpl {
    AdminSpawn.1() {
    }

    @Override
    public void runImpl() throws Exception {
        SpawnManager.getInstance().spawnAll();
    }
}
