/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 */
package instances;

import instances.Frintezza;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;

private class Frintezza.FrintezzaStart
extends RunnableImpl {
    private Frintezza.FrintezzaStart() {
    }

    public void runImpl() throws Exception {
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Spawn(Frintezza.this, 1)), 1000L);
    }
}
