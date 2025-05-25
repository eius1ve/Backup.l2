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

private class _610_MagicalPowerofWater2.UnspawnTask
implements Runnable {
    private _610_MagicalPowerofWater2.UnspawnTask() {
    }

    @Override
    public void run() {
        NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)25316);
        if (npcInstance != null) {
            npcInstance.deleteMe();
            NpcInstance npcInstance2 = _610_MagicalPowerofWater2.this.addSpawn(31560, 105452, -36775, -1050, 34000, 0, 0);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"61051", (Object[])new Object[0]);
        }
        if (_610_MagicalPowerofWater2.this.a != null) {
            _610_MagicalPowerofWater2.this.a.cancel(true);
            _610_MagicalPowerofWater2.this.a = null;
        }
    }
}
