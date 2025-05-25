/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import l2.commons.threading.LoggingRejectedExecutionHandler;
import l2.commons.threading.PriorityThreadFactory;
import l2.commons.threading.RunnableImpl;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ThreadPoolManager {
    private static final long aj = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2L;
    private static final ThreadPoolManager a = new ThreadPoolManager();
    private final ScheduledThreadPoolExecutor a;
    private final ThreadPoolExecutor a = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Integer.MAX_VALUE, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new PriorityThreadFactory("ThreadPoolExecutor", 5), new LoggingRejectedExecutionHandler());

    public static final ThreadPoolManager getInstance() {
        return a;
    }

    private ThreadPoolManager() {
        this.scheduleAtFixedRate(new RunnableImpl(){

            @Override
            public void runImpl() {
                ThreadPoolManager.this.a.purge();
                ThreadPoolManager.this.a.purge();
            }
        }, 600000L, 600000L);
    }

    private final long b(long l) {
        return Math.max(0L, Math.min(aj, l));
    }

    public void execute(Runnable runnable) {
        this.a.execute(runnable);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long l) {
        return this.a.schedule(runnable, this.b(l), TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2) {
        return this.a.scheduleAtFixedRate(runnable, this.b(l), this.b(l2), TimeUnit.MILLISECONDS);
    }

    public void shutdown() throws InterruptedException {
        try {
            this.a.shutdown();
            this.a.awaitTermination(1L, TimeUnit.SECONDS);
        }
        finally {
            this.a.shutdown();
            this.a.awaitTermination(1L, TimeUnit.SECONDS);
        }
    }

    public List<Runnable> shutdownNow() throws InterruptedException {
        LinkedList<Runnable> linkedList = new LinkedList<Runnable>();
        try {
            linkedList.addAll(this.a.shutdownNow());
        }
        finally {
            linkedList.addAll(this.a.shutdownNow());
        }
        return linkedList;
    }
}
