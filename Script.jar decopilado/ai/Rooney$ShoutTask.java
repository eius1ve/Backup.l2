/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

private final class Rooney.ShoutTask
extends RunnableImpl {
    private int ah = 0;

    private Rooney.ShoutTask() {
    }

    public final void runImpl() {
        if (this.ah < i.length) {
            Functions.npcShoutCustomMessage((NpcInstance)Rooney.this.getActor(), (String)i[this.ah], (Object[])new Object[0]);
            ++this.ah;
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), 120000L);
        }
    }
}
