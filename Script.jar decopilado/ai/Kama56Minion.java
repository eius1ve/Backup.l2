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

public class Kama56Minion
extends Fighter {
    public Kama56Minion(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.setIsInvul(true);
    }

    protected void onEvtAggression(Creature creature, int n) {
        if (n < 10000000) {
            return;
        }
        super.onEvtAggression(creature, n);
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }
}
