/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver.network.gamecomm.gs2as;

import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.ReceivablePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingResponse
extends ReceivablePacket {
    private static final Logger X = LoggerFactory.getLogger(PingResponse.class);
    private long al;

    @Override
    protected void readImpl() {
        this.al = this.readQ();
    }

    @Override
    protected void runImpl() {
        GameServer gameServer = this.getGameServer();
        if (!gameServer.isAuthed()) {
            return;
        }
        gameServer.getConnection().onPingResponse();
        long l = System.currentTimeMillis() - this.al;
        if (Math.abs(l) > 999L) {
            X.warn("Gameserver " + gameServer.getId() + " [" + gameServer.getName() + "] : time offset " + l + " ms.");
        }
    }
}
