/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.commons.threading;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggingRejectedExecutionHandler
implements RejectedExecutionHandler {
    private static final Logger ag = LoggerFactory.getLogger(LoggingRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        if (threadPoolExecutor.isShutdown()) {
            return;
        }
        ag.error(runnable + " from " + threadPoolExecutor, (Throwable)new RejectedExecutionException());
    }
}
