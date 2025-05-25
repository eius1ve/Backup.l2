/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import java.util.concurrent.atomic.AtomicLong;

public class SelectorStats {
    private AtomicLong a = new AtomicLong();
    private AtomicLong b = new AtomicLong();
    private AtomicLong c = new AtomicLong();
    private AtomicLong d = new AtomicLong();
    private AtomicLong e = new AtomicLong();
    private AtomicLong f = new AtomicLong();
    private AtomicLong g = new AtomicLong();
    private AtomicLong h = new AtomicLong();
    private AtomicLong i = new AtomicLong();

    public void increaseOpenedConnections() {
        if (this.b.incrementAndGet() > this.c.get()) {
            this.c.incrementAndGet();
        }
        this.a.incrementAndGet();
    }

    public void decreseOpenedConnections() {
        this.b.decrementAndGet();
    }

    public void increaseIncomingBytes(int n) {
        if ((long)n > this.h.get()) {
            this.h.set(n);
        }
        this.d.addAndGet(n);
    }

    public void increaseOutgoingBytes(int n) {
        if ((long)n > this.i.get()) {
            this.i.set(n);
        }
        this.e.addAndGet(n);
    }

    public void increaseIncomingPacketsCount() {
        this.f.incrementAndGet();
    }

    public void increaseOutgoingPacketsCount() {
        this.g.incrementAndGet();
    }

    public long getTotalConnections() {
        return this.a.get();
    }

    public long getCurrentConnections() {
        return this.b.get();
    }

    public long getMaximumConnections() {
        return this.c.get();
    }

    public long getIncomingBytesTotal() {
        return this.d.get();
    }

    public long getOutgoingBytesTotal() {
        return this.e.get();
    }

    public long getIncomingPacketsTotal() {
        return this.f.get();
    }

    public long getOutgoingPacketsTotal() {
        return this.g.get();
    }

    public long getMaxBytesPerRead() {
        return this.h.get();
    }

    public long getMaxBytesPerWrite() {
        return this.i.get();
    }
}
