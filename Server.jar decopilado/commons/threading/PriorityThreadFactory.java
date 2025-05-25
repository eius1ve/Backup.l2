/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.threading;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PriorityThreadFactory
implements ThreadFactory {
    private int eI;
    private String _name;
    private AtomicInteger i = new AtomicInteger(1);
    private ThreadGroup a;

    public PriorityThreadFactory(String string, int n) {
        this.eI = n;
        this._name = string;
        this.a = new ThreadGroup(this._name);
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.a, runnable);
        thread.setName(this._name + "-" + this.i.getAndIncrement());
        thread.setPriority(this.eI);
        return thread;
    }

    public ThreadGroup getGroup() {
        return this.a;
    }
}
