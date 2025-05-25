/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.gameserver.ai.Fighter;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.instances.NpcInstance;

public class ProtectorHolyGrail
extends Fighter {
    private static final String j = "[exclusive_despawn_normal]";
    private static final int ag = 36481;

    public ProtectorHolyGrail(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        int n = GameObjectsStorage.getCountByNpcId((int)36481);
        if (n >= 4) {
            SpawnManager.getInstance().spawn(j);
        }
    }

    protected void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
        int n = GameObjectsStorage.getCountByNpcId((int)36481);
        if (n == 0) {
            SpawnManager.getInstance().despawn(j);
        }
    }

    public void onEvtAggression(Creature creature, int n) {
    }
}
