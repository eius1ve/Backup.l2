/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package events.SavingSnowman;

import events.SavingSnowman.SavingSnowman;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class SavingSnowman.ShoutTask
implements Runnable {
    @Override
    public void run() {
        if (!T || r == null || _snowmanState != SavingSnowman.SnowmanState.CAPTURED) {
            return;
        }
        Functions.npcShoutCustomMessage((NpcInstance)r, (String)"scripts.events.SavingSnowman.SnowmanShout", (Object[])new Object[0]);
    }
}
