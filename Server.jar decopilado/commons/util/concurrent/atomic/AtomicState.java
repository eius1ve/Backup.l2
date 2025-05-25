/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.util.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicState {
    private static final AtomicIntegerFieldUpdater<AtomicState> a = AtomicIntegerFieldUpdater.newUpdater(AtomicState.class, "value");
    private volatile int value;

    public AtomicState(boolean bl) {
        this.value = bl ? 1 : 0;
    }

    public AtomicState() {
    }

    public final boolean get() {
        return this.value != 0;
    }

    private boolean b(int n) {
        if (n < 0) {
            throw new IllegalStateException();
        }
        return n > 0;
    }

    public final boolean setAndGet(boolean bl) {
        if (bl) {
            return this.b(a.incrementAndGet(this));
        }
        return this.b(a.decrementAndGet(this));
    }

    public final boolean getAndSet(boolean bl) {
        if (bl) {
            return this.b(a.getAndIncrement(this));
        }
        return this.b(a.getAndDecrement(this));
    }
}
