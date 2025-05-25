/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.instances.NpcInstance;

private class Tears.DeSpawnTask
extends RunnableImpl {
    private Tears.DeSpawnTask() {
    }

    public void runImpl() {
        for (NpcInstance npcInstance : Tears.this.spawns) {
            if (npcInstance == null) continue;
            npcInstance.deleteMe();
        }
        Tears.this.spawns.clear();
        Tears.this.despawnTask = null;
    }
}
