/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package events.Finder;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class Finder.ShoutTask
implements Runnable {
    @Override
    public void run() {
        if (!T || o == null || l == null) {
            return;
        }
        Functions.npcShoutCustomMessage((NpcInstance)o, (String)"scripts.events.Finder.HostageShout", (Object[])new Object[0]);
    }
}
