/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.util.concurrent.locks;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class ReentrantLock {
    private static final AtomicIntegerFieldUpdater<ReentrantLock> b = AtomicIntegerFieldUpdater.newUpdater(ReentrantLock.class, "state");
    private Thread a;
    private volatile int state;

    private final int getState() {
        return this.state;
    }

    private void b(int n) {
        this.state = n;
    }

    private boolean b(int n, int n2) {
        return b.compareAndSet(this, n, n2);
    }

    private Thread a() {
        return this.a;
    }

    private void a(Thread thread) {
        this.a = thread;
    }

    public void lock() {
        if (this.b(0, 1)) {
            this.a(Thread.currentThread());
        } else {
            while (!this.tryLock()) {
            }
        }
    }

    public boolean tryLock() {
        Thread thread = Thread.currentThread();
        int n = this.getState();
        if (n == 0) {
            if (this.b(0, 1)) {
                this.a(thread);
                return true;
            }
        } else if (thread == this.a()) {
            int n2 = n + 1;
            if (n2 < 0) {
                throw new Error("Maximum lock count exceeded");
            }
            this.b(n2);
            return true;
        }
        return false;
    }

    public boolean unlock() {
        int n = this.getState() - 1;
        if (Thread.currentThread() != this.a()) {
            throw new IllegalMonitorStateException();
        }
        boolean bl = false;
        if (n == 0) {
            bl = true;
            this.a(null);
        }
        this.b(n);
        return bl;
    }
}
