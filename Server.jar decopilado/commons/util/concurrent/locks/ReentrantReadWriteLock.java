/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.util.concurrent.locks;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class ReentrantReadWriteLock {
    private static final AtomicIntegerFieldUpdater<ReentrantReadWriteLock> c = AtomicIntegerFieldUpdater.newUpdater(ReentrantReadWriteLock.class, "state");
    static final int SHARED_SHIFT = 16;
    static final int SHARED_UNIT = 65536;
    static final int MAX_COUNT = 65535;
    static final int EXCLUSIVE_MASK = 65535;
    transient ThreadLocalHoldCounter readHolds = new ThreadLocalHoldCounter();
    transient HoldCounter cachedHoldCounter;
    private Thread a;
    private volatile int state;

    static int sharedCount(int n) {
        return n >>> 16;
    }

    static int exclusiveCount(int n) {
        return n & 0xFFFF;
    }

    public ReentrantReadWriteLock() {
        this.b(0);
    }

    private final int r() {
        return this.state;
    }

    private void b(int n) {
        this.state = n;
    }

    private boolean b(int n, int n2) {
        return c.compareAndSet(this, n, n2);
    }

    private Thread a() {
        return this.a;
    }

    private void a(Thread thread) {
        this.a = thread;
    }

    public void writeLock() {
        Thread thread = Thread.currentThread();
        while (true) {
            int n = this.r();
            int n2 = ReentrantReadWriteLock.exclusiveCount(n);
            if (n != 0) {
                if (n2 == 0 || thread != this.a()) continue;
                if (n2 + ReentrantReadWriteLock.exclusiveCount(1) > 65535) {
                    throw new Error("Maximum lock count exceeded");
                }
            }
            if (this.b(n, n + 1)) break;
        }
        this.a(thread);
    }

    public boolean tryWriteLock() {
        Thread thread = Thread.currentThread();
        int n = this.r();
        if (n != 0) {
            int n2 = ReentrantReadWriteLock.exclusiveCount(n);
            if (n2 == 0 || thread != this.a()) {
                return false;
            }
            if (n2 == 65535) {
                throw new Error("Maximum lock count exceeded");
            }
        }
        if (!this.b(n, n + 1)) {
            return false;
        }
        this.a(thread);
        return true;
    }

    final boolean tryReadLock() {
        Thread thread = Thread.currentThread();
        int n = this.r();
        int n2 = ReentrantReadWriteLock.exclusiveCount(n);
        if (n2 != 0 && this.a() != thread) {
            return false;
        }
        if (ReentrantReadWriteLock.sharedCount(n) == 65535) {
            throw new Error("Maximum lock count exceeded");
        }
        if (this.b(n, n + 65536)) {
            HoldCounter holdCounter = this.cachedHoldCounter;
            if (holdCounter == null || holdCounter.tid != thread.getId()) {
                this.cachedHoldCounter = holdCounter = (HoldCounter)this.readHolds.get();
            }
            ++holdCounter.count;
            return true;
        }
        return false;
    }

    public void readLock() {
        Thread thread = Thread.currentThread();
        HoldCounter holdCounter = this.cachedHoldCounter;
        if (holdCounter == null || holdCounter.tid != thread.getId()) {
            holdCounter = (HoldCounter)this.readHolds.get();
        }
        while (true) {
            int n;
            int n2;
            if ((n2 = ReentrantReadWriteLock.exclusiveCount(n = this.r())) != 0 && this.a() != thread) {
                continue;
            }
            if (ReentrantReadWriteLock.sharedCount(n) == 65535) {
                throw new Error("Maximum lock count exceeded");
            }
            if (this.b(n, n + 65536)) break;
        }
        this.cachedHoldCounter = holdCounter;
        ++holdCounter.count;
    }

    public void writeUnlock() {
        int n = this.r() - 1;
        if (Thread.currentThread() != this.a()) {
            throw new IllegalMonitorStateException();
        }
        if (ReentrantReadWriteLock.exclusiveCount(n) == 0) {
            this.a(null);
            this.b(n);
            return;
        }
        this.b(n);
    }

    public void readUnlock() {
        int n;
        int n2;
        HoldCounter holdCounter = this.cachedHoldCounter;
        Thread thread = Thread.currentThread();
        if (holdCounter == null || holdCounter.tid != thread.getId()) {
            holdCounter = (HoldCounter)this.readHolds.get();
        }
        if (holdCounter.tryDecrement() <= 0) {
            throw new IllegalMonitorStateException();
        }
        while (!this.b(n2 = this.r(), n = n2 - 65536)) {
        }
    }

    static final class ThreadLocalHoldCounter
    extends ThreadLocal<HoldCounter> {
        ThreadLocalHoldCounter() {
        }

        @Override
        public HoldCounter initialValue() {
            return new HoldCounter();
        }
    }

    static final class HoldCounter {
        int count;
        final long tid = Thread.currentThread().getId();

        HoldCounter() {
        }

        int tryDecrement() {
            int n = this.count;
            if (n > 0) {
                this.count = n - 1;
            }
            return n;
        }
    }
}
