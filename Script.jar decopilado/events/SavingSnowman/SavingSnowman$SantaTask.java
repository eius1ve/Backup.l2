/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package events.SavingSnowman;

import ai.FlyingSantaAI;
import l2.gameserver.Config;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class SavingSnowman.SantaTask
implements Runnable {
    @Override
    public void run() {
        if (!T) {
            return;
        }
        for (Location location : h) {
            NpcInstance npcInstance = NpcHolder.getInstance().getTemplate(Config.SAVING_SNOWMAN_EVENT_FLYING_SANTA_ID).getNewInstance();
            npcInstance.setAI((CharacterAI)new FlyingSantaAI(npcInstance));
            npcInstance.spawnMe(location);
            npcInstance.setSpawnedLoc(location);
        }
    }
}
