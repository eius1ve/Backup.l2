/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.listener.actor.npc.OnSpawnListener
 *  l2.gameserver.model.instances.NpcInstance
 */
package services;

import l2.gameserver.listener.actor.npc.OnSpawnListener;
import l2.gameserver.model.instances.NpcInstance;
import services.TeleportToRaid;

private static class TeleportToRaid.OnSpawnImpl
implements OnSpawnListener {
    private TeleportToRaid.OnSpawnImpl() {
    }

    public void onSpawn(NpcInstance npcInstance) {
        TeleportToRaid.s(npcInstance.getNpcId());
    }
}
