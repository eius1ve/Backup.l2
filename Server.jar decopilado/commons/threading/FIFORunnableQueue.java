/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.threading;

import java.util.Queue;

public abstract class FIFORunnableQueue<T extends Runnable>
implements Runnable {
    private static final int eF = 0;
    private static final int eG = 1;
    private static final int eH = 2;
    private int _state = 0;
    private final Queue<T> e;

    public FIFORunnableQueue(Queue<T> queue) {
        this.e = queue;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(T t) {
        this.e.add(t);
        FIFORunnableQueue fIFORunnableQueue = this;
        synchronized (fIFORunnableQueue) {
            if (this._state != 0) {
                return;
            }
            this._state = 1;
        }
        this.execute();
    }

    protected abstract void execute();

    public void clear() {
        this.e.clear();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Runnable runnable = this;
        synchronized (runnable) {
            if (this._state == 2) {
                return;
            }
            this._state = 2;
        }
        try {
            while ((runnable = (Runnable)this.e.poll()) != null) {
                runnable.run();
            }
        }
        finally {
            runnable = this;
            synchronized (runnable) {
                this._state = 0;
            }
        }
    }
}
