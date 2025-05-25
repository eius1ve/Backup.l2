/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package ai.isle_of_prayer;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

private class FafurionKindred.SpawnTask
extends RunnableImpl {
    private final int ba;

    public FafurionKindred.SpawnTask(int n) {
        this.ba = n;
    }

    public void runImpl() {
        NpcInstance npcInstance = FafurionKindred.this.getActor();
        SimpleSpawner simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(this.ba));
        simpleSpawner.setLoc(Location.findPointToStay((GameObject)npcInstance, (int)100, (int)120));
        simpleSpawner.setRespawnDelay(30L, 40L);
        simpleSpawner.doSpawn(true);
        FafurionKindred.this.spawns.add(simpleSpawner);
    }
}
