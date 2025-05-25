/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Shutdown;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.authcomm.SessionKey;
import l2.gameserver.network.authcomm.gs2as.PlayerAuthRequest;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.s2c.LoginFail;
import l2.gameserver.network.l2.s2c.ServerClose;

public class AuthLogin
extends L2GameClientPacket {
    private String dS;
    private int pR;
    private int pS;
    private int pT;
    private int pU;
    private int pV;
    private long cQ;
    private int pW;

    @Override
    protected void readImpl() {
        this.dS = this.readS(32).toLowerCase();
        this.pS = this.readD();
        this.pR = this.readD();
        this.pT = this.readD();
        this.pU = this.readD();
        this.pV = this.readD();
        this.cQ = this.readQ();
        this.pW = this.readD();
    }

    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        SessionKey sessionKey = new SessionKey(this.pT, this.pU, this.pR, this.pS);
        gameClient.setSessionId(sessionKey);
        gameClient.setLoginName(this.dS);
        if (gameClient.getRevision() == 0 || Shutdown.getInstance().getMode() != -1 && Shutdown.getInstance().getSeconds() <= 15) {
            gameClient.closeNow(false);
        } else {
            if (AuthServerCommunication.getInstance().isShutdown()) {
                gameClient.close(new LoginFail(LoginFail.SYSTEM_ERROR_LOGIN_LATER));
                return;
            }
            GameClient gameClient2 = AuthServerCommunication.getInstance().addWaitingClient(gameClient);
            if (gameClient2 != null) {
                gameClient2.close(ServerClose.STATIC);
            }
            AuthServerCommunication.getInstance().sendPacket(new PlayerAuthRequest(gameClient));
        }
    }
}
