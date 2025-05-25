/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package zones;

import java.util.function.LongSupplier;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import org.apache.commons.lang3.mutable.MutableObject;

static class CronZoneSwitcher.1
extends RunnableImpl {
    final /* synthetic */ Runnable val$r;
    final /* synthetic */ MutableObject val$futureRef;
    final /* synthetic */ LongSupplier val$remainingMills;

    CronZoneSwitcher.1(Runnable runnable, MutableObject mutableObject, LongSupplier longSupplier) {
        this.val$r = runnable;
        this.val$futureRef = mutableObject;
        this.val$remainingMills = longSupplier;
    }

    public void runImpl() throws Exception {
        try {
            this.val$r.run();
        }
        catch (Throwable throwable) {
            eA.error(throwable.getMessage(), throwable);
        }
        finally {
            this.val$futureRef.setValue((Object)ThreadPoolManager.getInstance().schedule((Runnable)((Object)this), this.val$remainingMills.getAsLong()));
        }
    }
}
