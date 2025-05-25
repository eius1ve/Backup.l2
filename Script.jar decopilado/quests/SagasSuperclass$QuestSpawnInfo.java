/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.gameserver.model.instances.NpcInstance
 */
package quests;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.model.instances.NpcInstance;

private class SagasSuperclass.QuestSpawnInfo {
    public final int npcId;
    private HardReference<NpcInstance> ab;

    public SagasSuperclass.QuestSpawnInfo(NpcInstance npcInstance) {
        this.npcId = npcInstance.getNpcId();
        this.ab = npcInstance.getRef();
    }

    public NpcInstance getNpc() {
        return (NpcInstance)this.ab.get();
    }
}
