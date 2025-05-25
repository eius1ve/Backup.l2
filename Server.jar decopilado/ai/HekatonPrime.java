/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;

public class HekatonPrime
extends Fighter {
    private long q;

    public HekatonPrime(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        this.q = System.currentTimeMillis();
    }

    protected boolean thinkActive() {
        if (this.q + 600000L < System.currentTimeMillis()) {
            if (this.getActor().getMinionList().hasMinions()) {
                this.getActor().getMinionList().deleteMinions();
            }
            this.getActor().deleteMe();
            return true;
        }
        return false;
    }

    protected void onEvtAttacked(Creature creature, int n) {
        this.q = System.currentTimeMillis();
        super.onEvtAttacked(creature, n);
    }
}
