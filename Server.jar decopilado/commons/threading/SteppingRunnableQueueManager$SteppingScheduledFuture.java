/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.threading;

import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SteppingRunnableQueueManager.SteppingScheduledFuture<V>
implements RunnableScheduledFuture<V> {
    private final Runnable b;
    private final long ay;
    private final boolean aR;
    private long az;
    private boolean aS;

    public SteppingRunnableQueueManager.SteppingScheduledFuture(Runnable runnable, long l, long l2, boolean bl) {
        this.b = runnable;
        this.az = l;
        this.ay = l2;
        this.aR = bl;
    }

    @Override
    public void run() {
        if (--this.az == 0L) {
            try {
                this.b.run();
            }
            catch (Throwable throwable) {
                ai.error("Exception in a Runnable execution:", throwable);
            }
            finally {
                if (this.aR) {
                    this.az = this.ay;
                }
            }
        }
    }

    @Override
    public boolean isDone() {
        return this.aS || !this.aR && this.az == 0L;
    }

    @Override
    public boolean isCancelled() {
        return this.aS;
    }

    @Override
    public boolean cancel(boolean bl) {
        this.aS = true;
        return true;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(this.az * SteppingRunnableQueueManager.this.tickPerStepInMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        return 0;
    }

    @Override
    public boolean isPeriodic() {
        return this.aR;
    }
}
