/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.isle_of_prayer;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;

private class FafurionKindred.PoisonTask
extends RunnableImpl {
    private FafurionKindred.PoisonTask() {
    }

    public void runImpl() {
        NpcInstance npcInstance = FafurionKindred.this.getActor();
        npcInstance.reduceCurrentHp(500.0, (Creature)npcInstance, null, true, false, true, false, false, false, false);
    }
}
