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

public class Scarecrow
extends Fighter {
    public Scarecrow(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.block();
        npcInstance.setIsInvul(true);
    }

    protected void onIntentionAttack(Creature creature) {
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
