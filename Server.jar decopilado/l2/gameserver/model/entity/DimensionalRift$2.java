/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.DimensionalRiftManager;
import l2.gameserver.model.SimpleSpawner;

class DimensionalRift.2
extends RunnableImpl {
    final /* synthetic */ DimensionalRiftManager.DimensionalRiftRoom val$riftRoom;

    DimensionalRift.2(DimensionalRiftManager.DimensionalRiftRoom dimensionalRiftRoom) {
        this.val$riftRoom = dimensionalRiftRoom;
    }

    @Override
    public void runImpl() throws Exception {
        for (SimpleSpawner simpleSpawner : this.val$riftRoom.getSpawns()) {
            SimpleSpawner simpleSpawner2 = simpleSpawner.clone();
            simpleSpawner2.setReflection(DimensionalRift.this);
            DimensionalRift.this.addSpawn(simpleSpawner2);
            if (!DimensionalRift.this.isBossRoom) {
                simpleSpawner2.startRespawn();
            }
            for (int i = 0; i < simpleSpawner2.getAmount(); ++i) {
                simpleSpawner2.doSpawn(true);
            }
        }
        DimensionalRift.this.addSpawnWithoutRespawn(DimensionalRift.this.getManagerId(), this.val$riftRoom.getTeleportCoords(), 0);
    }
}
