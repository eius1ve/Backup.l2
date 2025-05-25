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

private class GuardoftheGrave.DeSpawnTask
extends RunnableImpl {
    private GuardoftheGrave.DeSpawnTask() {
    }

    public void runImpl() {
        NpcInstance npcInstance = GuardoftheGrave.this.getActor();
        GuardoftheGrave.this.spawnChest(npcInstance);
        npcInstance.deleteMe();
    }
}
