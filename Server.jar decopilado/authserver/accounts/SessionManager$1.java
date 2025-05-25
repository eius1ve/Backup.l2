/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.accounts;

import java.util.Iterator;
import l2.authserver.accounts.SessionManager;
import l2.commons.threading.RunnableImpl;

class SessionManager.1
extends RunnableImpl {
    SessionManager.1() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void runImpl() {
        SessionManager.this.g.lock();
        try {
            long l = System.currentTimeMillis();
            Iterator<SessionManager.Session> iterator = SessionManager.this.s.values().iterator();
            while (iterator.hasNext()) {
                SessionManager.Session session = iterator.next();
                if (session.getExpireTime() >= l) continue;
                iterator.remove();
            }
        }
        finally {
            SessionManager.this.g.unlock();
        }
    }
}
