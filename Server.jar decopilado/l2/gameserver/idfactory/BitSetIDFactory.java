/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.idfactory;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.math.PrimeFinder;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.idfactory.IdFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitSetIDFactory
extends IdFactory {
    private static final Logger bk = LoggerFactory.getLogger(BitSetIDFactory.class);
    private BitSet a;
    private AtomicInteger j;
    private AtomicInteger k;

    protected BitSetIDFactory() {
        this.aw();
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new BitSetCapacityCheck(), 30000L, 30000L);
    }

    private void aw() {
        try {
            this.a = new BitSet(PrimeFinder.nextPrime(100000));
            this.a.clear();
            this.j = new AtomicInteger(0x6FFFFFFF);
            for (int n : this.extractUsedObjectIDTable()) {
                int n2 = n - 0x10000000;
                if (n2 < 0) {
                    bk.warn("Object ID " + n + " in DB is less than minimum ID of 268435456");
                    continue;
                }
                this.a.set(n - 0x10000000);
                this.j.decrementAndGet();
            }
            this.k = new AtomicInteger(this.a.nextClearBit(0));
            this.initialized = true;
            bk.info("IdFactory: " + this.a.size() + " id's available.");
        }
        catch (Exception exception) {
            this.initialized = false;
            bk.error("BitSet ID Factory could not be initialized correctly!", (Throwable)exception);
        }
    }

    @Override
    public synchronized void releaseId(int n) {
        if (n - 0x10000000 > -1) {
            this.a.clear(n - 0x10000000);
            this.j.incrementAndGet();
            super.releaseId(n);
        } else {
            bk.warn("BitSet ID Factory: release objectID " + n + " failed (< 268435456)");
        }
    }

    @Override
    public synchronized int getNextId() {
        int n = this.k.get();
        this.a.set(n);
        this.j.decrementAndGet();
        int n2 = this.a.nextClearBit(n);
        if (n2 < 0) {
            n2 = this.a.nextClearBit(0);
        }
        if (n2 < 0) {
            if (this.a.size() < 0x6FFFFFFF) {
                this.increaseBitSetCapacity();
            } else {
                throw new NullPointerException("Ran out of valid Id's.");
            }
        }
        this.k.set(n2);
        return n + 0x10000000;
    }

    @Override
    public synchronized int size() {
        return this.j.get();
    }

    protected synchronized int usedIdCount() {
        return this.size() - 0x10000000;
    }

    protected synchronized boolean reachingBitSetCapacity() {
        return PrimeFinder.nextPrime(this.usedIdCount() * 11 / 10) > this.a.size();
    }

    protected synchronized void increaseBitSetCapacity() {
        BitSet bitSet = new BitSet(PrimeFinder.nextPrime(this.usedIdCount() * 11 / 10));
        bitSet.or(this.a);
        this.a = bitSet;
    }

    public class BitSetCapacityCheck
    extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            if (BitSetIDFactory.this.reachingBitSetCapacity()) {
                BitSetIDFactory.this.increaseBitSetCapacity();
            }
        }
    }
}
