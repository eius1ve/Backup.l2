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

public class Furance.Switch
extends RunnableImpl {
    public void runImpl() {
        NpcInstance npcInstance = Furance.this.getActor();
        if (npcInstance.getNpcState() == 1) {
            npcInstance.setNpcState(2);
        } else {
            npcInstance.setNpcState(1);
        }
    }
}
