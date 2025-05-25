/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.accounts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.authserver.ThreadPoolManager;
import l2.authserver.accounts.Account;
import l2.authserver.network.l2.SessionKey;
import l2.commons.threading.RunnableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {
    private static final Logger P = LoggerFactory.getLogger(SessionManager.class);
    private static final SessionManager a = new SessionManager();
    private final Map<SessionKey, Session> s = new HashMap<SessionKey, Session>();
    private final Lock g = new ReentrantLock();

    public static final SessionManager getInstance() {
        return a;
    }

    private SessionManager() {
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new RunnableImpl(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void runImpl() {
                SessionManager.this.g.lock();
                try {
                    long l = System.currentTimeMillis();
                    Iterator<Session> iterator = SessionManager.this.s.values().iterator();
                    while (iterator.hasNext()) {
                        Session session = iterator.next();
                        if (session.getExpireTime() >= l) continue;
                        iterator.remove();
                    }
                }
                finally {
                    SessionManager.this.g.unlock();
                }
            }
        }, 30000L, 30000L);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Session openSession(Account account) {
        this.g.lock();
        try {
            Session session = new Session(account);
            this.s.put(session.getSessionKey(), session);
            Session session2 = session;
            return session2;
        }
        finally {
            this.g.unlock();
        }
    }

    public Session closeSession(SessionKey sessionKey) {
        this.g.lock();
        try {
            Session session = this.s.remove(sessionKey);
            return session;
        }
        finally {
            this.g.unlock();
        }
    }

    public Session getSessionByName(String string) {
        for (Session session : this.s.values()) {
            if (!session.a.getLogin().equalsIgnoreCase(string)) continue;
            return session;
        }
        return null;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public final class Session {
        private final Account a;
        private final SessionKey a;
        private final long ak;

        private Session(Account account) {
            this.a = account;
            this.a = SessionKey.create();
            this.ak = System.currentTimeMillis() + 60000L;
        }

        public SessionKey getSessionKey() {
            return this.a;
        }

        public Account getAccount() {
            return this.a;
        }

        public long getExpireTime() {
            return this.ak;
        }
    }
}
