/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai.custom;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Mystic;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class SSQLilimServantMage
extends Mystic {
    private boolean J = false;

    public SSQLilimServantMage(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        super.onEvtAttacked(creature, n);
        if (Rnd.chance((int)30) && !this.J) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)"1000208", (Object[])new Object[0]);
            this.J = true;
        }
    }

    protected void onEvtDead(Creature creature) {
        if (Rnd.chance((int)30)) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)"1000247", (Object[])new Object[0]);
        }
        super.onEvtDead(creature);
    }
}
