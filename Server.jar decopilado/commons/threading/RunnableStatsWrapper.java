/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.commons.threading;

import l2.commons.threading.RunnableStatsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnableStatsWrapper
implements Runnable {
    private static final Logger ah = LoggerFactory.getLogger(RunnableStatsWrapper.class);
    private final Runnable a;

    RunnableStatsWrapper(Runnable runnable) {
        this.a = runnable;
    }

    public static Runnable wrap(Runnable runnable) {
        return new RunnableStatsWrapper(runnable);
    }

    @Override
    public void run() {
        RunnableStatsWrapper.execute(this.a);
    }

    public static void execute(Runnable runnable) {
        long l = System.nanoTime();
        try {
            runnable.run();
            RunnableStatsManager.getInstance().handleStats(runnable.getClass(), System.nanoTime() - l);
        }
        catch (Exception exception) {
            ah.error("Exception in a Runnable execution:", (Throwable)exception);
        }
    }
}
