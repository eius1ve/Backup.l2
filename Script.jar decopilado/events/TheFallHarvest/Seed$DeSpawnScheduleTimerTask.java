/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.SimpleSpawner
 */
package events.TheFallHarvest;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.SimpleSpawner;

public class Seed.DeSpawnScheduleTimerTask
extends RunnableImpl {
    SimpleSpawner spawnedPlant = null;

    public Seed.DeSpawnScheduleTimerTask(SimpleSpawner simpleSpawner) {
        this.spawnedPlant = simpleSpawner;
    }

    public void runImpl() throws Exception {
        this.spawnedPlant.deleteAll();
    }
}
