/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.net.nio.impl;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.net.nio.impl.IMMOExecutor;
import l2.commons.net.nio.impl.MMOClient;
import l2.commons.net.nio.impl.ReceivablePacket;

public class MMOExecutableQueue<T extends MMOClient>
implements Runnable,
Queue<ReceivablePacket<T>> {
    private static final int er = 0;
    private static final int es = 1;
    private static final int et = 2;
    private final IMMOExecutor<T> a;
    private final Queue<ReceivablePacket<T>> c;
    private AtomicInteger h = new AtomicInteger(0);

    public MMOExecutableQueue(IMMOExecutor<T> iMMOExecutor) {
        this.a = iMMOExecutor;
        this.c = new ArrayDeque<ReceivablePacket<T>>();
    }

    @Override
    public void run() {
        while (this.h.compareAndSet(1, 2)) {
            try {
                Object object;
                while ((object = this.poll()) != null) {
                    object.run();
                }
            }
            finally {
                this.h.compareAndSet(2, 0);
            }
        }
    }

    @Override
    public int size() {
        return this.c.size();
    }

    @Override
    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<ReceivablePacket<T>> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <E> E[] toArray(E[] EArray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends ReceivablePacket<T>> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        Queue<ReceivablePacket<T>> queue = this.c;
        synchronized (queue) {
            this.c.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean add(ReceivablePacket<T> receivablePacket) {
        Queue<ReceivablePacket<T>> queue = this.c;
        synchronized (queue) {
            if (!this.c.add(receivablePacket)) {
                return false;
            }
        }
        if (this.h.getAndSet(1) == 0) {
            this.a.execute(this);
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean offer(ReceivablePacket<T> receivablePacket) {
        Queue<ReceivablePacket<T>> queue = this.c;
        synchronized (queue) {
            return this.c.offer(receivablePacket);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ReceivablePacket<T> remove() {
        Queue<ReceivablePacket<T>> queue = this.c;
        synchronized (queue) {
            return this.c.remove();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ReceivablePacket<T> poll() {
        Queue<ReceivablePacket<T>> queue = this.c;
        synchronized (queue) {
            return this.c.poll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ReceivablePacket<T> element() {
        Queue<ReceivablePacket<T>> queue = this.c;
        synchronized (queue) {
            return this.c.element();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ReceivablePacket<T> peek() {
        Queue<ReceivablePacket<T>> queue = this.c;
        synchronized (queue) {
            return this.c.peek();
        }
    }
}
