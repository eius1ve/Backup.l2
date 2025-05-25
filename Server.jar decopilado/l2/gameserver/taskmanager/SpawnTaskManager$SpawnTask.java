/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.model.instances.NpcInstance;

private class SpawnTaskManager.SpawnTask {
    private final HardReference<NpcInstance> ab;
    public long endtime;

    SpawnTaskManager.SpawnTask(NpcInstance npcInstance, long l) {
        this.ab = npcInstance.getRef();
        this.endtime = l;
    }

    public NpcInstance getActor() {
        return this.ab.get();
    }
}
