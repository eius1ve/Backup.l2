/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 */
package bosses;

import bosses.FrintezzaManager;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;

private class FrintezzaManager.DeathListener
implements OnDeathListener {
    private FrintezzaManager.DeathListener() {
    }

    public void onDeath(Creature creature, Creature creature2) {
        if (creature.isNpc()) {
            NpcInstance npcInstance = (NpcInstance)creature;
            if (npcInstance == FrintezzaManager.this.i) {
                npcInstance.decayMe();
            } else if (npcInstance == FrintezzaManager.this.j) {
                ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Die(FrintezzaManager.this, 1)), 10L);
            } else if (FrintezzaManager.this.a(npcInstance, FrintezzaManager.this.u)) {
                FrintezzaManager.this.d(npcInstance);
            } else if (FrintezzaManager.this.a(npcInstance, FrintezzaManager.this.w)) {
                FrintezzaManager.this.e((NpcInstance)creature);
            } else if (FrintezzaManager.this.a(npcInstance, FrintezzaManager.this.x)) {
                FrintezzaManager.this.a(npcInstance, creature2);
            }
        }
    }
}
