/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.util.concurrent.locks;

import l2.commons.util.concurrent.locks.ReentrantReadWriteLock;

static final class ReentrantReadWriteLock.ThreadLocalHoldCounter
extends ThreadLocal<ReentrantReadWriteLock.HoldCounter> {
    ReentrantReadWriteLock.ThreadLocalHoldCounter() {
    }

    @Override
    public ReentrantReadWriteLock.HoldCounter initialValue() {
        return new ReentrantReadWriteLock.HoldCounter();
    }
}
