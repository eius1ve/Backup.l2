/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.moveroute;

import ai.moveroute.MoveRouteDefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;

public class NotAggressiveNpc
extends MoveRouteDefaultAI {
    public NotAggressiveNpc(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
