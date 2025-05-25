/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.isle_of_prayer;

import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.model.instances.NpcInstance;

private class FafurionKindred.DeSpawnTask
extends RunnableImpl {
    private FafurionKindred.DeSpawnTask() {
    }

    public void runImpl() {
        NpcInstance npcInstance = FafurionKindred.this.getActor();
        FafurionKindred.this.a(npcInstance, 9691, Rnd.get((int)1, (int)2));
        if (Rnd.chance((int)36)) {
            FafurionKindred.this.a(npcInstance, 9700, Rnd.get((int)1, (int)3));
        }
        FafurionKindred.this.cleanUp();
        npcInstance.deleteMe();
    }
}
