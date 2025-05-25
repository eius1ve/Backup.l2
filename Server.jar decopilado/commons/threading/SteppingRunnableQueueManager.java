/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.mutable.MutableLong
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.commons.threading;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import l2.commons.collections.LazyArrayList;
import org.apache.commons.lang3.mutable.MutableLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SteppingRunnableQueueManager
implements Runnable {
    private static final Logger ai = LoggerFactory.getLogger(SteppingRunnableQueueManager.class);
    protected final long tickPerStepInMillis;
    private final List<SteppingScheduledFuture<?>> X = new CopyOnWriteArrayList();
    private final AtomicBoolean d = new AtomicBoolean();

    public SteppingRunnableQueueManager(long l) {
        this.tickPerStepInMillis = l;
    }

    public SteppingScheduledFuture<?> schedule(Runnable runnable, long l) {
        return this.a(runnable, l, l, false);
    }

    public SteppingScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2) {
        return this.a(runnable, l, l2, true);
    }

    private SteppingScheduledFuture<?> a(Runnable runnable, long l, long l2, boolean bl) {
        long l3 = this.c(l);
        long l4 = this.c(l2);
        SteppingScheduledFuture steppingScheduledFuture = new SteppingScheduledFuture(runnable, l3, l4, bl);
        this.X.add(steppingScheduledFuture);
        return steppingScheduledFuture;
    }

    private long c(long l) {
        return (l = Math.max(0L, l)) % this.tickPerStepInMillis > this.tickPerStepInMillis / 2L ? l / this.tickPerStepInMillis + 1L : (l < this.tickPerStepInMillis ? 1L : l / this.tickPerStepInMillis);
    }

    @Override
    public void run() {
        try {
            if (!this.d.compareAndSet(false, true)) {
                ai.warn("Slow running queue, managed by " + this + ", queue size : " + this.X.size() + "!");
                return;
            }
            try {
                if (this.X.isEmpty()) {
                    return;
                }
                for (SteppingScheduledFuture<?> steppingScheduledFuture : this.X) {
                    if (steppingScheduledFuture.isDone()) continue;
                    steppingScheduledFuture.run();
                }
            }
            finally {
                this.d.set(false);
            }
        }
        catch (Throwable throwable) {
            ai.error("Exception in stepped queue manager", throwable);
        }
    }

    public void purge() {
        LazyArrayList<SteppingScheduledFuture<?>> lazyArrayList = LazyArrayList.newInstance();
        for (SteppingScheduledFuture<?> steppingScheduledFuture : this.X) {
            if (!steppingScheduledFuture.isDone()) continue;
            lazyArrayList.add(steppingScheduledFuture);
        }
        this.X.removeAll(lazyArrayList);
        LazyArrayList.recycle(lazyArrayList);
    }

    public CharSequence getStats() {
        StringBuilder stringBuilder = new StringBuilder();
        TreeMap<String, MutableLong> treeMap = new TreeMap<String, MutableLong>();
        int n = 0;
        int n2 = 0;
        for (SteppingScheduledFuture<?> steppingScheduledFuture : this.X) {
            if (steppingScheduledFuture.isDone()) {
                ++n2;
                continue;
            }
            ++n;
            MutableLong mutableLong = (MutableLong)treeMap.get(steppingScheduledFuture.b.getClass().getName());
            if (mutableLong == null) {
                mutableLong = new MutableLong(1L);
                treeMap.put(steppingScheduledFuture.b.getClass().getName(), mutableLong);
                continue;
            }
            mutableLong.increment();
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            stringBuilder.append("\t").append((String)entry.getKey()).append(" : ").append(((MutableLong)entry.getValue()).longValue()).append("\n");
        }
        stringBuilder.append("Scheduled: ....... ").append(n).append("\n");
        stringBuilder.append("Done/Cancelled: .. ").append(n2).append("\n");
        return stringBuilder;
    }

    public class SteppingScheduledFuture<V>
    implements RunnableScheduledFuture<V> {
        private final Runnable b;
        private final long ay;
        private final boolean aR;
        private long az;
        private boolean aS;

        public SteppingScheduledFuture(Runnable runnable, long l, long l2, boolean bl) {
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
}
