/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.util.concurrent.locks;

static final class ReentrantReadWriteLock.HoldCounter {
    int count;
    final long tid = Thread.currentThread().getId();

    ReentrantReadWriteLock.HoldCounter() {
    }

    int tryDecrement() {
        int n = this.count;
        if (n > 0) {
            this.count = n - 1;
        }
        return n;
    }
}
