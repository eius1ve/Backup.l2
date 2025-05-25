/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2.c2s;

import l2.authserver.Config;
import l2.authserver.GameServerManager;
import l2.authserver.accounts.Account;
import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.ProxyServer;
import l2.authserver.network.l2.L2LoginClient;
import l2.authserver.network.l2.SessionKey;
import l2.authserver.network.l2.c2s.L2LoginClientPacket;
import l2.authserver.network.l2.s2c.LoginFail;
import l2.authserver.network.l2.s2c.PlayOk;

public class RequestServerLogin
extends L2LoginClientPacket {
    private int dU;
    private int dV;
    private int dW;

    @Override
    protected void readImpl() {
        this.dU = this.readD();
        this.dV = this.readD();
        this.dW = this.readC();
    }

    @Override
    protected void runImpl() {
        ProxyServer proxyServer;
        L2LoginClient l2LoginClient = (L2LoginClient)this.getClient();
        SessionKey sessionKey = l2LoginClient.getSessionKey();
        if (sessionKey == null || Config.REQUIRE_EULA && !sessionKey.checkLoginPair(this.dU, this.dV)) {
            l2LoginClient.close(LoginFail.LoginFailReason.REASON_ACCESS_FAILED);
            return;
        }
        Account account = l2LoginClient.getAccount();
        GameServer gameServer = GameServerManager.getInstance().getGameServerById(this.dW);
        if (gameServer == null && (proxyServer = GameServerManager.getInstance().getProxyServerById(this.dW)) != null) {
            gameServer = GameServerManager.getInstance().getGameServerById(proxyServer.getOrigServerId());
        }
        if (gameServer == null || !gameServer.isAuthed() || gameServer.getOnline() >= gameServer.getMaxPlayers() && account.getAccessLevel() < 50) {
            l2LoginClient.close(LoginFail.LoginFailReason.REASON_ACCESS_FAILED);
            return;
        }
        if (gameServer.isGmOnly() && account.getAccessLevel() < 100) {
            l2LoginClient.close(LoginFail.LoginFailReason.REASON_SERVER_MAINTENANCE);
            return;
        }
        account.setLastServer(this.dW);
        account.update();
        l2LoginClient.close(new PlayOk(sessionKey));
    }
}
