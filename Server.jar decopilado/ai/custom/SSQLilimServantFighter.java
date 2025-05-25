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
package ai.custom;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class SSQLilimServantFighter
extends Fighter {
    private boolean J = false;

    public SSQLilimServantFighter(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        if (Rnd.chance((int)30) && !this.J) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)(Rnd.chance((int)50) ? "1000108" : "1000268"), (Object[])new Object[0]);
            this.J = true;
        }
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtDead(Creature creature) {
        if (Rnd.chance((int)30)) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)(Rnd.chance((int)50) ? "1000270" : "1000271"), (Object[])new Object[0]);
        }
        super.onEvtDead(creature);
    }
}
