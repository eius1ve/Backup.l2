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

private class _625_TheFinestIngredientsPart2.UnspawnTask
implements Runnable {
    private _625_TheFinestIngredientsPart2.UnspawnTask() {
    }

    @Override
    public void run() {
        NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)25296);
        if (npcInstance != null) {
            npcInstance.deleteMe();
            NpcInstance npcInstance2 = _625_TheFinestIngredientsPart2.this.addSpawn(31542, 157136, -121456, -2363, 40000, 0, 0);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"61651", (Object[])new Object[0]);
        }
        if (_625_TheFinestIngredientsPart2.this.a != null) {
            _625_TheFinestIngredientsPart2.this.a.cancel(true);
            _625_TheFinestIngredientsPart2.this.a = null;
        }
    }
}
