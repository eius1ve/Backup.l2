/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.gamecomm.gs2as;

import l2.authserver.network.gamecomm.GameServer;
import l2.authserver.network.gamecomm.ReceivablePacket;

public class OnlineStatus
extends ReceivablePacket {
    private boolean aq;

    @Override
    protected void readImpl() {
        this.aq = this.readC() == 1;
    }

    @Override
    protected void runImpl() {
        GameServer gameServer = this.getGameServer();
        if (!gameServer.isAuthed()) {
            return;
        }
        gameServer.setOnline(this.aq);
    }
}
