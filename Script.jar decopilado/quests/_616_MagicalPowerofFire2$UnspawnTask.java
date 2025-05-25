/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package quests;

import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

private class _616_MagicalPowerofFire2.UnspawnTask
implements Runnable {
    private _616_MagicalPowerofFire2.UnspawnTask() {
    }

    @Override
    public void run() {
        NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)25306);
        if (npcInstance != null) {
            npcInstance.deleteMe();
            NpcInstance npcInstance2 = _616_MagicalPowerofFire2.this.addSpawn(31558, 142368, -82512, -6487, 58000, 0, 0);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"61651", (Object[])new Object[0]);
        }
        if (_616_MagicalPowerofFire2.this.a != null) {
            _616_MagicalPowerofFire2.this.a.cancel(true);
            _616_MagicalPowerofFire2.this.a = null;
        }
    }
}
