/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;

public class KashasEye
extends DefaultAI {
    public KashasEye(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean randomWalk() {
        return false;
    }

    protected void onEvtAggression(Creature creature, int n) {
    }

    protected void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
        NpcInstance npcInstance = this.getActor();
        if (npcInstance != null && creature != null && npcInstance != creature && Rnd.chance((int)35)) {
            npcInstance.setDisplayId(Rnd.get((int)18812, (int)18814));
        }
    }
}
