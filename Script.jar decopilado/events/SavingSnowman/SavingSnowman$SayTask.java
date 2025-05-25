/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package events.SavingSnowman;

import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class SavingSnowman.SayTask
implements Runnable {
    @Override
    public void run() {
        if (!T) {
            return;
        }
        Functions.npcSayCustomMessage((NpcInstance)GameObjectsStorage.getByNpcId((int)Config.SAVING_SNOWMAN_EVENT_MANAGER_ID), (String)"scripts.events.SavingSnowman.SantaSay", (Object[])new Object[0]);
    }
}
