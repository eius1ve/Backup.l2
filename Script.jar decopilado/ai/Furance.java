/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;

public class Furance
extends DefaultAI {
    public Furance(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        NpcInstance npcInstance = this.getActor();
        if (Rnd.chance((int)50)) {
            npcInstance.setNpcState(1);
        }
        ThreadPoolManager.getInstance().scheduleAtFixedRate((Runnable)((Object)new Switch()), 300000L, 300000L);
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }

    protected boolean randomAnimation() {
        return false;
    }

    public boolean isGlobalAI() {
        return true;
    }

    public class Switch
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
}
