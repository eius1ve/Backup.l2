/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import l2.authserver.network.l2.L2LoginClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ClientManager {
    private static final Logger J = LoggerFactory.getLogger(ClientManager.class);
    private static final long ah = 10000L;
    private static final ClientManager a = new ClientManager();
    private final ReadWriteLock a;
    private final Lock a = this.a.readLock();
    private final Lock b = this.a.writeLock();
    private final List<ClientHolder> O = new ArrayList<ClientHolder>();
    private final AtomicInteger e = new AtomicInteger(0);

    public static final ClientManager getInstance() {
        return a;
    }

    private ClientManager() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(long l) {
        long l2 = System.currentTimeMillis();
        CopyOnWriteArrayList<ClientHolder> copyOnWriteArrayList = new CopyOnWriteArrayList<ClientHolder>();
        this.a.lock();
        try {
            for (ClientHolder clientHolder : this.O) {
                if (clientHolder.getAddTime() >= l2 - l) continue;
                copyOnWriteArrayList.add(clientHolder);
            }
        }
        finally {
            this.a.unlock();
        }
        if (!copyOnWriteArrayList.isEmpty()) {
            this.b.lock();
            try {
                this.O.removeAll(copyOnWriteArrayList);
            }
            finally {
                this.b.unlock();
            }
        }
        for (ClientHolder clientHolder : copyOnWriteArrayList) {
            this.a(clientHolder);
        }
    }

    private void a(ClientHolder clientHolder) {
        L2LoginClient l2LoginClient = clientHolder.getLoginClient();
        if (l2LoginClient == null) {
            return;
        }
        if (l2LoginClient.getConnection() != null && l2LoginClient.isConnected()) {
            l2LoginClient.closeNow(false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int addClient(L2LoginClient l2LoginClient) {
        long l = System.currentTimeMillis();
        this.a(10000L);
        int n = this.e.incrementAndGet();
        ClientHolder clientHolder = new ClientHolder(l2LoginClient, l, n);
        this.b.lock();
        try {
            this.O.add(clientHolder);
        }
        finally {
            this.b.unlock();
        }
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public L2LoginClient removeClientByCookieId(int n) {
        ClientHolder clientHolder = null;
        this.a.lock();
        try {
            for (ClientHolder clientHolder2 : this.O) {
                if (clientHolder2.getCookieId() != n) continue;
                clientHolder = clientHolder2;
            }
        }
        finally {
            this.a.unlock();
        }
        if (clientHolder == null) {
            return null;
        }
        this.b.lock();
        try {
            this.O.remove(clientHolder);
        }
        finally {
            this.b.unlock();
        }
        return clientHolder.getLoginClient();
    }

    private static class ClientHolder {
        private L2LoginClient a;
        private final long ai;
        private final int cZ;

        private ClientHolder(L2LoginClient l2LoginClient, long l, int n) {
            this.a = l2LoginClient;
            this.ai = l;
            this.cZ = n;
        }

        public L2LoginClient getLoginClient() {
            return this.a;
        }

        public ClientHolder setLoginClient(L2LoginClient l2LoginClient) {
            this.a = l2LoginClient;
            return this;
        }

        public long getAddTime() {
            return this.ai;
        }

        public int getCookieId() {
            return this.cZ;
        }
    }
}
