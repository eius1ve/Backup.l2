/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.SpawnManager;

class AdminSpawn.3
extends RunnableImpl {
    AdminSpawn.3() {
    }

    @Override
    public void runImpl() throws Exception {
        SpawnManager.getInstance().spawnNight();
    }
}
