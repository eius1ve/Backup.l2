/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.Fighter;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.instances.NpcInstance;

public class GuardoftheGrave
extends Fighter {
    private static final int T = 90000;
    private static final int U = 18816;

    public GuardoftheGrave(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.setIsInvul(true);
        npcInstance.startImmobilized();
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new DeSpawnTask()), (long)(90000 + Rnd.get((int)1, (int)30)));
    }

    protected boolean checkTarget(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance != null && creature != null && !npcInstance.isInRange((GameObject)creature, (long)npcInstance.getAggroRange())) {
            npcInstance.getAggroList().remove(creature, true);
            return false;
        }
        return super.checkTarget(creature, n);
    }

    protected void spawnChest(NpcInstance npcInstance) {
        try {
            NpcInstance npcInstance2 = NpcHolder.getInstance().getTemplate(18816).getNewInstance();
            npcInstance2.setSpawnedLoc(npcInstance.getLoc());
            npcInstance2.setCurrentHpMp((double)npcInstance2.getMaxHp(), (double)npcInstance2.getMaxMp(), true);
            npcInstance2.spawnMe(npcInstance2.getSpawnedLoc());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private class DeSpawnTask
    extends RunnableImpl {
        private DeSpawnTask() {
        }

        public void runImpl() {
            NpcInstance npcInstance = GuardoftheGrave.this.getActor();
            GuardoftheGrave.this.spawnChest(npcInstance);
            npcInstance.deleteMe();
        }
    }
}
