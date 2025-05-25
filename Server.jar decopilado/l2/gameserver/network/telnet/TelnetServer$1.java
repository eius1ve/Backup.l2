/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.telnet;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

class TelnetServer.1
implements ThreadFactory {
    private final AtomicInteger s = new AtomicInteger(1);

    TelnetServer.1() {
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "TelnetServer thread-" + this.s.getAndIncrement());
    }
}
