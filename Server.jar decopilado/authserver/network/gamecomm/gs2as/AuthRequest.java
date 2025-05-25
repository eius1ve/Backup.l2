/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.gamecomm.gs2as;

import l2.authserver.GameServerManager;
import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.ReceivablePacket;
import l2.authserver.network.gamecomm.as2gs.AuthResponse;
import l2.authserver.network.gamecomm.as2gs.LoginServerFail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthRequest
extends ReceivablePacket {
    private static final Logger W = LoggerFactory.getLogger(AuthRequest.class);
    private int dI;
    private int dB;
    private boolean am;
    private String aD;
    private String aE;
    private int dJ;
    private int dq;
    private int dr;
    private boolean an;
    private boolean ao;
    private boolean ap;
    private int[] ak;
    private int dp;

    @Override
    protected void readImpl() {
        this.dI = this.readD();
        this.dB = this.readC();
        this.am = this.readC() == 1;
        this.dq = this.readD();
        this.dr = this.readD();
        this.an = this.readC() == 1;
        this.ao = this.readC() == 1;
        this.ap = this.readC() == 1;
        this.aD = this.readS();
        this.aE = this.readS();
        this.ak = new int[this.readH()];
        for (int i = 0; i < this.ak.length; ++i) {
            this.ak[i] = this.readH();
        }
        this.dJ = this.readD();
        this.dp = this.readH();
    }

    @Override
    protected void runImpl() {
        W.info("Trying to register gameserver: " + this.dB + " [" + this.getGameServer().getConnection().getIpAddress() + "]");
        int n = 0;
        GameServer gameServer = this.getGameServer();
        if (GameServerManager.getInstance().registerGameServer(this.dB, gameServer)) {
            gameServer.setPorts(this.ak);
            gameServer.setExternalHost(this.aD);
            gameServer.setInternalHost(this.aE);
            gameServer.setMaxPlayers(this.dJ);
            gameServer.setPvp(this.ap);
            gameServer.setServerType(this.dq);
            gameServer.setShowingBrackets(this.ao);
            gameServer.setGmOnly(this.an);
            gameServer.setAgeLimit(this.dr);
            gameServer.setProtocol(this.dI);
            gameServer.setAuthed(true);
            gameServer.getConnection().startPingTask();
            gameServer.setHaProxyPort(this.dp);
        } else if (this.am) {
            gameServer = this.getGameServer();
            if (GameServerManager.getInstance().registerGameServer(gameServer)) {
                gameServer.setPorts(this.ak);
                gameServer.setExternalHost(this.aD);
                gameServer.setInternalHost(this.aE);
                gameServer.setMaxPlayers(this.dJ);
                gameServer.setPvp(this.ap);
                gameServer.setServerType(this.dq);
                gameServer.setShowingBrackets(this.ao);
                gameServer.setGmOnly(this.an);
                gameServer.setAgeLimit(this.dr);
                gameServer.setProtocol(this.dI);
                gameServer.setAuthed(true);
                gameServer.getConnection().startPingTask();
                gameServer.setHaProxyPort(this.dp);
            } else {
                n = 5;
            }
        } else {
            n = 4;
        }
        if (n != 0) {
            W.info("Gameserver registration failed.");
            this.sendPacket(new LoginServerFail(n));
            return;
        }
        W.info("Gameserver registration successful.");
        this.sendPacket(new AuthResponse(gameServer));
    }
}
