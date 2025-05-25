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

private class _604_DaimontheWhiteEyedPart2.UnspawnTask
implements Runnable {
    private _604_DaimontheWhiteEyedPart2.UnspawnTask() {
    }

    @Override
    public void run() {
        NpcInstance npcInstance = GameObjectsStorage.getByNpcId((int)25290);
        if (npcInstance != null) {
            npcInstance.deleteMe();
            NpcInstance npcInstance2 = _604_DaimontheWhiteEyedPart2.this.addSpawn(31541, 186304, -43744, -3193, 57000, 0, 0);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance2, (String)"60404", (Object[])new Object[0]);
        }
        if (_604_DaimontheWhiteEyedPart2.this.a != null) {
            _604_DaimontheWhiteEyedPart2.this.a.cancel(true);
            _604_DaimontheWhiteEyedPart2.this.a = null;
        }
    }
}
