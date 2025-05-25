/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.l2;

import java.io.IOException;
import java.nio.ByteBuffer;
import l2.authserver.accounts.Account;
import l2.authserver.crypt.LoginCrypt;
import l2.authserver.network.l2.BaseLoginClient;
import l2.authserver.network.l2.SessionKey;
import l2.authserver.network.l2.c2s.AuthGameGuard;
import l2.authserver.network.l2.c2s.L2LoginClientPacket;
import l2.authserver.network.l2.c2s.RequestAuthLogin;
import l2.authserver.network.l2.c2s.RequestCmdLogin;
import l2.authserver.network.l2.c2s.RequestPIAgreement;
import l2.authserver.network.l2.c2s.RequestServerList;
import l2.authserver.network.l2.c2s.RequestServerLogin;
import l2.authserver.network.l2.s2c.AccountKicked;
import l2.authserver.network.l2.s2c.L2LoginServerPacket;
import l2.authserver.network.l2.s2c.LoginFail;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.ReceivablePacket;
import l2.commons.net.nio.impl.SendablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class L2LoginClient
extends BaseLoginClient<L2LoginClient> {
    private static final Logger Y = LoggerFactory.getLogger(L2LoginClient.class);
    private LoginClientState a;
    private LoginCrypt a = LoginClientState.CONNECTED;
    private String aJ;
    private SessionKey b;
    private Account b;

    public L2LoginClient(MMOConnection<L2LoginClient> mMOConnection) {
        super(mMOConnection);
        this._loginClient = this;
    }

    public LoginCrypt getLoginCrypt() {
        return this.a;
    }

    public L2LoginClient setLoginCrypt(LoginCrypt loginCrypt) {
        this.a = loginCrypt;
        return this;
    }

    @Override
    public ReceivablePacket<L2LoginClient> handlePacket(ByteBuffer byteBuffer, L2LoginClient l2LoginClient) {
        int n = byteBuffer.get() & 0xFF;
        L2LoginClientPacket l2LoginClientPacket = null;
        switch (this.getState()) {
            case CONNECTED: {
                if (n != 7) break;
                l2LoginClientPacket = new AuthGameGuard();
                break;
            }
            case AUTHED_GG: {
                if (n == 0) {
                    l2LoginClientPacket = new RequestAuthLogin();
                    break;
                }
                if (n != 11) break;
                l2LoginClientPacket = new RequestCmdLogin();
                break;
            }
            case AUTHED: {
                if (n == 5) {
                    l2LoginClientPacket = new RequestServerList();
                    break;
                }
                if (n == 2) {
                    l2LoginClientPacket = new RequestServerLogin();
                    break;
                }
                if (n != 15) break;
                l2LoginClientPacket = new RequestPIAgreement();
            }
        }
        return l2LoginClientPacket;
    }

    @Override
    public boolean decrypt(ByteBuffer byteBuffer, int n) {
        boolean bl;
        try {
            bl = this.a.decrypt(byteBuffer, byteBuffer.position(), n);
        }
        catch (IOException iOException) {
            Y.error("Decrypt exception", (Throwable)iOException);
            this.closeNow(true);
            return false;
        }
        if (!bl) {
            this.closeNow(true);
        }
        return bl;
    }

    @Override
    public boolean encrypt(ByteBuffer byteBuffer, int n) {
        int n2 = byteBuffer.position();
        try {
            n = this.a.encrypt(byteBuffer, n2, n);
        }
        catch (IOException iOException) {
            Y.error("Encrypt exception", (Throwable)iOException);
            return false;
        }
        byteBuffer.position(n2 + n);
        return true;
    }

    public LoginClientState getState() {
        return this.a;
    }

    public void setState(LoginClientState loginClientState) {
        this.a = loginClientState;
    }

    public String getLogin() {
        return this.aJ;
    }

    public void setLogin(String string) {
        this.aJ = string;
    }

    public Account getAccount() {
        return this.b;
    }

    public void setAccount(Account account) {
        this.b = account;
    }

    public SessionKey getSessionKey() {
        return this.b;
    }

    public void setSessionKey(SessionKey sessionKey) {
        this.b = sessionKey;
    }

    public int getSessionId() {
        return this.getLoginCrypt().getCookieId();
    }

    public void sendPacket(L2LoginServerPacket l2LoginServerPacket) {
        if (this.isConnected()) {
            ((MMOConnection)((Object)this.getConnection())).sendPacket((SendablePacket<L2LoginClient>)l2LoginServerPacket);
        }
    }

    public void close(LoginFail.LoginFailReason loginFailReason) {
        if (this.isConnected()) {
            ((MMOConnection)((Object)this.getConnection())).close(new LoginFail(loginFailReason));
        }
    }

    public void close(AccountKicked.AccountKickedReason accountKickedReason) {
        if (this.isConnected()) {
            ((MMOConnection)((Object)this.getConnection())).close(new AccountKicked(accountKickedReason));
        }
    }

    public void close(L2LoginServerPacket l2LoginServerPacket) {
        if (this.isConnected()) {
            ((MMOConnection)((Object)this.getConnection())).close(l2LoginServerPacket);
        }
    }

    @Override
    public void onDisconnection() {
        this.a = LoginClientState.DISCONNECTED;
        this.b = null;
        this.a = null;
    }

    @Override
    public String toString() {
        switch (this.a) {
            case AUTHED: {
                return "[ Account : " + this.getLogin() + " IP: " + this.getIpAddr() + "]";
            }
        }
        return "[ State : " + this.getState() + " IP: " + this.getIpAddr() + "]";
    }

    @Override
    protected void onForcedDisconnection() {
    }

    public static final class LoginClientState
    extends Enum<LoginClientState> {
        public static final /* enum */ LoginClientState CONNECTED = new LoginClientState();
        public static final /* enum */ LoginClientState AUTHED_GG = new LoginClientState();
        public static final /* enum */ LoginClientState AUTHED = new LoginClientState();
        public static final /* enum */ LoginClientState DISCONNECTED = new LoginClientState();
        private static final /* synthetic */ LoginClientState[] a;

        public static LoginClientState[] values() {
            return (LoginClientState[])a.clone();
        }

        public static LoginClientState valueOf(String string) {
            return Enum.valueOf(LoginClientState.class, string);
        }

        private static /* synthetic */ LoginClientState[] a() {
            return new LoginClientState[]{CONNECTED, AUTHED_GG, AUTHED, DISCONNECTED};
        }

        static {
            a = LoginClientState.a();
        }
    }
}
