/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.c2s;

import java.util.Collections;
import javax.crypto.Cipher;
import l2.authserver.ClientManager;
import l2.authserver.Config;
import l2.authserver.GameServerManager;
import l2.authserver.IpBanManager;
import l2.authserver.accounts.Account;
import l2.authserver.accounts.SessionManager;
import l2.authserver.crypt.PasswordHash;
import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.as2gs.NotifyWhitelistedIp;
import l2.authserver.network.l2.L2LoginClient;
import l2.authserver.network.l2.c2s.L2LoginClientPacket;
import l2.authserver.network.l2.s2c.LoginFail;
import l2.authserver.utils.Log;

public class RequestAuthLogin
extends L2LoginClientPacket {
    private byte[] e;
    private byte[] f = new byte[16];
    private int dL;
    private int dM;
    private int dN;
    private int dO;
    private int dP;
    private int dQ;
    private int dR;
    private int dS;

    @Override
    protected void readImpl() {
        if (this.getAvaliableBytes() < 171) {
            return;
        }
        this.e = new byte[128 * (Math.max(0, this.getAvaliableBytes() - 43) / 128)];
        this.readB(this.e);
        this.dL = this.readD();
        this.dM = this.readD();
        this.dN = this.readD();
        this.dO = this.readD();
        this.dP = this.readD();
        this.dQ = this.readD();
        this.dR = this.readH();
        this.dS = this.readC();
        this.readB(this.f);
    }

    @Override
    protected void runImpl() throws Exception {
        boolean bl;
        String string;
        Object object;
        byte[] byArray;
        L2LoginClient l2LoginClient = (L2LoginClient)this.getClient();
        if (Config.ENABLE_CMD_LINE_LOGIN && Config.ONLY_CMD_LINE_LOGIN) {
            return;
        }
        if (this.dQ != 8 || l2LoginClient.getSessionId() != this.dL) {
            l2LoginClient.close(LoginFail.LoginFailReason.REASON_NO_MESSAGE);
            return;
        }
        try {
            byArray = new byte[this.e.length];
            object = Cipher.getInstance("RSA/ECB/nopadding");
            ((Cipher)object).init(2, l2LoginClient.getLoginCrypt().getRSAPrivateKey());
            for (int i = 0; i < this.e.length; i += 128) {
                ((Cipher)object).doFinal(this.e, i, 128, byArray, i);
            }
        }
        catch (Exception exception) {
            l2LoginClient.closeNow(true);
            return;
        }
        switch (byArray.length / 128) {
            case 1: {
                object = new String(byArray, 94, 14).trim().toLowerCase();
                string = new String(byArray, 108, 16).trim();
                break;
            }
            case 2: {
                object = new String(byArray, 78, 50).trim().toLowerCase();
                string = new String(byArray, 220, 32).trim();
                break;
            }
            default: {
                l2LoginClient.close(LoginFail.LoginFailReason.REASON_NO_MESSAGE);
                return;
            }
        }
        int n = (int)(System.currentTimeMillis() / 1000L);
        Account account = new Account((String)object);
        account.restore();
        String string2 = Config.DEFAULT_CRYPT.encrypt(string);
        if (account.getPasswordHash() == null) {
            if (Config.AUTO_CREATE_ACCOUNTS && ((String)object).matches(Config.ANAME_TEMPLATE) && string.matches(Config.APASSWD_TEMPLATE)) {
                account.setPasswordHash(string2);
                account.save();
            } else {
                l2LoginClient.close(Config.IS_ENGLISH_SYSTEM ? LoginFail.LoginFailReason.REASON_ACCESS_FAILED : LoginFail.LoginFailReason.REASON_USER_OR_PASS_WRONG);
                return;
            }
        }
        if (!(bl = account.getPasswordHash().equalsIgnoreCase(string2))) {
            for (PasswordHash passwordHash : Config.LEGACY_CRYPT) {
                if (!passwordHash.compare(string, account.getPasswordHash())) continue;
                bl = true;
                account.setPasswordHash(string2);
                break;
            }
        }
        if (!IpBanManager.getInstance().tryLogin(l2LoginClient.getIpAddr(), bl)) {
            l2LoginClient.closeNow(false);
            return;
        }
        if (!bl) {
            l2LoginClient.close(Config.IS_ENGLISH_SYSTEM ? LoginFail.LoginFailReason.REASON_ACCESS_FAILED : LoginFail.LoginFailReason.REASON_USER_OR_PASS_WRONG);
            return;
        }
        if (account.getAccessLevel() < 0) {
            l2LoginClient.close(LoginFail.LoginFailReason.REASON_ACCESS_FAILED);
            return;
        }
        if (account.getBanExpire() > n) {
            l2LoginClient.close(LoginFail.LoginFailReason.REASON_ACCESS_FAILED);
            return;
        }
        if (!account.isAllowedIP(l2LoginClient.getIpAddr())) {
            l2LoginClient.close(LoginFail.LoginFailReason.REASON_RESTRICTED_IP);
            return;
        }
        account.setLastAccess(n);
        account.setLastIP(l2LoginClient.getIpAddr());
        Log.LogAccount(account);
        SessionManager.Session session = SessionManager.getInstance().openSession(account);
        session.getSessionKey().hwid = this.f;
        l2LoginClient.setAuthed(true);
        l2LoginClient.setLogin((String)object);
        l2LoginClient.setAccount(account);
        l2LoginClient.setSessionKey(session.getSessionKey());
        l2LoginClient.setState(L2LoginClient.LoginClientState.AUTHED);
        int n2 = ClientManager.getInstance().addClient(l2LoginClient);
        for (GameServer gameServer : GameServerManager.getInstance().getGameServers()) {
            if (!gameServer.isOnline()) continue;
            gameServer.sendPacket(new NotifyWhitelistedIp(n2, Collections.singletonList(l2LoginClient.getIpAddr()), Collections.emptyList()));
        }
    }
}
