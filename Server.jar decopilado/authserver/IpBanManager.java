/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import l2.authserver.Config;
import l2.authserver.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IpBanManager {
    private static final Logger M = LoggerFactory.getLogger(IpBanManager.class);
    private static final IpBanManager a = new IpBanManager();
    private final Map<String, IpSession> r = new ConcurrentHashMap<String, IpSession>();
    private final ReadWriteLock c = new ReentrantReadWriteLock();
    private final Lock e;
    private final Lock f;
    private final Set<String> e = this.c.readLock();

    public static final IpBanManager getInstance() {
        return a;
    }

    public void addBlackListIp(String string) {
        if (this.e.size() % 1000 == 0) {
            M.info("IpBanManager: Blacklisted ip(s): " + this.e.size());
        }
        this.e.add(string);
    }

    private IpBanManager() {
        this.f = this.c.writeLock();
        this.e = new ConcurrentSkipListSet();
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                long l = System.currentTimeMillis();
                IpBanManager.this.f.lock();
                try {
                    Iterator<IpSession> iterator = IpBanManager.this.r.values().iterator();
                    while (iterator.hasNext()) {
                        IpSession ipSession = iterator.next();
                        if (ipSession.banExpire >= l || ipSession.lastTry >= l - Config.LOGIN_TRY_TIMEOUT) continue;
                        iterator.remove();
                    }
                }
                finally {
                    IpBanManager.this.f.unlock();
                }
            }
        }, 1000L, 1000L);
        if (Config.BLACKLIST_CLEAN_INTERVAL > 0L) {
            ThreadPoolManager.getInstance().scheduleAtFixedRate(this::Z, Config.BLACKLIST_CLEAN_INTERVAL, Config.BLACKLIST_CLEAN_INTERVAL);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isIpBanned(String string) {
        if (Config.BLACK_IPS.contains(string) || this.e.contains(string)) {
            return true;
        }
        if (Config.WHITE_IPS.contains(string)) {
            return false;
        }
        this.e.lock();
        try {
            IpSession ipSession = this.r.get(string);
            if (ipSession == null) {
                boolean bl = false;
                return bl;
            }
            boolean bl = ipSession.banExpire > System.currentTimeMillis();
            return bl;
        }
        finally {
            this.e.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean tryLogin(String string, boolean bl) {
        if (Config.WHITE_IPS.contains(string)) {
            return true;
        }
        this.f.lock();
        try {
            long l;
            IpSession ipSession = this.r.get(string);
            if (ipSession == null) {
                ipSession = new IpSession();
                this.r.put(string, ipSession);
            }
            if ((l = System.currentTimeMillis()) - ipSession.lastTry < Config.LOGIN_TRY_TIMEOUT) {
                bl = false;
            }
            if (bl) {
                if (ipSession.tryCount > 0) {
                    --ipSession.tryCount;
                }
            } else if (ipSession.tryCount < Config.LOGIN_TRY_BEFORE_BAN) {
                ++ipSession.tryCount;
            }
            ipSession.lastTry = l;
            if (ipSession.tryCount == Config.LOGIN_TRY_BEFORE_BAN) {
                M.warn("IpBanManager: " + string + " banned for " + Config.IP_BAN_TIME / 1000L + " seconds.");
                ipSession.banExpire = l + Config.IP_BAN_TIME;
                boolean bl2 = false;
                return bl2;
            }
            boolean bl3 = true;
            return bl3;
        }
        finally {
            this.f.unlock();
        }
    }

    private void Z() {
        if (!this.e.isEmpty()) {
            int n = this.e.size();
            this.e.clear();
            M.info("Removed " + n + " Blacklisted IP(s).");
        }
    }

    private class IpSession {
        public int tryCount;
        public long lastTry;
        public long banExpire;

        private IpSession() {
        }
    }
}
