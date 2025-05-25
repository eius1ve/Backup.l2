/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package services;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import org.apache.commons.lang3.mutable.MutableObject;
import services.DoorTools;

static class DoorTools.1
extends RunnableImpl {
    final /* synthetic */ Runnable val$r;
    final /* synthetic */ MutableObject val$futureRef;
    final /* synthetic */ String val$p;

    DoorTools.1(Runnable runnable, MutableObject mutableObject, String string) {
        this.val$r = runnable;
        this.val$futureRef = mutableObject;
        this.val$p = string;
    }

    public void runImpl() throws Exception {
        try {
            this.val$r.run();
        }
        finally {
            this.val$futureRef.setValue((Object)ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), DoorTools.c(this.val$p)));
        }
    }
}
