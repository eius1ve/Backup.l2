/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver;

import java.util.Iterator;
import l2.authserver.Config;
import l2.authserver.IpBanManager;

class IpBanManager.1
implements Runnable {
    IpBanManager.1() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        long l = System.currentTimeMillis();
        IpBanManager.this.f.lock();
        try {
            Iterator<IpBanManager.IpSession> iterator = IpBanManager.this.r.values().iterator();
            while (iterator.hasNext()) {
                IpBanManager.IpSession ipSession = iterator.next();
                if (ipSession.banExpire >= l || ipSession.lastTry >= l - Config.LOGIN_TRY_TIMEOUT) continue;
                iterator.remove();
            }
        }
        finally {
            IpBanManager.this.f.unlock();
        }
    }
}
