/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

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
import l2.commons.threading.RunnableStatsWrapper;
import l2.gameserver.Config;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ThreadPoolManager {
    private static final long aI = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2L;
    private static final ThreadPoolManager a = new ThreadPoolManager();
    private final ScheduledThreadPoolExecutor b;
    private final ThreadPoolExecutor b = new ThreadPoolExecutor(Config.EXECUTOR_THREAD_POOL_SIZE, Integer.MAX_VALUE, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new PriorityThreadFactory("ThreadPoolExecutor", 5), new LoggingRejectedExecutionHandler());
    private boolean aA;

    public static ThreadPoolManager getInstance() {
        return a;
    }

    private ThreadPoolManager() {
        this.scheduleAtFixedRate(new RunnableImpl(){

            @Override
            public void runImpl() {
                ThreadPoolManager.this.b.purge();
                ThreadPoolManager.this.b.purge();
            }
        }, 300000L, 300000L);
    }

    private long b(long l) {
        return Math.max(0L, Math.min(aI, l));
    }

    public boolean isShutdown() {
        return this.aA;
    }

    public Runnable wrap(Runnable runnable) {
        return Config.ENABLE_RUNNABLE_STATS ? RunnableStatsWrapper.wrap(runnable) : runnable;
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long l) {
        return this.b.schedule(this.wrap(runnable), this.b(l), TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2) {
        return this.b.scheduleAtFixedRate(this.wrap(runnable), this.b(l), this.b(l2), TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> scheduleAtFixedDelay(Runnable runnable, long l, long l2) {
        return this.b.scheduleWithFixedDelay(this.wrap(runnable), this.b(l), this.b(l2), TimeUnit.MILLISECONDS);
    }

    public void execute(Runnable runnable) {
        this.b.execute(this.wrap(runnable));
    }

    public void shutdown() throws InterruptedException {
        this.aA = true;
        try {
            this.b.shutdown();
            this.b.awaitTermination(10L, TimeUnit.SECONDS);
        }
        finally {
            this.b.shutdown();
            this.b.awaitTermination(1L, TimeUnit.MINUTES);
        }
    }

    public List<Runnable> shutdownNow() throws InterruptedException {
        this.aA = true;
        LinkedList<Runnable> linkedList = new LinkedList<Runnable>();
        try {
            linkedList.addAll(this.b.shutdownNow());
        }
        finally {
            linkedList.addAll(this.b.shutdownNow());
        }
        return linkedList;
    }

    public CharSequence getStats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScheduledThreadPool\n");
        stringBuilder.append("=================================================\n");
        stringBuilder.append("\tgetActiveCount: ...... ").append(this.b.getActiveCount()).append("\n");
        stringBuilder.append("\tgetCorePoolSize: ..... ").append(this.b.getCorePoolSize()).append("\n");
        stringBuilder.append("\tgetPoolSize: ......... ").append(this.b.getPoolSize()).append("\n");
        stringBuilder.append("\tgetLargestPoolSize: .. ").append(this.b.getLargestPoolSize()).append("\n");
        stringBuilder.append("\tgetMaximumPoolSize: .. ").append(this.b.getMaximumPoolSize()).append("\n");
        stringBuilder.append("\tgetCompletedTaskCount: ").append(this.b.getCompletedTaskCount()).append("\n");
        stringBuilder.append("\tgetQueuedTaskCount: .. ").append(this.b.getQueue().size()).append("\n");
        stringBuilder.append("\tgetTaskCount: ........ ").append(this.b.getTaskCount()).append("\n");
        stringBuilder.append("ThreadPoolExecutor\n");
        stringBuilder.append("=================================================\n");
        stringBuilder.append("\tgetActiveCount: ...... ").append(this.b.getActiveCount()).append("\n");
        stringBuilder.append("\tgetCorePoolSize: ..... ").append(this.b.getCorePoolSize()).append("\n");
        stringBuilder.append("\tgetPoolSize: ......... ").append(this.b.getPoolSize()).append("\n");
        stringBuilder.append("\tgetLargestPoolSize: .. ").append(this.b.getLargestPoolSize()).append("\n");
        stringBuilder.append("\tgetMaximumPoolSize: .. ").append(this.b.getMaximumPoolSize()).append("\n");
        stringBuilder.append("\tgetCompletedTaskCount: ").append(this.b.getCompletedTaskCount()).append("\n");
        stringBuilder.append("\tgetQueuedTaskCount: .. ").append(this.b.getQueue().size()).append("\n");
        stringBuilder.append("\tgetTaskCount: ........ ").append(this.b.getTaskCount()).append("\n");
        return stringBuilder;
    }
}
