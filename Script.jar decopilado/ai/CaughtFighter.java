/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class CaughtFighter
extends Fighter {
    private static final int D = 60000;
    private final long l = System.currentTimeMillis() + 60000L;

    public CaughtFighter(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        if (Rnd.chance((int)75)) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)"scripts.ai.CaughtMob.spawn", (Object[])new Object[0]);
        }
    }

    protected void onEvtDead(Creature creature) {
        if (Rnd.chance((int)75)) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)"scripts.ai.CaughtMob.death", (Object[])new Object[0]);
        }
        super.onEvtDead(creature);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance != null && System.currentTimeMillis() >= this.l) {
            npcInstance.deleteMe();
            return false;
        }
        return super.thinkActive();
    }
}
