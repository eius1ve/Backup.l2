/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.accounts;

import l2.authserver.accounts.Account;
import l2.authserver.network.l2.SessionKey;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class SessionManager.Session {
    private final Account a;
    private final SessionKey a;
    private final long ak;

    private SessionManager.Session(Account account) {
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
